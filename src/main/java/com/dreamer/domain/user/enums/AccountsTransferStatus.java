package com.dreamer.domain.user.enums;

import java.io.Serializable;

public enum AccountsTransferStatus implements Serializable{

    TRANSFER(0, "主动转出"), PAY(1, "充值成功"), ERROR(2, "失败"), NEW(3, "未支付");

    private Integer state;

    public String stateInfo;

    AccountsTransferStatus(Integer state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}