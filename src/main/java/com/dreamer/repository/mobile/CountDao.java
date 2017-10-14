package com.dreamer.repository.mobile;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CountDao extends HibernateDaoSupport {

    //统计奖金
    public Double countVoucher() {
        String hql = "select sum(ar.amount) from AccountsRecord as ar where ar.accountsType = 0 and ar.addSub = 1 and ar.info like '%利润%'";
        Double sumVoucher = (Double) currentSession().createQuery(hql).uniqueResult();
        return sumVoucher;
    }

    //统计退回的奖金
    public Double countBackVoucher() {
        String hql = "select sum(ar.amount) from AccountsRecord as ar where ar.accountsType = 0 and ar.addSub = 0 and ar.info like '%追回%'";
        Double sumVoucher = (Double) currentSession().createQuery(hql).uniqueResult();
        return sumVoucher;
    }

    //统计已经提现
    public Double countWithdraw() {
        String hql = "select sum(af.amount) from AccountsTransfer as af where af.toAgent.id = 3 and af.status =5";
        Double sumWithdraw = (Double) currentSession().createQuery(hql).uniqueResult();
        return sumWithdraw;
    }

    //统计代理手上还有多少奖金
    public Double countAgentsVoucher() {
        String hql = "select sum(af.voucherBalance) from Accounts as af where af.user.id != 3";
        Double sumAV = (Double) currentSession().createQuery(hql).uniqueResult();
        return sumAV;
    }

}
