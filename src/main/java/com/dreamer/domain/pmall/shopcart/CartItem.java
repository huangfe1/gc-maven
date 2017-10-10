package com.dreamer.domain.pmall.shopcart;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.GoodsStandard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

public class CartItem implements Serializable{

	private Integer quantity = 0;
	private PmallGoods goods;
	private Set<GoodsStandard> goodsStandards;





	public CartItem(PmallGoods goods, Integer quantity, Set<GoodsStandard> goodsStandards) {
		this.goods = goods;
		this.quantity = quantity;
		this.goodsStandards=goodsStandards;
	}

	public CartItem() {
	}

	public CartItem increaseQuantity(Integer quantity) {
		setQuantity(getQuantity() + quantity);
		return this;
	}

	public CartItem decreaseQuantity(Integer quantity) {
		setQuantity(getQuantity() - quantity);
		return this;
	}


	public Double getAmount() {
		BigDecimal p=new BigDecimal(goods.getRetailPrice() * this.getQuantity());
		return p.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public PmallGoods getGoods() {
		return goods;
	}

	public void setGoods(PmallGoods goods) {
		this.goods = goods;
	}

	public Set<GoodsStandard> getGoodsStandards() {
		return goodsStandards;
	}

	public void setGoodsStandards(Set<GoodsStandard> goodsStandards) {
		this.goodsStandards = goodsStandards;
	}
}
