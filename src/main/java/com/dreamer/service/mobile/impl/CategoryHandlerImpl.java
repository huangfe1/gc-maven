package com.dreamer.service.mobile.impl;

import com.dreamer.domain.mall.goods.GoodsCategory;
import com.dreamer.repository.mobile.GoodsCategoryDao;
import com.dreamer.service.mobile.CategoryHandler;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class CategoryHandlerImpl extends BaseHandlerImpl<GoodsCategory> implements CategoryHandler {


    @Override
    public List<GoodsCategory> findByType(Integer type) {
        return goodsCategoryDao.getList("type",type);
    }

    @Override
    public List<GoodsCategory> findCategory(SearchParameter<GoodsCategory> parameter) {
        DetachedCriteria criteria = DetachedCriteria.forClass(GoodsCategory.class);
        criteria.addOrder(Order.asc("varStatus"));
        criteria.addOrder(Order.asc("id"));
        return goodsCategoryDao.searchByPage(parameter,criteria);
    }

    private GoodsCategoryDao goodsCategoryDao;

    public GoodsCategoryDao getGoodsCategoryDao() {
        return goodsCategoryDao;
    }

    @Autowired
    public void setGoodsCategoryDao(GoodsCategoryDao goodsCategoryDao) {
        this.goodsCategoryDao = goodsCategoryDao;
        super.setBaseDao(goodsCategoryDao);
    }
}
