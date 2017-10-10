package com.dreamer.time;

import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by huangfei on 16/6/14.
 */
@Component("tokenTimer")
public class TokenTimer {


    @Autowired
    private WxConfigFactory wxConfigFactory;



    public void doIt() {

        WxConfig baseConfig = wxConfigFactory.getBaseConfig();

        WxConfig payConfig = wxConfigFactory.getPayConfig();

//        TokenInfo.setAccessToken("2","22");

        initTokenAndTicket(baseConfig.getAppid(),wxConfigFactory.getBaseConfig().getSecret());
        if(!baseConfig.getId().equals(payConfig.getId())){
            initTokenAndTicket(wxConfigFactory.getPayConfig().getAppid(),wxConfigFactory.getPayConfig().getSecret());
        }

        //每隔7000秒获取一次token与ticket
        //获取token


    }


    private void initTokenAndTicket(String appid, String secrect) {
        SdkResult<JSONObject> sdkResult = JSAPI.getToken(appid, secrect);
        if (!sdkResult.isSuccess()) {
            logger.error("获取sdktoken错误:{},appid:{}", sdkResult.getError(),appid);
            return;
        }
        String access_token = sdkResult.getData().getString("access_token");
        TokenInfo.setAccessToken(appid,access_token);
        //ticket
        sdkResult = JSAPI.getTicket(access_token);
        if (!sdkResult.isSuccess()) {
            logger.error("获取sdk票据Ticket错误:{},Appid:{}", sdkResult.getError(),appid);
        }
        TokenInfo.setJsapiTicket(appid,sdkResult.getData().getString("ticket"));
    }


    private Logger logger = LoggerFactory.getLogger(this.getClass());

}
