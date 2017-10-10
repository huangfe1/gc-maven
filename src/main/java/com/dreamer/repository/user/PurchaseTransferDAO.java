package com.dreamer.repository.user;

import com.dreamer.domain.user.PurchaseTransfer;
import com.dreamer.domain.user.User;
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
import java.util.Objects;
import java.util.function.Function;

@Repository("purchaseTransferDAO")
public class PurchaseTransferDAO extends HibernateBaseDAO<PurchaseTransfer> {
    private static final Logger log = LoggerFactory
            .getLogger(PurchaseTransferDAO.class);
    // property constants
    public static final String VERSION = "version";
    public static final String REMARK = "remark";
    public static final String PURCHASE = "purchase";

    private Session getCurrentSession() {
        return getSessionFactory().getCurrentSession();
    }

    protected void initDao() {
        // do nothing
    }

    /**
     * 找出第一个订单
     *
     * @param id
     * @return
     */
    public PurchaseTransfer findByAgentId(Integer id) {

        try {
            String queryString = "from PurchaseTransfer as model where model.userByToAgent.id=? order by updateTime desc";
            Query query = getCurrentSession().createQuery(queryString);
            query.setParameter(0, id);
            query.setMaxResults(1);
            return (PurchaseTransfer) query.uniqueResult();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List<PurchaseTransfer> searchEntityByPage(
            SearchParameter<PurchaseTransfer> p,
            Function<SearchParameter<PurchaseTransfer>, ? extends Object> getSQL,
            Function<Void, Boolean> getCacheQueries, Integer agentId) {
        // TODO Auto-generated method stub
        return super.searchEntityByPage(p, (t) -> {
            Example example = Example.create(t.getEntity()).enableLike(
                    MatchMode.START);
            Criteria criteria = getHibernateTemplate()
                    .getSessionFactory().getCurrentSession()
                    .createCriteria(getClazz());
            criteria.add(example);
            Criteria to = criteria.createCriteria("userByToAgent");
            Criteria from = criteria.createCriteria("userByFromAgent");
            if (Objects.nonNull(agentId)) {
                criteria.add(Restrictions.or(Restrictions.eq("userByToAgent.id", agentId), Restrictions.eq("userByFromAgent.id", agentId)));
            } else {
                User agentTo = t.getEntity().getUserByToAgent();
                if (Objects.nonNull(agentTo)) {
                    to.add(
                            Restrictions.eq("id", agentTo.getId()));
                }
                User agentFrom = t.getEntity().getUserByFromAgent();
                if (Objects.nonNull(agentFrom)) {
                    from.add(
                            Restrictions.eq("id", agentFrom.getId()));
                }
            }
            if (t.getEndTime() != null || t.getStartTime() != null) {
                criteria.add(Restrictions.between("transferTime", t.getStartTimeByDate(), t.getEndTimeByDate()));
            }
//			criteria.addOrder(Order.desc("transferTime"));
            criteria.addOrder(Order.desc("id"));
            return criteria;
        }, getCacheQueries);
    }

    public void save(PurchaseTransfer transientInstance) {
        log.debug("saving PurchaseTransfer instance");
        try {
            transientInstance.setUpdateTime(new Date());
            getCurrentSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }


    public void delete(PurchaseTransfer persistentInstance) {
        log.debug("deleting PurchaseTransfer instance");
        try {
            getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PurchaseTransfer findById(Integer id) {
        log.debug("getting PurchaseTransfer instance with id: " + id);
        try {
            PurchaseTransfer instance = (PurchaseTransfer) getCurrentSession().get(
                    "com.dreamer.domain.user.PurchaseTransfer", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List<PurchaseTransfer> findByExample(PurchaseTransfer instance) {
        log.debug("finding PurchaseTransfer instance by example");
        try {
            List<PurchaseTransfer> results = getCurrentSession()
                    .createCriteria("com.dreamer.domain.user.PurchaseTransfer")
                    .add(Example.create(instance))
                    .addOrder(Order.asc("transferTime")).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List<PurchaseTransfer> findByProperty(String propertyName, Object value) {
        log.debug("finding PurchaseTransfer instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from PurchaseTransfer as model where model."
                    + propertyName + "= ?";
            Query queryObject = getCurrentSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List<PurchaseTransfer> findByVersion(Object version) {
        return findByProperty(VERSION, version);
    }

    public List<PurchaseTransfer> findByRemark(Object remark) {
        return findByProperty(REMARK, remark);
    }

    public List<PurchaseTransfer> findByPoint(Object point) {
        return findByProperty(PURCHASE, point);
    }

    public List<PurchaseTransfer> findAll() {
        log.debug("finding all PurchaseTransfer instances");
        try {
            String queryString = "from PurchaseTransfer";
            Query queryObject = getCurrentSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PurchaseTransfer merge(PurchaseTransfer detachedInstance) {
        log.debug("merging PurchaseTransfer instance");
        try {
            detachedInstance.setUpdateTime(new Date());
            PurchaseTransfer result = (PurchaseTransfer) getCurrentSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PurchaseTransfer instance) {
        log.debug("attaching dirty PurchaseTransfer instance");
        try {
            getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PurchaseTransfer instance) {
        log.debug("attaching clean PurchaseTransfer instance");
        try {
            getCurrentSession().buildLockRequest(LockOptions.NONE).lock(
                    instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }


    public static PurchaseTransferDAO getFromApplicationContext(
            ApplicationContext ctx) {
        return (PurchaseTransferDAO) ctx.getBean("PurchaseTransferDAO");
    }
}