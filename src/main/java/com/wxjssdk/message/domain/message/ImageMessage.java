package com.wxjssdk.message.domain.message;

/**
 * Created by huangfei on 20/06/2017.
 */
public class ImageMessage extends BaseMessage{

    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

}
