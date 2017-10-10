package com.dreamer.view.mall.shopcart;

import com.dreamer.util.TokenInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tmall/shopcart")
@SessionAttributes({ "agentCode", "url", "ref" })
public class TmallShopCartQueryController {

	private static final String CART = "tshopcart";

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String addGoodsToShopcart(
			@RequestParam(value = "agentCode", required = false) String agentCode,
			@RequestParam(value = "ref", required = false) String ref,
			HttpServletRequest request, Model model) {
		try {
			model.addAttribute("imgPath", TokenInfo.IMG_HEAD_PATH
					+ "/dmz/img/goods/");
			return "tmall/shopcart/shopcart_index";
		} catch (Exception exp) {
			exp.printStackTrace();
			return "";
		}
	}
}

//	@RequestMapping(value = "/pay.html", method = RequestMethod.GET)
//	public String pay(
//			@RequestParam(value = "agentCode", required = false) String agentCode,
//			@RequestParam(value = "ref", required = false) String ref,
//			HttpServletRequest request,HttpServletResponse response, Model model) {
//		try {
////			Agent agent=agentDAO.findById(((User)WebUtil.getCurrentUser(request)).getId());
////			UriComponents backUrl= ServletUriComponentsBuilder
////						.fromContextPath(request).path("/tmall/shopcart/callback.html").queryParam("agent", agent.getId()).build();
////				LOG.debug("Get OpenId CallBack URL:{}",backUrl.toUriString());
////			String uri=GetOpenIdHandler.createGetBaseOpenIdCallbackUrl(wxConfig, backUrl.toUriString(), agent.getId());
////			LOG.debug("To Pay URL:{}",uri);
////			response.setHeader("Location", uri);
////			ShopCart cart=(ShopCart) WebUtil.getSessionAttribute(request, CART);
////			cart.getItems().values().stream().forEach(item->{
////				Goods goods=goodsDAO.findById(item.getGoods().getId());
////				GoodsAccount gac= agent.loadAccountForGoodsNotNull(goods);
////				Price price=agent.caculatePrice(goods, item.getQuantity()+(gac==null?0:gac.getCumulative()));
////				item.setPrice(price.getPrice());
////				item.setPriceLevelName(price.getAgentLevel().getName());
////			});;
////			return "redirect:"+uri;
//			Agent agent=agentDAO.findById(((User) WebUtil.getCurrentUser(request)).getId());
//			ShopCart cart=(ShopCart)WebUtil.getSessionAttribute(request, CART);
//			cart.getItems().values().stream().forEach(item->{
//				Goods goods=goodsDAO.findById(item.getGoods().getId());
//				GoodsAccount gac=agent.loadAccountForGoodsNotNull(goods);
//				Price price=agent.caculatePrice(goods, item.getQuantity()+(gac==null?0:gac.getCumulative()));
//				item.setPrice(price.getPrice());
//				item.setPriceLevelName(price.getAgentLevel().getName());
//			});
//            String url = ServletUriComponentsBuilder.fromRequest(request).toUriString();
//            HashMap jspmap = jsApiParameterFactory.buildShare(wxConfig, url, TokenInfo.getJsapiTicket());
//            String jsonParam = JsonUtil.mapToJsonStr(jspmap);
//            model.addAttribute("jsapiParamJson",jsonParam);
//			model.addAttribute("imgPath", TokenInfo.IMG_HEAD_PATH
//					+ "/dmz/img/goods/");
//			return "tmall/shopcart/shopcart_pay";
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			return "tmall/index";
//		}
//	}
//
//	@RequestMapping(value = "/callback.html", method = RequestMethod.GET)
//	public String callback(
//			@RequestParam(value = "code", required = false) String code,
//			@RequestParam(value="agent",required=false) Integer agentId,
//			@RequestParam("state") String state,
//			HttpServletRequest request,HttpServletResponse response, Model model) {
//		try {
//			String url=ServletUriComponentsBuilder.fromRequest(request).toUriString();
//			LOG.debug("微商城获取共享地址网页授权获取用户信息回调URL:{}",url);
//			Agent agent = agentDAO.findById(agentId);
//			HashMap<String,String> map=getOpenIdHandler.getOpenId(wxConfig, code);
//			String openid = map.get("openid");
//			LOG.debug("获取到用户openid:{}", openid);
//			if(Objects.isNull(agent.getWxOpenid()) || agent.getWxOpenid().trim().isEmpty()){
//				agent.setWxOpenid(openid);
//				agentHandler.setWxOpenIdTo(agent, openid);
//			}
//
//			String accessToken=map.get("access_token");
//			String jsonParam=JsonUtil.mapToJsonStr(jsApiParameterFactory.buildEditAddress(wxConfig, url, accessToken));
//			LOG.debug("JSAPI Edit address Param：{}",jsonParam);
//			model.addAttribute("jsapiParamJson",jsonParam);
//
//			model.addAttribute("imgPath", TokenInfo.IMG_HEAD_PATH
//					+ "/dmz/img/goods/");
//			return "tmall/shopcart/shopcart_pay";
//		} catch (Exception exp) {
//			LOG.error("微商城结算页获取用户共享地址前授权失败",exp);
//			exp.printStackTrace();
//		}
//		return "tmall/shopcart/shopcart_pay";
//	}
