package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.AddressClone;
import com.dreamer.domain.user.AddressMy;
import com.dreamer.repository.mobile.AddressCloneDao;
import com.dreamer.service.mobile.AddressCloneHandler;
import com.dreamer.service.mobile.AddressMyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangfei on 12/07/2017.
 */
@Service
public class AddressCloneHandlerImpl extends BaseHandlerImpl<AddressClone> implements AddressCloneHandler {


    @Override
    @Transactional
    public AddressClone getFromAddressMy(AddressMy addressMy) {

        AddressClone address;
        if (addressMy.getId() != null) {//选择了老地址
            //通过老地址获取克隆地址
            address = get("addressMy.id", addressMy.getId());
            if (address == null) {//不存在克隆地址
                //找出克隆地址
                addressMy = addressMyHandler.get(addressMy.getId());
                address = new AddressClone(addressMy);//生成克隆地址
            }
        } else {//新地址
            //先保存我的地址
            addressMy = addressMyHandler.save(addressMy);
            //克隆地址
            address = new AddressClone(addressMy);
            //保存
            address = save(address);
        }

        return address;
    }

    private AddressCloneDao addressCloneDao;

    @Autowired
    private AddressMyHandler addressMyHandler;


    public AddressCloneDao getAddressCloneDao() {
        return addressCloneDao;
    }

    @Autowired
    public void setAddressCloneDao(AddressCloneDao addressCloneDao) {
        this.addressCloneDao = addressCloneDao;
        super.setBaseDao(addressCloneDao);
    }
}
