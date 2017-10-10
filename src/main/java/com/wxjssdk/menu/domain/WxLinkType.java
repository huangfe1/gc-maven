package com.wxjssdk.menu.domain;

/**
 * Created by huangfei on 09/07/2017.
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141013
 */
public enum  WxLinkType {

    click(0,"click"),
    view(1,"view"),
    scancode_push(2,"scancode_push"),
    scancode_waitmsg(3,"scancode_waitmsg"),
    pic_sysphoto(4,"pic_sysphoto"),
    pic_photo_or_album(5,"pic_photo_or_album"),
    pic_weixin(6,"pic_weixin"),
    location_select(7,"location_select"),
    media_id(8,"media_id"),
    view_limited(9,"view_limited");

    private Integer state;

    private String stateInfo;



    WxLinkType(Integer state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
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
