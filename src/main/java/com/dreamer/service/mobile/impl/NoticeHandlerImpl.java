package com.dreamer.service.mobile.impl;

import com.dreamer.domain.mall.delivery.DeliveryItem;
import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderItem;
import com.dreamer.domain.pmall.order.PaymentStatus;
import com.dreamer.domain.pmall.order.PaymentWay;
import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.Agent;
import com.dreamer.service.mobile.NoticeHandler;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public class NoticeHandlerImpl implements NoticeHandler {

    @Autowired
    private WxConfigFactory wxConfigFactory;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 调用微信通知 通知客户 资金通知
     *
     * @param records
     */
    public void noticeAccountRecords(List<AccountsRecord> records) {
        String template_id = "3JfLagPhXX7Ngbt3fcGJPHBe2_jCtyj13YU_rMl1yNs";
        Map<String, Object> data;
        for (AccountsRecord record : records) {
            String url = "http://ht.gcyy365.com/gc/mobile/accounts/records.html?stateType=" + record.getAccountsType().getState();
            data = new HashMap<>();
            data.put("first", createItemMap(record.getInfo()));//变动原因
            data.put("keyword2", createItemMap(record.getAmount()));
            data.put("keyword1", createItemMap(DateUtil.formatDate(record.getUpdateTime())));
            data.put("keyword3", createItemMap(record.getNowAmount()));
            data.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！"));
            sendTemplateMessage(record.getAgent().getWxOpenid(), template_id, url, data);

        }
    }

    /**
     * 调用微信通知 通知客户 转货通知
     *
     * @param records
     */
    public void noticeTransfer(Transfer transfer) {
        String url = "http://ht.gcyy365.com/gc/mobile/transfer/records.html";
        Agent fromAgent = transfer.getFromAgent();
        Agent toAgent = transfer.getToAgent();
        //入库通知
        String template_id = "cwLZSuKsVEibRZlI8Vwh6arpIk760ywRq7EbYTT9iws";
        Map<String, Object> toData = new HashedMap();
        Map<String, Object> fromData = new HashedMap();
        toData.put("first", createItemMap("您好，您有一批产品入库，请查看!"));
        StringBuffer stringBuffer = new StringBuffer();
        transfer.getItems().stream().forEach(p -> {
            stringBuffer.append(p.getGoods().getName());
            stringBuffer.append("X");
            stringBuffer.append(p.getQuantity());
            stringBuffer.append("\r\n");
        });
        toData.put("keyword1", createItemMap(stringBuffer.toString()));
        toData.put("keyword2", createItemMap("请前往系统查看!"));
        toData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品!感谢您的使用！"));
        sendTemplateMessage(toAgent.getWxOpenid(), template_id, url, toData);
        //出库通知

        template_id = "dYHXNZLeQhdETqUmJyQLi5Of0nu48K2tS8deMoZINaw";
        fromData.put("first", createItemMap("您好，您有一批产品出库，请查看!"));
        fromData.put("keyword1", createItemMap(transfer.getId()));
        fromData.put("keyword2", createItemMap(stringBuffer.toString()));
        fromData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品!感谢您的使用！"));
        sendTemplateMessage(fromAgent.getWxOpenid(), template_id, url, fromData);
    }

    /**
     * 调用微信通知 如果是付款方式是代付  就通知业务员(上家)代付  并且提供代付链接
     *
     * @param note
     */
    public void noticeDeliveryNote(DeliveryNote note) {
        Agent parent = note.getApplyAgent();//业务员
        Agent toAgent = note.getToAgent();//下单人
        if(!toAgent.getPayWay().equals(PaymentWay.PAY_BY_OTHER.getName())){//不是待支付
            return;
        }
        if(parent.getId().equals(toAgent.getId())){//自己申请的
            parent = toAgent.getParent();
        }
        String template_id = "wgyH0Q9lui-ctD9zD94n_R6StP0ytCPSoCknGqjBE9g";
//        String url = "http://ht.gcyy365.com/gc/mobile/dmz/note/pay.html?nid="+note.getId();
        String url = "http://ht.gcyy365.com/gc/mobile/delivery/records.html?noteId="+note.getId();
        Map<String, Object> toAgentData = new HashedMap();
        toAgentData.put("first", createItemMap("您好," + toAgent.getRealName()+"的订单需要您代支付!"));
        //产品数量
        String stringBuffer = items2String(note.getDeliveryItems());
        //收货人
        toAgentData.put("keyword1", createItemMap("高臣药业"));
        toAgentData.put("keyword2", createItemMap(toAgent.getRealName()));
        toAgentData.put("keyword3", createItemMap("待支付"));
        toAgentData.put("keyword4", createItemMap(note.getAmount()));
        toAgentData.put("keyword5", createItemMap(note.getUpdateTime()));
        toAgentData.put("remark", createItemMap("请尽快审核订单,并且完成支付!"));
        sendTemplateMessage(parent.getWxOpenid(), template_id, url, toAgentData);
    }


//    /**
//     * 优惠商城购买通知
//     *
//     * @param order
//     */
//    public void noticeOrderToParent(Order order) {
//
//    }
//
//    private void noticeOrder(){
//
//    }


    /**
     * 调用微信通知 通知客户 取消发货通知
     *
     * @param note
     */
    public void noticeDeliveryNoteDeleted(DeliveryNote note) {
        Agent fromAgent = note.getFromAgent();
        Agent toAgent = note.getToAgent();
        String template_id = "azqhgvIvoVyVnuM8vFNhMlqZKuEefQHMj7dD1ottlV0";
        String url = "http://ht.gcyy365.com/gc/mobile/delivery/records.html";
        Map<String, Object> fromAgentData = new HashedMap();
        Map<String, Object> toAgentData = new HashedMap();
        //出货人
        fromAgentData.put("first", createItemMap("您好，您发给" + toAgent.getRealName() + "的订单取消成功，产品已退回到您的库存!"));
        //产品数量
        String stringBuffer = items2String(note.getDeliveryItems());

        fromAgentData.put("keyword1", createItemMap(stringBuffer));
        fromAgentData.put("keyword2", createItemMap(DateUtil.formatDate(note.getUpdateTime())));
        fromAgentData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！如果多收了物流费，系统会在发货的时候退还！"));
        sendTemplateMessage(fromAgent.getWxOpenid(), template_id, url, fromAgentData);
        //收货人
        toAgentData.put("first", createItemMap("您好," + fromAgent.getRealName() + "给您的发货订单已经取消，不会发货！"));
        toAgentData.put("keyword1", createItemMap(stringBuffer));
        toAgentData.put("keyword2", createItemMap(DateUtil.formatDate(note.getUpdateTime())));
        toAgentData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！请耐心等待发货^.^"));
        sendTemplateMessage(toAgent.getWxOpenid(), template_id, url, toAgentData);
    }


    /**
     * 调用微信通知 通知客户 已经发货通知
     *
     * @param note
     */
    public void noticeDelived(DeliveryNote note) {
        Agent fromAgent = note.getFromAgent();
        Agent toAgent = note.getToAgent();
        String template_id = "F1nSt8KCk74Yla1fGCbA6t51Hg4QRIaLByGNLaX7Klw";
        String url = "http://ht.gcyy365.com/gc/mobile/delivery/records.html";
        Map<String, Object> fromAgentData = new HashedMap();
        Map<String, Object> toAgentData = new HashedMap();
        //出货人
        fromAgentData.put("first", createItemMap("您好，发给" + toAgent.getRealName() + "订单已经发货！"));
        //产品数量
        String stringBuffer = items2String(note.getDeliveryItems());
        fromAgentData.put("keyword1", createItemMap(stringBuffer));
        fromAgentData.put("keyword2", createItemMap(note.getLogistics()));
        fromAgentData.put("keyword3", createItemMap(note.getLogisticsCode()));
        fromAgentData.put("keyword4", createItemMap(note.getAddress().getAddress()));
        fromAgentData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！如果多收了物流费，系统会在发货的时候退还！"));
        sendTemplateMessage(fromAgent.getWxOpenid(), template_id, url, fromAgentData);
        if (fromAgent.getId().equals(toAgent.getId())) return;
        //收货人
        toAgentData.put("first", createItemMap("您好," + fromAgent.getRealName() + "发给您的发货订单已经发货，请耐心等待产品到来！"));
        toAgentData.put("keyword1", createItemMap(note.getId()));
        toAgentData.put("keyword2", createItemMap(DateUtil.formatDate(note.getUpdateTime())));
        fromAgentData.put("keyword3", createItemMap(note.getLogisticsCode()));
        fromAgentData.put("keyword4", createItemMap(note.getAddress().getProvince() + note.getAddress().getCity() + note.getAddress().getCounty() + note.getAddress().getAddress()));
        toAgentData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！请耐心等待配送哦^.^"));
        sendTemplateMessage(toAgent.getWxOpenid(), template_id, url, toAgentData);
    }


    @Override
    public void noticeOrder(Order order) {
        Agent toAgent = order.getUser();
        AddressClone addressClone = order.getAddressClone();
        String name = addressClone.getConsignee();
        String template_id = "azqhgvIvoVyVnuM8vFNhMlqZKuEefQHMj7dD1ottlV0";
        String url = "http://ht.gcyy365.com/gc/pm/order/myOrder.html";
        Map<String, Object> fromAgentData = new HashedMap();
        //出货人
        fromAgentData.put("first", createItemMap("您好，您发给" + name + "订单已经发货！"));
        //产品数量
        String stringBuffer = items2StringOrder(order.getItems());
        fromAgentData.put("keyword1", createItemMap(stringBuffer));
        fromAgentData.put("keyword2", createItemMap(order.getLogistics()));
        fromAgentData.put("keyword3", createItemMap(order.getLogisticsCode()));
        fromAgentData.put("keyword4", createItemMap(addressClone.getProvince() + addressClone.getCity() + addressClone.getCounty() + addressClone.getAddress()));
        fromAgentData.put("remark", createItemMap("高臣药业一站式购物，让更多的人用上优惠的好产品！如果多收了物流费，系统会在发货的时候退还！"));
        sendTemplateMessage(toAgent.getWxOpenid(), template_id, url, fromAgentData);
    }

    @Override
    public void noticeNewUser(Agent agent) {
        String template_id = "-9_Dmm_GAA3LdoICZrmNIJZVscabnmYx5HC-eOYiQo0";
        String url = "http://ht.gcyy365.com/gc/mobile/contacts.html";
        Map<String, Object> data = new HashedMap();
        data.put("first", createItemMap("您好，您新增了一位新的客户，请跟进服务！"));
        data.put("keyword1", createItemMap(agent.getRealName()));
        data.put("keyword2", createItemMap(DateUtil.formatDate(new Date())));
        data.put("remark", createItemMap("退热贴，选高臣！"));
        sendTemplateMessage(agent.getParent().getWxOpenid(), template_id, url, data);
    }

    private String items2String(Set<DeliveryItem> items) {
        StringBuffer stringBuffer = new StringBuffer();
        items.stream().forEach(p -> {
                    stringBuffer.append(p.getGoods().getName());
                    stringBuffer.append("X");
                    stringBuffer.append(p.getQuantity());
                    stringBuffer.append("\r\n");
                }
        );
        return stringBuffer.toString();
    }

    private String items2StringOrder(Set<OrderItem> items) {
        StringBuffer stringBuffer = new StringBuffer();
        items.stream().forEach(p -> {
                    stringBuffer.append(p.getPmallGoods().getName());
                    stringBuffer.append("X");
                    stringBuffer.append(p.getQuantity());
                    stringBuffer.append("\r\n");
                }
        );
        return stringBuffer.toString();
    }


    private void sendTemplateMessage(String openid, String template_id, String url, Map<String, Object> data) {
        Runnable runnable = () -> {
            try {
                SdkResult sdkResult = JSAPI.sendTemplateMessage(TokenInfo.getAccessToken(wxConfigFactory.getBaseConfig().getAppid()), openid, template_id, url, data);
                if (!sdkResult.isSuccess()) {
                    logger.error("通知失败内容,{},{},{}", data.toString(), sdkResult.getError(), openid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Map createItemMap(Object value) {
        Map map = new HashedMap();
        map.put("value", value + "\r\n");
        map.put("color", "#000000");
        return map;
    }


}
