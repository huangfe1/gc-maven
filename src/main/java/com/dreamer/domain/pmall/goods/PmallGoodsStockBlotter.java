package com.dreamer.domain.pmall.goods;

import com.dreamer.domain.user.Admin;
import com.dreamer.domain.user.User.UserBaseView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import ps.mx.otter.utils.json.DateTimeDeserializer;
import ps.mx.otter.utils.json.DateTimeSerializer;

import java.util.Date;

public class PmallGoodsStockBlotter implements java.io.Serializable {

	private static final long serialVersionUID = -1517102293182165804L;
	@JsonView(MallGoodsStockBlotterView.class)
	private Integer id;
	@JsonView(MallGoodsStockBlotterView.class)
	private Admin user;
	@JsonView(MallGoodsStockBlotterView.class)
	private PmallGoods goods;
	@JsonView(MallGoodsStockBlotterView.class)
	private Integer change;
	@JsonView(MallGoodsStockBlotterView.class)
	private Integer currentBalance;
	@JsonView(MallGoodsStockBlotterView.class)
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using=DateTimeSerializer.class)
	private Date operateTime;
	@JsonView(MallGoodsStockBlotterView.class)
	private Integer currentStock;
	@JsonView(MallGoodsStockBlotterView.class)
	private String remark;
	
	

	/** default constructor */
	public PmallGoodsStockBlotter() {
	}


	/** full constructor */
	public PmallGoodsStockBlotter(Admin user, PmallGoods goods, Integer change, Date operateTime, Integer currentStock, String remark) {
		this.user = user;
		this.goods = goods;
		this.change = change;
		this.operateTime = operateTime;
		this.currentStock = currentStock;
		this.remark = remark;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Admin getUser() {
		return this.user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}

	public PmallGoods getGoods() {
		return this.goods;
	}

	public void setGoods(PmallGoods goods) {
		this.goods = goods;
	}

	public Integer getChange() {
		return this.change;
	}

	public void setChange(Integer change) {
		this.change = change;
	}

	public Integer getCurrentBalance() {
		return this.currentBalance;
	}

	public void setCurrentBalance(Integer currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Integer getCurrentStock() {
		return this.currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public interface MallGoodsStockBlotterView extends UserBaseView{}
}