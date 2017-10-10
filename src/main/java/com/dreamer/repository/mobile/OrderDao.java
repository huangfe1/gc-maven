package com.dreamer.repository.mobile;

import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderStatus;
import com.dreamer.domain.pmall.order.PaymentStatus;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 11/07/2017.
 */
@Repository
public class OrderDao extends BaseDaoImpl<Order> {

    public List<Order> findAllOrders(SearchParameter<Order> p) {
        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
        Example example = Example.create(p.getEntity()).enableLike(MatchMode.ANYWHERE);
        dc.add(example);
        if (p.getStartTime() != null && p.getEndTime() != null) {
            dc.add(Restrictions.between("orderTime", p.getStartTimeByDate(), p.getEndTimeByDate()));
        }
        return findByCriteria(dc, null, null);
    }


    public List<Order> findNewOrders(Integer limit) {
        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
        dc.add(Restrictions.eq("status", OrderStatus.NEW));
        dc.add(Restrictions.or(Restrictions.eq("paymentStatus", PaymentStatus.PAID), Restrictions.eq("paymentStatus", PaymentStatus.PAIDERROR)));
        Criteria criteria = getCriteria(dc);
        criteria.setMaxResults(limit);
        //        SearchParameter<Order> parameter = new SearchParameter<>();
//        Order orderTemp = new Order();
//        orderTemp.setStatus(OrderStatus.NEW);
//        orderTemp.setPaymentStatus(PaymentStatus.PAID);
//        parameter.setEntity(orderTemp);
        return findByCriteria(dc,null,null);
    }





//    public List<Object[]> getOrdersItemCount() {
//        DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
//        dc.add(Restrictions.eq("status", OrderStatus.NEW));
//        dc.add(Restrictions.or(Restrictions.eq("paymentStatus", PaymentStatus.PAID), Restrictions.eq("paymentStatus", PaymentStatus.PAIDERROR)));
//        DetachedCriteria item = dc.createCriteria("items");
//        DetachedCriteria goods = item.createCriteria("mallGoods");
//        goods.setProjection(Projections.property("name"));
//        goods.setProjection(Projections.groupProperty("name"));
//        item.setProjection(Projections.sum("quantity"));
//        ProjectionList list = Projections.projectionList();
//        list.add();
//        Criteria criteria = getCriteria(dc);
//        List list = criteria.list();
//        System.out.println(list.get(0));
//        return   list;
//    }


    /**
     * 找出没有发货订单的货物数量总数
     */
    public List<Object[]> getOrdersItemCount(Integer limit){
        String sql="select goods.`name` as goods_name,sum(item.quantity) as count from order_item as item left join mall_goods as goods on item.goods=goods.id left join pmall_order as po on item.order_id=po.id   where po.id in (select p.id from (select * from `pmall_order` where status=? and  payment_status=? limit ?) as p ) GROUP BY goods.id";
        SQLQuery query=getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.setParameter(0,0);
        query.setParameter(1,1);
        query.setParameter(2,limit);
        return query.list();

    }


}
