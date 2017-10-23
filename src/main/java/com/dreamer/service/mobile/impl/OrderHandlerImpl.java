package com.dreamer.service.mobile.impl;

import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.order.*;
import com.dreamer.domain.pmall.shopcart.ShopCart;
import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.repository.mobile.OrderDao;
import com.dreamer.repository.mobile.OrderItemDao;
import com.dreamer.service.mobile.*;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.CommonUtil;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.util.XmlUtil;
import net.sf.json.JSONObject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.SearchParameter;

import java.util.*;

/**
 * Created by huangfei on 11/07/2017.
 */
@Service
public class OrderHandlerImpl extends BaseHandlerImpl<Order> implements OrderHandler {


    @Override
    @Transactional
    public Order commitOrder(Agent agent, AddressClone addressClone, ShopCart shopCart, String remark) {
        Order order = new Order(agent, addressClone, remark);
        Set<OrderItem> items = bulidItems(order, shopCart);
        order.setItems(items);
        order.setAmount(shopCart.getAmount());
        order.setQuantity(shopCart.getQuantity());
        //提交订单
        commitOrder(order);
        //保存订单
        order = save(order);
        return order;
    }


    //生成payOpenid  并且去支付
    @Override
    @Transactional
    public String resetOpenIdToPay(String code, String orderId) {
        Order order = get(Integer.valueOf(orderId));
        if (order == null) {
            LOG.error("获取order为空,{}", orderId);
            throw new ApplicationException("订单不存在！请联系管理员！");
        }
        Agent agent = agentHandler.get(order.getUser().getId());
        WxConfig wxConfig = wxConfigFactory.getPayConfig();
        SdkResult sdkResult = JSAPI.getTokenAndOpenId(wxConfig.getAppid(), wxConfig.getSecret(), code);
        if (sdkResult.isSuccess()) {//获取openId成功了  就去支付
            JSONObject jsonObject = (JSONObject) sdkResult.getData();
            try {
                if (agent.getPayOpenid() == null || !agent.getPayOpenid().equals(jsonObject.getString("openid"))) {
                    agent.setPayOpenid(jsonObject.getString("openid"));
                    agentHandler.merge(agent);
                }
            } catch (Exception e) {
                System.out.println("异常:" + e.getMessage());
            }
            String jsApiParam = payHandler.toWxPay(wxConfig, agent.getPayOpenid(), order.getOrderNo(), order.getAmount(),WxConfig.PMALL_NOTICE_URL);
            return jsApiParam;
        } else {
            LOG.error("获取payOpenid失败:{}", sdkResult.getError());
            throw new ApplicationException("获取获取payOpenid失败失败ERROR:" + sdkResult.getError());
        }
    }

    //支付
    @Transactional
    private void payOrder(Integer id, PaymentWay paymentWay) {
        Order order = get(id);
        //支付状态
        payOrder(order, paymentWay);
        //减库存
        deductMallGoodsAccount(order);
        //增加消费
//        AccountsRecord accountsRecord = accountsHandler.increaseAccountAndRecord(AccountsType.CONSUME,order.getUser(),order.getUser(),order.getAmount(),"累积-优惠商城购物累积消费值");
        AccountsRecord accountsRecord = new AccountsRecord();
        //返利
        List<AccountsRecord> records = rewardVoucher(order);
        //保存返利记录
        accountsRecordHandler.saveList(records);
        //累积消费记录
        accountsRecordHandler.save(accountsRecord);
        //通知返利
        noticeHandler.noticeAccountRecords(records);
        //保存
        merge(order);
    }

    //支付 //接受微信支付回调 并支付  返回支付结果
    @Override
    @Transactional
    public String payOrderByWx(String body) {
        Map<String, String> xml = XmlUtil.xmlToMap(body);
        String out_trade_no = xml.get("out_trade_no");
        Order order = get("orderNo", out_trade_no);
        try {
            payOrder(order.getId(), PaymentWay.WX);//微信支付
        } catch (Exception e) {
            LOG.error("微信支付回调支付异常,{}", e.getMessage());
            LOG.error("订单id,{}", order.getId());
            payOrderWithError(order.getId(), PaymentWay.WX);
        }
        //返回支付结果
        Map<String, Object> map = new HashMap();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        String result = XmlUtil.mapToXml(map);
        return result;
    }


    //管理员支付
    @Override
    @Transactional
    public void payOrderByAdmin(Integer id) {
        payOrder(id, PaymentWay.ADMIN);
    }


    private void delivery(Order order) {
        //设置状态
        order.setStatus(OrderStatus.SHIPPED);
        order.setShippingTime(new Date());
    }

    @Transactional
    private void payOrderWithError(Integer id, PaymentWay paymentWay) {
        Order order = get(id);
//        payOrder(order, paymentWay);
        order.setPaymentWay(paymentWay);
        order.setPaymentStatus(PaymentStatus.PAIDERROR);
        order.setPaymentTime(new Date());
        merge(order);
    }

    private void payOrder(Order order, PaymentWay paymentWay) {
        if (order == null) {
            logger.error("订单不存在id:{}", order.getId());
            throw new ApplicationException("订单不存在!id:" + order.getId());
        }
        if (hasPaid(order)) {
            logger.error("订单已经支付!");
            throw new ApplicationException("订单已经支付!id:" + order.getId());

        }
        order.setPaymentStatus(PaymentStatus.PAID);//设置已经支付
        order.setPaymentTime(new Date());
        order.setPaymentWay(paymentWay);
    }

    //扣减账户
    private AccountsRecord deductAccounts(Order order, AccountsType accountsType) {
        if (accountsType == AccountsType.ADVANCE) {//预存款支付只能支付预存款类型的
            if (!order.getCanAdvance()) {
                throw new ApplicationException("包含不能预存款支付的产品!");
            }
        }
        String more = "购物-优惠商城购物支付";
        AccountsRecord accountsRecord = accountsHandler.deductAccountAndRecord(accountsType, order.getUser(), order.getUser(), order.getAmount(), more);
        return accountsRecord;
    }


    //是否已经支付
    private boolean hasPaid(Order order) {
        if (order.getPaymentStatus() == PaymentStatus.UNPAID) {
            return false;
        }
        return true;
    }

    //是否已经发货
    private boolean hasDelivery(Order order) {
        if (order.getStatus().equals(OrderStatus.NEW)) {
            return false;
        }
//        System.out.println(order.getLogistics()==null);
        return true;
    }

    @Override
    @Transactional
    public void payOrderByAccounts(Integer id, Integer payOrderByAccounts) {
        AccountsType accountsType = AccountsType.stateOf(payOrderByAccounts);
        Order order = get(id);
        //减少库存
        deductAccounts(order, accountsType);
        PaymentWay paymentWay;
        //设置为支付状态
        if (accountsType == AccountsType.ADVANCE) {//预存款
            paymentWay = PaymentWay.ADVANCE;
        } else {//奖金
            paymentWay = PaymentWay.VOUCHER;
        }
        List<AccountsRecord> records = new ArrayList<>();
        //减少奖金
        records.add(deductAccounts(order, accountsType));
        //增加消费
//        AccountsRecord accountsRecord = accountsHandler.increaseAccountAndRecord(AccountsType.CONSUME,order.getUser(),order.getUser(),order.getAmount(),"累积-优惠商城购物累积消费值");
        AccountsRecord accountsRecord = new AccountsRecord();
        //支付状态
        payOrder(order, paymentWay);
        //返利
        records.addAll(rewardVoucher(order));
        //保存
        merge(order);
        //保存奖金记录
        accountsRecordHandler.saveList(records);
        //累积消费记录
        accountsRecordHandler.save(accountsRecord);
        //通知通知
        noticeHandler.noticeAccountRecords(records);
    }


    @Override
    public List<Order> findOrders(SearchParameter<Order> p) {
        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
        Example example = Example.create(p.getEntity()).enableLike(MatchMode.ANYWHERE);
        dc.add(example);
        //下单人搜索
        if (p.getEntity().getUser() != null) {
            DetachedCriteria user = dc.createCriteria("user");
            Example example2 = Example.create(p.getEntity().getUser());
            user.add(example2);
//            addRestraction(user,"realName",p.getEntity().getUser().getRealName());
//            addRestraction(user,"agentCode",p.getEntity().getUser().getAgentCode());
        }
        //地址条件
        AddressClone addressClone = p.getEntity().getAddressClone();
        if (addressClone != null) {
            DetachedCriteria address = dc.createCriteria("addressClone");
            Example example1 = Example.create(p.getEntity().getAddressClone()).enableLike(MatchMode.ANYWHERE);
            address.add(example1);
//            addRestraction(address, "mobile", addressClone.getMobile());
//            addRestraction(address, "consignee", addressClone.getConsignee());
        }
        //时间搜索
        if (p.getStartTime() != null && p.getEndTime() != null) {
            dc.add(Restrictions.between("orderTime", p.getStartTimeByDate(), p.getEndTimeByDate()));
        }
        dc.addOrder(org.hibernate.criterion.Order.desc("id"));
        return orderDao.searchByPage(p, dc);
    }


    @Override
    public List<Order> findOrdersWithChildren(SearchParameter<Order> p) {
        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
        Example example = Example.create(p.getEntity()).enableLike(MatchMode.ANYWHERE);
        dc.add(example);
        //下单人搜索
        if (p.getEntity().getUser() != null) {
            DetachedCriteria user = dc.createCriteria("user");
//            Example example2 = Example.create(p.getEntity().getUser());
//            user.add(example2);
//            addRestraction(user,"realName",p.getEntity().getUser().getRealName());
//            addRestraction(user,"agentCode",p.getEntity().getUser().getAgentCode());
            //自己的或者用户的
            user.add(Restrictions.or(Restrictions.eq("id", p.getEntity().getUser().getId()), Restrictions.eq("parent.id", p.getEntity().getUser().getId())));
        }

        //时间搜索
        if (p.getStartTime() != null && p.getEndTime() != null) {
            dc.add(Restrictions.between("orderTime", p.getStartTimeByDate(), p.getEndTimeByDate()));
        }
        dc.addOrder(org.hibernate.criterion.Order.desc("id"));
        return orderDao.searchByPage(p, dc);
    }


    //提交订单
    private void commitOrder(Order order) {
        order.setUpdateTime(new Date());
        order.setOrderTime(new Date());
        order.setOrderNo(CommonUtil.createNo());
        order.setPaymentStatus(PaymentStatus.UNPAID);//设置成未支付
        order.setStatus(OrderStatus.NEW);//设置为新下单
    }

    private Set<OrderItem> bulidItems(Order order, ShopCart shopCart) {
        Set<OrderItem> items = new HashSet<>();
        shopCart.getItems().values().stream().forEach(p -> {
            //限制购买数量
            validateActitivty(p.getGoods(), order.getUser(), p.getQuantity());
            OrderItem item = new OrderItem();
            item.setPmallOrder(order);
            item.setPmallGoods(p.getGoods());
            item.setPrice(p.getGoods().getRetailPrice());
            item.setQuantity(p.getQuantity());
            Set<GoodsStandard> goodsStandards = new HashSet<>();
            for (GoodsStandard gs : p.getGoodsStandards()) {
                GoodsStandard tem = goodsStandardHandler.get(gs.getId());
                goodsStandards.add(tem);
            }
            item.setStandards(goodsStandards);
            if (!item.getPmallGoods().getCanAdvance()) {//存在一款不能奖金的
                order.setCanAdvance(false);//就都不能奖金
            }
            items.add(item);
        });
        return items;
    }


    /**
     * @param pmallGoods 购买产品
     * @param agent      购买人
     * @param quantity   购买数量
     */
    private void validateActitivty(PmallGoods pmallGoods, Agent agent, Integer quantity) {
        if (pmallGoods.getActivity() == false) return;
        List<OrderItem> orderItems = orderItemDao.findOrderItemByGoodsAndUserAndTime(pmallGoods.getId(), agent.getId(), pmallGoods.getStartTime());
        Integer sumQuantity = 0;
        for (OrderItem item : orderItems) {
            sumQuantity += item.getQuantity();
        }
        if (sumQuantity + quantity > pmallGoods.getLimitNumer()) {
            throw new ApplicationException("提交失败," + pmallGoods.getName() + "限购" + pmallGoods.getLimitNumer());
        }
    }

    //获取返利明细
    private HashMap<Agent, Double> getAgentsWithVoucher(Order order) {
        HashMap<Agent, Double> maps = new HashMap<>();
        Agent agent = order.getUser();
        //首先找出能返利的代理
        List<Agent> parents = new ArrayList<>();
        Agent parent = agent.getParent();
        while (parent != null && !parent.isMutedUser()) {
            if (agentHandler.canPmallReward(parent)) {
                parents.add(parent);//可以返利的上级
            }
            parent = parent.getParent();
        }
        order.getItems().forEach(item -> {
            CommonUtil.putAll(maps, accountsHandler.rewardPmallVoucher(parents, item.getQuantity(),item.getPmallGoods().getProfit()));
        });
        return maps;
    }


    //返利
    private List<AccountsRecord> rewardVoucher(Order order) {
        List<AccountsRecord> accountsRecords = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("利润--");
        sb.append(order.getUser().getRealName()).append("优惠商城购买:");
        order.getItems().forEach(p -> {
            if (p.getPmallGoods().getName().contains("避孕")) {
                sb.append("******");
            } else {
                sb.append(p.getPmallGoods().getName());
            }
            sb.append("X").append(p.getQuantity()).append("  ");
        });
        Map<Agent, Double> map = getAgentsWithVoucher(order);
        for (Agent agent : map.keySet()) {
            accountsRecords.add(accountsHandler.increaseAccountAndRecord(AccountsType.VOUCHER, agent, order.getUser(), map.get(agent), sb.toString()));
        }
        return accountsRecords;
    }


    /**
     * 减少产品库存
     *
     * @param order
     */
    private void deductMallGoodsAccount(Order order) {
        order.getItems().forEach(p -> {
            //减少总库存
            mallGoodsHandler.deductMallGoodsAccount(p.getPmallGoods(), p.getQuantity());
            //减少单品库存
            p.getStandards().forEach(st -> {
                st.deductCurrentStock(p.getQuantity());
            });
        });
    }


    //发货
    @Override
    @Transactional
    public void delivery(Integer id, String logistics, String logisticsCode, String operate) {
        Order order = get(id);
        if (hasDelivery(order)) {
            throw new ApplicationException("订单已经发货");
        }
        order.setLogisticsCode(logisticsCode);
        order.setLogistics(logistics);
        order.setShippingOperator(operate);
        order.setShippingTime(new Date());//发货时间
        order.setStatus(OrderStatus.SHIPPED);//已经发货
        merge(order);
        //通知
        noticeHandler.noticeOrder(order);
    }


    @Override
    public List<Order> findAllOrders(SearchParameter<Order> p) {

        return orderDao.findAllOrders(p);
    }

    @Override
    public List<Order> findNewOrders(Integer limit) {

        return orderDao.findNewOrders( limit);
    }

    //获取所有没有发货的订单总数
    @Override
    public List<Object[]> getOrdersItemCount(Integer limit) {
        return orderDao.getOrdersItemCount( limit);
    }

    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
        super.setBaseDao(orderDao);
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountsHandler accountsHandler;

    @Autowired
    private NoticeHandler noticeHandler;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private MallGoodsHandler mallGoodsHandler;

    @Autowired
    private GoodsStandardHandler goodsStandardHandler;

    @Autowired
    private WxConfigFactory wxConfigFactory;

    @Autowired
    private OrderItemDao orderItemDao;



    @Autowired
    private PayHandler payHandler;

    @Autowired
    private AccountsRecordHandler accountsRecordHandler;

    private Logger LOG = LoggerFactory.getLogger(this.getClass());
}
