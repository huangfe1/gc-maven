package com.dreamer.service.mobile.impl;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.repository.mobile.GoodsDao;
import com.dreamer.service.mobile.GoodsHandler;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
@Service
public class GoodsHandlerImpl extends BaseHandlerImpl<Goods> implements GoodsHandler{

    @Override
    public List<Goods> findGoods(SearchParameter<Goods> parameter) {
        Example example = Example.create(parameter.getEntity());
        DetachedCriteria criteria = DetachedCriteria.forClass(Goods.class);
        criteria.add(example);
        criteria.addOrder(Order.asc("order"));
        return goodsDao.searchByPage(parameter,criteria);
    }


    @Override
    @Transactional
    public void addBalance(Integer gid,Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.increaseCurrentBalance(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void reduceBalacne(Integer gid,Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.deductCurrentBalance(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void addStock(Integer gid,Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.increaseCurrentStock(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void reduceStock(Integer gid,Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.deductCurrentStock(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void addStockSum(Integer gid, Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.increaseStockSum(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void reduceStockSum(Integer gid, Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.deductStockSum(quantity);
        goodsDao.merge(goods);
    }

    private GoodsDao goodsDao;

    public GoodsDao getGoodsDao() {
        return goodsDao;
    }

    @Autowired
    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
        super.setBaseDao(goodsDao);
    }
}
