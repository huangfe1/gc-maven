package com.dreamer.domain.mall.transfer;

import com.dreamer.domain.mall.goods.Goods;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferItem implements Serializable{

	private Integer id;
	
	private Transfer transfer;

	private Integer quantity;
	
	private Double price;

	private Goods goods;
	
	private String priceLevelName;




	
	
	
	
	public TransferItem(){}


    public TransferItem(Transfer transfer, Integer quantity, Double price, Goods goods, String priceLevelName) {
        this.transfer = transfer;
        this.quantity = quantity;
        this.price = price;
        this.goods = goods;
        this.priceLevelName = priceLevelName;
    }

    public Double getAmount(){
		BigDecimal p=new BigDecimal( price*quantity);
		return p.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceLevelName() {
		return priceLevelName;
	}

	public void setPriceLevelName(String priceLevelName) {
		this.priceLevelName = priceLevelName;
	}


	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}
