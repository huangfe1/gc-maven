package com.dreamer.domain.user;

import ps.mx.otter.utils.date.DateUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class VoucherTransfer implements java.io.Serializable {

	private static final long serialVersionUID = 2759558931721988426L;
	// Fields

	private Integer id;
	private Integer version;
	private Agent userByToAgent;
	private Agent userByFromAgent;
	private Date transferTime;
	private Date updateTime;
	private String remark;
	private Double voucher;
    private String 	operator;
	private VoucherTransferType type;
    private String out_trade_no;//订单号码

	// Constructors

//
//	/**
//	 * mobile
//     * 转让代金券
//	 */
//	public void transfer(){
//		type=VoucherTransferType.NORMAL;
//		userByFromAgent.transferVoucher(userByFromAgent,voucher);
//	}
	
	
//	public void transferVoucher(Agent from,Agent to,Double voucher){
//		userByToAgent=to;
//		userByFromAgent=from;
//		transferTime=new Date();
//		this.voucher=voucher;
//		type=VoucherTransferType.NORMAL;
//		from.transferVoucher(to, voucher);
//	}

	/** default constructor */
	public VoucherTransfer() {
	}

	/** minimal constructor */
	public VoucherTransfer(Agent userByToAgent, Agent userByFromAgent, Double point) {
		this.userByToAgent = userByToAgent;
		this.userByFromAgent = userByFromAgent;
		this.voucher = point;
	}

	/** full constructor */
	public VoucherTransfer(Agent userByToAgent, Agent userByFromAgent,
			Date transferTime, Date updateTime, String remark,
			Double point) {
		this.userByToAgent = userByToAgent;
		this.userByFromAgent = userByFromAgent;
		this.transferTime = transferTime;
		this.updateTime = updateTime;
		this.remark = remark;
		this.voucher = point;
	}

    /**
     * 在线充值订单
     * @param userByFromAgent
     * @param userByToAgent
     */
    public void commit(Agent userByFromAgent,Agent userByToAgent){
        setOut_trade_no(fillOrderNo());//设置订单
        setUpdateTime(new Date());//设置提交时间
        setUserByFromAgent(userByFromAgent);//转代金券人
        setUserByToAgent(userByToAgent);//收代金券人
		setType(VoucherTransferType.ERROR);
//		setRemark(VoucherTransferType.ERROR.desc);//设置成未付款
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

    public VoucherTransferType getType() {
        return type;
    }

    public void setType(VoucherTransferType type) {
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


    public Agent getUserByToAgent() {
        return userByToAgent;
    }

    public void setUserByToAgent(Agent userByToAgent) {
        this.userByToAgent = userByToAgent;
    }

    public Agent getUserByFromAgent() {
        return userByFromAgent;
    }

    public void setUserByFromAgent(Agent userByFromAgent) {
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

	public Double getVoucher() {
		return voucher;
	}

	public void setVoucher(Double voucher) {
		this.voucher = voucher;
	}

}