package com.wxjssdk.menu.domain;

/**
 * Created by huangfei on 09/07/2017.
 * 带附属链接的链接
 */
public class WxTopLink extends WxLink {

    private WxBotLink[] sub_button;//附属链接

    public WxBotLink[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(WxBotLink[] sub_button) {
        this.sub_button = sub_button;
    }
}
