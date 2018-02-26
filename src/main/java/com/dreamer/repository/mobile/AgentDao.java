package com.dreamer.repository.mobile;

import com.dreamer.domain.user.Agent;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 28/06/2017.
 */
@Repository
public class AgentDao extends BaseDaoImpl<Agent> {


    public List<Agent> findByParentHasUnionId(Integer pid) {
        DetachedCriteria dc = DetachedCriteria.forClass(Agent.class);
        dc.add(Restrictions.eq("parent.id", pid));
        dc.add(Restrictions.isNotNull("wxUnionID"));
        dc.add(Restrictions.ne("wxUnionID", ""));
        //TODO 没有结果不知道这里会返回什么
        return findByCriteria(dc);
    }

    public List<Object[]> countAgentsByLevel(List<Integer> uids){
        String hql = "select level.name,count(gc.id) from GoodsAccount  as gc left join AgentLevel as level on level.id = gc.agentLevel.id left join Goods as g on g.id = gc.goods.id where g.benchmark = true and gc.user.id in :list   group by level.id  order by level.level ";
        Query query = currentSession().createQuery(hql);
        query.setParameter("list",uids);
        return query.getResultList();
    }


    public List<Agent> findAgents(SearchParameter<Agent> parameter) {
//        Example example = Example.create(parameter.getEntity()).enableLike(MatchMode.ANYWHERE);
        DetachedCriteria criteria = DetachedCriteria.forClass(Agent.class);
//        criteria.add(example);

        if(parameter.getEntity().getAgentCode()!=null){
//            criteria.add(Restrictions.eq("parent.id",parameter.getEntity().getParent().getId()));
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("agentCode","%"+parameter.getEntity().getAgentCode()+"%"));
            disjunction.add(Restrictions.like("mobile","%"+parameter.getEntity().getAgentCode()+"%"));
            disjunction.add(Restrictions.like("weixin","%"+parameter.getEntity().getAgentCode()+"%"));
            disjunction.add(Restrictions.like("realName","%"+parameter.getEntity().getAgentCode()+"%"));
            criteria.add(disjunction);
        }

        if(parameter.getEntity().getParent()!=null){
            Disjunction dis = Restrictions.disjunction();
            DetachedCriteria c = criteria.createCriteria("parent");
            dis.add(Restrictions.eq("id",parameter.getEntity().getParent().getId()));
            dis.add(Restrictions.eq("parent.id",parameter.getEntity().getParent().getId()));
            c .add(dis);
        }
        //时间
        if (parameter.getStartTime() != null || parameter.getEndTime() != null) {
            criteria.add(Restrictions.between("joinDate", parameter.getStartTimeByDate(), parameter.getEndTimeByDate()));
        }
        criteria.addOrder(Order.desc("joinDate"));
        return searchByPage(parameter,criteria);
    }

    public List<Agent> findAgentByLvAndPid(Integer lid,Integer pid){
        String hql = "select user from User as user left join GoodsAccount  as ga on ga.user.id = user.id left join AgentLevel as lv on lv.id = ga.agentLevel.id left join Goods as goods on goods.id = ga.goods.id where goods.benchmark = true and  lv.id = :lid and user.parent.id = :pid";
        Query query = currentSession().createQuery(hql);
        query.setParameter("lid",lid);
        query.setParameter("pid",pid);
        return query.list();
    }


    public List<Agent> findByTimeAndPid(Integer pid,String startTime,String endTime){
        String hql = "from User as user where user.joinDate >= :startTime and user.joinDate <= :endTime and user.parent.id = :pid";
        Query query = currentSession().createQuery(hql);
        query.setParameter("pid",pid);
        query.setParameter("startTime",startTime);
        query.setParameter("endTime",endTime);
        return query.list();
    }

}