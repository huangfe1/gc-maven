package com.dreamer.domain.pmall.shopcart;

import com.dreamer.domain.pmall.goods.PmallGoods;
import com.dreamer.domain.pmall.goods.GoodsStandard;
import com.dreamer.domain.pmall.order.OrderItem;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ShopCart implements Serializable {


    public List<OrderItem> buildOrderItems() {
        return items.values().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setPmallGoods(i.getGoods());
            item.setQuantity(i.getQuantity());
            item.setStandards(i.getGoodsStandards());//设置分类
            return item;
        }).collect(Collectors.toList());
    }

    public Map<String, CartItem> items = new HashMap<>();

    public Integer getQuantity() {
        AtomicInteger quantity = new AtomicInteger(0);
        items.forEach((key, item) -> {
            quantity.addAndGet(item.getQuantity());
        });
        return quantity.get();
    }

    public Double getAmount() {
        Iterator<String> keyIte = items.keySet().iterator();
        Double amount = 0.0D;
        while (keyIte.hasNext()) {
            CartItem item = items.get(keyIte.next());
            amount += item.getAmount();
        }
        return amount;
    }


    public CartItem addGoods(String no, PmallGoods goods, Integer quantity, Set<GoodsStandard> goodsStandards) {
        CartItem itemtemp = new CartItem(goods, quantity, goodsStandards);
        CartItem item = items.get(no);
        if (Objects.nonNull(item)) {
            item.increaseQuantity(quantity);
            return item;
        } else {
            items.put(no, itemtemp);
            return itemtemp;
        }
    }


    public void removeItems(String no) {
        CartItem item = items.get(no);
        if (Objects.nonNull(item)) {
            items.remove(no);
        }
    }


    public Map<String, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<String, CartItem> items) {
        this.items = items;
    }

}
