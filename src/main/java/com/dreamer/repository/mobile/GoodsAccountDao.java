package com.dreamer.repository.mobile;

import com.dreamer.domain.account.GoodsAccount;
import com.dreamer.domain.user.Agent;
import com.dreamer.util.CommonUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by huangfei on 28/06/2017.
 */
@Repository
public class GoodsAccountDao extends BaseDaoImpl<GoodsAccount> {

    public GoodsAccount getMainGoodsAccount(Agent agent){
        DetachedCriteria dc = DetachedCriteria.forClass(GoodsAccount.class);
        dc.add(Restrictions.eq("user.id",agent.getId()));
        DetachedCriteria gdc = dc.createCriteria("goods");
        gdc.add(Restrictions.eq("benchmark",true));
        return (GoodsAccount) CommonUtil.getFirst(findByCriteria(dc,null,null));
    }

}
