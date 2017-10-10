package com.dreamer.view.pmall.goods;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.domain.user.User;
import com.dreamer.repository.mall.goods.MallGoodsDAO;
import com.dreamer.repository.mall.goods.MallGoodsTypeDAO;
import com.dreamer.service.mall.goods.MallGoodsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ps.mx.otter.exception.NotAuthorizationException;
import ps.mx.otter.utils.WebUtil;
import ps.mx.otter.utils.message.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/mall/goods")
public class PmallGoodsController {

	

	@RequestMapping(value = "/edit.json", method = {RequestMethod.POST,RequestMethod.PUT})
	public Message edit(
            @ModelAttribute("parameter") PmallGoods parameter, @RequestParam(value="img",required=false) MultipartFile file, @RequestParam(value = "wallImg",required = false) MultipartFile wallFile,
            HttpServletRequest request, Integer mType, Integer[] ids, String[] standardNames, Double[] standardPrices, Integer[] standardStocks) {

		try{
			User user = (User) WebUtil.getCurrentUser(request);
			if (!user.isAdmin()) {
				LOG.error("非管理员身份,无优惠商城商品编辑权限");
				throw new NotAuthorizationException(); 
			}
			PmallGoodsType pmallGoodsType = mallGoodsTypeDAO.findById(mType);
			parameter.setGoodsType(pmallGoodsType);
			String imgType  = null;
			String wallType =null;
			byte[] imgBytes =null;
			byte[] wallBytes =null;
			if(!Objects.isNull(file)&&!file.isEmpty()){
				 imgType =getImgType( file.getOriginalFilename());
				imgBytes = file.getBytes();
// 	mallGoodsHandler.addMallGoods(parameter,null,null,null,null,ids,standardNames,standardPrices,standardStocks);
				}

			if(!Objects.isNull(wallFile)&&!wallFile.isEmpty()){
				 wallType =getImgType( wallFile.getOriginalFilename());
				 wallBytes=wallFile.getBytes();
//					mallGoodsHandler.addMallGoods(parameter,null,null,null,null,ids,standardNames,standardPrices,standardStocks);
			}

			mallGoodsHandler.addMallGoods(parameter,imgType,wallType,imgBytes,wallBytes,ids,standardNames,standardPrices,standardStocks);
//				if(Objects.isNull(file)||file.isEmpty()){
//					mallGoodsHandler.modifyMallGoods(parameter,null,null);
//				}else{
//					String imgType =getImgType(file.getOriginalFilename());
//					mallGoodsHandler.modifyMallGoods(parameter,imgType,file.getBytes());
//				}
			return Message.createSuccessMessage();
		}catch(Exception exp){
			exp.printStackTrace();
			LOG.error("优惠商城商品维护失败",exp);
			return Message.createFailedMessage(exp.getMessage());
		}
	}
	
	private String getImgType(String name){
		return name.substring(name.indexOf("."));
	}
	
	@RequestMapping(value = "/remove.json", method = {RequestMethod.DELETE})
	public Message delete(
			@ModelAttribute("parameter") PmallGoods parameter,
			HttpServletRequest request, Model model) {		
		try{
			User user = (User) WebUtil.getCurrentUser(request);
			if (!user.isAdmin()) {
				LOG.error("非管理员身份,无优惠商城商品编辑权限");
				throw new NotAuthorizationException(); 
			} 
			mallGoodsHandler.removeMallGoods(parameter);
			return Message.createSuccessMessage();
		}catch(Exception exp){
			exp.printStackTrace();
			LOG.error("优惠商城商品删除失败",exp);
			return Message.createFailedMessage(exp.getMessage());
		}
	}
	


	@ModelAttribute("parameter")
	public PmallGoods preprocess(
			@RequestParam("id") Optional<Integer> id) {
		PmallGoods goods = null;
		if (id.isPresent()) {
			goods = (PmallGoods) mallGoodsDAO.findById(id.get());
		} else {
			goods = new PmallGoods();
		}
		return goods;
	}

	@Autowired
	private MallGoodsDAO mallGoodsDAO;
	@Autowired
	private MallGoodsTypeDAO mallGoodsTypeDAO;
	@Autowired
	private MallGoodsHandler mallGoodsHandler;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

}
