package com.dreamer.domain.user;

import ps.mx.otter.utils.date.DateUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class PurchaseTransfer implements java.io.Serializable {

	private static final long serialVersionUID = 2759558931721988426L;
	// Fields

	private Integer id;
	private Integer version;
	private User userByToAgent;
	private User userByFromAgent;
	private Date transferTime;
	private Date updateTime;
	private String remark;
	private Double purchase;
    private String 	operator;
	private PurchaseTransferType type;
    private String out_trade_no;//订单号码

	// Constructors
	
//
//	public void transferpurchase(Agent from,Agent to,Double purchase){
//		userByToAgent=to;
//		userByFromAgent=from;
//		transferTime=new Date();
//		this.purchase=purchase;
//		type=PurchaseTransferType.NORMAL;
//		from.transferPurchase(to, purchase);
//	}

	/** default constructor */
	public PurchaseTransfer() {
	}

	/** minimal constructor */
	public PurchaseTransfer(User userByToAgent, User userByFromAgent, Double point) {
		this.userByToAgent = userByToAgent;
		this.userByFromAgent = userByFromAgent;
		this.purchase = point;
	}

	/** full constructor */
	public PurchaseTransfer(User userByToAgent, User userByFromAgent,
                            Date transferTime, Date updateTime, String remark,
                            Double point) {
		this.userByToAgent = userByToAgent;
		this.userByFromAgent = userByFromAgent;
		this.transferTime = transferTime;
		this.updateTime = updateTime;
		this.remark = remark;
		this.purchase = point;
	}

    /**
     * 在线充值订单
     * @param userByFromAgent
     * @param userByToAgent
     */
    public void commit(User userByFromAgent,User userByToAgent){
        setOut_trade_no(fillOrderNo());//设置订单
        setUpdateTime(new Date());//设置提交时间
        setUserByFromAgent(userByFromAgent);//转奖金人
        setUserByToAgent(userByToAgent);//收奖金人
		setType(PurchaseTransferType.ERROR);
//		setRemark(purchaseTransferType.ERROR.desc);//设置成未付款
		setRemark("请向公司指定支付宝账号汇款");//设置成未付款
    }

    public String fillOrderNo(){
        return Integer.toHexString(Objects.hashCode(DateUtil.date2string(Date.from(Instant.now()),"MMddHHmmss")));
    }
	// Property accessors

	public Integer getId() {
		return this.id;
	}

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

    public PurchaseTransferType getType() {
        return type;
    }

    public void setType(PurchaseTransferType type) {
        this.type = type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setVersion(Integer version) {
		this.version = version;
	}

	public User getUserByToAgent() {
		return this.userByToAgent;
	}

	public void setUserByToAgent(User userByToAgent) {
		this.userByToAgent = userByToAgent;
	}

	public User getUserByFromAgent() {
		return this.userByFromAgent;
	}

	public void setUserByFromAgent(User userByFromAgent) {
		this.userByFromAgent = userByFromAgent;
	}

	public Date getTransferTime() {
		return this.transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getpurchase() {
		return purchase;
	}

	public void setpurchase(Double purchase) {
		this.purchase = purchase;
	}

}