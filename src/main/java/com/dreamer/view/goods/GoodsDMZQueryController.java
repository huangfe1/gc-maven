package com.dreamer.view.goods;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.service.mobile.GoodsHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.SearchParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dmz/goods")
public class GoodsDMZQueryController {

//	@RequestMapping(value = { "/index.html", "/search.html" }, method = RequestMethod.GET)
//	public String index(
//			@ModelAttribute("parameter") SearchParameter<Goods> parameter,
//			HttpServletRequest request, Model model) {
//		try {
//			List<Goods> goods = goodsHandler.findGoods(parameter);
//			WebUtil.turnPage(parameter, request);
//			model.addAttribute("goods", goods);
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			LOG.error("产品查询失败", exp);
//		}
//		return "/goods/goods_index";
//	}

	
	@RequestMapping(value="/names.json",method=RequestMethod.GET)
	@ResponseBody
	public List<String> searchGoodsNames(@ModelAttribute("parameter") SearchParameter<Goods> parameter){
		List<Goods> goods=goodsHandler.likeList("name","%"+parameter.getEntity().getName()+"%");
		List<String> names=new ArrayList<>();
		goods.forEach((g)-> names.add(g.getName()));
		return names;
	}
	
//	@RequestMapping(value = "/detail.html", method = RequestMethod.GET)
//	public String detail(
//			@ModelAttribute("parameter") SearchParameter<Goods> parameter, Model model) {
//			List<AgentLevel> levels = agentLevelHandler.findAll();
//			Set<Price> prices = parameter.getEntity().getPrices();
//			HashMap<Integer, Price> maps = new HashMap<Integer, Price>();
//			Iterator<Price> ite = prices.iterator();
//			while (ite.hasNext()) {
//				Price p = ite.next();
//				maps.put(p.getAgentLevel().getId(), p);
//			}
//			model.addAttribute("levels", levels);
//			model.addAttribute("prices", maps);
//			return "/goods/goods_detail";
//
//	}


	@ModelAttribute("parameter")
	public SearchParameter<Goods> preprocess(@RequestParam("id") Optional<Integer> id) {
		SearchParameter<Goods> parameter = new SearchParameter<>();
		Goods goods;
		if (id.isPresent()) {
			goods =  goodsHandler.get(id.get());
		} else {
			goods = new Goods();
		}
		parameter.setEntity(goods);
		return parameter;
	}


	@Autowired
	private GoodsHandler goodsHandler;


}
