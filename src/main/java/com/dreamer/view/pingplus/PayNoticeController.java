package com.dreamer.view.pingplus;

import com.pingplus.PingUtil;
import com.pingplusplus.model.Event;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huangfei on 16/05/2017.
 */
@RestController
@RequestMapping("/pingplus")
public class PayNoticeController {

    @RequestMapping(value = "/notice",method = RequestMethod.POST)
    private void  notice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Event event = PingUtil.getEventResult(request);
        if ("charge.succeeded".equals(event.getType())) {
            response.setStatus(200);
        } else if ("refund.succeeded".equals(event.getType())) {
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
        JSONObject jsonObject = JSONObject.fromObject(event.getObject());
        System.out.println(jsonObject.get("order_no")+"=========order_no");
    }

}
