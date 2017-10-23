package com.dreamer.view.user;

import com.dreamer.domain.user.User;
import com.dreamer.domain.user.VoucherTransfer;
import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.service.pay.GetOpenIdHandler;
import com.dreamer.service.user.AgentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/voucher/pay")
public class VoucherPayController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
	@RequestMapping(value = "/commit.json",method = RequestMethod.POST)
	public Message commit( VoucherTransfer voucherTransfer ){
        try {
            User user=(User)WebUtil.getCurrentUser(request);
            agentHandler.addVoucher(agentDAO.findById(user.getId()),voucherTransfer);//充值奖金
            String uri;
            String backUrl= ServletUriComponentsBuilder.fromContextPath(request).path("/voucher/pay/pay.html").queryParam("orderId",voucherTransfer.getId()).build().toUriString();
//            if(Objects.nonNull(user.getWxOpenid())&&!user.getWeixin().isEmpty()){//有OpenID直接去下单
//                uri=backUrl;
//            }else{//先去获得网页授权,返回Code
                uri= GetOpenIdHandler.createGetBaseOpenIdCallbackUrl(null, backUrl, voucherTransfer.getId());
//            }
            response.setHeader("Location",uri);
            return Message.createSuccessMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
	}

    @RequestMapping(value = "/alicommit.json",method = RequestMethod.POST)
    public Message alicommit( VoucherTransfer voucherTransfer ){
        try {
            User user=(User)WebUtil.getCurrentUser(request);
//            advanceTransfer.setUseVoucher(0.0);//不使用奖金
            agentHandler.addVoucher(agentDAO.findById(user.getId()),voucherTransfer);//提交充值奖金订单
            String backUrl= ServletUriComponentsBuilder.fromContextPath(request).path("/voucher/pay/alipay.html").queryParam("orderId",voucherTransfer.getId()).build().toUriString();
            response.setHeader("Location",backUrl);
            return Message.createSuccessMessage("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return Message.createFailedMessage(e.getMessage());
        }
    }

//    @ModelAttribute("parameter")
//    public VoucherTransfer preprocess(@RequestParam("id")Optional<Integer> id){
//        if(id.isPresent()){
//
//        }else {
//           // return new VoucherTransfer();
//        }
//
//    }

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private AgentDAO agentDAO;


}
