package com.dreamer.service.mobile;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.goods.GoodsTransferStatus;
import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.User;
import ps.mx.otter.utils.SearchParameter;

import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 02/07/2017.
 */
public interface TransferHandler extends BaseHandler<Transfer> {

//    List<AccountsRecord> rewardVoucher(Transfer transfer);

    List<Transfer> findTransferRecords(Integer uid, Integer toId, Date startDate, Date endDate, GoodsTransferStatus status);

    List<Transfer> findTransfers(Integer uid, Integer nid);

    void transfer(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    void confirm(Transfer transfer);


    void transferAutoConfirm(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    void transferAutoConfirmAndDelivery(AddressMy address, Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    Integer applyTransfer(Integer fromUid, Integer toUid, Integer[] goodsIds, Integer[] amounts, String remark);

    List<Transfer> findRecords(SearchParameter<Transfer> p, User currentUser);

    //申请退回
    void applyBackTransfer(Integer uid, Integer[] goodsIds, Integer[] amounts, String remark);

    //确认退货
    void backTransferConfirm(Transfer transfer);

    //拒绝退货
    void backTransferRefuse(Transfer transfer);

    //从删除的订单中生成公司补货订单
    Transfer transferFromDeleteNote(DeliveryNote note);

}


