package com.dreamer.view.pmall;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderStatus;
import com.dreamer.domain.pmall.order.PaymentStatus;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.repository.pmall.order.OrderDAO;
import com.dreamer.service.mobile.OrderHandler;
import com.dreamer.service.pmall.order.*;
import com.dreamer.util.ExcelFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pm/order")
public class OrderPayController {

//	@RequestMapping(value="/pay.json",method=RequestMethod.POST)
//	public Message pay(@ModelAttribute("parameter") Order param,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model){
////		try{
////			orderPayHandler.pay(param, param.getPaymentWay(), param.getActualPayment());
////			return Message.createSuccessMessage();
////		}catch(Exception exp){
////			exp.printStackTrace();
////			LOG.error("进入订单付款确认失败"+exp.getMessage());
////			return Message.createFailedMessage(exp.getMessage());
////		}
//		return null;
//	}

	/**
	 *  将没有支付的订单进行提交
	 * @return
     */
//	@RequestMapping(value = "/doOrder.json",method = RequestMethod.GET)
//	public Message doOrder(){
//        try {
//            String fileName="/Users/huangfei/Desktop/order.xlsx";
//            ExcelFile excelFile = new ExcelFile();
//            String[] columns=new String[]{"交易时间","微信支付单号","商户订单号","商户号","特约商户号","设备号","appid","下单用户","交易场景","交易状态","支付成功时间","交易金额(元)"};
//            List<Map<String, Object>> lists= excelFile.read(fileName,0,1,columns);
//            for(int i=0;i<lists.size();i++){
//                Map<String,Object> map=lists.get(i);
//                String orderNu=map.get(columns[2]).toString().replace("`","");
//
//
//                Order order=orderDAO.findNoPaidOrderByOrderNo(orderNu);
//                if(Objects.nonNull(order)){
//                order.setPaymentTime(DateUtil.string2date(order.getUpdateTime().toString(), "yyyyMMddHHmmss"));
//                orderPayHandler.pay(order, PaymentWay.WX, new Double(order.getTotalMoney()));
//                System.out.println(i+"==="+orderNu);
//                }
//            }
////            orderDAO.autoPayNoPaid(orderNumber);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return Message.createSuccessMessage();
//	}

//
//	@RequestMapping(value="/revoke.json",method=RequestMethod.POST)
//	public Message revoke(@ModelAttribute("parameter") Order param,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model){
//		try{
//			User user=(User)WebUtil.getCurrentUser(request);
//			param.setRevokeOperator(user.getRealName());
//			orderRevokeHandler.revoke(param);
//			return Message.createSuccessMessage();
//		}catch(Exception exp){
//			exp.printStackTrace();
//			LOG.error("订单撤销操作失败");
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}
	
//	@RequestMapping(value="/receive.json",method=RequestMethod.POST)
//	public Message receive(@ModelAttribute("parameter") Order param,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model){
//		try{
//			User user=(User)WebUtil.getCurrentUser(request);
//			orderReceiveHandler.receive(param, user);
//			return Message.createSuccessMessage();
//		}catch(Exception exp){
//			exp.printStackTrace();
//			LOG.error("订单收货确认操作失败");
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}
	
	@RequestMapping(value="/shipping/confirm.json",method=RequestMethod.POST)
	public Message confirmShipping(@ModelAttribute("parameter") Order param,
			HttpServletRequest request, HttpServletResponse response,
			Model model){
		try{
			User user=(User)WebUtil.getCurrentUser(request);
			param.setShippingOperator(user.getRealName());
			orderHandler.delivery(param.getId(),param.getLogistics(),param.getLogisticsCode(),user.getRealName());
			return Message.createSuccessMessage();
		}catch(Exception exp){
			exp.printStackTrace();
			LOG.error("订单发货操作失败");
			return Message.createFailedMessage(exp.getMessage());
		}
	}
	
//	@RequestMapping(value="/refund.json",method=RequestMethod.POST)
//	public Message refund(@ModelAttribute("parameter") Order param,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model){
//		try{
//			User user=(User)WebUtil.getCurrentUser(request);
//			orderRefundHandler.refund(param, user.getRealName());
//			return Message.createSuccessMessage();
//		}catch(Exception exp){
//			exp.printStackTrace();
//			LOG.error("订单发货操作失败");
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}
	
//	@RequestMapping(value="/return.json",method=RequestMethod.POST)
//	public Message returns(@ModelAttribute("parameter") Order param,
//			HttpServletRequest request, HttpServletResponse response,
//			Model model){
//		try{
//			User user=(User)WebUtil.getCurrentUser(request);
//			orderReturnHandler.returns(param, user.getRealName());
//			return Message.createSuccessMessage();
//		}catch(Exception exp){
//			exp.printStackTrace();
//			LOG.error("订单退货操作失败");
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}

	/**
	 * 上传订单编号与ID
	 * @param imageFile
     */
	@RequestMapping(value = "/uploadOrdersNumber.json",method = RequestMethod.POST)
	public Message uploadOrdersNumber( MultipartFile file,HttpServletRequest request){
		int i=0;
		ExcelFile excelFile = new ExcelFile();
		String[] columns=new String[]{"快递公司","业务单号","订单ID"};
		try {
            List<Map<String,Object>> lists=excelFile.read(file.getInputStream(),0,1,columns);
			for(Map<String,Object> map:lists){
				String orderNo=(String) map.get("业务单号");
				Integer orderId=Integer.parseInt((String) map.get("订单ID"));
//                String company="百事快递";
                String company=(String) map.get("快递公司");
                if(orderNo!=null&&!orderNo.equals("")&&orderId!=null){
                    User user=(User)WebUtil.getCurrentUser(request);
                    orderHandler.delivery(orderId,company,orderNo,user.getRealName());
                    i++;
                }
			}
            return Message.createSuccessMessage();
		}catch (Exception exp){
			return Message.createFailedMessage(exp.getMessage()+"本次共处理了"+i);
		}
	}
	
	@ModelAttribute("parameter")
	public Order orderPreprocess(
			@RequestParam(required = false) Optional<Integer> id) {
		if (id.isPresent()) {
			return (orderHandler.get(id.get()));
		} else {
			return new Order();
		}
	}

	@Autowired
	private OrderHandler orderHandler;

	private final Logger LOG=LoggerFactory.getLogger(getClass());
}
