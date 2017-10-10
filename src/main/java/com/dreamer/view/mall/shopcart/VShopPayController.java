package com.dreamer.view.mall.shopcart;

import com.dreamer.repository.goods.GoodsDAO;
import com.dreamer.repository.user.AgentDAO;
import com.dreamer.service.delivery.DeliveryNoteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping({"/vmall/pay"})
@SessionAttributes({ "indexUrl", "ref" })
public class VShopPayController {

	private static final String CART = "shopcart";


//	@RequestMapping(value = "/pay.json", method = RequestMethod.POST)
//	public Message pay(@ModelAttribute("parameter") DeliveryNote parameter,
//			/*@ModelAttribute("indexUrl") String indexUrl,*/
//			HttpServletRequest request, HttpServletResponse response) {
//		try {
//			Agent agentUser = (Agent) WebUtil.getCurrentUser(request);
//			Agent agent = agentDAO.findById(agentUser.getId());
//			ShopCart cart = (ShopCart) WebUtil.getSessionAttribute(request,
//					CART);
//			parameter.setUserByApplyAgent(agent);
//			parameter.setUserByOperator(agent);
//			if(agentUser.isVip()){//如果是vip
//				parameter.setParentByOperator(agentDAO.findById(3));//处理人为公司
//			}else {
//				parameter.setParentByOperator(agent.getParent());//处理人为代理上家
//			}
//
//			deliveryNoteHandler.buildDeliveryNoteByShopcart(parameter,
//					agentUser.getAgentCode(), cart);
//			UriComponents ucb = ServletUriComponentsBuilder
//					.fromContextPath(request).path("/dmz/vmall/index.html")
//					.build();
//			response.setHeader("Location", ucb.toUriString());
//			WebUtil.removeSessionAttribute(request, CART);
//			return Message.createSuccessMessage("ok",String.valueOf(parameter.getId()));
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("提交失败",exp);
//			return Message.createFailedMessage(exp.getMessage());
//		}
//	}

	
	@Autowired
	private GoodsDAO goodsDAO;

	@Autowired
	private AgentDAO agentDAO;

	@Autowired
	private DeliveryNoteHandler deliveryNoteHandler;
	
	private Logger LOG=LoggerFactory.getLogger(getClass());
}
