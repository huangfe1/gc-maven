package com.dreamer.service.mobile.impl;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.mall.delivery.DeliveryApplyOrigin;
import com.dreamer.domain.mall.delivery.DeliveryItem;
import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.delivery.DeliveryStatus;
import com.dreamer.domain.mall.goods.*;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.repository.mobile.*;
import com.dreamer.service.mobile.*;
import com.dreamer.util.CommonUtil;
import com.dreamer.util.PreciseComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.SearchParameter;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public class DeliveryNoteHandlerImpl extends BaseHandlerImpl<DeliveryNote> implements DeliveryNoteHandler {
    /**
     * 减少库存
     *
     * @param note
     */
    private void deductGoodsAccounts(DeliveryNote note) {
        Agent agent = note.getFromAgent();
        GoodsAccount main = goodsAccountHandler.getMainGoodsAccount(agent);
        for (DeliveryItem item : note.getDeliveryItems()) {
            Goods goods = item.getGoods();
            GoodsAccount goodsAccount = goodsAccountHandler.getGoodsAccount(agent, goods, main);
            goodsAccountHandler.deductGoodsAccount(goodsAccount, item.getQuantity());
            //TODO 不知道会不会减少  实际证明减少了
            //减少公司库房
            goodsHandler.reduceStock(item.getGoods().getId(),item.getQuantity());
        }
    }

    /**
     * 增加
     *
     * @param note
     */
    private void increaseGoodsAccounts(DeliveryNote note) {
        Agent agent = note.getFromAgent();
        GoodsAccount main = goodsAccountHandler.getMainGoodsAccount(agent);
        for (DeliveryItem item : note.getDeliveryItems()) {
            Goods goods = item.getGoods();
            GoodsAccount goodsAccount = goodsAccountHandler.getGoodsAccount(agent, goods, main);
            goodsAccountHandler.increaseGoodsAccount(goodsAccount, item.getQuantity());
            //TODO 不知道会不会减少  实际证明减少了
            //退回公司库房
            goodsHandler.addStock(item.getGoods().getId(),item.getQuantity());
        }
    }


    /**
     * 扣除物流费
     *
     * @param note
     * @return
     */
    private List<AccountsRecord> deductLogistFee(DeliveryNote note) {
        if (note.getApplyAgent().isMutedUser()) {//公司不用减少物流费
            return new ArrayList<>();
        }
        String more = "物流费-发货给" + note.getToAgent().getRealName();
        AccountsRecord record = accountsHandler.deductAccountAndRecord(AccountsType.ADVANCE, note.getApplyAgent(), note.getToAgent(), note.getLogisticsFee(), more);
        List<AccountsRecord> records = new ArrayList<>();
        records.add(record);
        return records;
    }


    /**
     * 获取物流费
     *
     * @param note
     * @return
     */
    private Double getLogisticsFee(DeliveryNote note) {
        Logistics logistics = logisticsDao.like("provinces", "%" + note.getAddress().getProvince() + "%");
        if (logistics == null) {
            throw new ApplicationException(note.getAddress().getProvince() + "地址不存在！");
        }
        Double result = 0.0;
        for (DeliveryItem item : note.getDeliveryItems()) {
            Double fee = getLogisticsFee(item.getGoods(), item.getQuantity(), logistics);
            result = PreciseComputeUtil.add(result, fee);
        }
        result = PreciseComputeUtil.round(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(PreciseComputeUtil.round(15.018000000000001));
    }

    /**
     * 获取物流费核心算法
     *
     * @param weight   重量
     * @param province 身份
     * @return
     */
    private Double getLogisticsFee(Double weight, Logistics logistics) {
        weight = Math.ceil(weight);
        String[] weights = logistics.getWeights().trim().split("_");
        String[] prices = logistics.getPrices().trim().split("_");
        Double temp_price = 0.0;
        for (int i = 0; i < weights.length - 1; i++) {
            Double temp_weight = Double.parseDouble(weights[i]);
            if (weight <= temp_weight) {//小于第一区间
                temp_price = Double.parseDouble(prices[i]);
                break;
            }
        }
        if (temp_price == 0.0) {//如果不在区间内
            Double first_weight = Double.parseDouble(weights[weights.length - 1]);
            Double first_price = Double.parseDouble(prices[prices.length - 2]);
            Double per_price = Double.parseDouble(prices[prices.length - 1]);
            temp_price = first_price + (weight - first_weight) * per_price;
        }
        return temp_price;
    }

    private Double getLogisticsFee(Goods goods, Integer amount, Logistics logistics) {
        Double boxfee = getLogisticsFee(goods.getBoxamount() * goods.getWeight(), logistics);
        Integer box = amount / goods.getBoxamount();
        Integer retail = amount % goods.getBoxamount();
        return retail == 0 ? box * boxfee : box * boxfee + getLogisticsFee(retail * goods.getWeight(), logistics);
    }

    /**
     * 申请提交
     *
     * @param deliveryNote
     */
    private void applyDeliveryNote(DeliveryNote deliveryNote) {
        //获取物流费
        deliveryNote.setLogisticsFee(getLogisticsFee(deliveryNote));
        deliveryNote.setApplyTime(new Date());
        deliveryNote.setUpdateTime(new Timestamp(new Date().getTime()));
        deliveryNote.setApplyOrigin(DeliveryApplyOrigin.SYSTEM);
        deliveryNote.setOrderNo(CommonUtil.createNo());//订单号
        deliveryNote.setStatus(DeliveryStatus.NEW);
    }

    /**
     * 删除发货订单
     *
     * @param uid
     * @param nid
     * @return
     */
    @Override
    @Transactional
    public void deleteDeliveryNote(Integer nid) {
        DeliveryNote note = deliveryNoteDao.get(nid);
        if(onDelivery(note)){
            throw new ApplicationException("删除失败,正在出货/已经出货");
        }
        List<AccountsRecord> accountsRecords = new ArrayList<>();
        if(note.getLogisticsFee()>0){
            //退物流费
            String more = "物流费退回-来自给" + note.getToAgent().getRealName() + "的订单取消发货";
            accountsRecords.add(accountsHandler.increaseAccountAndRecord(AccountsType.ADVANCE, note.getApplyAgent(), note.getToAgent(), note.getLogisticsFee(), more));
        }

        //货物退回
//        Transfer transfer = transferHandler.transferFromDeleteNote(note);
//        transferHandler.merge(transfer);
        increaseGoodsAccounts(note);
        //删除订单
        deliveryNoteDao.delete(note);
        //保存物流费记录
        accountsRecordHandler.saveList(accountsRecords);
        //通知 代金券
        noticeHandler.noticeAccountRecords(accountsRecords);
        //通知转货
//        noticeHandler.noticeTransfer(transfer);
        //通知取消订单
        noticeHandler.noticeDeliveryNoteDeleted(note);
    }

    /**
     * 出货/正在出货
     * @param deliveryNote
     * @return
     */
    private boolean onDelivery(DeliveryNote deliveryNote){
        if(deliveryNote.getStatus().toString().contains("DELIVER")){
            return true;
        }
        return false;
    }

    /**
     * 克隆地址
     *
     * @param address1
     * @param address2
     */
    private void cloneAddress(Address address1, Address address2) {
        address1.setAgent(address2.getAgent());
        address1.setProvince(address2.getProvince());
        address1.setCity(address2.getCity());
        address1.setCounty(address2.getCounty());
        address1.setAddress(address2.getAddress());
        address1.setMobile(address2.getMobile());
        address1.setConsignee(address2.getConsignee());
        address1.setConsigneeCode(address2.getConsigneeCode());
    }


    @Override
    public List<DeliveryNote> findDeliveryNotes(Integer uid, Integer nid) {
        return deliveryNoteDao.findDeliveryNotes(uid, nid);
    }


    /**
     * 申请发货
     *
     * @param address  收货地址
     * @param toUid    发给谁
     * @param goodsIds 产品
     * @param amounts  库存
     */
    @Override
    @Transactional
    public void deliveryGoods(AddressMy address, Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark) {
        Agent toAgent = agentDao.get(toUid);
        Agent fromAgent = agentDao.get(fromUid);
        address.setAgent(fromAgent);
        AddressClone cloneAddress = addressCloneHandler.getFromAddressMy(address);
        //保存副本
        cloneAddress = addressCloneDao.merge(cloneAddress);
        //发货申请 生成以订单
        DeliveryNote deliveryNote = new DeliveryNote(fromAgent, fromAgent, toAgent, cloneAddress, remark);
        deliveryNote.setDeliveryItems(buildItems(deliveryNote, goodsIds, amounts));
        applyDeliveryNote(deliveryNote);//提交申请
        //减少库存
        deductGoodsAccounts(deliveryNote);
        //首单设置物流费为0
        if(findDeliveryNotes(deliveryNote.getApplyAgent().getId(),null).isEmpty()){
            deliveryNote.setLogisticsFee(0.0);
            //保存
            deliveryNote = deliveryNoteDao.merge(deliveryNote);
        }else {
            //保存
            deliveryNote = deliveryNoteDao.merge(deliveryNote);
            //减少物流费 生成记录
            List<AccountsRecord> records = deductLogistFee(deliveryNote);
            //保存记录
            accountsRecordDao.saveList(records);
            noticeHandler.noticeAccountRecords(records);//物流费扣除通知
        }




        noticeHandler.noticeDeliveryNote(deliveryNote);//待发货通知
    }


    /**
     * 库房发货
     *
     * @param noteId
     * @param company
     * @param code
     * @param actual_logisticsFee
     */
    @Override
    @Transactional
    public void delivery(Integer noteId, String company, String code, Double actual_logisticsFee) {
        DeliveryNote note = deliveryNoteDao.get(noteId);
        if(note==null){
            throw new ApplicationException("订单，ID号"+noteId+",不存在，请勿发货！！！");
        }
        note.setLogistics(company);//物流公司
        note.setLogisticsCode(code);//物流单号
        //确认发货
        if (!note.getStatus().equals(DeliveryStatus.DELIVERY)) {
            //设置成已经发货状态
            note.setStatus(DeliveryStatus.DELIVERY);
            note.setDeliveryTime(new Date());
            //补物流费差价
            List<AccountsRecord> accountsRecords = new ArrayList<>();
            if (actual_logisticsFee > note.getLogisticsFee()) {//实际物流费要多一些
                Double fee = PreciseComputeUtil.round(PreciseComputeUtil.sub(actual_logisticsFee, note.getLogisticsFee()));
                String more = "物流费扣除-来自给" + note.getToAgent().getRealName() + "发货的物流费差价";
                accountsRecords.add(accountsHandler.deductAccountAndRecord(AccountsType.ADVANCE, note.getApplyAgent(), note.getToAgent(), fee, more));
            }
            if (actual_logisticsFee < note.getLogisticsFee()) {//实际物流费要多一些
                Double fee = PreciseComputeUtil.round(PreciseComputeUtil.sub(note.getLogisticsFee(), actual_logisticsFee));
                String more = "物流费退回-来自给" + note.getToAgent().getRealName() + "发货的物流费差价";
                accountsRecords.add(accountsHandler.increaseAccountAndRecord(AccountsType.ADVANCE, note.getApplyAgent(), note.getToAgent(), fee, more));
            }
            note.setLogisticsFee(actual_logisticsFee);//设置实际物流费
            //保存
            deliveryNoteDao.merge(note);
            //保存记录
            accountsRecordHandler.saveList(accountsRecords);
            //物流费通知
            noticeHandler.noticeAccountRecords(accountsRecords);
            //已经发货通知
            noticeHandler.noticeDelived(note);
        }
    }

    @Override
    public List<DeliveryNote> findDeliveryNotes(SearchParameter<DeliveryNote> parameter, User user) {
        return deliveryNoteDao.findDeliveryNotes(parameter, user);
    }

    @Override
    public List<DeliveryNote> findNotDelivery(Integer limit) {
        return deliveryNoteDao.findNotDelivery(limit);
    }

    @Override
    public List<Object[]> getOrdersItemCount(Integer limit) {
        return deliveryNoteDao.getOrdersItemCount(limit);
    }

    /**
     * 组装发货的Item
     *
     * @return
     */
    private Set<DeliveryItem> buildItems(DeliveryNote note, Integer[] goodsIds, Integer[] amounts) {
        Agent toAgent = note.getToAgent();
        Set<DeliveryItem> items = new HashSet<>();
        DeliveryItem item;
        for (int i = 0; i < goodsIds.length; i++) {
            Goods goods = goodsDao.get(goodsIds[i]);
            Double price;
            String levelName;
            //设置价格
            if (toAgent.getAgentCode() != null) {//完善了信息的
                Price temP = priceHandler.getPrice(toAgent, goods);
                price = temP.getPrice();
                levelName = temP.getAgentLevel().getName();//等级名字
            } else {
                price = goods.getRetailPrice();
                levelName = "游客";
            }
            item = new DeliveryItem(goods, note, amounts[i], price, levelName);
            items.add(item);
        }
        return items;
    }


    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private AccountsHandler accountsHandler;

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private AccountsRecordDao accountsRecordDao;


    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private PriceHandler priceHandler;

    @Autowired
    private AddressCloneDao addressCloneDao;

    @Autowired
    private AddressMyDao addressMyDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private NoticeHandler noticeHandler;

    @Autowired
    private AccountsRecordHandler accountsRecordHandler;

    @Autowired
    private TransferHandler transferHandler;

    @Autowired
    private AddressCloneHandler addressCloneHandler;


    private DeliveryNoteDao deliveryNoteDao;

    @Autowired
    private GoodsHandler goodsHandler;

    public DeliveryNoteDao getDeliveryNoteDao() {
        return deliveryNoteDao;
    }

    @Autowired
    public void setDeliveryNoteDao(DeliveryNoteDao deliveryNoteDao) {
        this.deliveryNoteDao = deliveryNoteDao;
        super.setBaseDao(deliveryNoteDao);
    }
}
