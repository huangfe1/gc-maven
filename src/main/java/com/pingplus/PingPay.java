package com.pingplus;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangfei on 16/05/2017.
 */
public class PingPay {

    private static void setApiKey() {
        Pingpp.apiKey = PingConfig.apikey;
    }

    /**
     * 微信公众号支付
     * https://www.pingxx.com/docs/server/charge
     *
     * @param order_no 订单编号
     * @param amount   订单金额 单位：分
     * @param subject  商品主题
     * @param body     描述信息
     * @param open_id  openid
     * @return
     * @throws RateLimitException
     * @throws APIException
     * @throws ChannelException
     * @throws InvalidRequestException
     * @throws APIConnectionException
     * @throws AuthenticationException
     */
    public static Charge wxPubPay(String order_no, Integer amount, String subject, String body,String pingAppId,String open_id){
        try{
            setApiKey();
            Map<String, Object> chargeParams = new HashMap();
            chargeParams.put("order_no",  order_no);
            chargeParams.put("amount", amount);//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
            Map<String, String> app = new HashMap();
            app.put("id", pingAppId);
            chargeParams.put("app", app);
            chargeParams.put("channel",  "wx_pub");
            chargeParams.put("currency", "cny");
            chargeParams.put("client_ip",  "127.0.0.1");
            chargeParams.put("subject",  subject);
            chargeParams.put("body",  body);
            Map<String, String> extra = new HashMap();
            extra.put("open_id", open_id);
            chargeParams.put("extra", extra);
            Charge.create(chargeParams);
            return Charge.create(chargeParams);
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }
}
