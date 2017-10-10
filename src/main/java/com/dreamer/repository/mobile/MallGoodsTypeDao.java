package com.dreamer.repository.mobile;

import com.dreamer.domain.pmall.goods.PmallGoodsType;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangfei on 13/07/2017.
 */
@Repository
public class MallGoodsTypeDao extends BaseDaoImpl<PmallGoodsType> {


    //找出二级菜单
    public List<PmallGoodsType> findSubType(){
        DetachedCriteria dc = DetachedCriteria.forClass(PmallGoodsType.class);
        dc.add(Restrictions.eq("type",1));
        dc.addOrder(Order.asc("orderIndex"));
        return findByCriteria(dc);
    }

}
