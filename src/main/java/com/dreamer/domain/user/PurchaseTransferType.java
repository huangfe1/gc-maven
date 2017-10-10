package com.dreamer.domain.user;

public enum PurchaseTransferType {
	NORMAL("主动转出"),PAY("充值"),ERROR("失败/未付款");
    public String desc;

    PurchaseTransferType(String desc){
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}