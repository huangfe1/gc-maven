package com.dreamer.domain.user;

import com.dreamer.domain.user.enums.AccountsTransferStatus;
import com.dreamer.domain.user.enums.AccountsType;

import java.util.Date;

/**
 * 转账
 */
public class AccountsTransfer implements java.io.Serializable {

    private static final long serialVersionUID = 2759558931721988426L;

    // Fields

    private Integer id;
    private Integer version;
    private Agent toAgent;
    private Agent fromAgent;
    private Date transferTime;
    private Date updateTime;
    private String remark;
    private Double amount;
    private String operator;
    private AccountsTransferStatus status;
    private AccountsType type;
    private String out_trade_no;//订单号码

    private Boolean canShow;//是否能展示

    // Constructors

    public AccountsTransfer() {
    }





    public AccountsTransfer(Agent toAgent, Agent fromAgent, String remark, Double amount, AccountsType type, Date updateTime) {
        this.toAgent = toAgent;
        this.fromAgent = fromAgent;
        this.remark = remark;
        this.amount = amount;
        this.type = type;
        this.updateTime = updateTime;
    }

    public Boolean getCanShow() {
        return canShow;
    }

    public void setCanShow(Boolean canShow) {
        this.canShow = canShow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agent getToAgent() {
        return toAgent;
    }

    public void setToAgent(Agent toAgent) {
        this.toAgent = toAgent;
    }

    public Agent getFromAgent() {
        return fromAgent;
    }

    public void setFromAgent(Agent fromAgent) {
        this.fromAgent = fromAgent;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public AccountsTransferStatus getStatus() {
        return status;
    }

    public void setStatus(AccountsTransferStatus status) {
        this.status = status;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public AccountsType getType() {
        return type;
    }

    public void setType(AccountsType type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}