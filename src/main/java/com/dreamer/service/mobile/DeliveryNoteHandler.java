package com.dreamer.service.mobile;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.User;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 05/07/2017.
 */
public interface DeliveryNoteHandler extends BaseHandler<DeliveryNote> {

   void deleteDeliveryNote(Integer nid);

    List<DeliveryNote> findDeliveryNotes(Integer uid, Integer nid);

    void deliveryGoods(AddressMy address, Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    List<DeliveryNote> findDeliveryNotes(SearchParameter<DeliveryNote> uid, User user);

    List<DeliveryNote> findNotDelivery(Integer limit);

    List<Object[]> getOrdersItemCount(Integer limit);

    void  delivery(Integer noteId, String company, String code, Double actual_logisticsFee);


}
