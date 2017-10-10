package com.pingplus;

import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by huangfei on 16/05/2017.
 */
public class PingUtil {

    /**
     * 接受ping++发送过啦的
     * @param request
     * @return
     * @throws IOException
     */
    public static Event getEventResult(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF8");
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key+" "+value);
        }
        // 获得 http body 内容
        BufferedReader reader = request.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        // 解析异步通知数据
        Event event = Webhooks.eventParse(buffer.toString());
        return event;
    }

}
