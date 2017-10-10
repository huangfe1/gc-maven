package com.dreamer.service.pmall.order;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderItem;
import com.dreamer.domain.pmall.order.PaymentWay;
import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.User;
import com.dreamer.repository.mall.goods.MallGoodsDAO;
import com.dreamer.repository.pmall.goods.StandardDao;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.repository.pmall.order.OrderItemDAO;
import com.dreamer.service.mobile.AccountsHandler;
import com.dreamer.service.mobile.GoodsAccountHandler;
import com.dreamer.service.mobile.NoticeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderPayHandler {

    @Transactional
    public void pay(Order order, PaymentWay paymentWay, Double money) {
//        if (!order.paymentRequired()) {
//            throw new ApplicationException("此订单无需支付");
//        }
//        order.pay(paymentWay, money);
//        List<AccountsRecord> records = fireEvent(order);
//        deductGoodsStock(order);
//        orderDAO.merge(order);
//        //通知
//        noticeHandler.noticeAccountRecords(records);

    }


    /**
     * 减少库存的时候还要减少单品库存  这里可以抛出一个库存不足的异常  然后捕货
     *
     * @param order
     */
    @Transactional
    public void deductGoodsStock(Order order) {
//        order.getItems().entrySet().stream().forEach(entry -> {
//            PmallGoods goods = mallGoodsDAO.findById(entry.getKey());
//            if (goods.getStockQuantity() >= entry.getValue().getQuantity())//库存足够的时候在减少库存,有些偏差
//                goods.deductCurrentStock(entry.getValue().getQuantity());
//            entry.getValue().getStandards().keySet().forEach(s -> {
//                GoodsStandard goodsStandard = standardDao.findById(s);
//                //扣件库存
//                goodsStandard.deductCurrentStock(entry.getValue().getStandards().get(s));
////		        s.deductCurrentStock(entry.getValue().getQuantity());//减少单品库存
//            });
//        });
    }


    @Transactional
    public void back(OrderItem item) {
        orderItemDao.merge(item);
    }


    private List<AccountsRecord> fireEvent(Order order) {
//        List<AccountsRecord> records = new ArrayList<>();
//        //订单的每个货物都返利
//        order.getItems().values().stream().forEach(p -> records.addAll(fireVoucher(p, order.getUser())));
//        return records;
        return null;
    }

    /**
     * 返还代金券
     */
    private List<AccountsRecord> fireVoucher(OrderItem item, User user) {
//        List<AccountsRecord> records = new ArrayList<>();
//        StringBuilder more = new StringBuilder(user.getRealName());
//        more.append("奖金奖励-来自").append(user.getRealName()).append("在优惠商城购物!");
//        Double[] vs = getbackVouchers(item.getVouchers());//返利模式
//        Agent parent = user.getParent();//获取上家
//        if (vs.length > 3) {
//            Double tempSum = 0.0;//已经扣除的奖金
//            String[] vipNames = {AgentLevelName.联盟单位.toString(), AgentLevelName.分公司.toString(), AgentLevelName.金董.toString(), AgentLevelName.董事.toString()};
//            Map<String, Double> map = new HashedMap();
//            for (int i = 0; i < vipNames.length; i++) {//组装vip需要的奖金
//                map.put(vipNames[i], vs[i]);
//            }
//            int index = 0;
//            while (parent != null && !parent.isMutedUser()) {
//                Double voucher = 0.0;
//                if (parent.getAgentCode() != null && parent.getAgentCode() != "") {
//                    GoodsAccount main = goodsAccountHandler.getMainGoodsAccount(parent);
//                    String levelName = main.getAgentLevel().getName();
//                    if (map.containsKey(levelName)) {//如果包含当前级别
//                        Double cj = map.get(levelName) - tempSum;
//                        if (cj > 0) {//可以返利
//                            //差价+普通返利
//                            voucher = (cj + getVoucherFromIndex(vs, vipNames, index)) * item.getQuantity();
////                            parent.getAccounts().increaseVoucher(voucher, more.toString());
//                            tempSum += cj;//累积已经返了的利
//                        }
//                    } else {
//                        voucher = getVoucherFromIndex(vs, vipNames, index) * item.getQuantity();
//
////                        parent.getAccounts().increaseVoucher(voucher, more.toString());
//                    }
//                } else {
//                    voucher = getVoucherFromIndex(vs, vipNames, index) * item.getQuantity();
////                    parent.getAccounts().increaseVoucher(voucher, more.toString());
//                }
//
//                //返利
//                records.add(accountsHandler.increaseAccountAndRecord(AccountsType.VOUCHER, parent, (Agent) user, voucher, more.toString()));
//                index++;
//                parent = parent.getParent();
//            }
//        }
        return null ;
    }

    private Double getVoucherFromIndex(Double[] vs, String[] names, int index) {
        Integer start = names.length;
        if (start + index >= vs.length) return 0.0;
        return vs[start + index];
    }

    /**
     * 获取返利模式
     *
     * @return
     */
    public Double[] getbackVouchers(String str) {
        String[] strs = str.trim().split("_");
        Double[] temps = new Double[strs.length];
        for (int i = 0; i < temps.length; i++) {
            temps[i] = Double.parseDouble(strs[i]);
            strs[i] = null;//回收
        }
        return temps;
    }


    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private MallGoodsDAO mallGoodsDAO;
    @Autowired
    private OrderItemDAO orderItemDao;
    @Autowired
    private StandardDao standardDao;
    @Autowired
    private GoodsAccountHandler goodsAccountHandler;
    @Autowired
    private AccountsHandler accountsHandler;
    @Autowired
    private NoticeHandler noticeHandler;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
}
