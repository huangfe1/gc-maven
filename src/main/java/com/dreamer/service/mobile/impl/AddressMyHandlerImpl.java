package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.AddressMy;
import com.dreamer.domain.user.Agent;
import com.dreamer.repository.mobile.AddressMyDao;
import com.dreamer.service.mobile.AddressMyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangfei on 05/07/2017.
 */
@Service
public class AddressMyHandlerImpl extends  BaseHandlerImpl<AddressMy> implements AddressMyHandler{


    /**
     * 找出某人的地址中包含某人的地址
     *
     * @param agent
     * @param toAgent
     * @return
     */
    @Override
    public List<AddressMy> findAddressByAgent(Agent agent, Agent toAgent) {
        return addressMyDao.findAddressByAgent(agent,toAgent);
    }

    @Override
    public List<AddressMy> findAddressByAgent(Agent agent) {
        return addressMyDao.findAddressByAgent(agent);
    }

    private AddressMyDao addressMyDao;

    public AddressMyDao getAddressMyDao() {
        return addressMyDao;
    }

    @Autowired
    public void setAddressMyDao(AddressMyDao addressMyDao) {
        this.addressMyDao = addressMyDao;
        super.setBaseDao(addressMyDao);
    }
}
