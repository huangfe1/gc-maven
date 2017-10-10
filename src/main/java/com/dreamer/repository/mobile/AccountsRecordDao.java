package com.dreamer.repository.mobile;

import com.dreamer.domain.user.AccountsRecord;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.User;
import com.dreamer.domain.user.enums.AccountsType;
import com.wxjssdk.util.DateUtil;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import ps.mx.otter.utils.SearchParameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 26/06/2017.
 */
@Repository
public class AccountsRecordDao extends BaseDaoImpl<AccountsRecord> {

    /**
     * 批量存储记录
     * @param records
     */
    public void saveList(List<AccountsRecord> records){
        for(int i=0;i<records.size();i++){
            AccountsRecord record = records.get(i);
            record.setOrderIndex(i);
            getHibernateTemplate().save(record);
        }
    }


    public List<AccountsRecord> findAccountsRecords(Integer uid, String startDate, String endDate, Integer stateType) {
        if (startDate == null || startDate.equals("")) {//找出当前
            startDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        }
        if (endDate == null || endDate.equals("")) {//找出当前
            endDate = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
        }
        DetachedCriteria dc = DetachedCriteria.forClass(AccountsRecord.class);
        dc.add(Restrictions.eq("agent.id", uid));
        dc.add(Restrictions.between("updateTime", DateUtil.formatStartTime(startDate), DateUtil.formatEndTime(endDate)));//时间
//       dc.add(Restrictions.like("updateTime", DateUtil.formatStr(startDate,"yyyy-MM-dd")));
        dc.add(Restrictions.eq("accountsType", AccountsType.stateOf(stateType)));
        dc.addOrder(Order.desc("id"));
        List<AccountsRecord> records = findByCriteria(dc, null, null);
        //统计
        if (records == null) {
            return new ArrayList<>();
        }
        return records;
    }

    public List<AccountsRecord> findAccountsRecords(SearchParameter<AccountsRecord> p, User user){
        Example example = Example.create(p.getEntity());
        DetachedCriteria dc = DetachedCriteria.forClass(AccountsRecord.class);
        dc.addOrder(Order.desc("id"));//id排序
        dc.add(example);
        if(user.isAdmin()){//查询所有
            Agent agent =  p.getEntity().getAgent();
            //当事人
            if(agent!=null){
                DetachedCriteria dcAgent = dc.createCriteria("agent");
                Example example1 = Example.create(agent).enableLike(MatchMode.ANYWHERE);
                dcAgent.add(example1);
            }
            //参与人
            Agent causedAgent = p.getEntity().getCausedAgent();
            if(causedAgent!=null){
                DetachedCriteria dcCaseAgent = dc.createCriteria("causedAgent");
                Example example1 = Example.create(causedAgent).enableLike(MatchMode.ANYWHERE);
                dcCaseAgent.add(example1);
            }
        }else {
            dc.add(Restrictions.or(Restrictions.eq("fromAgent.id",user.getId()),Restrictions.eq("toAgent.id",user.getId())));
        }

        if(p.getStartTime()!=null&&p.getEndTime()!=null){
            dc.add(Restrictions.between("updateTime",p.getStartTimeByDate(),p.getEndTimeByDate()));
        }

        return searchByPage(p,dc);
    }









}
