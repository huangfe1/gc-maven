package com.dreamer.domain.user;

/**
 * Created by huangfei on 16/9/26.
 */

public class AddressClone extends Address {

    private AddressMy addressMy;

    public AddressMy getAddressMy() {
        return addressMy;
    }

    public void setAddressMy(AddressMy addressMy) {
        this.addressMy = addressMy;
    }

    public AddressClone() {

    }

    public AddressClone(AddressMy addressMy) {
        setAddressMy(addressMy);
        setAgent(addressMy.getAgent());
        setProvince(addressMy.getProvince());
        setCity(addressMy.getCity());
        setCounty(addressMy.getCounty());
        setAddress(addressMy.getAddress());
        setMobile(addressMy.getMobile());
        setConsignee(addressMy.getConsignee());
        setConsigneeCode(addressMy.getConsigneeCode());
    }
}
