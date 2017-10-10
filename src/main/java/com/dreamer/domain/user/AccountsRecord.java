package com.dreamer.domain.user;

import com.dreamer.domain.user.enums.AccountsType;

import java.util.Date;

/**
 * 转账
 */
public class AccountsRecord implements java.io.Serializable {

	private static final long serialVersionUID = 2759558931721988426L;

	public static final  Integer ADD = 1;//进账

	public static  final  Integer SUB = 0;//出账

	private Integer id;

	private Integer version;

	private Agent agent;

	private Agent  causedAgent;//因为谁产生的

    private AccountsType accountsType;//券的类型

    private String info;//什么事件

    private Double amount;//金额

    private Double nowAmount;//当前剩余

    private Date updateTime;//时间

    private Integer addSub;//进出

    private Integer orderIndex;//顺序

    public AccountsRecord(Agent agent, Agent causedAgent, AccountsType accountsType, String info, Double amount, Double nowAmount, Date updateTime, Integer addSub) {
        this.agent = agent;
        this.causedAgent = causedAgent;
        this.accountsType = accountsType;
        this.info = info;
        this.amount = amount;
        this.nowAmount = nowAmount;
        this.updateTime = updateTime;
        this.addSub = addSub;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public AccountsRecord(){}

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Agent getCausedAgent() {
        return causedAgent;
    }

    public void setCausedAgent(Agent causedAgent) {
        this.causedAgent = causedAgent;
    }

    public AccountsType getAccountsType() {
        return accountsType;
    }

    public void setAccountsType(AccountsType accountsType) {
        this.accountsType = accountsType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public Integer getAddSub() {
        return addSub;
    }

    public void setAddSub(Integer addSub) {
        this.addSub = addSub;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getNowAmount() {
        return nowAmount;
    }

    public void setNowAmount(Double nowAmount) {
        this.nowAmount = nowAmount;
    }
}