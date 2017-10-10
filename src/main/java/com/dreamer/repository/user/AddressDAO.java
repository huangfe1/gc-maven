package com.dreamer.repository.user;

import com.dreamer.domain.user.Address;
import com.dreamer.domain.user.VoucherTransfer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.dao.hibernate.HibernateBaseDAO;

@Repository("addressDao")
public class AddressDAO extends HibernateBaseDAO<Address> {
    private static final Logger log = LoggerFactory
            .getLogger(AddressDAO.class);

    private Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    protected void initDao() {
        // do nothing
    }

    /**
     * 找出第一个订单
     * @param id
     * @return
     */
    public VoucherTransfer findByAgentId(Integer id){

        try {
            String queryString = "from VoucherTransfer as model where model.userByToAgent.id=? order by updateTime desc";
            Query query=getCurrentSession().createQuery(queryString);
            query.setParameter(0,id);
            query.setMaxResults(1);
            return (VoucherTransfer)query.uniqueResult();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }




//	public List<Address> searchEntityByPage(
//			SearchParameter<Address> p,
//			Function<SearchParameter<Address>, ? extends Object> getSQL,
//			Function<Void, Boolean> getCacheQueries,Integer aid) {
//		// TODO Auto-generated method stub
//		return super.searchEntityByPage(p, (t)->{
//			Example example = Example.create(t.getEntity()).enableLike(
//					MatchMode.START);
//			Criteria criteria = getHibernateTemplate()
//					.getSessionFactory().getCurrentSession()
//					.createCriteria(getClazz());
//			criteria.add(example);
//			if (Objects.nonNull(aid)) {
//				criteria.add(Restrictions.or(Restrictions.eq("userByToAgent.id", agentId),Restrictions.eq("userByFromAgent.id", agentId)));
//			} else {
//				User agentTo = t.getEntity().getUserByToAgent();
//				if (Objects.nonNull(agentTo)) {
//					criteria.createCriteria("userByToAgent").add(
//							Restrictions.idEq(agentTo.getId()));
//				}
//				User agentFrom = t.getEntity().getUserByFromAgent();
//				if (Objects.nonNull(agentFrom)) {
//					criteria.createCriteria("userByFromAgent").add(
//							Restrictions.idEq(agentFrom.getId()));
//				}
//			}
////			if(t.getEndTime()!=null || t.getStartTime()!=null){
////				criteria.add(Restrictions.between("transferTime",t.getStartTimeByDate(), t.getEndTimeByDate()));
////			}
////			criteria.addOrder(Order.desc("transferTime"));
//			criteria.addOrder(Order.desc("id"));
//			return criteria;
//		}, getCacheQueries);
//	}

    /**
     * 保存一个地址
     * @param transientInstance
     */
    public void save(Address transientInstance) {
        log.debug("saving VoucherTransfer instance");
        try {
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }


    /**
     * 删除一个地址
     * @param persistentInstance
     */
    public void delete(Address persistentInstance) {
        log.debug("deleting Address instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    /**
     * 查找一个地址
     * @param id
     * @return
     */
    public Address findById(Integer id) {
        log.debug("getting VoucherTransfer instance with id: " + id);
        try {
            Address instance = getCurrentSession().get(
                    Address.class, id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    /**
     * 更新一个地址
     * @param detachedInstance
     * @return
     */
    public Address merge(Address detachedInstance) {
        log.debug("merging VoucherTransfer instance");
        try {
            Address result = (Address) getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }


}