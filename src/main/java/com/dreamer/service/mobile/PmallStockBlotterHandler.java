package com.dreamer.service.mobile;

import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 27/07/2017.
 */

public interface PmallStockBlotterHandler extends BaseHandler<PmallGoodsStockBlotter>{

    List<PmallGoodsStockBlotter> findStockBlotter(SearchParameter<PmallGoodsStockBlotter> p);


}
