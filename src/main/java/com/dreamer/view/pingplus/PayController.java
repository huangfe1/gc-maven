package com.dreamer.view.pingplus;

import com.pingplus.PingConfig;
import com.pingplus.PingPay;
import com.pingplusplus.model.Charge;
import com.pingplusplus.util.WxpubOAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;

/**
 * Created by huangfei on 16/05/2017.
 */
@Controller
@RequestMapping("/pingplus")
public class PayController {

    private final String appid = "wx937d1d6e484a4b25";

    private final String secrect = "264de5cc5e304711952642b0a844e38c";


    @RequestMapping("/index")
    private String index() {
        return "ping_index";
    }

    /**
     * 跳转到获取code页面
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/createCode")
    @ResponseBody
    private String getOpenId(HttpServletRequest request) throws UnsupportedEncodingException {
        String redirectUrl = ServletUriComponentsBuilder.fromContextPath(request).path("/pingplus/prepareToPay").toUriString();
        String url = WxpubOAuth.createOauthUrlForCode(appid,redirectUrl,false);
        return url;
    }


    /**
     * 获取openid 提交订单 前往支付页面
     * @param code
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/prepareToPay")
    private String getOpenIdOrCommitOrder(Optional<String> code, HttpServletRequest request, Model model) {
        try {
            if(code.isPresent()){//code存在就是 获取openid
                String openid = WxpubOAuth.getOpenId(appid,secrect,code.get());
                request.getSession().setAttribute("openId",openid);
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error",e.getMessage());
        }
        return "ping_pay";
    }

    @RequestMapping(value = "/onePay",method = RequestMethod.POST)
    @ResponseBody
    private Charge onePay(HttpServletRequest request){
        String openId = (String) request.getSession().getAttribute("openId");
        return PingPay.wxPubPay(createOrderNo(),1,"subject","body", PingConfig.appid,openId);
    }



    /**
     * 生成订单号
     * @return
     */
    private String createOrderNo(){
        SimpleDateFormat sd = new SimpleDateFormat("MMddHHmmss");
        return  sd.format(Date.from(Instant.now()));
    }

}
