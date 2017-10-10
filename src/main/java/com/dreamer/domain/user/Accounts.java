package com.dreamer.domain.user;

import com.dreamer.domain.user.enums.AccountsType;
import ps.mx.otter.exception.ApplicationException;

import java.util.Date;
import java.util.Objects;


public class Accounts implements java.io.Serializable {


    private static final long serialVersionUID = 956581918691993666L;
    // Fields

    private Integer id;
    private Integer version;
    private Agent user;
    private Date updateTime;
    private Double pointsBalance;
    private Double voucherBalance;
    private Double advanceBalance;//预存款
    private Double benefitPointsBalance;
    private Double payBalance;//消费金额
    private Double purchaseBalance;//进货券



//    /**
//     * 代金券转移
//     *
//     * @param acs
//     * @param voucher
//     */
//    public void payVoucherTo(Accounts acs, Double voucher) {
//        String more = user.getRealName() + "转货支付给" + acs.getUser().getRealName();
////		deductVoucher(voucher,more);
////		acs.increaseVoucher(voucher,more);
//        payVoucherTo(acs, voucher, more);
//    }

//    /**
//     * 代金券转移
//     *
//     * @param acs
//     * @param voucher
//     */
//    public void payVoucherTo(Accounts acs, Double voucher, String more) {
//        deductVoucher(voucher, more);
////        acs.increaseVoucher(voucher, more);
//    }

//    /**
//     * 预存款支付
//     *
//     * @param acs
//     * @param advance
//     */
//    public void payAdvanceTo(Accounts acs, Double advance) {
//        String more = user.getRealName() + "转货支付给" + acs.getUser().getRealName();
//        deductAdvance(advance, more);//减少自己的
//        acs.increaseAdvance(advance, more);//增加收款人的
//    }

//    /**
//     * 预存款转移
//     *
//     * @param acs
//     * @param more
//     */
//    public void transferAdvanceToAnother(Accounts acs, Double advance) {
//        String more = user.getRealName() + "主动转移预存款到" + acs.getUser().getRealName();
//        this.deductAdvance(advance, more);//本人减少
//        acs.increaseAdvance(advance, more);//接收方增加
//    }

//    /**
//     * 进货券支付
//     *
//     * @param acs
//     * @param advance
//     */
//    public void payPurchaseTo(Accounts acs, Double purchase) {
//        String more = user.getRealName() + "转货支付给" + acs.getUser().getRealName();
//        deductPurchase(purchase, more);//减少自己的
//        acs.increasePurchase(purchase, more);//增加收款人的
//    }

//    /**
//     * 进货券转移
//     *
//     * @param acs
//     * @param more
//     */
//    public void transferPurchaseToAnother(Accounts acs, Double purchase) {
//        String more = user.getRealName() + "主动转移进货券到" + acs.getUser().getRealName();
//        if (!this.getUser().isMutedUser()) {
//            this.deductPurchase(purchase, more);
//        }
//        acs.increasePurchase(purchase, more);//接收方增加
//    }

    /**
     * 积分转移
     *
     * @param acs
     * @param points
     */
    public void transferPointsToAnoher(Accounts acs, Double points) {
        deductPoints(points);
        acs.increasePoints(points);
    }

//    /**
//     * 代金券转移
//     *
//     * @param acs
//     * @param points
//     */
//    public void transferVoucherToAnoher(Accounts acs, Double voucher) {
//        String more = user.getRealName() + "主动转移代金券到" + acs.getUser().getRealName();
//        if (!this.getUser().isMutedUser()) {
//            this.deductVoucher(voucher, more);
//        }
//        acs.increaseVoucher(voucher, more);
//    }


    public Double increasePayBalance(Double payBalance) {
        if (getPayBalance() + payBalance < 0) {
            throw new ApplicationException("消费金额增加值非法");
        }
        setPayBalance(getPayBalance() + payBalance);
        return getPayBalance();
    }

    public Double getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(Double payBalance) {
        this.payBalance = payBalance;
    }


    /**
     * 增加积分
     *
     * @param points
     * @return
     */
    public Double increasePoints(Double points) {
        if (getPointsBalance() + points < 0) {
            throw new ApplicationException("积分增加值非法");
        }
        setPointsBalance(getPointsBalance() + points);
        return getPointsBalance();
    }

    /**
     * 积分扣减
     *
     * @param points
     * @return
     */
    public Double deductPoints(Double points) {
        if (points < 0) {
            throw new ApplicationException("积分扣减值不能为负数");
        }
        if (points > getPointsBalance()) {
            throw new ApplicationException("执行积分扣减时,积分余额不足");
        }
        setPointsBalance(getPointsBalance() - points);
        return getPointsBalance();
    }


//    /**
//     * 增加代金券
//     *
//     * @param voucher
//     * @return
//     */
//    public Double increaseVoucher(Double voucher, String more) {
//        if (voucher == 0) {//为0不处理
//            return getVoucherBalance();
//        }
//        if (voucher < 0) {
//            throw new ApplicationException("代金券增加值非法,不能为负数");
//        }
////		if(getVoucherBalance()+voucher<0){
////			throw new ApplicationException("代金券增加值非法");
////		}
//        // 进行加法运算
//        Double result = PreciseComputeUtil.add(getVoucherBalance(), voucher);
//        setVoucherBalance(result);
//        user.addVoucherRecord(1, more, result);//增加记录
//        return getVoucherBalance();
//    }

//    /**
//     * 扣减代金券
//     *
//     * @param voucher
//     * @return
//     */
//    public Double deductVoucher(Double voucher, String more) {
//        if (voucher == 0) {//为0不处理
//            return getVoucherBalance();
//        }
//        if (voucher < 0) {
//            throw new ApplicationException("代金券扣减值不能为负数");
//        }
//        if (voucher > getVoucherBalance()) {
//            throw new ApplicationException("代金券/物流费余额不足,请及时充值");
//        }
//        // 进行减法运算
//        Double result = PreciseComputeUtil.sub(getVoucherBalance(), voucher);
//        setVoucherBalance(result);
//        user.addVoucherRecord(0, more, result);//减少记录
//        return getVoucherBalance();
//    }

//    /**
//     * 追回代金券
//     *
//     * @param voucher
//     * @return
//     */
//    public Double deductVoucherForBack(Double voucher, String more) {
//        if (voucher == 0) {//为0不处理
//            return getVoucherBalance();
//        }
//        if (voucher < 0) {
//            throw new ApplicationException("代金券扣减值不能为负数");
//        }
//        // 进行减法运算
//        Double result = PreciseComputeUtil.sub(getVoucherBalance(), voucher);
//        setVoucherBalance(result);
//        user.addVoucherRecord(0, more, result);//减少记录
//        return getVoucherBalance();
//    }

//    /**
//     * 增加预存款/预存款
//     *
//     * @param advance
//     * @param more
//     * @return
//     */
//    public Double increaseAdvance(Double advance, String more) {
//        if (advance == 0) return getAdvanceBalance();//不处理
//        if (advance < 0) {
//            throw new ApplicationException("预存款增加值非法");
//        }
//        // 进行加法运算
//        Double result = PreciseComputeUtil.add(getAdvanceBalance(), advance);
//        setAdvanceBalance(result);
////        user.addAdvanceRecord(0, more, result);
//        return getAdvanceBalance();
//    }

//    /**
//     * 扣减预存款
//     *
//     * @param advance 数量
//     * @param more    原因
//     * @return
//     */
//    public Double deductAdvance(Double advance, String more) {
//        if (advance == 0) return getAdvanceBalance();//不处理
//
//        if (advance < 0) {
//            throw new ApplicationException("预存款扣减值不能为负数");
//        }
//        if (advance > getAdvanceBalance()) {
//            throw new ApplicationException("预存款余额不足");
//        }
//        // 进行减法运算
//        Double result = PreciseComputeUtil.sub(getAdvanceBalance(), advance);
//        setAdvanceBalance(result);
//        user.addAdvanceRecord(0, more, result);
//        return getAdvanceBalance();
//    }

//    /**
//     * 扣减进货券
//     *
//     * @param purchase 数量
//     * @param more     原因
//     * @return
//     */
//    public Double deductPurchase(Double purchase, String more) {
//        if (purchase == 0) return getPurchaseBalance();//不处理
//
//        if (purchase < 0) {
//            throw new ApplicationException("进货券扣减值不能为负数");
//        }
//        if (purchase > getPurchaseBalance()) {
//            throw new ApplicationException("进货券余额不足");
//        }
//        // 进行减法运算
//        Double result = PreciseComputeUtil.sub(getPurchaseBalance(), purchase);
//        setPurchaseBalance(result);
//        user.addPurchaseRecord(0, more, result);
//        return getPurchaseBalance();
//    }

//    /**
//     * 增加进货券
//     *
//     * @param advance
//     * @param more
//     * @return
//     */
//    public Double increasePurchase(Double purchase, String more) {
//        if (purchase == 0) return getPurchaseBalance();//不处理
//        if (purchase < 0) {
//            throw new ApplicationException("进货券增加值非法");
//        }
//        // 进行加法运算
//        Double result = PreciseComputeUtil.add(getPurchaseBalance(), purchase);
//        setPurchaseBalance(result);
//        user.addPurchaseRecord(0, more, result);
//        return getPurchaseBalance();
//    }


    public boolean pointsBalanceEnough(Double points) {
        return Double.compare(this.pointsBalance, points) >= 0;
    }

    /**
     * 增加福利积分
     *
     * @param benefitPoints
     * @return
     */
    public Double increaseBenefitPoints(Double benefitPoints) {
        if (getBenefitPointsBalance() + benefitPoints < 0) {
            throw new ApplicationException("福利积分增加值非法");
        }
        benefitPointsBalance = (getBenefitPointsBalance() + benefitPoints);
        return getBenefitPointsBalance();
    }

    /**
     * 扣减福利积分
     *
     * @param benefitPoints
     * @return
     */
    public Double deductBenefitPoints(Double benefitPoints) {
        if (benefitPoints < 0) {
            throw new ApplicationException("福利积分扣减值不能为负数");
        }
        if (benefitPoints > this.getBenefitPointsBalance()) {
            throw new ApplicationException("福利积分账户余额不足");
        }
        benefitPointsBalance = this.getBenefitPointsBalance() - benefitPoints;
        return this.getBenefitPointsBalance();
    }

    // Constructors

    /**
     * default constructor
     */
    public Accounts() {
        this.voucherBalance=0.0;
        this.advanceBalance=0.0;
        this.purchaseBalance=0.0;
        this.pointsBalance=0.0;
        this.benefitPointsBalance=0.0;
        this.payBalance=0.0;
        this.updateTime=new Date();
    }





    // Property accessors
    //设置账户
    public void setAccount(Double amount, AccountsType accountsType) {
        switch (accountsType) {
            case VOUCHER:
                setVoucherBalance(amount);
                break;
            case ADVANCE:
                setAdvanceBalance(amount);
                break;
            case BENEFIT:
                setBenefitPointsBalance(amount);
                break;
        }
    }

    //获得账户
    public Double getAccount(AccountsType accountsType) {
        switch (accountsType) {
            case VOUCHER:
                return getVoucherBalance();
            case ADVANCE:
                return getAdvanceBalance();
            case BENEFIT:
                return getBenefitPointsBalance();
            default:return  -1.0;
        }
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Agent getUser() {
        return this.user;
    }

    public void setUser(Agent user) {
        this.user = user;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getPointsBalance() {
        return this.pointsBalance;
    }

    public void setPointsBalance(Double pointsBalance) {
        this.pointsBalance = pointsBalance;
    }

    public Double getVoucherBalance() {
        return this.voucherBalance;
    }

    public void setVoucherBalance(Double voucherBalance) {
        this.voucherBalance = voucherBalance;
    }

    public Double getPurchaseBalance() {
        return purchaseBalance;
    }

    public void setPurchaseBalance(Double purchaseBalance) {
        this.purchaseBalance = purchaseBalance;
    }

    public Double getBenefitPointsBalance() {
        return benefitPointsBalance;
    }

    public void setBenefitPointsBalance(Double benefitPointsBalance) {
        this.benefitPointsBalance = benefitPointsBalance;
    }

    public Double getAdvanceBalance() {
        return advanceBalance;
    }

    public void setAdvanceBalance(Double advanceBalance) {
        this.advanceBalance = advanceBalance;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return Objects.hash(user);
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) obj;
        return Objects.equals(user, other.getUser());
    }


}