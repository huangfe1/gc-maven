package com.dreamer.domain.account;

import com.dreamer.domain.user.Agent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangfei on 16/4/11.
 */
public class PurchaseRecord implements Serializable{

    private Integer id;

    private Integer type;// 0代表扣除  1代表增加

    private Double purchase;//流动数量

    private Double purchase_now;//变更后的量

    private String more;//详情

    private Agent agent;//所属人

    private Date updateTime;//产生时间


    public PurchaseRecord(Integer type, Agent agent, Double purchase, String more, Double purchase_now, Date updateTime){
        this.type=type;
        this.agent=agent;
        this.purchase=purchase;
        this.more=more;
        this.purchase_now=purchase_now;
        this.updateTime=updateTime;
    }
    public PurchaseRecord(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }





    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getPurchase_now() {
        return purchase_now;
    }

    public void setPurchase_now(Double purchase_now) {
        this.purchase_now = purchase_now;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
