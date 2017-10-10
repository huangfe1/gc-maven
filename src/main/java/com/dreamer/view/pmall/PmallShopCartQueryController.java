package com.dreamer.view.pmall;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.service.mobile.AddressMyHandler;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.OrderHandler;
import com.dreamer.service.mobile.PayHandler;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = {"/pmall/shopcart", "/dmz/pmall/shopcart"})
public class PmallShopCartQueryController {


    //购物车
    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String addGoodsToShopcart(
            HttpServletRequest request, Model model) {
        return "pmall/shopcart/index";
    }

    //提交订单
    @RequestMapping(value = "/commit.html")
    public String commit(HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        model.addAttribute("agent", agent);
        //地址
        List<AddressMy> addressMyList = addressMyHandler.findAddressByAgent(agent);
        model.addAttribute("addressMyList", addressMyList);
        return "pmall/shopcart/commit";
    }

    //去支付
    @RequestMapping(value = "/pay.html")
    public String pay(HttpServletRequest request, Model model, String id) {
        User user = (User) WebUtil.getCurrentUser(request);
        Agent agent = agentHandler.get(user.getId());
        //获取订单
        Order order = orderHandler.get(Integer.valueOf(id));
        if (order == null) {
            model.addAttribute("message", "订单不存在!");
            return "mobile/error";
        }
        //没有支付的openid 就去获取
        WxConfig wxConfig = wxConfigFactory.getBaseConfig();
//        if (agent.getPayOpenid() == null) {//如果支付的openid为空 跳转到获取code的地址
//            String redirectUrl = ServletUriComponentsBuilder.fromContextPath(request).path("/pmall/shopcart/callBack.html").build().toString();
//            String url = JSAPI.createGetCodeUrl(wxConfig.getAppid(), redirectUrl, "snsapi_base", id);
//            return "redirect:" + url;
//        }
        //去支付
        String jsApiParam = payHandler.toWxPay(wxConfig, agent.getWxOpenid(), order.getOrderNo(), order.getAmount(),WxConfig.PMALL_NOTICE_URL);
        model.addAttribute("jsApiParam", jsApiParam);
        model.addAttribute("agent", agent);
        model.addAttribute("order", order);
        return "pmall/shopcart/pay";
    }




    //获取用户支付的id 再去支付
    @RequestMapping("/callBack.html")
    public String resetOpenIdPay(String code, String state, HttpServletRequest request, Model model) {
        String jsApiParam ="";
        try {// TODO 这里要捕获异常 但是总是抛出一个莫名其妙的异常
//            System.out.println("callBack支付页面");
            jsApiParam = orderHandler.resetOpenIdToPay(code, state);

        } catch (Exception e) {
//            e.printStackTrace();
//            model.addAttribute("message", e.getMessage());
//            return "mobile/error";
            System.out.println("下单异常" + e.getMessage());
        }
        Order order = orderHandler.get(Integer.valueOf(state));
        model.addAttribute("jsApiParam", jsApiParam);
        model.addAttribute("agent", order.getUser());
        model.addAttribute("order", order);
        return "pmall/shopcart/pay";

    }


    @Autowired
    private OrderHandler orderHandler;

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private AddressMyHandler addressMyHandler;

    @Autowired
    private PayHandler payHandler;

    @Autowired
    private WxConfigFactory wxConfigFactory;

    private final Logger LOG = LoggerFactory.getLogger(getClass());
}
