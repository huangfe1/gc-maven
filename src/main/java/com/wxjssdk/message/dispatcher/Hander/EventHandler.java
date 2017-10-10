package com.wxjssdk.message.dispatcher.Hander;

import com.wxjssdk.message.domain.event.SubscribeEvent;

/**
 * Created by huangfei on 20/06/2017.
 */
public abstract class EventHandler {
    //处理扫带参数的二维码事件
    public abstract String handSUBSCRIBE(SubscribeEvent subscribeEvent);
}
