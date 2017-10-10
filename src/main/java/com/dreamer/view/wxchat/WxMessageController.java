package com.dreamer.view.wxchat;

import com.wxjssdk.message.dispatcher.WxDispatcher;
import com.wxjssdk.util.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huangfei on 20/06/2017.
 */
@Controller
@RequestMapping("/wxchat")
public class WxMessageController {

    @Autowired
    private WxDispatcher wxDispatcher;


    private final String token = "huangfei08";

    //验证token  是微信服务器发来的消息
    @RequestMapping(value = "/handleMessage",method = RequestMethod.GET)
    public void validateToken(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) throws IOException {
        List<String>  stringList = new ArrayList<>();
        stringList.add(token);
        stringList.add(timestamp);
        stringList.add(nonce);
        Collections.sort(stringList);
        String result = "";
        for(String str : stringList){
            result+=str;
        }
//        logger.debug("result:{}",result);
//        logger.debug("token验证:{}",Encrypt.Encode(result,"SHA1")+"++="+signature);
        if(Encrypt.Encode(result,"SHA1").equals(signature)){
            response.getWriter().print(echostr);
        }
    }

    //处理消息
    @RequestMapping(value = "/handleMessage",method = RequestMethod.POST,produces = "text/html;charset=utf-8")
    @ResponseBody
    public String handleMessage(@RequestBody(required = false) String body) throws UnsupportedEncodingException {
        //事件分发处理器
        return wxDispatcher.dispatcher(body);//处理
    }

}
