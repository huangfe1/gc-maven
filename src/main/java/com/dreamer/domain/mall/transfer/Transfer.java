package com.dreamer.domain.mall.transfer;

import com.dreamer.domain.mall.goods.Goods;
import com.dreamer.domain.mall.goods.GoodsTransferStatus;
import com.dreamer.domain.mall.goods.Price;
import com.dreamer.domain.user.Agent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 转货类
 * 
 * @author Tankman
 *
 */

public class Transfer implements java.io.Serializable {

	private static final long serialVersionUID = -5140495984534174981L;
	private Integer id;
	private Integer version;
	private Agent toAgent;
	private Agent fromAgent;
	private GoodsTransferStatus status;
	private Date applyTime;//申请时间
	private Date updateTime;// 更新时间
	private String remittance;//类型
	private String remark;//代理备注
	private Double voucher;//奖金
    private Double purchase;//进货券
	private Integer quantity;//总数
	private Double amount;//总金额
	private String 	operator;//操作员
	private Set<TransferItem> items=new HashSet<>();
//	private TransferApplyOrigin applyOrigin;//转货订单类型


	public Transfer(Agent userByToAgent, Agent userByFromAgent, Date updateTime, String remark) {
		this.toAgent = userByToAgent;
		this.fromAgent = userByFromAgent;
		this.updateTime = updateTime;
		this.remark = remark;
	}





	public Integer getQuantity() {
		return quantity;
	}

	public Set<TransferItem> getItems() {
		return items;
	}

	public void setItems(Set<TransferItem> items) {
		this.items = items;
	}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    public Agent getToAgent() {
        return toAgent;
    }

    public void setToAgent(Agent toAgent) {
        this.toAgent = toAgent;
    }

    public Agent getFromAgent() {
        return fromAgent;
    }

    public void setFromAgent(Agent fromAgent) {
        this.fromAgent = fromAgent;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void addBackTransferItem(Goods goods, Integer quantity, Price price){
//		TransferItem item=TransferItem.build(goods, -quantity, price);
//		addItem(item);
	}

	public void refuse(){
		status=GoodsTransferStatus.DISAGREE;
	}
	
//	public void apply(){
//		applyTime=new Timestamp(System.currentTimeMillis());
//		status=GoodsTransferStatus.NEW;
//		calculate();
//	}

    /**
     *退货申请
     */
//    public void backApply(){
//        applyTime=new Timestamp(System.currentTimeMillis());
//        status=GoodsTransferStatus.NEW;
//        calculate();
//    }




//	public void calculate(){
//		quantity=caculateQuantity();
//        if(getUseVoucher()!=null&&getUseVoucher()) {//使用奖金
//            voucher = voucherAmountPayable();
//        }else {
//            voucher=0.0;
//        }
//	}



//	/**
//	 * 奖金可支付金额
//	 * @return
//	 */
//	public Double voucherAmountPayable(){
//		Double voucherBalance=userByToAgent.getAccounts().getVoucherBalance();
//		Double amount=getAmount();
//		return amount>=voucherBalance?voucherBalance:amount;
//	}

//    /**
//     * 进货券可用金额为当前订单总额的百分比  当前为3%
//     * @return
//     */
//    public Double purchaseAmountPayable(){
//		Double d = 0.03;
//		Double amount=getAmount();
//		Double allP = userByToAgent.getAccounts().getPurchaseBalance();
//        Double balance =amount*d;
//        BigDecimal   b   =   new   BigDecimal(balance);
//        balance   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();//四舍五入保留两位小数
//        return allP>=balance?balance:allP;
//    }

    public static void main(String[] args) {
        double   f   =   111231.24*0.3;
        BigDecimal   b   =   new   BigDecimal(f);
        double   f1   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();
        System.out.println(f1);
    }


//	private Integer caculateQuantity(){
//		return items.values().stream().mapToInt(item->item.getQuantity()).sum();
//	}
	
	
	
//	public void confirm(){
//		transferTime=new Timestamp(System.currentTimeMillis());
//		status=GoodsTransferStatus.CONFIRM;
//		quantity=caculateQuantity();
//		transfer();
//	}

//	public void confirmAutoByPurchase(){
//        if(purchaseAmountPayable()+voucherAmountPayable()<getAmount()){
//            throw new ApplicationException("余额不足,请充值!");
//        }
//        setPurchase(purchaseAmountPayable());//扣减可用的进货券
//		//先扣减预存款
//        userByToAgent.getAccounts().payPurchaseTo(userByFromAgent.getAccounts(), purchaseAmountPayable());
//        //支付奖金
//        BigDecimal b1= BigDecimal.valueOf(getAmount());
//        BigDecimal b2= BigDecimal.valueOf(getPurchase());
//        setVoucher(b1.subtract(b2).doubleValue());//设置还需扣减的奖金
//		userByToAgent.getAccounts().payVoucherTo(userByFromAgent.getAccounts(), getVoucher());
//        confirm();//转货
//	}

    /**
     * 主动将货物转出
     * @param toAgent
     */
	public void transferToAnother(){
//		status=GoodsTransferStatus.CONFIRM;
//		back=false;
//		setApplyTime(new Timestamp(System.currentTimeMillis()));
//		transferTime=new Timestamp(System.currentTimeMillis());
//        if(voucher==null)
//		voucher=0D;
//        transfer();
//		if(userByFromAgent.isMyParent(toAgent)){
////			setRemittance("转货退回");
//			transferToBack();
//		}else{
////			setRemittance("主动转出");
//			transfer();
//		}
	}

//	/**
//	 * 将货物退给上级 add By hf
//	 */
//	public void transferBackToParent(){
//		this.setBack(true);
//		status=GoodsTransferStatus.CONFIRM;
//		setApplyTime(new Timestamp(System.currentTimeMillis()));
//		transferTime=new Timestamp(System.currentTimeMillis());
//		voucher=0D;
//        this.getItems().forEach((k,v)->{
//            GoodsAccount accTo = userByToAgent.loadAccountForGoodsId(k);
//            if (Objects.isNull(accTo)) {
//                throw new DataNotFoundException("转入方对应货物账户不存在");
//            }
//            GoodsAccount accFrom = userByFromAgent.loadAccountForGoodsId(k);
//            if (Objects.isNull(accFrom)) {
//                throw new DataNotFoundException("转出方对应货物账户不存在");
//            }
//            accFrom.transferGoodsToBack(accFrom,accTo, v.getQuantity());
//        });
//        String more="退货返还奖金给"+userByFromAgent.getRealName();
//        userByToAgent.getAccounts().payVoucherTo(userByFromAgent.getAccounts(),getAmount(),more);
//	}
	
//	public void transferBackToParent(Agent toAgent){
//		this.setBack(true);
//		status=GoodsTransferStatus.CONFIRM;
//		userByToAgent=toAgent;
//		setApplyTime(new Timestamp(System.currentTimeMillis()));
//		transferTime=new Timestamp(System.currentTimeMillis());
//		voucher=0D;
////        setRemittance("转货退回");
//		transferToBack();
////		if(userByFromAgent.isMyParent(toAgent)){
////			setRemittance("转货退回");
////			transferToBack();
////		}else{
////			throw new ApplicationException("只能向上级退货");
////		}
//	}
	

	private void transfer(){
//		userByFromAgent.transferGoodsToAnother(userByToAgent, this);
	}
	
//	public Double getAmount(){
//		BigDecimal p=new BigDecimal(items.values().stream().mapToDouble(item->item.getAmount()).sum());
//		return p.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//	}
	
	public boolean isNew(){
		return status==GoodsTransferStatus.NEW;
	}

	// Constructors

	/** default constructor */
	public Transfer() {
	}


	// Property accessors

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


//	public Integer getQuantity() {
//		quantity= caculateQuantity();
//		return quantity;
//	}

	public GoodsTransferStatus getStatus() {
		return this.status;
	}

	public void setStatus(GoodsTransferStatus status) {
		this.status = status;
	}






	public String getRemittance() {
		return this.remittance;
	}

	public void setRemittance(String remittance) {
		this.remittance = remittance;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}
	
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}



	
//	public void addItem(TransferItem item){
//		item.setTransfer(this);
//		items.put(item.getGoodsId(), item);
//	}

//    /**
//     * 退货
//     * @return
//     */
//	public boolean isBackedTransfer() {
//		if(applyOrigin==TransferApplyOrigin.BACK){
//			return true;
//		}
//		return false;
//	}

//    /**
//     * 主动转出
//     * @return
//     */
//    public boolean isOutTransfer() {
//        if(applyOrigin==TransferApplyOrigin.OUT){
//            return true;
//        }
//        return false;
//    }

	public void clear(){
		getItems().clear();
	}

//	public TransferApplyOrigin getApplyOrigin() {
//		return applyOrigin;
//	}

//	public void setApplyOrigin(TransferApplyOrigin applyOrigin) {
//		this.applyOrigin = applyOrigin;
//	}

	
	
	
}