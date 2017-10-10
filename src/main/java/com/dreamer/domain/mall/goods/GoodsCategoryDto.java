package com.dreamer.domain.mall.goods;

import java.util.List;

/**
 * Created by huangfei on 25/05/2017.
 * 商城分类
 */
public class GoodsCategoryDto {

    private GoodsCategory parentCategory;

    private List<GoodsCategory> goodsCategories;

    public GoodsCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(GoodsCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<GoodsCategory> getGoodsCategories() {
        return goodsCategories;
    }

    public void setGoodsCategories(List<GoodsCategory> goodsCategories) {
        this.goodsCategories = goodsCategories;
    }
}
