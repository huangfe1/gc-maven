package com.dreamer.view.pmall.goods;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.MallGoodsTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.DatatableDTO;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mall/goods")
public class PmallGoodsQueryController {

	@RequestMapping(value = { "/index.html", "/search.html" }, method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<PmallGoods> parameter,
			HttpServletRequest request, Model model) {
		try {
			/*List<PmallGoods> goods = mallGoodsDAO.searchEntityByPage(parameter, null,
					(t) -> true);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("goods", goods);*/
			List<PmallGoodsType> pmallGoodsTypes = mallGoodsTypeHandler.findSubType();
			model.addAttribute("types", pmallGoodsTypes);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("产品查询失败", exp);
		}
		return "/mall/mallGoods_index";
	}
	
	@RequestMapping(value = { "/query.json", "/search.json" }, method = RequestMethod.GET)
	@ResponseBody
	public DatatableDTO<PmallGoods> queryASJson(
			@ModelAttribute("parameter") SearchParameter<PmallGoods> parameter,
			HttpServletRequest request) {
		DatatableDTO<PmallGoods> dts=new DatatableDTO<>();
		try {
            List<PmallGoods> goods = mallGoodsHandler.findMallGoods(parameter);
            WebUtil.turnPage(parameter, request);
			dts.setData(goods);
			dts.setRecordsFiltered(parameter.getTotalRows());
			dts.setRecordsTotal(parameter.getTotalRows());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("产品查询失败", exp);
		}
		return dts;
	}
	

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String edit_enter(
			@ModelAttribute("parameter") SearchParameter<PmallGoods> parameter,
			HttpServletRequest request, Model model) {
		User user = (User) WebUtil.getCurrentUser(request);
		if (user.isAdmin()) {
//			model.addAttribute("types", PmallGoodsType.values());
            List<PmallGoodsType> pmallGoodsTypes = mallGoodsTypeHandler.findSubType();
            model.addAttribute("types", pmallGoodsTypes);
			return "/mall/mallGoods_edit";
		} else {
			LOG.error("非管理员身份,无优惠商城商品编辑权限");
			return "/common/403";
		}

	}
	


	@ModelAttribute("parameter")
	public SearchParameter<PmallGoods> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<PmallGoods> parameter = new SearchParameter<PmallGoods>();
		PmallGoods goods;
		if (id.isPresent()) {
			goods =  mallGoodsHandler.get(id.get());
		} else {
			goods = new PmallGoods();
		}
		parameter.setEntity(goods);
		return parameter;
	}

	@Autowired
	private MallGoodsHandler mallGoodsHandler;

	@Autowired
	private MallGoodsTypeHandler mallGoodsTypeHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

}
