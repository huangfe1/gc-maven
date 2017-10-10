package com.dreamer.view.pmall.goods;

import com.dreamer.domain.mall.goods.StockBlotter;
import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.PmallStockBlotterHandler;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping("/stock/pm/")
public class PmallGoodsStockBlotterQueryController {

	@RequestMapping(value = { "/index.html", "/search.html" }, method = RequestMethod.GET)
	public String index(
			@ModelAttribute("parameter") SearchParameter<PmallGoods> param,
			Integer startStock, Integer endStock, Model model,
			HttpServletRequest request) {
		try {
			List<PmallGoods> goods = mallGoodsHandler.findMallGoods(param);
			WebUtil.turnPage(param, request);
			model.addAttribute("goods", goods);
			model.addAttribute("startStock", startStock);
			model.addAttribute("endStock", endStock);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		return "mall/stock_index";
	}

	@RequestMapping(value = { "/edit.html" }, method = RequestMethod.GET)
	public String edit_enter(
			@ModelAttribute("stockBlotter") SearchParameter<StockBlotter> param,
			Model model, HttpServletRequest request) {
		return "mall/stock_edit";
	}

	@RequestMapping(value = { "/detail.html" }, method = RequestMethod.GET)
	public String detail(
			@ModelAttribute("stockBlotter") SearchParameter<PmallGoodsStockBlotter> param,
			Model model, HttpServletRequest request) {
		List<PmallGoodsStockBlotter> stocks = stockBlotterHandler.findStockBlotter(param);
		model.addAttribute("stocks", stocks);
		return "mall/stock_detail";
	}

	@RequestMapping(value = { "/detail/query.json" }, method = RequestMethod.GET)
	@ResponseBody
	@JsonView(PmallGoodsStockBlotter.MallGoodsStockBlotterView.class)
	public DatatableDTO<PmallGoodsStockBlotter> detailQuery(
			@ModelAttribute("stockBlotter") SearchParameter<PmallGoodsStockBlotter> param, HttpServletRequest request) {
		DatatableDTO<PmallGoodsStockBlotter> dts = new DatatableDTO<>();
		try {
			List<PmallGoodsStockBlotter> stocks = stockBlotterHandler.findStockBlotter(param);
			dts.setData(stocks);
			WebUtil.turnPage(param, request);
			dts.setRecordsTotal(param.getTotalRows());
			dts.setRecordsFiltered(param.getTotalRows());
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("库存流水查询失败", exp);
		}
		return dts;
	}

	@ModelAttribute("parameter")
	public SearchParameter<PmallGoods> preprocess(
			@RequestParam("id") Optional<Integer> id) {
		SearchParameter<PmallGoods> parameter = new SearchParameter<>();
		PmallGoods goods ;
		if (id.isPresent()) {
			goods =  mallGoodsHandler.get(id.get());
		} else {
			goods = new PmallGoods();
		}
		parameter.setEntity(goods);
		return parameter;
	}

	@ModelAttribute("stockBlotter")
	public SearchParameter<PmallGoodsStockBlotter> getStockBlotter(
			@RequestParam("id") Optional<Integer> id,
			@RequestParam("goodsId") Optional<Integer> goodsId) {
		SearchParameter<PmallGoodsStockBlotter> parameter = new SearchParameter<>();
		PmallGoodsStockBlotter stockBlotter;
		if (id.isPresent()) {
			stockBlotter =  stockBlotterHandler.get(id.get());
		} else {
			stockBlotter = new PmallGoodsStockBlotter();
			if (goodsId.isPresent()) {
				PmallGoods goods = mallGoodsHandler.get(goodsId.get());
				stockBlotter.setGoods(goods);
			}
		}
		parameter.setEntity(stockBlotter);
		return parameter;
	}

	@Autowired
	private MallGoodsHandler mallGoodsHandler;

	@Autowired
	private PmallStockBlotterHandler stockBlotterHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());
}
