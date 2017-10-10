package com.dreamer.repository.mobile;

import com.dreamer.domain.mall.transfer.Transfer;
import com.dreamer.domain.user.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 01/07/2017.
 */
@Repository
public class TransferDao extends BaseDaoImpl<Transfer> {

    public List<Transfer> findRecords(SearchParameter<Transfer> p, User currentUser) {
        Example example = Example.create(p.getEntity());
        DetachedCriteria dc = DetachedCriteria.forClass(Transfer.class);
        dc.add(example);
        if (p.getEntity().getToAgent() != null&&!p.getEntity().getToAgent().getRealName().equals("")) {
            DetachedCriteria toAgent = dc.createCriteria("toAgent");
            toAgent.add(Restrictions.or(Restrictions.eq("realName", p.getEntity().getToAgent().getRealName()), Restrictions.eq("agentCode", p.getEntity().getToAgent().getAgentCode())));
        }
        if (currentUser.isAdmin()) {//查询所有
            if (p.getEntity().getFromAgent() != null&&!p.getEntity().getFromAgent().getRealName().equals("")) {
                DetachedCriteria fromAgent = dc.createCriteria("fromAgent");
                fromAgent.add(Restrictions.or(Restrictions.eq("realName", p.getEntity().getFromAgent().getRealName()), Restrictions.eq("agentCode", p.getEntity().getFromAgent().getAgentCode())));
            }
        } else {
            dc.add(Restrictions.or(Restrictions.eq("fromAgent.id", currentUser.getId()), Restrictions.eq("toAgent.id", currentUser.getId())));
        }
        if (p.getStartTime() != null && !p.getStartTime().equals("")) {
            dc.add(Restrictions.between("applyTime", p.getStartTimeByDate(), p.getEndTimeByDate()));
        }
        dc.addOrder(Order.desc("id"));//id排序
        return searchByPage(p, dc);
    }


}
