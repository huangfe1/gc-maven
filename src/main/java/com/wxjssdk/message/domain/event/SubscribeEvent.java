package com.wxjssdk.message.domain.event;

/**
 * Created by huangfei on 20/06/2017.
 */
public class SubscribeEvent extends BaseEvent {

    //事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String EventKey;

    //二维码的ticket，可用来换取二维码图片
    private String Ticket;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
