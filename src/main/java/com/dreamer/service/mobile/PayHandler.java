package com.dreamer.service.mobile;

import com.dreamer.domain.wechat.WxConfig;

/**
 * Created by huangfei on 14/07/2017.
 */
public interface PayHandler {

    String toWxPay(WxConfig wxConfig,String openId,String orderNo,Double amount,String noticeUrl);

}
