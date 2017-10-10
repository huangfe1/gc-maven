package com.dreamer.repository.pmall.order;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.order.Order;
import com.dreamer.domain.pmall.order.OrderItem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.dao.hibernate.HibernateBaseDAO;

import java.util.List;


@Repository
public class OrderItemDAO extends HibernateBaseDAO<Order> {
	private static final Logger log = LoggerFactory.getLogger(OrderItemDAO.class);
	// property constants
	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}


	public OrderItem merge(OrderItem detachedInstance) {
		log.debug("merging Order instance");
		try {
			OrderItem result = (OrderItem) getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}


	/**
	 * 找出某个时间之前的某个货物某个人的订单
	 */
	public List<OrderItem> getOrdersDateBefore(Integer uid, PmallGoods goods){
		//找出所有超出1胸膜的订单
		String hql ="from OrderItem item   where goodsId=? and item.order.user=? and item.order.orderTime>=? and item.order.paymentStatus=1";
		Query query= getCurrentSession().createQuery(hql);
		query.setInteger(0,goods.getId());
		query.setInteger(1,uid);
		query.setTimestamp(2,goods.getStartTime());
		List<OrderItem> orderItems =query.list();
		return  orderItems;
	}

	/**
	 * 找出所有胸膜数量超过1的
	 */
	public List<OrderItem> getOrdersMoreThan1(){
		//找出所有超出1胸膜的订单
		String hql ="from OrderItem  item where goodsId=40 and quantity > 1";
		Query query= getCurrentSession().createQuery(hql);
		List<OrderItem> orderItems =query.list();
        //每一个胸膜的单上架都返利了
        return  orderItems;
	}

	public static OrderItemDAO getFromApplicationContext(ApplicationContext ctx) {
		return (OrderItemDAO) ctx.getBean("OrderItemDAO");
	}
}