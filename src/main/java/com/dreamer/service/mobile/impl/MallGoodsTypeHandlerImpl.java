package com.dreamer.service.mobile.impl;

import com.dreamer.domain.pmall.goods.PmallGoodsType;
import com.dreamer.repository.mobile.MallGoodsTypeDao;
import com.dreamer.service.mobile.MallGoodsTypeHandler;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 13/07/2017.
 */
@Service
public class MallGoodsTypeHandlerImpl extends BaseHandlerImpl<PmallGoodsType> implements MallGoodsTypeHandler {


    @Override
    public List<PmallGoodsType>  findSubType() {

        return mallGoodsTypeDao.findSubType();
    }

    @Override
    public List<PmallGoodsType> findMallGoodsTypes(SearchParameter<PmallGoodsType> searchParameter) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(searchParameter.getEntity().getClass());
        Example example = Example.create(searchParameter.getEntity());
        detachedCriteria.add(example);
        detachedCriteria.addOrder(Order.asc("orderIndex"));
        return mallGoodsTypeDao.searchByPage(searchParameter,detachedCriteria);
    }

    private MallGoodsTypeDao mallGoodsTypeDao;


    public MallGoodsTypeDao getMallGoodsTypeDao() {
        return mallGoodsTypeDao;
    }

    @Autowired
    public void setMallGoodsTypeDao(MallGoodsTypeDao mallGoodsTypeDao) {
        this.mallGoodsTypeDao = mallGoodsTypeDao;
        super.setBaseDao(mallGoodsTypeDao);
    }
}
