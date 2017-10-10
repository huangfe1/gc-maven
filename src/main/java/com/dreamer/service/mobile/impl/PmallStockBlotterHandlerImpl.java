package com.dreamer.service.mobile.impl;

import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.repository.mobile.PmallStockBlotterDao;
import com.dreamer.service.mobile.PmallStockBlotterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 27/07/2017.
 */
@Service
public class PmallStockBlotterHandlerImpl extends BaseHandlerImpl<PmallGoodsStockBlotter> implements PmallStockBlotterHandler {


    @Override
    public List<PmallGoodsStockBlotter> findStockBlotter(SearchParameter<PmallGoodsStockBlotter> p) {
        return stockBlotterDao.findStockBlotter(p);
    }

    private PmallStockBlotterDao stockBlotterDao;

    public PmallStockBlotterDao getStockBlotterDao() {
        return stockBlotterDao;
    }

    @Autowired
    public void setStockBlotterDao(PmallStockBlotterDao stockBlotterDao) {
        this.stockBlotterDao = stockBlotterDao;
        this.setBaseDao(stockBlotterDao);
    }
}
