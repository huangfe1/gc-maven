package com.dreamer.service.mobile;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.StockBlotter;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 03/07/2017.
 */
public interface GoodsHandler extends BaseHandler<Goods> {

    List<Goods> findGoods(SearchParameter<Goods> parameter);

    //增加余额
    void addBalance(Integer gid,Integer quantity);

    //减少余额
    void reduceBalacne(Integer gid,Integer quantity);

    //增加库存
    void addStock(Integer gid,Integer quantity);

    void addStockBlotter(StockBlotter stockBlotter);

    //减少库存
    void reduceStock(Integer gid,Integer quantity);

    //增加库存总数
    void addStockSum(Integer gid,Integer quantity);

    //减少库存总数
    void reduceStockSum(Integer gid,Integer quantity);



}
