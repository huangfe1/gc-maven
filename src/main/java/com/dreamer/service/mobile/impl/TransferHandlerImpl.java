package com.dreamer.service.mobile.impl;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.goods.*;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.mall.transfer.TransferItem;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.repository.mobile.AccountsRecordDao;
import com.dreamer.repository.mobile.AgentDao;
import com.dreamer.repository.mobile.GoodsDao;
import com.dreamer.repository.mobile.TransferDao;
import com.dreamer.service.mobile.*;
import com.dreamer.util.CommonUtil;
import com.dreamer.util.PreciseComputeUtil;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.SearchParameter;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class TransferHandlerImpl extends BaseHandlerImpl<Transfer> implements TransferHandler {
    /**
     * 返利
     *
     * @param transfer
     */
    private List<AccountsRecord> rewardVoucher(Transfer transfer) {
        List<AccountsRecord> records = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("利润--");
        sb.append(transfer.getToAgent().getRealName()).append("购买:");
        transfer.getItems().forEach(p -> {
            sb.append(p.getGoods().getName()).append("X").append(p.getQuantity()).append("  ");
        });
        String more = sb.toString();
        //获取整个的返利
        HashMap<Agent, Double> map = getAgentsWithVoucher(transfer.getToAgent(), transfer.getItems());
        for (Agent agent : map.keySet()) {
            //增加返利库存
            records.add(accountsHandler.increaseAccountAndRecord(AccountsType.VOUCHER, agent, transfer.getToAgent(), map.get(agent), more));
        }
        return records;
    }


    /**
     * 追回奖金
     *
     * @param transfer
     * @return
     */
    private List<AccountsRecord> backVoucher(Transfer transfer) {
        List<AccountsRecord> records = new ArrayList<>();
        StringBuffer goodsInfo = new StringBuffer();
        transfer.getItems().forEach(p -> {
            goodsInfo.append(p.getGoods().getName()).append("X").append(p.getQuantity()).append("  ");
        });
        //全部退奖金
        Double voucher = transfer.getAmount();
        StringBuffer backMy = new StringBuffer();
        backMy.append("退回--退货退回预存款，货物:");
        backMy.append(goodsInfo).append("  ");
        records.add(accountsHandler.increaseAccountAndRecord(AccountsType.ADVANCE, transfer.getFromAgent(), muteUserHandler.getMuteUser(), voucher, backMy.toString()));
        AccountsRecord record1 = accountsHandler.deductAccountAndRecord(AccountsType.BENEFIT, transfer.getFromAgent(), transfer.getFromAgent(), voucher, "退货减少-退货减少总业绩！");//增加消费数量 TODO
        records.add(record1);
        //追回上级奖金
        StringBuffer sb = new StringBuffer();
        sb.append("追回--");
        sb.append(transfer.getFromAgent().getRealName()).append("退货追回奖金，货物:");
        sb.append(goodsInfo.toString());
        HashMap<Agent, Double> map = getAgentsWithVoucher(transfer.getFromAgent(), transfer.getItems());
        for (Agent agent : map.keySet()) {//减少上级的奖金 不验证 可以为负数
            records.add(accountsHandler.deductAccountAndRecord(AccountsType.VOUCHER, agent, transfer.getFromAgent(), map.get(agent), sb.toString(), false));
        }
        return records;
    }


    /**
     * 核心返利算法
     * 获取某个订单可以返利的总数
     *
     * @param transfer
     * @return
     */
    private HashMap<Agent, Double> getAgentsWithVoucher(Agent agent, Set<TransferItem> items) {
        HashMap<Agent, Double> maps = new HashMap<>();
        Agent toAgent = agent;
        //首先找出能返利的代理
        List<Agent> parents = new ArrayList<>();
        Agent parent = toAgent.getParent();
        while (parent != null && !parent.isMutedUser()) {
            if (agentHandler.canReward(parent)) {
                parents.add(parent);//可以返利的上级
            }
            parent = parent.getParent();
        }
        //开始返利
        items.forEach(
                item -> {
                    //装载所有返利
                    Agent fAgent = agentHandler.findVip(toAgent);//分公司
                    Price price = priceHandler.getPrice(fAgent, item.getGoods());
                    CommonUtil.putAll(maps, accountsHandler.rewardVoucher(parents, price.getVoucherStr(), item.getQuantity()));
                }
        );

        return maps;
    }


    /**
     * 主动转出
     *
     * @param transfer
     */
    private void transfer(Transfer transfer) {
        transfer.setRemittance("主动转出");
        transfer.setStatus(GoodsTransferStatus.CONFIRM);//设置已完成状态
        //转出货物
        transferGoods(transfer);

    }

    /**
     * 申请退货
     *
     * @param transfer
     */
    private void applyBackTransfer(Transfer transfer) {
        if (!agentHandler.isVip(transfer.getFromAgent())) {//不是vip不能退货
            throw new ApplicationException("只有vip才可以退货！");
        }
        transfer.setRemittance("退货冻结");
        transfer.setStatus(GoodsTransferStatus.BACK);//设置已完成状态
        transfer.setApplyTime(new Date());
        sumAmount(transfer);
        sumQuantity(transfer);
        //转出货物
        transferGoods(transfer);
    }

    /**
     * 转出货物
     *
     * @param transfer
     */
    private void transferGoods(Transfer transfer) {
        Agent fromAgent = transfer.getFromAgent();
        Agent toAgent = transfer.getToAgent();
        GoodsAccount fromAgentMain = goodsAccountHandler.getMainGoodsAccount(fromAgent);
        GoodsAccount toAgentMain = goodsAccountHandler.getMainGoodsAccount(toAgent);

        transfer.setUpdateTime(new Date());//转货时间
        transfer.setOperator(fromAgent.getRealName());//操作员

        //拨库存
        transfer.getItems().stream().forEach(
                p -> {
                    //这里要判断是不是公司转出，或者公司转入
                    if (fromAgent.isMutedUser()) {//如果转出人是公司
                        goodsHandler.reduceBalacne(p.getGoods().getId(), p.getQuantity());//减少公司余额
                    } else {
                        GoodsAccount fromGoodsAccount = goodsAccountHandler.getGoodsAccount(fromAgent, p.getGoods(), fromAgentMain);
                        goodsAccountHandler.deductGoodsAccount(fromGoodsAccount, p.getQuantity());//减少库存
                    }

                    if (toAgent.isMutedUser()) {//如果接收方是公司
                        goodsHandler.addBalance(p.getGoods().getId(), p.getQuantity());//增加公司余额
                    } else {
                        GoodsAccount toGoodsAccount = goodsAccountHandler.getGoodsAccount(toAgent, p.getGoods(), toAgentMain);
                        goodsAccountHandler.increaseGoodsAccount(toGoodsAccount, p.getQuantity());//增加库存
                    }


                }
        );
    }

    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.setId(1);
        Agent agent1 = new Agent();
        agent1.setId(2);
        Map<Agent, Double> map = new HashedMap();
        map.put(agent1, 1.0);
        Map<Agent, Double> map1 = new HashedMap();
        map1.put(agent, 2.0);
        map1.put(agent1, 2.0);
        Map<Agent, Double> map2 = new HashedMap();
        CommonUtil.putAll(map2, map);
        CommonUtil.putAll(map2, map1);
        for (Double d : map2.values()) {
            System.out.println(d);
        }

    }


    /**
     * 找出某人的转账记录
     *
     * @param uid
     * @param toId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<Transfer> findTransferRecords(Integer uid, Integer toId, Date startDate, Date endDate, GoodsTransferStatus status) {
        DetachedCriteria dc = DetachedCriteria.forClass(Transfer.class);
        dc.add(Restrictions.eq("fromAgent.id", uid));
        dc.add(Restrictions.eq("toAgent.id", toId));
        if (startDate != null && endDate != null) {
            dc.add(Restrictions.between("updateTime", startDate, endDate));//时间
        }
        if (status != null) {//类别
            dc.add(Restrictions.eq("status", status));
        }
//       dc.add(Restrictions.like("updateTime", DateUtil.formatStr(startDate,"yyyy-MM-dd")));
        dc.addOrder(Order.desc("id"));
        List<Transfer> records = transferDao.findByCriteria(dc, null, null);
        //统计
        if (records == null) {
            return new ArrayList<>();
        }
        return records;
    }


    @Override
    public List<Transfer> findTransfers(Integer uid, Integer nid) {
        DetachedCriteria dc = DetachedCriteria.forClass(Transfer.class);
        if (nid == null) {
            //收货人或者转货人是我
            dc.add(Restrictions.or(Restrictions.eq("fromAgent.id", uid), Restrictions.eq("toAgent.id", uid)));
        } else {
            //根据id查找
            dc.add(Restrictions.eq("id", nid));
        }
        //时间倒序  查询30条
        dc.addOrder(Order.desc("id"));
        List<Transfer> items = transferDao.findByCriteria(dc, 0, 30);
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }


    /**
     * 初始化一个transfer
     *
     * @return
     */
    private Transfer initTransfer(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        Agent fromAgent = agentDao.get(fromUid);
        Agent toAgent = agentDao.get(toUid);
        //生成订单
        Transfer transfer = new Transfer(toAgent, fromAgent, new Timestamp(new Date().getTime()), remark);
        buildItems(transfer, goodsIds, amounts);//组装货物
        return transfer;
    }


    /**
     * 获取总金额
     *
     * @return
     */
    private Double sumAmount(Transfer transfer) {
        //计算费用 总金额
        Double amount = transfer.getItems().stream().mapToDouble(item -> item.getAmount()).sum();
        transfer.setAmount(amount);
        return PreciseComputeUtil.round(amount);
    }

    /**
     * 获取总货物
     *
     * @return
     */
    private Integer sumQuantity(Transfer transfer) {
        //计算费用 总金额
        Integer quantity = transfer.getItems().stream().mapToInt(item -> item.getQuantity()).sum();
        transfer.setQuantity(quantity);
        return quantity;
    }


    private void buildItems(Transfer transfer, Integer[] goodsIds, Integer[] amounts) {
        Agent toAgent = transfer.getToAgent();
        Agent fromAgent = transfer.getFromAgent();
        Set<TransferItem> items = new HashSet<>();
        TransferItem item;
        for (int i = 0; i < goodsIds.length; i++) {
            Goods goods = goodsDao.get(goodsIds[i]);
            Integer quantity = amounts[i];
            Price price;
            if (toAgent.isMutedUser()) {
                price = priceHandler.getPrice(fromAgent, goods);
            } else {
                price = priceHandler.getPrice(toAgent, goods);
            }

            item = new TransferItem(transfer, quantity, price.getPrice(), goods, price.getAgentLevel().getName());
            items.add(item);
        }
        transfer.setItems(items);
    }


    /**
     * 确认转货
     *
     * @param transfer
     */
    private void confirmTransfer(Transfer transfer) {
        transfer.setRemittance("订单已成交");
        transfer.setStatus(GoodsTransferStatus.CONFIRM);
        transferGoods(transfer);//转出货物
    }

    /**
     * 确认退货订单 退奖金  追回奖金  货物已经扣除不用管
     *
     * @param transfer
     */
    private List<AccountsRecord> confirmBackTransfer(Transfer transfer) {
        if (!transfer.getStatus().equals(GoodsTransferStatus.BACK)) {
            throw new ApplicationException("不可退货订单!当前订单状态为" + transfer.getStatus().getDesc());
        }
        //退奖金
        List<AccountsRecord> accountsRecord = backVoucher(transfer);

        //更新状态
        transfer.setRemittance("订单已退货!");
        transfer.setStatus(GoodsTransferStatus.CONFIRM);
        return accountsRecord;
    }

    /**
     * 主动转货
     *
     * @param fromUid
     * @param toUid
     * @param goodsIds
     * @param amounts
     * @param remark
     * @return
     */
    @Override
    @Transactional
    public void transfer(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        Transfer transfer = initTransfer(fromUid, toUid, goodsIds, amounts, remark);
        //提交订单
        applyTransfer(transfer);
        //主动转货
        transfer(transfer);
        //保存
        transfer = transferDao.merge(transfer);
        //通知 异步
        noticeHandler.noticeTransfer(transfer);
    }


    /**
     * 退货操作  冻结库存 先把库存转给公司  奖金先不返还  等待公司确认
     *
     * @param uid
     * @param goodsIds
     * @param amounts
     * @param remark
     */
    @Override
    @Transactional
    public void applyBackTransfer(Integer uid, Integer[] goodsIds, Integer[] amounts, String remark) {
        MutedUser mutedUser = muteUserHandler.getMuteUser();
        //退给公司的订单
        Transfer transfer = initTransfer(uid, mutedUser.getId(), goodsIds, amounts, remark);
        applyBackTransfer(transfer);
        merge(transfer);
    }

    /**
     * 申请提交
     *
     * @param transfer
     */
    private void applyTransfer(Transfer transfer) {
        transfer.setStatus(GoodsTransferStatus.NEW);//转货状态
        transfer.setApplyTime(new Date());//转货时间
        sumAmount(transfer);//统计价格
        sumQuantity(transfer);//统计货物总数
    }

    /**
     * 扣除费用
     *
     * @return
     */
    private List<AccountsRecord> deductMoney(Transfer transfer) {
        Agent agent = transfer.getToAgent();
        Agent fromAgent = transfer.getFromAgent();
        Double sumAmount = transfer.getAmount();
        //可用的预存款
        Double advance = accountsHandler.getAvailableAdvance(agent, sumAmount);
//        只能用预存款 不能使用奖金
//        Double voucher = accountsHandler.getAvailableVoucher(agent, PreciseComputeUtil.round(sumAmount - purchase));
        transfer.setVoucher(0.0);
        transfer.setPurchase(advance);
        List<AccountsRecord> records = new ArrayList<>();
        //减少奖金 生成记录
        String more = "进货-支付给" + fromAgent.getRealName();
        AccountsRecord record = accountsHandler.deductAccountAndRecord(AccountsType.ADVANCE, agent, fromAgent, advance, more);
        AccountsRecord record1 = accountsHandler.increaseAccountAndRecord(AccountsType.BENEFIT, agent, fromAgent, advance, "进货增加-进货增加总业绩");//增加消费数量 TODO
        records.add(record);
        records.add(record1);
//        //减少进货券
//        if (purchase > 0) {
//            AccountsRecord purchaseRecord = accountsHandler.deductAccountAndRecord(AccountsType.PURCHASE, agent, fromAgent, purchase, more);
//            records.add(purchaseRecord);
//        }
        return records;
    }


    /**
     * //TODO
     * 确认订单
     *
     * @param transfer
     */
    @Override
    @Transactional
    public void confirm(Transfer transfer) {
        confirmTransfer(transfer);
        transferDao.merge(transfer);
    }


    /**
     * //TODO
     * //确认退货订单
     *
     * @param transfer
     */
    @Override
    @Transactional
    public void backTransferConfirm(Transfer transfer) {
        List<AccountsRecord> records = confirmBackTransfer(transfer);
        //保存通知
        accountsRecordDao.saveList(records);
        //保存订单
        transferDao.merge(transfer);
        //通知
        noticeHandler.noticeAccountRecords(records);
    }

    /**
     * 拒绝退货订单
     *
     * @param transfer
     */
    @Override
    @Transactional
    public void backTransferRefuse(Transfer transfer) {
        Transfer transfer1 = refuseBack(transfer);
        merge(transfer);
        //通知
        noticeHandler.noticeTransfer(transfer1);
    }

    //拒绝退货
    private Transfer refuseBack(Transfer transfer) {
        //生成新的转货订单
        Transfer transfer1 = getTransferFromRefuseTrasfer(transfer);
        transferGoods(transfer1);//转货
        //保存
        transfer.setRemittance("退货订单拒绝");
        transfer.setStatus(GoodsTransferStatus.DISAGREE);
        return transfer1;

    }

    /**
     * 从拒绝退货订单获取补货订单
     *
     * @param transfer
     */
    private Transfer getTransferFromRefuseTrasfer(Transfer transfer) {
        Transfer transfer1 = new Transfer();
        transfer1.setFromAgent(transfer.getToAgent());
        transfer1.setToAgent(transfer.getFromAgent());
        transfer1.setItems(transfer.getItems());
        sumAmount(transfer1);
        sumQuantity(transfer1);
        transfer1.setApplyTime(new Date());
        transfer1.setRemittance("拒绝退货退回");
        transfer1.setStatus(GoodsTransferStatus.CONFIRM);
        return transfer1;
    }


    /**
     * 从拒绝退货发货订单获取补货订单
     *
     * @param transfer
     */
    @Override
    public Transfer transferFromDeleteNote(DeliveryNote note) {
        Transfer transfer = new Transfer();
        transfer.setFromAgent(muteUserHandler.getMuteUser());
        transfer.setToAgent(note.getApplyAgent());//申请人
        transfer.setRemittance("取消发货退回");
        Set<TransferItem> items = new HashSet<>();
        note.getDeliveryItems().stream().forEach(
                p -> {
                    TransferItem item = new TransferItem(transfer, p.getQuantity(), p.getPrice(), p.getGoods(), p.getPriceLevelName());
                    items.add(item);
                }
        );
        transfer.setItems(items);
        transfer.setApplyTime(new Date());
        sumAmount(transfer);
        sumQuantity(transfer);
        transfer.setStatus(GoodsTransferStatus.CONFIRM);
        transferGoods(transfer);//转货
        return transfer;
    }


    /**
     * 通过奖金+进货券购买
     *
     * @param fromUid
     * @param toUid
     * @param goodsIds
     * @param amounts
     * @param remark
     */
    @Override
    @Transactional
    public void transferAutoConfirm(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        Transfer transfer = initTransfer(fromUid, toUid, goodsIds, amounts, remark);
        applyTransfer(transfer);//提交 初始化相关参数
        //扣除费用
        List<AccountsRecord> records = deductMoney(transfer);
        //确认
        confirmTransfer(transfer);
        //更新购物时间
        updateBuyTime(transfer);
        //返利
        List<AccountsRecord> rewardRecords = rewardVoucher(transfer);
        records.addAll(rewardRecords);
        //保存订单
        transferDao.merge(transfer);
        //保存记录
        accountsRecordDao.saveList(records);
        //奖金通知
        noticeHandler.noticeAccountRecords(records);
        //产品库存通知
        noticeHandler.noticeTransfer(transfer);
    }


    private void updateBuyTime(Transfer transfer) {
        Integer amount = 0;
        Goods goods = null;
        for (TransferItem item : transfer.getItems()) {
            if (item.getGoods().isMainGoods()) {
                amount += item.getQuantity();//累积数量
                goods = item.getGoods();
            }
        }
        if (amount != 0) {
            Agent fAgent = agentHandler.findVip(transfer.getToAgent());
            Integer buyAmount = priceHandler.getPrice(fAgent, goods).getBuyAmount();
            agentHandler.updateBuyDate(transfer.getToAgent(), amount, buyAmount);
        }

    }


    /**
     * 自动转货与发货
     *
     * @param address
     * @param fromUid
     * @param toUid
     * @param goodsIds
     * @param amounts
     * @param remark
     */
    @Override
    @Transactional
    public void transferAutoConfirmAndDelivery(AddressMy address, Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        transferAutoConfirm(fromUid, toUid, goodsIds, amounts, remark);//公司转出去
        if (address.getConsigneeCode() != null && !address.getConsigneeCode().equals("")) {//
            Agent toAgent = agentHandler.get("agentCode", address.getConsigneeCode().trim());
            if (toAgent == null) {
                throw new ApplicationException("收货人编号对应的代理不存在！请检查");
            }
            fromUid = toAgent.getId();
        } else {
            fromUid = toUid;
        }
        deliveryNoteHandler.deliveryGoods(address, toUid, fromUid, goodsIds, amounts, remark);
    }

    /**
     * 提交申请
     *
     * @param fromUid
     * @param toUid
     * @param goodsIds
     * @param amounts
     * @param remark
     * @return
     */
    @Override
    @Transactional
    public Integer applyTransfer(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        Transfer transfer = initTransfer(fromUid, toUid, goodsIds, amounts, remark);
        //申请
        applyTransfer(transfer);
        //保存
        transfer = transferDao.merge(transfer);
        return transfer.getId();
    }

    /**
     * 分页查询记录
     *
     * @param p
     * @param currentUser
     * @return
     */
    @Override
    public List<Transfer> findRecords(SearchParameter<Transfer> p, User currentUser) {
        return transferDao.findRecords(p, currentUser);
    }

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private AccountsHandler accountsHandler;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private TransferDao transferDao;

    public TransferDao getTransferDao() {
        return transferDao;
    }

    public void setTransferDao(TransferDao transferDao) {
        this.transferDao = transferDao;
        super.setBaseDao(transferDao);
    }

    @Autowired
    private AccountsRecordDao accountsRecordDao;

    @Autowired
    private NoticeHandler noticeHandler;

    @Autowired
    private GoodsDao goodsDao;


    @Autowired
    private AgentDao agentDao;

    @Autowired
    private DeliveryNoteHandler deliveryNoteHandler;

    @Autowired
    private MuteUserHandler muteUserHandler;

    @Autowired
    private PriceHandler priceHandler;

    @Autowired
    private GoodsHandler goodsHandler;


}
