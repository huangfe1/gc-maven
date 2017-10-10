package com.dreamer.view.mall.shopcart;

import com.dreamer.repository.user.AddressDAO;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.service.delivery.DeliveryNoteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping({"/tmall/pay"})
@SessionAttributes({ "indexUrl", "ref" })
public class TShopPayController {

	private static final String CART = "tshopcart";


//	/**
//	 * add by hf 特权商城直接发货
//	 * @param parameter
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/pay.json", method = RequestMethod.POST)
//	public Message pay(Address addr,@ModelAttribute("parameter") DeliveryNote parameter, HttpServletRequest request, HttpServletResponse response) {
//		try {
//			Agent agentUser = (Agent) WebUtil.getCurrentUser(request);
//			Agent agent = agentDAO.findById(agentUser.getId());
//			ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request, CART);
//			parameter.setUserByApplyAgent(agent);
//			parameter.setUserByOperator(agent);
//
//            addr = bulidAddress(addr,parameter);//组装地址
//
//			if(agent.isTeqVip()){//如果是特权vip
//				parameter.setParentByOperator(agentDAO.findById(3));//处理人公司
//			}else {
//				parameter.setParentByOperator(agent.getTeqparent());//处理人为他的特权上家
//			}
//			if(agent.isTeqVip()){//使用预存款
//				//建立特权商城发货订单
//				deliveryNoteHandler.buildDeliveryNoteByTmallAndConfirm(parameter, addr.getConsigneeCode(), cart);
//
//			}else {//不使用预存款,不是品牌代言
//				//建立特权商城发货订单
//				deliveryNoteHandler.buildDeliveryNoteByTmall(parameter, agentUser.getAgentCode(), cart);
//			}
//			WebUtil.removeSessionAttribute(request, CART);
//			return Message.createSuccessMessage();
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("提交失败",exp);
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}

//	private Address bulidAddress(Address addr,DeliveryNote parameter){
//        //填充地址
//        if(addr.getId()==null) {
//            addr.setAgent(parameter.getUserByApplyAgent());
////            parameter.getUserByApplyAgent().getAddresses().add(addr);
//        }else {
//            addr = addressDAO.findById(addr.getId());
//        }
//        //地址填入
//        parameter.setConsigneeName(addr.getConsignee());
//        parameter.setMobile(addr.getMobile());
//        parameter.setProvince(addr.getProvince());
//        parameter.setCity(addr.getCity());
//        parameter.setCounty(addr.getCounty());
//        parameter.setAddress(addr.getAddress());
//        return addr;
//    }

	
	@Autowired
	private AddressDAO addressDAO;

	@Autowired
	private AgentDAO agentDAO;

	@Autowired
	private DeliveryNoteHandler deliveryNoteHandler;
	
	private Logger LOG=LoggerFactory.getLogger(getClass());
}
