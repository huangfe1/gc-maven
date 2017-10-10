package com.dreamer.repository.mobile;

import com.dreamer.domain.user.AgentLevel;
import com.dreamer.util.CommonUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by huangfei on 28/06/2017.
 */
@Repository
public class AgentLevelDao extends BaseDaoImpl<AgentLevel> {


    public AgentLevel findLowestLevel() {
        DetachedCriteria criteria = DetachedCriteria.forClass(AgentLevel.class);
        criteria.setProjection(Projections.max("level"));
        Integer lid= (Integer) getCriteria(criteria).uniqueResult();
        criteria.setProjection(null);
        criteria.add(Restrictions.eq("level",lid));
        return (AgentLevel) CommonUtil.getFirst(findByCriteria(criteria, null, null));
    }


    public List<AgentLevel> findAllOrderByLevel() {
        DetachedCriteria dc = DetachedCriteria.forClass(AgentLevel.class);
        dc.addOrder(Order.asc("level"));
        return findByCriteria(dc,null,null);
    }


}