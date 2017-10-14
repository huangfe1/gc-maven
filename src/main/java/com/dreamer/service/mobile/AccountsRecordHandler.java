package com.dreamer.service.mobile;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 05/07/2017.
 */
public interface AccountsRecordHandler extends BaseHandler<AccountsRecord> {

    Map<Integer, Double> countRecordsAmount(List<AccountsRecord> records);

    List<AccountsRecord> findAccountsRecords(Integer uid, String startDate, String endDate, Integer stateType);

    List<AccountsRecord> findAccountsRecords(SearchParameter<AccountsRecord> parameter, User user);

    void saveList(List<AccountsRecord> records);

    List<AccountsRecord> listByChlidrens(AccountsType accountsType,List<Agent> agents,String startTime,String endTime);

//    Double countVoucher();//统计所有产生的奖金

//    Double countWithdraw();//统计所有提现的奖金

//    Double countAgentsVoucher();//统计代理手上还有多少奖金
}
