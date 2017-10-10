package com.dreamer.repository.mobile;

import com.dreamer.domain.mall.delivery.DeliveryNote;
import com.dreamer.domain.mall.delivery.DeliveryStatus;
import com.dreamer.domain.user.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangfei on 28/06/2017.
 */
@Repository
public class DeliveryNoteDao extends BaseDaoImpl<DeliveryNote> {

    public List<DeliveryNote> findDeliveryNotes(SearchParameter<DeliveryNote> parameter, User user) {
        Example example = Example.create(parameter.getEntity());
        DetachedCriteria dc = DetachedCriteria.forClass(DeliveryNote.class);
        if (!user.isAdmin()) {
            dc.add(Restrictions.or(Restrictions.eq("fromAgent.id", user.getId()), Restrictions.eq("toAgent.id", user.getId()), Restrictions.eq("applyAgent.id", user.getId())));
        }
//        addExample(dc,"applyAgent",parameter.getEntity().getApplyAgent());
//        System.out.println(parameter.getEntity().getApplyAgent().getAgentCode());
       if(parameter.getEntity().getApplyAgent()!=null){
           DetachedCriteria agent = dc.createCriteria("applyAgent");
           addRestraction(agent,"agentCode",parameter.getEntity().getApplyAgent().getAgentCode());
       }
        addExample(dc,"address",parameter.getEntity().getAddress());
        if(parameter.getStartTime()!=null&&!parameter.getStartTime().equals("")){
            dc.add(Restrictions.between("applyTime",parameter.getStartTimeByDate(),parameter.getEndTimeByDate()));
        }
        dc.add(example);
        dc.addOrder(Order.desc("id"));
        return searchByPage(parameter, dc);
    }

    public List<DeliveryNote> findDeliveryNotes(Integer uid, Integer nid) {
        DetachedCriteria dc = DetachedCriteria.forClass(DeliveryNote.class);
        if (nid == null) {
            //收货人或者发货人是我
            dc.add(Restrictions.or(Restrictions.eq("fromAgent.id", uid), Restrictions.eq("toAgent.id", uid), Restrictions.eq("applyAgent.id", uid)));
        } else {
            //根据id查找
            dc.add(Restrictions.eq("id", nid));
        }
        //时间倒序  查询30条
        dc.addOrder(Order.desc("applyTime"));
        List<DeliveryNote> items = findByCriteria(dc, 0, 30);
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public List<DeliveryNote> findNotDelivery(Integer limit) {
        DetachedCriteria dc = DetachedCriteria.forClass(DeliveryNote.class);
        dc.add(Restrictions.eq("status", DeliveryStatus.NEW));
        Criteria cri =  getCriteria(dc);
        cri.setMaxResults(limit);
        return findByCriteria(dc);
    }

    /**
     * 找出没有发货订单的货物数量总数
     */
    public List<Object[]> getOrdersItemCount(Integer limit) {
        String sql = "select g.`name` as goods_name,sum(item.quantity) as count from delivery_item as item left join goods as g on item.goods=g.id left join delivery_note as note on item.delivery_note=note.id    where note.id in (select d.id from ( select * from delivery_note  where status= :oneStatus or (status=:twoStatus and origin=:noteOrigin ) limit :limit ) as d)   GROUP BY g.id ";
        NativeQuery query = getHibernateTemplate().getSessionFactory().getCurrentSession().createNativeQuery(sql);
        query.setParameter("oneStatus", DeliveryStatus.CONFIRM.toString());
        query.setParameter("twoStatus", DeliveryStatus.NEW.toString());
        query.setParameter("noteOrigin", 0);
        query.setParameter("limit", limit);
        return query.list();
    }


}