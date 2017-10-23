package com.dreamer.service.mobile.impl;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.StockBlotter;
import com.dreamer.repository.mobile.GoodsDao;
import com.dreamer.repository.mobile.StockBlotterDao;
import com.dreamer.service.mobile.GoodsAccountHandler;
import com.dreamer.service.mobile.GoodsHandler;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ps.mx.otter.utils.SearchParameter;

import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
@Service
public class GoodsHandlerImpl extends BaseHandlerImpl<Goods> implements GoodsHandler {

    @Override
    public List<Goods> findGoods(SearchParameter<Goods> parameter) {
        Example example = Example.create(parameter.getEntity());
        DetachedCriteria criteria = DetachedCriteria.forClass(Goods.class);
        criteria.add(example);
        criteria.addOrder(Order.asc("order"));
        return goodsDao.searchByPage(parameter, criteria);
    }


    @Override
    @Transactional
    public void addBalance(Integer gid, Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.increaseCurrentBalance(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void reduceBalacne(Integer gid, Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.deductCurrentBalance(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void addStock(Integer gid, Integer quantity) {
        Goods goods = goodsDao.get(gid);
        goods.increaseCurrentStock(quantity);
        goodsDao.merge(goods);
    }

    @Override
    @Transactional
    public void addStockBlotter(StockBlotter stockBlotter) {
        Goods goods = get(stockBlotter.getGoods().getId());
        stockBlotter.setGoods(goods);
        stockBlotter.setCurrentBalance(goods.getCurrentBalance());
        stockBlotter.setCurrentStock(goods.getCurrentStock());
        stockBlotter.setOperateTime(new Date());
        sb.merge(stockBlotter);
    }

    @Override
    @Transactional
    public void reduceStock(Integer gid, Integer quantity) {
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


    @Override
    @Transactional
    public void adminAddStock(StockBlotter stock) {

        Integer gid = stock.getGoods().getId();
        Integer quantity = stock.getChange();

        if (stock.getChange() == 0) {
            return;
        }
        if (stock.getChange() > 0) {

            addBalance(gid, quantity);
            addStock(gid, quantity);
            addStockSum(gid, quantity);
        } else {
            reduceBalacne(gid, quantity);
            reduceStock(gid, quantity);
            reduceStockSum(gid, quantity);
        }
        addStockBlotter(stock);
//        addBalance(gid, quantity);
//        addStock(gid, quantity);
//        addStockSum(gid,quantity);
//        增加总公司虚拟账户的
//        MutedUser mutedUser = muteUserHandler.getMuteUser();
//        Goods goods = goodsHandler.get(stock.getGoods().getId());
//        GoodsAccount goodsAccount = goodsAccountHandler.getGoodsAccount(mutedUser, goods);
//        goodsAccountHandler.increaseGoodsAccount(goodsAccount, stock.getChange());
//        goodsAccountHandler.merge(goodsAccount);
    }



    private GoodsDao goodsDao;

    @Autowired
    private StockBlotterDao sb;

    public GoodsDao getGoodsDao() {
        return goodsDao;
    }

    @Autowired
    public void setGoodsDao(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
        super.setBaseDao(goodsDao);
    }

    @Autowired
    private GoodsAccountHandler goodsAccountHandler;

}
