package com.dreamer.view.pmall.goods;

import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.domain.user.User;
import com.dreamer.repository.mall.goods.MallGoodsTypeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mall/goods/type")
public class PmallGoodsTypeQueryController {

	@RequestMapping(value = { "/index.html", "/search.html" }, method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<PmallGoodsType> parameter,
			HttpServletRequest request, Model model) {
		try {
			List<PmallGoodsType> goods = mallGoodsTypeDAO.searchEntityByPage(parameter, null,
					(t) -> true);
			WebUtil.turnPage(parameter, request);
			model.addAttribute("goodsType", goods);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("产品类型查询失败", exp);
		}
		return "/mall/mallGoodsType_index";
	}
	

	

	@RequestMapping(value = "/edit.html", method = RequestMethod.GET)
	public String edit_enter(
			@ModelAttribute("parameter") SearchParameter<PmallGoodsType> parameter,
			HttpServletRequest request, Model model) {
		User user = (User) WebUtil.getCurrentUser(request);
		if (user.isAdmin()) {
			return "/mall/mallGoodsType_edit";
		} else {
			LOG.error("非管理员身份,无积分商城商品编辑权限");
			return "/common/403";
		}
	}
	


	@ModelAttribute("parameter")
	public SearchParameter<PmallGoodsType> preprocess(
            @RequestParam("id") Optional<Integer> id, @RequestParam("parentType") Optional<Integer> parentType) {
		SearchParameter<PmallGoodsType> parameter = new SearchParameter<>();
		PmallGoodsType goodsType;
		if (id.isPresent()) {
			goodsType =  mallGoodsTypeDAO.findById(id.get());
		} else {
			goodsType = new PmallGoodsType();
		}
		if(parentType.isPresent()){
            goodsType.setParentType(mallGoodsTypeDAO.findById(parentType.get()));
        }
		parameter.setEntity(goodsType);
		return parameter;
	}

	@Autowired
	private MallGoodsTypeDAO mallGoodsTypeDAO;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

}
