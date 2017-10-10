package com.dreamer.repository.mobile;

import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 27/07/2017.
 */
@Repository
public class PmallStockBlotterDao extends BaseDaoImpl<PmallGoodsStockBlotter> {
    public List<PmallGoodsStockBlotter> findStockBlotter(SearchParameter<PmallGoodsStockBlotter> p) {
        Example example = Example.create(p.getEntity());
        DetachedCriteria dc = DetachedCriteria.forClass(p.getEntity().getClass());
        dc.add(example);
        System.out.println(p.getEntity().getGoods().getId());
        addExample(dc,"goods",p.getEntity().getGoods());
        dc.addOrder(Order.desc("id"));
        return searchByPage(p,dc);
    }
}
