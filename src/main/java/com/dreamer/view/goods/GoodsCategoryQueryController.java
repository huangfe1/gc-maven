package com.dreamer.view.goods;

import com.dreamer.domain.mall.goods.GoodsCategory;
import com.dreamer.domain.mall.goods.GoodsCategoryDto;
import com.dreamer.domain.user.User;
import com.dreamer.service.mobile.CategoryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by huangfei on 25/05/2017.
 * 商城分类
 */
@Controller
@RequestMapping("/goods/category")
public class GoodsCategoryQueryController {

    @RequestMapping(value = {"/index.html", "/search.html"}, method = RequestMethod.GET)
    public String index(
            @ModelAttribute("parameter") SearchParameter<GoodsCategory> parameter,
            HttpServletRequest request, Model model) {
        try {
            List<GoodsCategory> goods = categoryHandler.findCategory(parameter);
            WebUtil.turnPage(parameter, request);
            model.addAttribute("goodsType", goods);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return "/goods/goodsCategory_index";
    }

    @RequestMapping({"/show/{parentId}","/show"})
    public String show(Model model,@PathVariable(required = false) Integer parentId) {
        List<GoodsCategoryDto> dtos = new ArrayList<>();
        List<GoodsCategory> parents = categoryHandler.getList("type",0);
        if(parentId==null) {
            for (GoodsCategory parent : parents) {
                GoodsCategoryDto dto = new GoodsCategoryDto();
                dto.setParentCategory(parent);
                dto.setGoodsCategories(categoryHandler.getList("parentType.id",parent.getId()));
                dtos.add(dto);
            }
        }else {
            GoodsCategoryDto dto = new GoodsCategoryDto();
            GoodsCategory parent = categoryHandler.get(parentId);
            dto.setParentCategory(parent);
            dto.setGoodsCategories(categoryHandler.getList("parentType.id",parent.getId()));
            dtos.add(dto);
        }
        model.addAttribute("dtos", dtos);
        return "mobile/category";
    }

    @RequestMapping(value = "/edit.html", method = RequestMethod.GET)
    public String edit_enter(
            @ModelAttribute("parameter") SearchParameter<GoodsCategory> parameter,
            HttpServletRequest request, Model model) {
        User user = (User) WebUtil.getCurrentUser(request);
        if (user.isAdmin()) {
            return "/goods/goodsCategory_edit";
        } else {
            return "/common/403";
        }
    }

    @ModelAttribute("parameter")
    public SearchParameter<GoodsCategory> preprocess(
            @RequestParam("id") Optional<Integer> id, @RequestParam("parentType") Optional<Integer> parentType) {
        SearchParameter<GoodsCategory> parameter = new SearchParameter<>();
        GoodsCategory goodsType;
        if (id.isPresent()) {
            goodsType = categoryHandler.get(id.get());
        } else {
            goodsType = new GoodsCategory();
        }
        if (parentType.isPresent()) {
            goodsType.setParentType(categoryHandler.get(parentType.get()));
        }
        parameter.setEntity(goodsType);
        return parameter;
    }


    @Autowired
    private CategoryHandler categoryHandler;
}
