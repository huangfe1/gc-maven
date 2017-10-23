package com.dreamer.service.mobile.impl;

import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.user.*;
import com.dreamer.domain.user.enums.AccountsTransferStatus;
import com.dreamer.domain.user.enums.AccountsType;
import com.dreamer.domain.user.enums.AgentStatus;
import com.dreamer.domain.user.enums.UserStatus;
import com.dreamer.repository.mobile.AccountsDao;
import com.dreamer.repository.mobile.AccountsRecordDao;
import com.dreamer.repository.mobile.AccountsTransferDao;
import com.dreamer.repository.mobile.AgentDao;
import com.dreamer.service.mobile.AccountsHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.GoodsAccountHandler;
import com.dreamer.service.mobile.PriceHandler;
import com.dreamer.service.user.agentCode.AgentCodeGenerator;
import com.dreamer.util.PreciseComputeUtil;
import com.dreamer.util.RewardUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.exception.ValidationException;

import javax.sound.midi.Soundbank;
import java.util.*;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class AccountsHandlerImpl extends BaseHandlerImpl<Accounts> implements AccountsHandler {


    /**
     * 减少账户
     * 生成记录
     *
     * @param accounts
     * @param amount
     */
    public AccountsRecord deductAccountAndRecord(AccountsType accountsType, Agent agent, Agent casedAgent, Double amount, String more) {
        Accounts accounts = agent.getAccounts();
        Double nowAmount = deductAccount(accountsType, accounts, amount);
        AccountsRecord record = new AccountsRecord(agent, casedAgent, accountsType, more, amount, nowAmount, new Date(), AccountsRecord.SUB);
        return record;
    }


    /**
     * 减少账户
     * 生成记录
     *
     * @param accounts
     * @param amount
     */
    public AccountsRecord deductAccountAndRecord(AccountsType accountsType, Agent agent, Agent casedAgent, Double amount, String more, Boolean validate) {
        Accounts accounts = agent.getAccounts();
        Double nowAmount = deductAccount(accountsType, accounts, amount, validate);
        AccountsRecord record = new AccountsRecord(agent, casedAgent, accountsType, more, amount, nowAmount, new Date(), AccountsRecord.SUB);
        return record;
    }

    /**
     * 增加账户
     * 生成记录
     *
     * @param accounts
     * @param amount
     */
    public AccountsRecord increaseAccountAndRecord(AccountsType accountsType, Agent agent, Agent casedAgent, Double amount, String more) {
        Accounts accounts = agent.getAccounts();
        Double nowAmount = increaseAccount(accountsType, accounts, amount);
        AccountsRecord record = new AccountsRecord(agent, casedAgent, accountsType, more, amount, nowAmount, new Date(), AccountsRecord.ADD);
        return record;
    }


    /**
     * 提高账户
     *
     * @param accounts
     * @param amount
     */
    private Double increaseAccount(AccountsType accountsType, Accounts accounts, Double amount) {
        validateAmount(amount);
        Double balance = accounts.getAccount(accountsType);//获取当前要转换的库存
        balance = PreciseComputeUtil.add(balance, amount);//加法计算
        accounts.setAccount(balance, accountsType);//设置为新的值
        return balance;
    }


    /**
     * 减少账户
     *
     * @param accounts
     * @param amount
     */
    private Double deductAccount(AccountsType accountsType, Accounts accounts, Double amount) {
//        validateAmount(amount);
//        Double balance = accounts.getAccount(accountsType);//获取当前要转换的库存
//        balance = PreciseComputeUtil.sub(balance, amount);//计算
//        validateResult(balance, accountsType);//验证结果
//        accounts.setAccount(balance, accountsType);//设置为新的值
        return deductAccount(accountsType, accounts, amount, true);
    }

    /**
     * 减少账户
     *
     * @param accounts
     * @param amount
     */
    private Double deductAccount(AccountsType accountsType, Accounts accounts, Double amount, boolean validate) {
        //四舍五入 保留两位小数
        validateAmount(amount);
        Double balance = accounts.getAccount(accountsType);//获取当前要转换的库存
        balance = PreciseComputeUtil.sub(balance, amount);//计算
        if (validate) {
            validateResult(balance, accountsType);//验证结果
        }
        accounts.setAccount(balance, accountsType);//设置为新的值
        return balance;
    }


    /**
     * 验证金额
     *
     * @param amount
     */
    private void validateAmount(Double amount) {
        if (amount <= 0 || !PreciseComputeUtil.isMoneyAmount(amount)) {//值非法
            throw new ApplicationException("金额输入错误！" + amount);
        }
    }


    /**
     * 验证结果
     *
     * @param result
     * @param accountsType
     * @return
     */
    private void validateResult(Double result, AccountsType accountsType) {
        if (result < 0) {
            throw new ApplicationException(accountsType.getStateInfo() + "余额不足");
        }
    }


    /**
     * 返回可用的奖金
     *
     * @param agent
     * @param amount
     * @return
     */
    public Double getAvailableVoucher(Agent agent, Double amount) {
        Double voucher = agent.getAccounts().getAccount(AccountsType.VOUCHER);
        if (voucher >= amount) {
            return amount;
        } else {
            throw new ApplicationException("奖金余额不足,需要" + amount);
        }
    }

//    /**
//     * 返回可用的进货券
//     *
//     * @param agent
//     * @param amount
//     * @return
//     */
//    public Double getAvailablePurchase(Agent agent, Double amount) {
//        Double purchase = agent.getAccounts().getAccount(AccountsType.PURCHASE);
//        amount = PreciseComputeUtil.round(amount * 0.03);//进货使用3%
//        if (purchase >= amount) {
//            return amount;
//        } else {
//            return purchase;//全用了
//        }
//    }

    @Override
    public Double getAvailableAdvance(Agent agent, Double amount) {
        Double advance = agent.getAccounts().getAccount(AccountsType.ADVANCE);
        if (advance >= amount) {
            return amount;
        } else {
            throw new ApplicationException("预存款不足,需要" + amount);
        }
    }

    @Override
    public Map<Agent, Double> rewardVoucher(List<Agent> parents, String voucherStr, Integer qunantity) {
        HashMap<Agent, Double> maps = new HashMap<>();
        //返利参数
//        Agent fAgent =  agentHandler.findVip(agent);//找打分公司
//        Price price =  priceHandler.getPrice(agent)
        Double[] vs = RewardUtil.getVsFromStr(voucherStr);
        //循环返利 业务员 药店
        for (int i = 0; i < parents.size(); i++) {
            Double result = 0.0;//该返的奖金
            //加上基础的返利
            result += RewardUtil.getVipVoucher(vs, i, qunantity);
            //如果返利为0不加入
            if (result.equals(0.0)) {
                continue;
            }
            //加入返利map
            if (maps.containsKey(parents.get(i))) {
                Double temp = maps.get(parents.get(i));
                maps.put(parents.get(i), PreciseComputeUtil.add(temp, result));
            } else {
                maps.put(parents.get(i), result);
            }
        }
        return maps;
    }


    @Override
    public Map<Agent, Double> rewardPmallVoucher(List<Agent> parents, Integer qunantity, Double profit) {
        HashMap<Agent, Double> maps = new HashMap<>();
//        Map<String, Double> mapV = new HashMap<>();
        //截流总数
        Double sumReward = 0.0;
        //崔秀娟 特殊处理
//        Boolean hasFenGs = false;
        //循环返利
        for (int i = 0; i < parents.size(); i++) {
            Double result;//该返的奖金
            String levelName = agentHandler.getLevelName(parents.get(i));
            //增加返利
            Double tem = getVipPerCent(parents.get(i)) * profit * qunantity;//获取额外的返利
            result = tem - sumReward;
            if (result < 0) {//减去已经返了的利润
                result = 0.0;
            }
            sumReward += result;//累积已经返了的利
            //如果是崔秀娟 且有如果有分公司
//            if (levelName.contains(AgentLevelName.分公司.toString())) {
//                hasFenGs = true;
//            }
//            if (parents.get(i).getAgentCode().equals("zmz126786") && hasFenGs) {
//                result = result / 2;
//            }

            if (i < basePercent.length) {
                //加上基础的返利
                result += basePercent[i] * profit * qunantity;//返利比*毛利润*数量
            }

            result = PreciseComputeUtil.round(result);

            //如果返利为0不加入
            if (result.equals(0.0)) {
                continue;
            }
            //加入返利map
            if (maps.containsKey(parents.get(i))) {
                Double temp = maps.get(parents.get(i));
                maps.put(parents.get(i), PreciseComputeUtil.add(temp, result));
            } else {
                maps.put(parents.get(i), result);
            }
        }
        return maps;
    }

    private final String[] levelNames = {"VIP", "市代", "省代", "品牌代言", "董事", "金董", "分公司", "联盟单位"};

    private final Integer[] paySum = {310, 2500, 4200, 16000, 32000, 64000, 128000, 1000000000};

    private final Double[] vipPercent = {0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6};

    private final Double[] basePercent = {0.3, 0.05, 0.05};


    //获取该拿多少百分比
    private Double getVipPerCent(Agent agent) {
//        System.out.println(agent.getRealName()+"===");
//        Double payBalance = agent.getAccounts().getAccount(AccountsType.CONSUME);//消费金额
        Double payBalance = 0.0;//消费金额
        if (payBalance == null) payBalance = 0.0;
        //如果不是游客
        if (agent.getAgentCode() != null) {
            String levelName = agentHandler.getLevel(agent).getName();
            //不是亲情价
            //如果是联盟单位
            if (levelName.equals("联盟单位")) {
                return vipPercent[vipPercent.length - 1];//返回最后一个百分比
            }
            //如果不是联盟单位 获取级别对用的消费
            Integer index = getIndexFromArray(levelNames, levelName);
            if (index < paySum.length && index > -1) {
                payBalance += paySum[index];
            }
        }
        //获取对应消费对应的返利百分比
        for (int i = 0; i < paySum.length; i++) {
            if (payBalance < paySum[i]) {
                Integer ii = i - 1;
                if (ii < 0) {
                    return 0.0;
                } else {
                    return vipPercent[ii];
                }
            }
        }
        return vipPercent[vipPercent.length - 1];
    }


    /**
     * 从数组中获取对应位置
     *
     * @param objects
     * @param o
     * @return
     */
    private Integer getIndexFromArray(Object[] objects, Object o) {
        for (int i = 0; i < objects.length; i++) {
            if (o.equals(objects[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 从数组中获取对应区间
     *
     * @param objects
     * @param o
     * @return
     */
    private Integer getIndexFromArray(Integer[] objects, Object o) {
        for (int i = 0; i < objects.length; i++) {
            if (o.equals(objects[i])) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        final String[] levelNames = {"VIP", "市代", "省代", "品牌代言", "董事", "金董", "分公司", "联盟单位"};

        final Integer[] paySum = {310, 2500, 4200, 16000, 32000, 64000, 128000, 1000000000};

        final Double[] percent = {0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5, 0.6};

        Double payBalance = 1222222.0;//消费金额
        if (payBalance == null) payBalance = 2200.0;
        //如果不是游客
        if (true) {
            String levelName = "金董";
            //不是亲情价
            //如果是联盟单位
            if (levelName.equals("联盟单位")) {
                System.out.println(percent[percent.length - 1]);
                return;
            }
            //如果不是联盟单位 获取级别对用的消费
            Integer index = new AccountsHandlerImpl().getIndexFromArray(levelNames, levelName);
            System.out.println(index);
            if (index < paySum.length && index > -1) {
                payBalance += paySum[index];
                System.out.println(payBalance);
            }
        }
        //获取对应消费对应的返利百分比
        for (int i = 0; i < paySum.length; i++) {
            if (payBalance < paySum[i]) {
                Integer ii = i - 1;
                if (ii < 0) {
                    System.out.println(0.0);
                    return;
                } else {
                    System.out.println(ii);
                    System.out.println(percent[ii]);
                    return;
                }
            }
        }
        System.out.println(percent[percent.length - 1]);
    }

    private AccountsDao accountsDao;

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private PriceHandler priceHandler;


    public AccountsDao getAccountsDao() {
        return accountsDao;
    }

    @Autowired
    public void setAccountsDao(AccountsDao accountsDao) {
        this.accountsDao = accountsDao;
        super.setBaseDao(accountsDao);
    }
}
