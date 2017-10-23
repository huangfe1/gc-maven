package com.dreamer.service.mobile.impl;

import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.service.mobile.PayHandler;
import com.hfpay.WxPay;
import com.hfpay.domain.UnifiedOrderResData;
import com.hfpay.dto.WxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ps.mx.otter.exception.ApplicationException;

/**
 * Created by huangfei on 14/07/2017.
 */
@Service
public class PayHandlerImpl implements PayHandler {
    @Override
    public String toWxPay(WxConfig wxConfig, String openId, String orderNo,Double amount,String noticeUrl) {
        //统一下单
        WxResult<UnifiedOrderResData> resDataWxResult = WxPay.unifiedOrder(wxConfig.getAppid(), wxConfig.getMchID(), openId, "高臣药业商城", orderNo, amount, noticeUrl, "JSAPI", wxConfig.getKey());
        if (!resDataWxResult.isSuccess()) {
            logger.error("统一下单失败-message:{}",resDataWxResult.getError());
            throw new ApplicationException("统一下单失败:"+resDataWxResult.getError());
        }
        String jsApiParam = WxPay.getJSAPIParam(wxConfig.getAppid(), resDataWxResult.getData().getPrepay_id(), wxConfig.getKey());
        return jsApiParam;
    }
    private Logger logger = LoggerFactory.getLogger(this.getClass());
}
