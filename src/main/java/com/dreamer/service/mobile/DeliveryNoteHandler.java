package com.dreamer.service.mobile;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 05/07/2017.
 */
public interface DeliveryNoteHandler extends BaseHandler<DeliveryNote> {

   void deleteDeliveryNote(Integer nid);

    List<DeliveryNote> findDeliveryNotes(Integer uid, Integer nid);

    List<DeliveryNote> findDeliveryByParent(Integer pid, Integer nid);

    void deliveryGoods(AddressMy address, Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    List<DeliveryNote> findDeliveryNotes(SearchParameter<DeliveryNote> uid, User user);

    List<DeliveryNote> findNotDelivery(Integer limit);

    List<Object[]> getOrdersItemCount(Integer limit);

    void  delivery(Integer noteId, String company, String code, Double actual_logisticsFee);

    List<DeliveryNote> findByChlidrens(List<Agent> chlidrens,String startTime,String endTime);

    void confirmPay(Integer id);//确认付款

    String confirmPayByWx(String body);//微信支付回调

    void confirmPayByAccounts(Integer nid,Integer uid);//代金券支付

}
