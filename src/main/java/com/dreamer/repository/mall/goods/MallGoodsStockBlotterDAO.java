package com.dreamer.repository.mall.goods;

import java.util.List;
import java.util.function.Function;

import com.dreamer.domain.pmall.goods.PmallGoodsStockBlotter;
import com.dreamer.domain.pmall.goods.PmallGoods;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.dao.hibernate.HibernateBaseDAO;

@Repository("mallGoodsStockBlotterDAO")
public class MallGoodsStockBlotterDAO extends HibernateBaseDAO<PmallGoodsStockBlotter> {
	private static final Logger log = LoggerFactory
			.getLogger(MallGoodsStockBlotterDAO.class);
	// property constants
	public static final String CHANGE = "change";
	public static final String CURRENT_BALANCE = "currentBalance";
	public static final String CURRENT_STOCK = "currentStock";
	public static final String POINT = "point";
	public static final String CURRENT_POINT = "currentPoint";

	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}
	
	

	@Override
	public List<PmallGoodsStockBlotter> searchEntityByPage(
			SearchParameter<PmallGoodsStockBlotter> p,
			Function<SearchParameter<PmallGoodsStockBlotter>, ? extends Object> getSQL,
			Function<Void, Boolean> getCacheQueries) {
		// TODO Auto-generated method stub
		return super.searchEntityByPage(p, (t)->{
			Example example = Example.create(t.getEntity()).enableLike(
					MatchMode.START);
			Criteria criteria = getHibernateTemplate().getSessionFactory()
					.getCurrentSession()
					.createCriteria(getClazz());
			criteria.add(example);
			PmallGoods goods=t.getEntity().getGoods();
			if(goods!=null && goods.getId()!=null){
				criteria.createCriteria("goods").add(Restrictions.idEq(goods.getId()));
			}
			criteria.addOrder(Order.asc("goods")).addOrder(Order.desc("operateTime"));
			return criteria;
		}, getCacheQueries);
	}

	public void save(PmallGoodsStockBlotter transientInstance) {
		log.debug("saving PmallGoodsStockBlotter instance");
		try {
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PmallGoodsStockBlotter persistentInstance) {
		log.debug("deleting PmallGoodsStockBlotter instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PmallGoodsStockBlotter findById(java.lang.Integer id) {
		log.debug("getting PmallGoodsStockBlotter instance with id: " + id);
		try {
			PmallGoodsStockBlotter instance = (PmallGoodsStockBlotter) getCurrentSession().get(
					"com.dreamer.repository.goods.PmallGoodsStockBlotter", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PmallGoodsStockBlotter> findByExample(PmallGoodsStockBlotter instance) {
		log.debug("finding PmallGoodsStockBlotter instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.dreamer.domain.mall.goods.PmallGoodsStockBlotter")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<PmallGoodsStockBlotter> findByProperty(String propertyName, Object value) {
		log.debug("finding PmallGoodsStockBlotter instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PmallGoodsStockBlotter as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PmallGoodsStockBlotter> findByChange(Object change) {
		return findByProperty(CHANGE, change);
	}

	public List<PmallGoodsStockBlotter> findByCurrentBalance(Object currentBalance) {
		return findByProperty(CURRENT_BALANCE, currentBalance);
	}

	public List<PmallGoodsStockBlotter> findByCurrentStock(Object currentStock) {
		return findByProperty(CURRENT_STOCK, currentStock);
	}

	public List<PmallGoodsStockBlotter> findByPoint(Object point) {
		return findByProperty(POINT, point);
	}

	public List<PmallGoodsStockBlotter> findByCurrentPoint(Object currentPoint) {
		return findByProperty(CURRENT_POINT, currentPoint);
	}

	public List<PmallGoodsStockBlotter> findAll() {
		log.debug("finding all PmallGoodsStockBlotter instances");
		try {
			String queryString = "from PmallGoodsStockBlotter";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PmallGoodsStockBlotter merge(PmallGoodsStockBlotter detachedInstance) {
		log.debug("merging PmallGoodsStockBlotter instance");
		try {
			PmallGoodsStockBlotter result = (PmallGoodsStockBlotter) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PmallGoodsStockBlotter instance) {
		log.debug("attaching dirty PmallGoodsStockBlotter instance");
		try {
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PmallGoodsStockBlotter instance) {
		log.debug("attaching clean PmallGoodsStockBlotter instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MallGoodsStockBlotterDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (MallGoodsStockBlotterDAO) ctx.getBean("StockBlotterDAO");
	}
}