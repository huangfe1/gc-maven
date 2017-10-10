package com.dreamer.service.mobile.impl;

import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.repository.mobile.GoodsStandardDao;
import com.dreamer.service.mobile.GoodsStandardHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangfei on 18/07/2017.
 */
@Service
public class GoodsStandardHandlerImpl extends BaseHandlerImpl<GoodsStandard> implements GoodsStandardHandler{

    private GoodsStandardDao goodsStandardDao;

    public GoodsStandardDao getGoodsStandardDao() {
        return goodsStandardDao;
    }

    @Autowired
    public void setGoodsStandardDao(GoodsStandardDao goodsStandardDao) {
        this.goodsStandardDao = goodsStandardDao;
        super.setBaseDao(goodsStandardDao);
    }
}
