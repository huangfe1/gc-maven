package com.dreamer.domain.account;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.mall.transfer.TransferApplyOrigin;
import com.dreamer.domain.user.Agent;
import com.dreamer.domain.user.AgentLevel;
import com.dreamer.domain.user.AgentLevelName;
import com.dreamer.domain.user.User;
import ps.mx.otter.exception.ApplicationException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;


public class GoodsAccount implements java.io.Serializable {

    private static final long serialVersionUID = -1216038804344510293L;
    private Integer id;
    private User user;
    private Goods goods;
    private Double currentPoint;
    private Integer currentBalance;
    private Date updateTime;
    private Integer version;
    //累积购买量
    private Integer cumulative;
    //代理针对货物所处的级别
    private AgentLevel agentLevel;
    //交易记录

    public boolean isMainGoodsAccount() {

        return goods.isMainGoods();
    }



    /**
     * 获取返利模式
     *
     * @return
     */
    public Double[] getbackVouchers() {
        String[] strs = getGoods().getVoucher().trim().split("_");
        Double[] temps = new Double[strs.length];
        for (int i = 0; i < temps.length; i++) {
            temps[i] = Double.parseDouble(strs[i]);
            strs[i]=null;//释放内存
        }
        return temps;
    }




    public void accept(Integer amount) {
        increaseBalance(amount);
        increaseCumulative(amount);
        double points = goods.caculatePoints(amount);
        increasePoints(points);

        //autoPromotion();
    }






    /**
     * 增加积分
     *
     * @param points
     * @return
     */
    public Double increasePoints(Double points) {
        if (getCurrentPoint() + points < 0) {
            throw new ApplicationException("积分增加值非法");
        }
        setCurrentPoint(getCurrentPoint() + points);
        return getCurrentPoint();
    }


    /**
     * 累积购买量增加
     *
     * @param cumulative
     * @return
     */
    public Integer increaseCumulative(Integer cumulative) {
        setCumulative(getCumulative() + cumulative);
        return getCumulative();
    }

    /**
     * 累积购买量减少
     *
     * @param cumulative
     * @return
     */
    public Integer deductCumulative(Integer cumulative) {
        setCumulative(getCumulative() - cumulative);
        if (this.getCumulative() < 0) {
            this.setCumulative(0);
        }
        return getCumulative();
    }

    /**
     * 增加账户余额
     *
     * @param balance
     * @return
     */
    public Integer increaseBalance(Integer balance) {
        if (getCurrentBalance() + balance < 0) {
            throw new ApplicationException("货物账户余额增加值非法");
        }
        setCurrentBalance(getCurrentBalance() + balance);
        return getCurrentBalance();
    }

    /**
     * 扣减账户余额
     *
     * @param balance
     * @return
     */
    public Integer deductBalance(Integer balance) {
        if (balance < 0) {
            throw new ApplicationException("账户余额扣减值不能为负数");
        }
        if (balance > getCurrentBalance()) {
            throw new ApplicationException("需扣减的[" + goods.getName() + "]账户余额不足");
        }
        setCurrentBalance(getCurrentBalance() - balance);
        return getCurrentBalance();
    }

    public Double getPrice() {
        Price price = goods.getPrice(agentLevel);
        return price == null ? 0.0D : price.getPrice();

    }

    // Constructors

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     * default constructor
     */
    public GoodsAccount() {
    }


    public GoodsAccount(Agent agent,Goods goods,AgentLevel agentLevel ) {
        this.user=agent;
        this.agentLevel=agentLevel;
        this.goods=goods;
        this.currentBalance=0;
        this.cumulative=0;
        this.currentPoint=0.0;
        this.updateTime=new Date();
    }

    /**
     * minimal constructor
     */
    public GoodsAccount(Agent user, Goods goods, Double currentPoint) {
        this.user = user;
        this.goods = goods;
        this.currentPoint = currentPoint;
    }

    /**
     * full constructor
     */
    public GoodsAccount(Agent user, Goods goods, Double currentPoint,
                        Timestamp updateTime, Integer version) {
        this.user = user;
        this.goods = goods;
        this.currentPoint = currentPoint;
        this.updateTime = updateTime;
        this.version = version;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;

    }

    public Double getCurrentPoint() {
        return this.currentPoint;
    }

    public void setCurrentPoint(Double currentPoint) {
        this.currentPoint = currentPoint;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCumulative() {
        return cumulative;
    }

    public void setCumulative(Integer cumulative) {
        this.cumulative = cumulative;
    }

    public AgentLevel getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(AgentLevel agentLevel) {
        this.agentLevel = agentLevel;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return Objects.hash(goods);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GoodsAccount)) {
            return false;
        }
        GoodsAccount other = (GoodsAccount) obj;
        return Objects.equals(goods, other.getGoods());
    }


}