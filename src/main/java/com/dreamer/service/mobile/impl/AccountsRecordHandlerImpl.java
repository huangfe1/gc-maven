package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.repository.mobile.AccountsRecordDao;
import com.dreamer.service.mobile.AccountsRecordHandler;
import com.dreamer.util.PreciseComputeUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.utils.SearchParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public class AccountsRecordHandlerImpl extends BaseHandlerImpl<AccountsRecord> implements AccountsRecordHandler {

    /**
     * 统计账户  进出总数
     *
     * @param records
     * @return
     */
    @Override
    public Map<Integer, Double> countRecordsAmount(List<AccountsRecord> records) {
        Double sumAdd = 0.0;
        Double sumSub = 0.0;
        for (AccountsRecord record : records) {
            if (record.getAddSub() == AccountsRecord.ADD) {//入库
                sumAdd = PreciseComputeUtil.add(sumAdd, record.getAmount());
            } else {//出库
                sumSub = PreciseComputeUtil.add(sumSub, record.getAmount());
            }
        }
        Map map = new HashedMap();
        map.put(AccountsRecord.ADD, sumAdd);
        map.put(AccountsRecord.SUB, sumSub);
        return map;
    }


    /**
     * 找出账户记录
     *
     * @param uid
     * @param startDate
     * @param endDate
     * @param stateType
     * @return
     */
    @Override
    public List<AccountsRecord> findAccountsRecords(Integer uid, String startDate, String endDate, Integer stateType) {
        return accountsRecordDao.findAccountsRecords(uid,startDate,endDate,stateType);
    }

    @Override
    public List<AccountsRecord> findAccountsRecords(SearchParameter<AccountsRecord> parameter, User user) {
        return accountsRecordDao.findAccountsRecords(parameter,user);
    }

    @Override
    public List<AccountsRecord> listByChlidrens(AccountsType accountsType, List<Agent> agents,String startTime,String endTime) {
        List<Integer> uids = new ArrayList<>();
        for(Agent agent : agents){
            uids.add(agent.getId());
        }
        return accountsRecordDao.listByChlidrens(accountsType,uids,startTime,endTime);
    }

    @Override
    @Transactional
    public void saveList(List<AccountsRecord> records) {
        accountsRecordDao.saveList(records);
    }

    private AccountsRecordDao accountsRecordDao;

    public AccountsRecordDao getAccountsRecordDao() {
        return accountsRecordDao;
    }

    @Autowired
    public void setAccountsRecordDao(AccountsRecordDao accountsRecordDao) {
        this.accountsRecordDao = accountsRecordDao;
        super.setBaseDao(accountsRecordDao);
    }
}
