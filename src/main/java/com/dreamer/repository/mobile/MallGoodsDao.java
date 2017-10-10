package com.dreamer.repository.mobile;

import com.dreamer.domain.pmall.goods.PmallGoods;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 12/07/2017.
 */
@Repository
public class MallGoodsDao extends BaseDaoImpl<PmallGoods> {

    public List<PmallGoods> findMallGoods(SearchParameter<PmallGoods> p) {
        DetachedCriteria dc = DetachedCriteria.forClass(PmallGoods.class);
        Example example = Example.create(p.getEntity()).enableLike(MatchMode.ANYWHERE);
        if(p.getEntity().getGoodsType()!=null&&p.getEntity().getGoodsType().getId()!=null){
            dc.add(Restrictions.eq("goodsType.id",p.getEntity().getGoodsType().getId()));
        }
        dc.add(example);
        dc.addOrder(Order.desc("sequence"));
        return searchByPage(p,dc);
    }

}


