package com.dreamer.repository.mobile;

import com.dreamer.domain.user.AccountsTransfer;
import com.dreamer.domain.user.User;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.List;

/**
 * Created by huangfei on 26/06/2017.
 */
@Repository
public class AccountsTransferDao extends BaseDaoImpl<AccountsTransfer> {

    public List<AccountsTransfer> findAccountsTransfer(SearchParameter<AccountsTransfer> parameter, User user) {
        Example example = Example.create(parameter.getEntity());
        DetachedCriteria dc = DetachedCriteria.forClass(AccountsTransfer.class);
        dc.add(example);
        if (!user.isAdmin()) {
            dc.add(Restrictions.or(Restrictions.eq("toAgent.id", user.getId()), Restrictions.eq("fromAgent.id", user.getId())));
        }
//        if (parameter.getEntity().getToAgent() != null) {
//            Agent agent =  parameter.getEntity().getToAgent();
//            //当事人
//            if(agent!=null){
//                DetachedCriteria dcAgent = dc.createCriteria("toAgent");
//                Example example1 = Example.create(agent).enableLike(MatchMode.ANYWHERE);
//                dcAgent.add(example1);
//            }
//        }
        addExample(dc, "toAgent", parameter.getEntity().getToAgent());
        addExample(dc, "fromAgent", parameter.getEntity().getFromAgent());
//        addExample(dc, "toAgent", parameter.getEntity().getToAgent());
//        addExample(dc, "fromAgent", parameter.getEntity().getFromAgent());
        dc.addOrder(Order.desc("id"));
        return searchByPage(parameter, dc);
    }


}
