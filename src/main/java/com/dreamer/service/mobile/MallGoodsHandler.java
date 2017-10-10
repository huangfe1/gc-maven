package com.dreamer.service.mobile;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.user.Admin;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 12/07/2017.
 */
public interface MallGoodsHandler extends  BaseHandler<PmallGoods>{

    List<PmallGoods> findMallGoods(SearchParameter<PmallGoods> p);

    void  deductMallGoodsAccount(PmallGoods pmallGoods, Integer qunatity);

    void  increaseMallGoodsAccount(PmallGoods pmallGoods, Integer qunatity);

    PmallGoodsStockBlotter changeStock(Admin admin, PmallGoods pmallGoods, Integer quantity, String remark);

}
