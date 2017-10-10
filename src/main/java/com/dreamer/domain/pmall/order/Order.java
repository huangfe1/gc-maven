package com.dreamer.domain.pmall.order;

import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.Agent;
import com.dreamer.util.PreciseComputeUtil;

import javax.persistence.Entity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer version;
	private Agent user;
	private String orderNo;
	private Date orderTime;
	private OrderStatus status;//订单状态 新订单 、发货 、退货
	private AddressClone addressClone;//地址
	private String logisticsCode;//物流编号
	private Double logisticsFee;//物流费
	private String logistics;//物流公司
	private Date updateTime;
	private String remark;
	private Double amount;//总消费需要的金额
	private Integer quantity;//总数量
	private PaymentWay paymentWay;
	private AcknowledgmentWay acknowledgmentWay;
	private Date paymentTime;
	private PaymentStatus paymentStatus;//是否已经支付
	private Date shippingTime;
	private String shippingOperator;
	private String revokeReason;
	private String revokeOperator;
	private Boolean canAdvance;//能否预存款
	private Date receiveTime;
	private String refundReason;
	private String refundOperator;
	private Date refundTime;

	private Set<OrderItem> items = new HashSet();

	public Order(Agent user, AddressClone addressClone, String remark) {
		this.user = user;
		this.addressClone = addressClone;
		this.remark = remark;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Boolean getCanAdvance() {
		return canAdvance;
	}

	public void setCanAdvance(Boolean canAdvance) {
		this.canAdvance = canAdvance;
	}

	//	public boolean paymentRequired() {
//		return isUnpaid();
//	}
	
//	public boolean requiredPaymentBeforeShipping(){
//		return isUnpaid() && this.paymentWay!=PaymentWay.COD;
//	}
//
//	public void commit(Agent agent){
//		//不需要积分
////		if(!agent.getAccounts().pointsBalanceEnough(getTotalPoints())){
////			throw new BalanceNotEnoughException("您的账户积分不足");
////		}
//		paymentStatus=PaymentStatus.UNPAID;
//		status=OrderStatus.NEW;
//		user=agent;
//		orderTime=Timestamp.from(Instant.now());
//		orderNo=fillOrderNo();
//		voucher=caculateVoucher();
//		benefitPoints=caculateBenefitPoints().doubleValue();
//	}
//
//	public boolean isNew(){
//		return Objects.equals(status, OrderStatus.NEW);
//	}

	/**
	 * 订单未支付
	 * 
	 * @return
	 */
//	public boolean isUnpaid() {
//		return Objects.equals(paymentStatus, PaymentStatus.UNPAID);
//	}
	
//	public boolean isRefund(){
//		return Objects.equals(paymentStatus, PaymentStatus.REFUND);
//	}

	/**
	 * 订单已支付
	 * 
	 * @return
	 */
//	public boolean isPaid() {
//		return Objects.equals(paymentStatus, PaymentStatus.PAID);
//	}

	/**
	 * 订单已发货
	 * 
	 * @return
	 */
//	public boolean isShipped() {
//		return Objects.equals(status, OrderStatus.SHIPPED);
//	}

	/**
	 * 订单已确认收货
	 * 
	 * @return
	 */
//	public boolean isReceived() {
//		return Objects.equals(status, OrderStatus.RECEIVED);
//	}

	/**
	 * 订单已完成
	 * 
	 * @return
	 */
//	public boolean isCompleted() {
//		return isReceived();
//	}

	/**
	 * 订单已撤销
	 * 
	 * @return
	 */
//	public boolean isRevoked() {
//		return Objects.equals(status, OrderStatus.REVOKED);
//	}
//
//	public boolean isReturned() {
//		return Objects.equals(status, OrderStatus.RETURNED);
//	}
	
//	public void pay(PaymentWay paymentWay,Double money){
//		this.paymentWay=paymentWay;
//		paymentStatus=PaymentStatus.PAID;
//		paymentTime=new Date();
//		user.getAccounts().increasePayBalance(money);//增加优惠商城消费金额
//        //去掉积分购买
////		user.getAccounts().deductPoints(getTotalPoints());
//	}
	
//	public void shipping(){
//		if(isReceived()){
//			throw new ApplicationException("此订单已收货");
//		}
//		if(isShipped()){
//			throw new ApplicationException("此订单已发货");
//		}
//		if(requiredPaymentBeforeShipping()){
//			throw new ApplicationException("非货到付款订单,必须先完成付款后才能发货");
//		}
//		this.status=OrderStatus.SHIPPED;
//		this.shippingTime=new Date();
//	}
	
//	public void revoke(){
//		if(this.isPaid()){
//			throw new ApplicationException("已支付订单无法撤销");
//		}
//		if(this.isRevoked()){
//			throw new ApplicationException("已撤销订单无需反复撤销");
//		}
//		if(!this.isNew()){
//			throw new ApplicationException("仅能撤销未支付未发货未退款的新订单");
//		}
//		status=OrderStatus.REVOKED;
//	}
	
//
//	public void returnGoods(){
//		if(this.isReturned()){
//			throw new ApplicationException("已退货订单无需再次操作");
//		}
//		if(!this.isReceived()){
//			throw new ApplicationException("订单必须确认收货后才能退货");
//		}
//		if(this.isNew()){
//			throw new ApplicationException("未发货订单应当执行撤销操作");
//		}
//		this.status=OrderStatus.RETURNED;
//	}
	
//	public void refund(){
//		if(!this.isReturned() && !this.isRevoked()){
//			throw new ApplicationException("只能退款已撤销或已退货订单");
//		}
//		if(!this.isPaid()){
//			throw new ApplicationException("只能退款已付款订单");
//		}
//		if(this.isRefund()){
//			throw new ApplicationException("该订单已退款");
//		}
//		this.paymentStatus=PaymentStatus.REFUND;
//		this.refundTime=new Date();
//	}
	
//	public void receive(User user){
//		if(!this.isShipped()){
//			throw new ApplicationException("不是已发货订单,无法进行收货确认");
//		}
//		if(user.isAdmin()){
//			this.acknowledgmentWay=AcknowledgmentWay.Manager;
//		}else{
//			this.acknowledgmentWay=AcknowledgmentWay.Consignee;
//		}
//		status=OrderStatus.RECEIVED;
//		receiveTime=new Date();
//	}

//	public void addItem(OrderItem item) {
//		if(Objects.nonNull(items.put(item.getGoodsId(), item))){
//			item.setOrder(this);
//		}
//	}

//	public void removeItem(OrderItem item) {
//		items.remove(item.getGoodsId());
//	}

//	public Double caculateVoucher() {
//		return items.values().stream().mapToDouble(item -> item.getVoucher())
//				.sum();
//	}
//
//	public Double caculateBenefitPoints() {
//		return items.values().stream()
//				.mapToDouble(item -> item.getBenefitPoints()).sum();
//	}

//	public void clearItems() {
//		items.clear();
//	}

//	/**
//	 * 订单货物总数量
//	 *
//	 * @return
//	 */
//	public Integer getQuantity() {
//		return items.values().stream().mapToInt(item -> item.getQuantity())
//				.sum();
//	}
//
//	/**
//	 * 订单总现金价金额
//	 *
//	 * @return
//	 */
//	public Double getTotalMoney() {
//		return items.values().stream().mapToDouble(item -> item.getAmountMoney()).sum();
//	}

//	/**
//	 * 订单总积分价金额
//	 *
//	 * @return
//	 */
//	public Double getTotalPoints() {
//		return items.values().stream()
//				.mapToDouble(item -> item.getAmountPoints()).sum();
//	}

//	/**
//	 * 订单总价金额
//	 *
//	 * @return
//	 */
//	public Double getTotalPrice() {
//		return items.values().stream()
//				.mapToDouble(item -> item.getAmountPrice()).sum();
//	}

	/**
	 * 应支付金额
	 * 
	 * @return
	 */
//	public Double getActualPayment() {
//		return getTotalMoney() - this.getDiscountAmount();
//	}

	// Constructors

	/** default constructor */
	public Order() {
	}

//	/** minimal constructor */
//	public Order(String orderNo, Double voucher) {
//		this.orderNo = orderNo;
//		this.voucher = voucher;
//	}



//	public String fillOrderNo(){
//		return Integer.toHexString(Objects.hashCode(user.getId()))+DateUtil.date2string(Date.from(Instant.now()), "MMddHHmmss");
//	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Agent getUser() {
		return user;
	}

	public void setUser(Agent user) {
		this.user = user;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

    public AddressClone getAddressClone() {
        return addressClone;
    }

    public void setAddressClone(AddressClone addressClone) {
        this.addressClone = addressClone;
    }

    public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	public String getLogistics() {
		return logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getAmount() {
		return amount;
	}

	public static void main(String[] args) {
		Double d = 19.9;
		System.out.println(PreciseComputeUtil.round(19.9*100));
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public AcknowledgmentWay getAcknowledgmentWay() {
		return acknowledgmentWay;
	}

	public void setAcknowledgmentWay(AcknowledgmentWay acknowledgmentWay) {
		this.acknowledgmentWay = acknowledgmentWay;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Date shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShippingOperator() {
		return shippingOperator;
	}

	public void setShippingOperator(String shippingOperator) {
		this.shippingOperator = shippingOperator;
	}

	public String getRevokeReason() {
		return revokeReason;
	}

	public void setRevokeReason(String revokeReason) {
		this.revokeReason = revokeReason;
	}

	public String getRevokeOperator() {
		return revokeOperator;
	}

	public void setRevokeOperator(String revokeOperator) {
		this.revokeOperator = revokeOperator;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundOperator() {
		return refundOperator;
	}

	public void setRefundOperator(String refundOperator) {
		this.refundOperator = refundOperator;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}


	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}