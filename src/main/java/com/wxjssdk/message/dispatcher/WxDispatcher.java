package com.wxjssdk.message.dispatcher;

import com.wxjssdk.message.util.MessageUtil;
import com.wxjssdk.util.XmlUtil;

import java.util.Map;

/**
 * Created by huangfei on 20/06/2017.
 */
public class WxDispatcher {

    private EventDispatcher eventDispatcher;

    private MsgDispatcher msgDispatcher;

    public String dispatcher(String xml) {
        Map<String, String> map = XmlUtil.xmlToMap(xml);
        if (isEvent(map)) {//处理事件
            return eventDispatcher.processEvent(map, xml);
        } else {
            return msgDispatcher.processMessage(map);
        }
    }

    public static boolean isEvent(Map<String, String> map) {
        String msgtype = map.get("MsgType");
        if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)) {
            return true;
        }
        return false;
    }

    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    public void setEventDispatcher(EventDispatcher eventDispatcher) {
        this.eventDispatcher = eventDispatcher;
    }

    public MsgDispatcher getMsgDispatcher() {
        return msgDispatcher;
    }

    public void setMsgDispatcher(MsgDispatcher msgDispatcher) {
        this.msgDispatcher = msgDispatcher;
    }
}
