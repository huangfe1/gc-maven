package com.dreamer.domain.user;

import java.io.Serializable;

/**
 * Created by huangfei on 16/9/26.
 */
public class Address implements Serializable,Cloneable{
    private Integer id;
    private Agent agent;
    private String province;//省
    private String city;//市
    private String county;//县
    private String address;//详细地址
    private String post_code;//邮政编码
    private String mobile;//收货人电话
    private String consignee;//收货人
    private String consigneeCode;//收货人编号
    public String getConsigneeCode() {
        return consigneeCode;
    }
    public void setConsigneeCode(String consigneeCode) {
        this.consigneeCode = consigneeCode;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Address clone() throws CloneNotSupportedException {
        Address address = (Address) super.clone();
        address.setId(null);
        return address;
    }
}
