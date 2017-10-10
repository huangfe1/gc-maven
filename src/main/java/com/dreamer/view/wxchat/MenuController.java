package com.dreamer.view.wxchat;

import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.menu.domain.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangfei on 09/07/2017.
 */
@Controller
@RequestMapping("/wechat/menu")
public class MenuController {

    private String url = "ht.52zdt.com/zdt/wechat/menu/create";

    @RequestMapping("/create")
    @ResponseBody
    private SdkResult create(@RequestParam(required = false) String menuJson) {
        if (menuJson == null || menuJson.equals("")) {
            WxMenu wxMenu = new WxMenu();
            //商城入口
            WxBotLink wxBotLink0 = new WxBotLink();
            wxBotLink0.setType(WxLinkType.view.getStateInfo());
            wxBotLink0.setName("商城入口");
//            wxBotLink0.setUrl("http://ht.52zdt.com/zdt/dmz/pmall/show.html");
            wxBotLink0.setUrl("http://ht.52zdt.com/zdt/mobile/my.html");
            //操作中心
            WxTopLink wxTopLink1 = new WxTopLink();
            wxTopLink1.setName("后台操作");
            //0 绑定账号
            WxBotLink wxBotLink1_0 = new WxBotLink();
            wxBotLink1_0.setType(WxLinkType.view.getStateInfo());
            wxBotLink1_0.setName("登陆账号");
            wxBotLink1_0.setUrl("http://ht.52zdt.com/zdt/login.html");//验证账号地方
            //1个人中心
            WxBotLink wxBotLink1_1 = new WxBotLink();
            wxBotLink1_1.setType(WxLinkType.view.getStateInfo());
            wxBotLink1_1.setName("个人中心");
            wxBotLink1_1.setUrl("http://ht.52zdt.com/zdt/mobile/my.html");//验证账号地方
            //2我的钱包
            WxBotLink wxBotLink1_2 = new WxBotLink();
            wxBotLink1_2.setType(WxLinkType.view.getStateInfo());
            wxBotLink1_2.setName("我的钱包");
            wxBotLink1_2.setUrl("http://ht.52zdt.com/zdt/mobile/wallet.html");//验证账号地方
            //3联系人
            WxBotLink wxBotLink1_3 = new WxBotLink();
            wxBotLink1_3.setType(WxLinkType.view.getStateInfo());
            wxBotLink1_3.setName("联系客户");
            wxBotLink1_3.setUrl("http://ht.52zdt.com/zdt/mobile/contacts.html");//验证账号地方
//            //4优惠商城
//            WxBotLink wxBotLink1_4 = new WxBotLink();
//            wxBotLink1_4.setType(WxLinkType.view.getStateInfo());
//            wxBotLink1_4.setName("优惠商城");
//            wxBotLink1_4.setUrl("http://ht.52zdt.com/zdt/dmz/pmall/show.html");//验证账号地方
            wxTopLink1.setSub_button(new WxBotLink[]{wxBotLink1_0, wxBotLink1_1, wxBotLink1_2, wxBotLink1_3});
            //查询
            WxTopLink wxTopLink2 = new WxTopLink();
            wxTopLink2.setName("查询入口");
            //0 授权查询
            WxBotLink wxBotLink2_0 = new WxBotLink();
            wxBotLink2_0.setType(WxLinkType.view.getStateInfo());
            wxBotLink2_0.setName("授权查询");
            wxBotLink2_0.setUrl("http://ht.52zdt.com/zdt/dmz/agent/search.html?f=1");//验证账号地方
            //防伪查询
            WxBotLink wxBotLink2_1 = new WxBotLink();
            wxBotLink2_1.setType(WxLinkType.view.getStateInfo());
            wxBotLink2_1.setName("防伪码查询");
            wxBotLink2_1.setUrl("http://ht.52zdt.com/zdt/dmz/securityCode/search.html?f=1");//验证账号地方
            //公司简介
            WxBotLink wxBotLink2_2 = new WxBotLink();
            wxBotLink2_2.setType(WxLinkType.view.getStateInfo());
            wxBotLink2_2.setName("公司简介");
            wxBotLink2_2.setUrl("http://ht.52zdt.com/zdt/dmz/securityCode/search.html?f=1");//验证账号地方
            wxTopLink2.setSub_button(new WxBotLink[]{wxBotLink2_0, wxBotLink2_1, wxBotLink2_2});
            //组装
            wxMenu.setButton(new WxLink[]{wxBotLink0, wxTopLink1, wxTopLink2});
            //拼成JSON
            menuJson = JSONObject.fromObject(wxMenu).toString();
        }
        SdkResult sdkResult = JSAPI.createMenu(TokenInfo.getAccessToken(wxConfigFactory.getBaseConfig().getAppid()), menuJson);
        return sdkResult;
    }


    public static void main(String[] args) {
        WxMenu wxMenu = new WxMenu();
        //商城入口
        WxBotLink wxBotLink0 = new WxBotLink();
        wxBotLink0.setType(WxLinkType.view.getStateInfo());
        wxBotLink0.setName("公司简介");
//        wxBotLink0.setUrl("http://ht.52zdt.com/zdt/pmall/show.html");
        wxBotLink0.setUrl("http://www.china-zdt.com/");
        //操作中心
//        WxTopLink wxTopLink1 = new WxTopLink();
//        wxTopLink1.setName("后台操作");
        //0 绑定账号
//        WxBotLink wxBotLink1_0 = new WxBotLink();
//        wxBotLink1_0.setType(WxLinkType.view.getStateInfo());
//        wxBotLink1_0.setName("登陆账号");
//        wxBotLink1_0.setUrl("http://ht.52zdt.com/zdt/login.html");//验证账号地方
        //1个人中心
        WxBotLink wxBotLink1_1 = new WxBotLink();
        wxBotLink1_1.setType(WxLinkType.view.getStateInfo());
        wxBotLink1_1.setName("会员登录");
        wxBotLink1_1.setUrl("http://ht.52zdt.com/zdt/mobile/my.html");//验证账号地方
        //2我的钱包
//        WxBotLink wxBotLink1_2 = new WxBotLink();
//        wxBotLink1_2.setType(WxLinkType.view.getStateInfo());
//        wxBotLink1_2.setName("我的钱包");
//        wxBotLink1_2.setUrl("http://ht.52zdt.com/zdt/mobile/wallet.html");//验证账号地方
        //3联系人
//        WxBotLink wxBotLink1_3 = new WxBotLink();
//        wxBotLink1_3.setType(WxLinkType.view.getStateInfo());
//        wxBotLink1_3.setName("联系客户");
//        wxBotLink1_3.setUrl("http://ht.52zdt.com/zdt/mobile/contacts.html");//验证账号地方
//        //4查看所有代理
//        WxBotLink wxBotLink1_4 = new WxBotLink();
//        wxBotLink1_4.setType(WxLinkType.view.getStateInfo());
//        wxBotLink1_4.setName("所有代理");
//        wxBotLink1_4.setUrl("http://ht.52zdt.com/zdt/agent/children.html");//验证账号地方
//        wxTopLink1.setSub_button(new WxBotLink[]{wxBotLink1_0, wxBotLink1_1, wxBotLink1_2, wxBotLink1_3});
        //查询
        WxTopLink wxTopLink2 = new WxTopLink();
        wxTopLink2.setName("查询入口");
        //0 授权查询
        WxBotLink wxBotLink2_0 = new WxBotLink();
        wxBotLink2_0.setType(WxLinkType.view.getStateInfo());
        wxBotLink2_0.setName("授权查询");
        wxBotLink2_0.setUrl("http://ht.52zdt.com/zdt/dmz/agent/search.html?f=1");//验证账号地方
        //防伪查询
        WxBotLink wxBotLink2_1 = new WxBotLink();
        wxBotLink2_1.setType(WxLinkType.view.getStateInfo());
        wxBotLink2_1.setName("防伪码查询");
        wxBotLink2_1.setUrl("http://ht.52zdt.com/zdt/dmz/securityCode/search.html?f=1");//验证账号地方
//        //公司简介
//        WxBotLink wxBotLink2_2 = new WxBotLink();
//        wxBotLink2_2.setType(WxLinkType.view.getStateInfo());
//        wxBotLink2_2.setName("公司简介");
//        wxBotLink2_2.setUrl("http://www.china-zdt.com/");//验证账号地方
        wxTopLink2.setSub_button(new WxBotLink[]{wxBotLink2_0, wxBotLink2_1});
        //组装
        wxMenu.setButton(new WxLink[]{wxBotLink0, wxBotLink1_1, wxTopLink2});
        //拼成JSON
        String menuJson = JSONObject.fromObject(wxMenu).toString();
        System.out.println(menuJson);
    }


    @Autowired
    private WxConfigFactory wxConfigFactory;


}
