package com.dreamer.view.pmall.goods;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.user.Admin;
import com.dreamer.service.mobile.MallGoodsHandler;
import com.dreamer.service.mobile.PmallStockBlotterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/stock/pm")
public class PmallGoodsStockBlotterController {

	
	@RequestMapping(value={"/edit.json"},method=RequestMethod.POST)
	public Message edit_enter(@ModelAttribute("stockBlotter")PmallGoodsStockBlotter stock, Model model, HttpServletRequest request){
		try{
			Admin user=(Admin)WebUtil.getCurrentUser(request);
            PmallGoods pmallGoods = mallGoodsHandler.get(stock.getGoods().getId());
            mallGoodsHandler.changeStock(user, pmallGoods,stock.getChange(),stock.getRemark());
			return Message.createSuccessMessage("新增库存成功");
		}catch(Exception exp){
			exp.printStackTrace();
			return Message.createFailedMessage(exp.getMessage());
		}
	}
	
	
	@ModelAttribute("stockBlotter")
	public PmallGoodsStockBlotter preprocess(
			@RequestParam("id") Optional<Integer> id) {
		PmallGoodsStockBlotter stockBlotter;
		if (id.isPresent()) {
			stockBlotter =  stockBlotterHandler.get(id.get());
		} else {
			stockBlotter = new PmallGoodsStockBlotter();
		}
		return stockBlotter;
	}


	@Autowired
	private PmallStockBlotterHandler stockBlotterHandler;

	@Autowired
	private MallGoodsHandler mallGoodsHandler;
}
