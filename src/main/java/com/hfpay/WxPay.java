package com.hfpay;

import com.dreamer.service.pay.Signature;
import com.dreamer.util.PreciseComputeUtil;
import com.hfpay.domain.UnifiedOrderReqData;
import com.hfpay.domain.UnifiedOrderResData;
import com.hfpay.dto.WxResult;
import com.hfpay.util.HttpTookit;
import com.hfpay.util.WxUrl;
import com.hfpay.util.WxUtil;
import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * Created by huangfei on 12/05/2017.
 */
public class WxPay {

    /**
     * 微信公众号统一下单，获取prepay_id 或者 code_url
     *
     * @param unifiedOrderReqData
     * @return
     */
    public static WxResult<UnifiedOrderResData> unifiedOrder(String appid,String mchid,String openid,String body,String out_trade_no,Double total_fee,String notify_url,String trade_type, String key) {
        WxResult<UnifiedOrderResData> result = new WxResult<>();
        UnifiedOrderReqData unifiedOrderReqData = new UnifiedOrderReqData();
        unifiedOrderReqData.setAppid(appid);
        unifiedOrderReqData.setTrade_type(trade_type);
        unifiedOrderReqData.setOpenid(openid);
        unifiedOrderReqData.setNonce_str(WxUtil.getNoncestr(32));
//        unifiedOrderReqData.setTotal_fee(new Double(total_fee*100).intValue());
        Double fee = PreciseComputeUtil.round(total_fee*100);
        unifiedOrderReqData.setTotal_fee(new Double(fee).intValue());
        unifiedOrderReqData.setOut_trade_no(out_trade_no);
        unifiedOrderReqData.setBody(body);
        unifiedOrderReqData.setMch_id(mchid);
        unifiedOrderReqData.setNotify_url(notify_url);
        try {
            String url = WxUrl.UnifiedOrderUrl;
            //设置签名
            unifiedOrderReqData.setSign(WxUtil.getSign(unifiedOrderReqData, key));
            String reqXml = WxUtil.getXml(unifiedOrderReqData);//获取xml
            String resXml = HttpTookit.doPostStr(url, reqXml);
            UnifiedOrderResData resData = WxUtil.getObjectFromXML(resXml, UnifiedOrderResData.class);
            if (resData.getReturn_code().equals("SUCCESS")) {//请求成功
                if (resData.getResult_code().equals("SUCCESS")) {//业务处理成功
                    result.setSuccess(true);
                    result.setData(resData);
                } else {//业务处理失败
                    result.setSuccess(false);
                    result.setError(resData.getErr_code() + resData.getErr_code_des());
                }
            } else {//请求失败
                result.setSuccess(false);
                result.setError(resData.getReturn_msg());//失败原因
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setError(e.getMessage());
        }
        return result;
    }

    /**
     *  获取JSAPI页面需要的参数
     * @param appId appid
     * @param secret 秘钥
     * @param prepay_id 统一下单获取
     * @return
     */
    public static String getJSAPIParam(String appId,String prepay_id,String key){
        HashMap<String,String> jsapiParam=new HashMap<String,String>();
        jsapiParam.put("appId", appId);
        jsapiParam.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        jsapiParam.put("nonceStr", WxUtil.getNoncestr(32));
        jsapiParam.put("package", "prepay_id="+prepay_id);
        jsapiParam.put("signType", "MD5");
        jsapiParam.put("paySign", WxUtil.getPaySign(jsapiParam,key));
        JSONObject jsonObject = JSONObject.fromObject(jsapiParam);
        return jsonObject.toString();
    }



}
