package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.AccountsTransfer;
import com.dreamer.repository.mobile.*;
import com.dreamer.service.mobile.MobileHandler;
import com.wxjssdk.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 24/06/2017.
 */
@Service
public class MobileHandlerImpl implements MobileHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


















    public static void main(String[] args) {
//        System.out.println(getVsFromStr("1_2_3")[0]);
        Date date = new Date();
        System.out.println(DateUtil.formatDate(date, "dd"));
    }







































    /**
     * 生成转券记录
     *
     * @param accountsTransfer
     */
    private List<AccountsRecord> generateRecords(AccountsTransfer accountsTransfer) {
        List<AccountsRecord> list = new ArrayList<>();
        AccountsRecord accountsRecord;
        String info;
        Date date = new Date();
        //转出的记录
        accountsRecord = new AccountsRecord();
        accountsRecord.setAgent(accountsTransfer.getFromAgent());
        accountsRecord.setCausedAgent(accountsTransfer.getToAgent());
        accountsRecord.setAccountsType(accountsTransfer.getType());
        accountsRecord.setAddSub(AccountsRecord.SUB);//转出
        accountsRecord.setAmount(accountsTransfer.getAmount());//金额
        //设置当前金额
        accountsRecord.setNowAmount(accountsTransfer.getFromAgent().getAccounts().getAccount(accountsTransfer.getType()));
        info = accountsTransfer.getType().getStateInfo() + "转账-转给" + accountsTransfer.getToAgent().getRealName();
        accountsRecord.setInfo(info);
        accountsRecord.setUpdateTime(date);
        list.add(accountsRecord);
        //转入记录
        accountsRecord = new AccountsRecord();
        accountsRecord.setAgent(accountsTransfer.getToAgent());
        accountsRecord.setCausedAgent(accountsTransfer.getFromAgent());
        accountsRecord.setAccountsType(accountsTransfer.getType());
        accountsRecord.setAddSub(AccountsRecord.ADD);//转出
        accountsRecord.setAmount(accountsTransfer.getAmount());//金额
        //设置当前金额
        accountsRecord.setNowAmount(accountsTransfer.getToAgent().getAccounts().getAccount(accountsTransfer.getType()));
        info = accountsTransfer.getType().getStateInfo() + "转账-来自" + accountsTransfer.getFromAgent().getRealName();
        accountsRecord.setInfo(info);
        accountsRecord.setUpdateTime(date);
        list.add(accountsRecord);
        return list;
    }












    @Autowired
    private AddressMyDao addressMyDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private AccountsTransferDao accountsTransferDao;

    @Autowired
    private AccountsRecordDao accountsRecordDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsAccountDao goodsAccountDao;

    @Autowired
    private AgentLevelDao agentLevelDao;

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private LogisticsDao logisticsDao;

    @Autowired
    private DeliveryNoteDao deliveryNoteDao;

    @Autowired
    private AddressCloneDao addressCloneDao;

    @Autowired
    private TransferDao transferDao;

}
