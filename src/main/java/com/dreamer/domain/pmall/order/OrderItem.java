package com.dreamer.domain.pmall.order;


import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.GoodsStandard;

import java.util.Set;

public class OrderItem {

    private Integer id;

    private Order pmallOrder;

    private Set<GoodsStandard> standards;//分类

    private PmallGoods pmallGoods;//产品

    private Integer quantity;//数量

    private Double price;//当时购买需要的现金




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Order getPmallOrder() {
        return pmallOrder;
    }

    public void setPmallOrder(Order pmallOrder) {
        this.pmallOrder = pmallOrder;
    }

    public Set<GoodsStandard> getStandards() {
        return standards;
    }

    public void setStandards(Set<GoodsStandard> standards) {
        this.standards = standards;
    }

    public PmallGoods getPmallGoods() {
        return pmallGoods;
    }

    public void setPmallGoods(PmallGoods pmallGoods) {
        this.pmallGoods = pmallGoods;
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


}
