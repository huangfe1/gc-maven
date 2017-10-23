package com.dreamer.view.goods;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsCategory;
import com.dreamer.repository.goods.GoodsDAO;
import com.dreamer.service.goods.GoodsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ps.mx.otter.utils.message.Message;

import java.util.Optional;

@RestController
@RequestMapping("/goods")
public class GoodsController {


	@RequestMapping(value = "/edit.json", method = RequestMethod.POST)
	public Message edit(@ModelAttribute("parameter") Goods goods, MultipartHttpServletRequest request,
						Double[] levelPrice, String[] voucherStrs,Integer[] buyAmounts,
						Integer[] levelId, Integer[] priceId, Integer cId) {
		try {
			if (goods.getAuthorizationType() != null) {
				goods.getAuthorizationType().setGoods(goods);
			}
			MultipartFile img = request.getFile("img");
			MultipartFile actImg = request.getFile("actImgFile");
			if (img != null) {
				String imgName = goodsHandler.saveImg(img);
				goods.setImgFile(imgName);
			}
			if (actImg != null) {
				String actName = goodsHandler.saveImg(actImg);
				goods.setActImg(actName);
			}
			GoodsCategory category = new GoodsCategory();
			category.setId(cId);
			goods.setCategory(category);
			goodsHandler.saveOrUpdateGoods(goods, levelPrice, voucherStrs,buyAmounts, levelId, priceId);
			return Message.createSuccessMessage();
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("产品编辑保存失败", exp);
			return Message.createFailedMessage();
		}
	}

	@RequestMapping(value = "/remove.json", method = RequestMethod.DELETE)
	public Message remove(@ModelAttribute("parameter") Goods goods, Model model) {
		try {
			goodsHandler.removeGoods(goods);
			return Message.createSuccessMessage();
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("产品删除失败", exp);
			return Message.createFailedMessage();
		}
	}

	@ModelAttribute("parameter")
	public Goods preprocess(@RequestParam("id") Optional<Integer> id) {
		Goods goods = new Goods();
		if (id.isPresent()) {
			goods = goodsDAO.findById(id.get());
		}
		return goods;
	}

	@Autowired
	private GoodsDAO goodsDAO;
	@Autowired
	private GoodsHandler goodsHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

}
