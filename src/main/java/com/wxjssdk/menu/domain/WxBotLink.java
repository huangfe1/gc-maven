package com.wxjssdk.menu.domain;

/**
 * Created by huangfei on 09/07/2017.
 * 普通链接 最底层的链接
 */
public class WxBotLink extends WxLink {

    private String type;//类型

    private String key;//click等点击类型必须

    private String url;//网页链接，用户点击菜单可打开链接，不超过1024字节

    private String media_id;//media_id类型和view_limited类型必须

    private String appid;//miniprogram类型必须 小程序的appid（仅认证公众号可配置）

    private String pagepath; //miniprogram类型必须	小程序的页面路径


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
