package com.dreamer.service.pay;

import com.dreamer.domain.wechat.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ps.mx.otter.exception.ApplicationException;
import ps.mx.otter.utils.http.HttpClient;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.user.Agent;
import com.dreamer.service.pay.param.PayUnifiedOrderReqData;
import com.dreamer.service.pay.param.UnifiedOrderResData;

@Service
public class UnifiedOrderHandler {

	public UnifiedOrderResData unifiedOrder(WxConfig wxConfig, Agent agent, Order order, String body) {
//		try {
//			PayUnifiedOrderReqData param = new PayUnifiedOrderReqData();
//			param.setAppid(wxConfig.getAppid());
//			param.setBody(body);
//			param.setOpenid(agent.getWxOpenid());
//			param.setOut_trade_no(order.getOrderNo());
//            param.setMch_id(wxConfig.getMchID());
//			param.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
//            param.setTotal_fee((new Double(order.getActualPayment() * 100)).intValue());
//			param.setSpbill_create_ip(wxConfig.getSpbill_create_ip());
//			param.setNotify_url(wxConfig.getNoticeUrl());
//			param.setSign(Signature.getSign(param, wxConfig.getKey()));
//			String unifiedRes = HttpClient.httpPostForString(UNIFIED_ORDER_URL,
//					param.toXmlString());
//			unifiedRes = new String(unifiedRes.getBytes("ISO-8859-1"), "UTF-8");
//			UnifiedOrderResData unifiedOrder = Util.getObjectFromXML(
//					unifiedRes, UnifiedOrderResData.class);
//			return unifiedOrder;
//		} catch (Exception exp) {
//			LOG.error("统一下单调用失败",exp);
//			exp.printStackTrace();
//			throw new ApplicationException("统一下单调用异常",exp);
//		}
			return null;
	}

	public UnifiedOrderResData unifiedOrder(String notifyUrl, String orderNo, Double number, WxConfig wxConfig, Agent agent, String body){
		try {
			PayUnifiedOrderReqData param = new PayUnifiedOrderReqData();
			param.setAppid(wxConfig.getAppid());
			param.setBody(body);
			param.setOpenid(agent.getWxOpenid());
			param.setOut_trade_no(orderNo);
			param.setMch_id(wxConfig.getMchID());
			param.setNonce_str(RandomStringGenerator
					.getRandomStringByLength(32));
			param.setTotal_fee((new Double(number * 100))
					.intValue());
			param.setSpbill_create_ip(wxConfig.getSpbill_create_ip());
			param.setNotify_url(notifyUrl);
			param.setSign(Signature.getSign(param, wxConfig.getKey()));
			String unifiedRes = HttpClient.httpPostForString(UNIFIED_ORDER_URL,
					param.toXmlString());
			unifiedRes = new String(unifiedRes.getBytes("ISO-8859-1"), "UTF-8");
			UnifiedOrderResData unifiedOrder = Util.getObjectFromXML(
					unifiedRes, UnifiedOrderResData.class);
			return unifiedOrder;
		} catch (Exception exp) {
			LOG.error("统一下单调用失败",exp);
			exp.printStackTrace();
			throw new ApplicationException("统一下单调用异常",exp);
		}
	}

	private final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	private final Logger LOG = LoggerFactory.getLogger(getClass());
}
