package com.dreamer.service.mobile.impl;

import com.dreamer.domain.system.SystemParameter;
import com.dreamer.repository.mobile.SystemParameterDao;
import com.dreamer.service.mobile.SystemParameterHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class SystemParameterHandlerImpl extends BaseHandlerImpl<SystemParameter> implements SystemParameterHandler {

    private final String GOODS_PATH = "09";


    @Override
    public String getGoodsImgPath() {
        return systemParameterDao.get("code",GOODS_PATH).getParamValue();
    }

    private SystemParameterDao systemParameterDao;

    public SystemParameterDao getSystemParameterDao() {
        return systemParameterDao;
    }

    @Autowired
    public void setSystemParameterDao(SystemParameterDao systemParameterDao) {
        this.systemParameterDao = systemParameterDao;
        super.setBaseDao(systemParameterDao);
    }
}
