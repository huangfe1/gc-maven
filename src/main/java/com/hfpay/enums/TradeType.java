package com.hfpay.enums;

/**
 * Created by huangfei on 12/05/2017.
 */
public enum  TradeType {

    JSAPI("JSAPI","公众号支付"),NATIVE("NATIVE","原生扫码"),APP("APP","app支付"),MICROPAY("MICROPAY","刷卡支付");

    private String name;

    private String description;

    TradeType(String name,String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
