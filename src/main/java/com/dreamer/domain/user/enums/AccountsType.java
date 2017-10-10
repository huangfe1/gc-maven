package com.dreamer.domain.user.enums;

import java.io.Serializable;

/**
 * Created by huangfei on 25/06/2017.
 */
public enum  AccountsType implements Serializable {

    VOUCHER(0,"代金券"),ADVANCE(1,"预存款"),BENEFIT(2,"总业绩");

    private int state;

    private String stateInfo;

    AccountsType(int state,String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static AccountsType stateOf(int index){
        for(AccountsType statEnum : values()){
            if(statEnum.getState()==index){
                return statEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}

