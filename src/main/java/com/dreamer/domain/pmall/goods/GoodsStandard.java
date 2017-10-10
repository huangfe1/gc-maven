package com.dreamer.domain.pmall.goods;

import ps.mx.otter.exception.ApplicationException;

import java.io.Serializable;

/**
 * Created by huangfei on 2017/3/12.
 */
public class GoodsStandard implements Serializable{

    private Integer id;

    private String name;

    private Integer stock;//库存

    private Integer version;

    private Double price;//价格  如果是单选  就必须设置价格

    public Double getPrice() {
        return price;
    }

    public void deductCurrentStock(Integer quantity){
        if(stock>=quantity){
            stock-=quantity;
        }else {
            throw new ApplicationException(name+"库存不足");
        }
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
