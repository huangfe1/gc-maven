package com.wxjssdk.message.dispatcher;

import com.wxjssdk.message.dispatcher.Hander.EventHandler;
import com.wxjssdk.message.domain.event.SubscribeEvent;
import com.wxjssdk.message.util.MessageUtil;
import com.wxjssdk.util.XmlUtil;

import java.util.Map;

/**
 * Created by huangfei on 20/06/2017.
 */
public class EventDispatcher {


    private EventHandler eventHandler;

    public String  processEvent(Map<String, String> map,String xml) {
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
            SubscribeEvent subscribeEvent = XmlUtil.getObjectFromXML(xml,SubscribeEvent.class);
            return  eventHandler.handSUBSCRIBE(subscribeEvent);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            //已经关注的二维码事件跟未关注的调用同一个方法  参数区别是EventKey  qrscene_
            SubscribeEvent subscribeEvent = XmlUtil.getObjectFromXML(xml,SubscribeEvent.class);
            return  eventHandler.handSUBSCRIBE(subscribeEvent);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单View事件
            System.out.println("==============这是自定义菜单View事件！");
        }
        return "SUCCESS";

    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}
