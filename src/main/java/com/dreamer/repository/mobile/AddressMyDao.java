package com.dreamer.repository.mobile;

import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangfei on 28/06/2017.
 */
@Repository
public class AddressMyDao extends BaseDaoImpl<AddressMy> {

    public List<AddressMy> findAddressByAgent(Agent agent, Agent toAgent) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AddressMy.class);
        criteria.add(Restrictions.eq("agent", agent));//某人的地址
        if (toAgent.getAgentCode() != null) {//收货人有编号 匹配编号】
            criteria.add(Restrictions.eq("consigneeCode", toAgent.getAgentCode()));//匹配某人名字的
        } else {//匹配名字
            criteria.add(Restrictions.eq("consignee", toAgent.getRealName()));
        }
        List<AddressMy> addresses = findByCriteria(criteria, null, null);
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        return addresses;
    }


    public List<AddressMy> findAddressByAgent(Agent agent) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AddressMy.class);
        criteria.add(Restrictions.eq("agent", agent));//某人的地址
        List<AddressMy> addresses = findByCriteria(criteria, null, null);
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
        return addresses;
    }
}
