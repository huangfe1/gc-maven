package com.dreamer.view.pmall.dto;

import java.util.List;

public class OrderQueryDto  {

    private String status;//交易状态

	private String address;//地址

    private Integer quantity;//总数量

    private String logisticsCode;//物流单号

    private String logistics;//物流公司

    private Double money;//总金额

    private String time;//时间

    private String logist;//物流费

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getLogist() {
        return logist;
    }

    public void setLogist(String logist) {
        this.logist = logist;
    }

    private List<ItemDto> items;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}


