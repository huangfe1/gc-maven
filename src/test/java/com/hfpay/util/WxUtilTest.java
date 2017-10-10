package com.hfpay.util;

import com.dreamer.domain.wechat.WxConfig;
import com.hfpay.domain.UnifiedOrderReqData;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by huangfei on 16/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"})
public class WxUtilTest {

    @Autowired
    private WxConfig wxConfig;

    @Test
    public void getNoncestr() throws Exception {
        System.out.println(WxUtil.getNoncestr(8));
    }

    @Test
    public void getSign() throws Exception {
        UnifiedOrderReqData uq = new UnifiedOrderReqData();
        uq.setAppid(wxConfig.getAppid());
        uq.setBody("测试产品");
        uq.setNonce_str("opVGOLE4");
        uq.setNotify_url("www.zmz365.com");
        uq.setOpenid("asd");
        System.out.println(WxUtil.getSign(uq,"huangfei"));
    }

    @Test
    public void getPaySign() throws Exception {
        Map map = new HashedMap();
        map.put("appid","asd");
        map.put("nonceStr","vsd");
        map.put("body","csd");
        System.out.println( WxUtil.getPaySign(map,"huangfei"));
    }

    @Test
    public void getXml() throws Exception {
        UnifiedOrderReqData uq = new UnifiedOrderReqData();
        uq.setAppid(wxConfig.getAppid());
        uq.setBody("测试产品");
        uq.setNonce_str("opVGOLE4");
        uq.setNotify_url("www.zmz365.com");
        uq.setOpenid("asd");
        uq.setSign(WxUtil.getSign(uq,"huangfei"));
        System.out.println(WxUtil.getXml(uq));
    }

    @Test
    public void getObjectFromXML() throws Exception {
        UnifiedOrderReqData uq = new UnifiedOrderReqData();
        uq.setAppid(wxConfig.getAppid());
        uq.setBody("测试产品");
        uq.setNonce_str("opVGOLE4");
        uq.setNotify_url("www.zmz365.com");
        uq.setOpenid("asd");
        uq.setSign(WxUtil.getSign(uq,"huangfei"));
        UnifiedOrderReqData test = WxUtil.getObjectFromXML(WxUtil.getXml(uq),UnifiedOrderReqData.class);
        System.out.println(test.getAppid());
    }

    @Test
    public void md5Encode() throws Exception {
    }

}