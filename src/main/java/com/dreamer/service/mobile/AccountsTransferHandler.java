package com.dreamer.service.mobile;

import com.dreamer.domain.user.AccountsTransfer;
import com.dreamer.domain.user.User;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 02/07/2017.
 */
public interface AccountsTransferHandler extends BaseHandler<AccountsTransfer>{

    void transferAccounts(Integer fid, Integer tid, Integer typeState, Double amount, String remark);

    List<AccountsTransfer> findAccountsTransfer(SearchParameter<AccountsTransfer> parameter,User user);

    String recharge(String body);//充值

    void withDraw(Integer uid,Double amount,Integer cid,String remark);//提现

    void refuseWithdraw(Integer aid);//拒绝提现

    void confirmWithdraw(Integer aid);//同意提现

}
