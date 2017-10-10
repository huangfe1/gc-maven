package com.dreamer.repository.account;

import com.dreamer.domain.account.PurchaseRecord;
import com.dreamer.domain.user.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;
import ps.mx.otter.utils.dao.hibernate.HibernateBaseDAO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository("purchaseRecordDAO")
public class PurchaseRecordDAO extends HibernateBaseDAO<PurchaseRecord>{
	private static final Logger log = LoggerFactory.getLogger(PurchaseRecordDAO.class);


	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	protected void initDao() {
		// do nothing
	}

//	public List<PurchaseRecord> findRecordsForUser(User user){
//		log.debug("finding PurchaseRecord instance by user");
//		try {
//			String queryString = "from PurchaseRecord as model where model.user.id= ?";
//			Query queryObject = getCurrentSession().createQuery(queryString);
//			queryObject.setParameter(0, user.getId());
//			return queryObject.list();
//		} catch (RuntimeException re) {
//			log.error("find by user failed", re);
//			throw re;
//		}
//	}

//	/**
//	 * 获取没有通知的预存款记录
//	 * @return
//     */
//	public List<PurchaseRecord> getNeedNoticeRecord(){
//		String hql="from PurchaseRecord where hasNoticed = 0";
//		Query query =getCurrentSession().createQuery(hql);
//		return query.list();
//	}


	/**
	 * 代理查询
	 * @param p
	 * @param parent
     * @return
     */
	public List<PurchaseRecord> searchEntityByPage(SearchParameter<PurchaseRecord> p,
												  User parent) {
		// TODO Auto-generated method stub
		return super.searchEntityByPage(
				p,
				(t) -> {
					Example example = Example.create(t.getEntity()).enableLike(
							MatchMode.START);
					Criteria criteria =getCurrentSession()
							.createCriteria(PurchaseRecord.class);
                    criteria.add(example);
                    criteria.addOrder(Order.desc("updateTime"));//根据时间排序
                    criteria.addOrder(Order.desc("purchase_now"));//根据金钱排序 钱越来越多
                    if(Objects.nonNull(parent)){//上级不为空
                        criteria.createCriteria("agent").add(Restrictions.eq("id",parent.getId()));
                    }
					return criteria;
				}, null);
	}

	/**
	 * 管理员
	 * @param p
     * @return
     */
	public List<PurchaseRecord> searchEntityByPage(SearchParameter<PurchaseRecord> p) {
		// TODO Auto-generated method stub
		return super.searchEntityByPage(
				p,
				(t) -> {
					Example example = Example.create(t.getEntity()).enableLike(
							MatchMode.ANYWHERE);
					Criteria criteria = getCurrentSession()
							.createCriteria(PurchaseRecord.class);
					criteria.add(example);
//					criteria.addOrder(Order.desc("updateTime"));//时间排序
//					criteria.addOrder(Order.asc("type"));//积分量排序
//					criteria.addOrder(Order.asc("purchase"));//积分量排序
//					if(t.getEntity().getAgent()!=null&&!t.getEntity().getAgent().getRealName().equals("")){
//                        criteria.createCriteria("agent").add(Restrictions.eq("realName",t.getEntity().getAgent().getRealName()));
//					}
//					if(t.getEndTime()!=null || t.getStartTime()!=null){
//						criteria.add(Restrictions.between("updateTime",t.getStartTimeByDate(), t.getEndTimeByDate()));
//					}
					return criteria;
				}, null);
	}


    /**
     * 管理员
     * @param p
     * @return
     */
    public List<PurchaseRecord> downPurchaseRecord(SearchParameter<PurchaseRecord> t) {
                    Example example = Example.create(t.getEntity()).enableLike(
                            MatchMode.ANYWHERE);
                    Criteria criteria = getCurrentSession()
                            .createCriteria(PurchaseRecord.class);
                    criteria.add(example);
                    criteria.addOrder(Order.desc("updateTime"));//时间排序
                    criteria.addOrder(Order.asc("type"));//积分量排序
                    criteria.addOrder(Order.asc("purchase"));//积分量排序
                    if(t.getEntity().getAgent()!=null&&!t.getEntity().getAgent().getRealName().equals("")){
                        criteria.createCriteria("agent").add(Restrictions.eq("realName",t.getEntity().getAgent().getRealName()));
                    }
                    if(t.getEndTime()!=null || t.getStartTime()!=null){
                        criteria.add(Restrictions.between("updateTime",t.getStartTimeByDate(), t.getEndTimeByDate()));
                    }
                    return criteria.list();
    }


	public void save(PurchaseRecord transientInstance) {
		log.debug("saving GoodsAccount instance");
		try {
			transientInstance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			getCurrentSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}








	public PurchaseRecord merge(PurchaseRecord detachedInstance) {
		log.debug("merging PurchaseRecord instance");
		try {
			detachedInstance.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			PurchaseRecord result = (PurchaseRecord) getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}



}