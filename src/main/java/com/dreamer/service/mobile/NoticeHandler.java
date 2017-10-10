package com.dreamer.service.mobile;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.Agent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public interface NoticeHandler {

    void noticeAccountRecords(List<AccountsRecord> records);

    void noticeTransfer(Transfer transfer);

    void noticeDeliveryNote(DeliveryNote note);

    void noticeDelived(DeliveryNote note);

    void noticeDeliveryNoteDeleted(DeliveryNote note);

    void noticeNewUser(Agent agent);

    //优惠商城发货通知
    void noticeOrder(Order agent);


}
