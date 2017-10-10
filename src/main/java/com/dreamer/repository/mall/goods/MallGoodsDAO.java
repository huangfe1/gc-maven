package com.dreamer.repository.mall.goods;

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

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Repository
public class MallGoodsDAO extends HibernateBaseDAO<PmallGoods>{
	private static final Logger log = LoggerFactory
			.getLogger(MallGoodsDAO.class);
	// property constants
	public static final String VERSION = "version";
	public static final String NAME = "name";
	public static final String SPEC = "spec";
	public static final String SHELF = "shelf";
	public static final String VOUCHER = "voucher";
	public static final String SEQUENCE = "sequence";
	public static final String PRICE = "price";
	public static final String POINT_PRICE = "pointPrice";
	public static final String MONEY_PRICE = "moneyPrice";


	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}


	/**
	 * 根据类型查找
	 * @param type
	 * @param limit
	 * @return
	 */
	public List<PmallGoods> findLimit(Integer type, Integer limit){
		String hql="from PmallGoods goods where goods.goodsType.id = ? and shelf = true ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0,type);
		query.setMaxResults(limit);
		return query.list();
	}
	

	@Override
	public List<PmallGoods> searchEntityByPage(SearchParameter<PmallGoods> p,
                                               Function<SearchParameter<PmallGoods>, ? extends Object> getSQL,
                                               Function<Void, Boolean> getCacheQueries) {
		// TODO Auto-generated method stub
		return super.searchEntityByPage(p, (t)->{
			Example example = Example.create(t.getEntity()).enableLike(
					MatchMode.ANYWHERE);
			Criteria criteria = getHibernateTemplate().getSessionFactory()
					.getCurrentSession()
					.createCriteria(getClazz());
			if(p.getEntity().getGoodsType()!=null&&p.getEntity().getGoodsType().getId()!=null){
				criteria.add(Restrictions.eq("goodsType.id",p.getEntity().getGoodsType().getId()));
			}
			criteria.add(example);
			criteria.addOrder(Order.asc("sequence"));
			return criteria;
		}, getCacheQueries);
	}
	
	public List<PmallGoods> search(SearchParameter<PmallGoods> p,
                                   Function<SearchParameter<PmallGoods>, ? extends Object> getSQL, Integer startStock, Integer endStock,
                                   Function<Void, Boolean> getCacheQueries) {
		// TODO Auto-generated method stub
		return super.searchEntityByPage(p, (t)->{
			Example example = Example.create(t.getEntity()).enableLike(
					MatchMode.START);
			Criteria criteria = getCurrentSession()
					.createCriteria(PmallGoods.class);
			criteria.add(example);
			Integer tempStartStock=startStock,tempEndStock=endStock;
			if(startStock==null){
				tempStartStock=-99999999;
			}
			if(endStock==null){
				tempEndStock=999999999;
			}
			criteria.add(Restrictions.between("stockQuantity", tempStartStock, tempEndStock));
			return criteria;
		}, getCacheQueries);
	}

	public void save(PmallGoods transientInstance) {
		log.debug("saving PmallGoods instance");
		try {
			transientInstance.setUpdateTime(new Date());
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}


	public void delete(PmallGoods persistentInstance) {
		log.debug("deleting PmallGoods instance");
		try {
			getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PmallGoods findById(java.lang.Integer id) {
		log.debug("getting PmallGoods instance with id: " + id);
		try {
			PmallGoods instance = (PmallGoods) getCurrentSession().get(
					"com.dreamer.domain.pmall.goods.PmallGoods", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<PmallGoods> findByExample(PmallGoods instance) {
		log.debug("finding PmallGoods instance by example");
		try {
			List results = getCurrentSession()
					.createCriteria("com.dreamer.domain.pmall.goods.PmallGoods")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<PmallGoods> findByProperty(String propertyName, Object value) {
		log.debug("finding PmallGoods instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PmallGoods as model where model."
					+ propertyName + "= ?";
			Query queryObject = getCurrentSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<PmallGoods> findByVersion(Object version) {
		return findByProperty(VERSION, version);
	}

	public List<PmallGoods> findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List<PmallGoods> findBySpec(Object spec) {
		return findByProperty(SPEC, spec);
	}

	public List<PmallGoods> findByShelf(Object shelf) {
		return findByProperty(SHELF, shelf);
	}

	public List<PmallGoods> findByVoucher(Object voucher) {
		return findByProperty(VOUCHER, voucher);
	}

	public List<PmallGoods> findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List<PmallGoods> findByPrice(Object price) {
		return findByProperty(PRICE, price);
	}

	public List<PmallGoods> findByPointPrice(Object pointPrice) {
		return findByProperty(POINT_PRICE, pointPrice);
	}

	public List<PmallGoods> findByMoneyPrice(Object moneyPrice) {
		return findByProperty(MONEY_PRICE, moneyPrice);
	}

	public List<PmallGoods> findAll() {
		log.debug("finding all PmallGoods instances");
		try {
			String queryString = "from PmallGoods";
			Query queryObject = getCurrentSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PmallGoods merge(PmallGoods detachedInstance) {
		log.debug("merging PmallGoods instance");
		try {
			detachedInstance.setUpdateTime(new Date());
			PmallGoods result = (PmallGoods) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}



	public void attachDirty(PmallGoods instance) {
		log.debug("attaching dirty PmallGoods instance");
		try {
			instance.setUpdateTime(new Date());
			getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PmallGoods instance) {
		log.debug("attaching clean PmallGoods instance");
		try {
			getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
					instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static MallGoodsDAO getFromApplicationContext(ApplicationContext ctx) {
		return (MallGoodsDAO) ctx.getBean("MallGoodsDAO");
	}
}