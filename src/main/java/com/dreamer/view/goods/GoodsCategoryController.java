package com.dreamer.view.goods;

import com.dreamer.domain.mall.goods.GoodsCategory;
import com.dreamer.domain.user.User;
import com.dreamer.repository.mobile.GoodsCategoryDao;
import com.dreamer.service.goods.GoodsHandler;
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

/**
 * Created by huangfei on 25/05/2017.
 */
@RequestMapping("/goods/category")
@RestController
public class GoodsCategoryController {
    @RequestMapping(value = "/edit.json", method = {RequestMethod.POST,RequestMethod.PUT})
    public Message edit(@ModelAttribute("parameter") GoodsCategory parameter, @RequestParam(value="imgFile",required=false) MultipartFile file,
                        HttpServletRequest request) {
        try{
            User user = (User) WebUtil.getCurrentUser(request);
            if (!user.isAdmin()) {
                throw new NotAuthorizationException();
            }
            if(!Objects.isNull(file)&&!file.isEmpty()){
             parameter.setImg(goodsHandler.saveImg(file));
            }
            goodsHandler.saveOrUpdateGoodsCategory(parameter);
            return Message.createSuccessMessage();
        }catch(Exception exp){
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }

    @RequestMapping(value = "/remove.json", method = {RequestMethod.DELETE})
    public Message delete(
            @ModelAttribute("parameter") GoodsCategory parameter,
            HttpServletRequest request, Model model) {
        try{
            User user = (User) WebUtil.getCurrentUser(request);
            if (!user.isAdmin()) {
                throw new NotAuthorizationException();
            }
            goodsHandler.removeGoodsCategory(parameter);
            return Message.createSuccessMessage();
        }catch(Exception exp){
            exp.printStackTrace();
            return Message.createFailedMessage(exp.getMessage());
        }
    }



    @ModelAttribute("parameter")
    public GoodsCategory preprocess(@RequestParam("id") Optional<Integer> id) {
        GoodsCategory goods;
        if (id.isPresent()) {
            goods =  categoryDao.get(id.get());
        } else {
            goods = new GoodsCategory();
        }
        return goods;
    }
    
    private String getImgType(String name){
        return name.substring(name.indexOf("."));
    }

    @Autowired
    private GoodsHandler goodsHandler;

    @Autowired
    private GoodsCategoryDao categoryDao;
}
