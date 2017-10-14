package com.dreamer.service.mobile.impl;

import com.dreamer.repository.mobile.CountDao;
import com.dreamer.service.mobile.CountHandler;
import com.dreamer.util.PreciseComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountHandlerImpl implements CountHandler {

    @Override
    public Double countVoucher() {
        Double addSum = countDao.countVoucher();
        Double subSum = countDao.countBackVoucher();
        return PreciseComputeUtil.sub(addSum,subSum);
    }

    @Override
    public Double countWithdraw() {
        return countDao.countWithdraw();
    }

    @Override
    public Double countAgentsVoucher() {
        return countDao.countAgentsVoucher();
    }

    @Autowired
    private CountDao countDao;


}
