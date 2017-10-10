package com.dreamer.service.mobile;

import com.dreamer.domain.pmall.goods.PmallGoodsType;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 13/07/2017.
 */
public interface MallGoodsTypeHandler extends BaseHandler<PmallGoodsType>{

    List<PmallGoodsType> findSubType();

    List<PmallGoodsType> findMallGoodsTypes(SearchParameter<PmallGoodsType> searchParameter);

}
