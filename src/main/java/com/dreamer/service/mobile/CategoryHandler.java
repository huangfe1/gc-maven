package com.dreamer.service.mobile;

import com.dreamer.domain.mall.goods.GoodsCategory;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 02/07/2017.
 */
public interface CategoryHandler extends BaseHandler<GoodsCategory>{

    List<GoodsCategory> findByType(Integer type);

    List<GoodsCategory> findCategory(SearchParameter<GoodsCategory> parameter);

}
