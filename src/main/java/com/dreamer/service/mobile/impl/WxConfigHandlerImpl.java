package com.dreamer.service.mobile.impl;

import com.dreamer.domain.wechat.WxConfig;
import com.dreamer.repository.mobile.WxConfigDao;
import com.dreamer.service.mobile.WxConfigHandler;
import org.springframework.stereotype.Service;

/**
 * Created by huangfei on 12/07/2017.
 */
@Service
public class WxConfigHandlerImpl extends BaseHandlerImpl<WxConfig> implements WxConfigHandler {


    private WxConfigDao wxConfigDao;

    public WxConfigDao getWxConfigDao() {
        return wxConfigDao;
    }

    public void setWxConfigDao(WxConfigDao wxConfigDao) {
        this.wxConfigDao = wxConfigDao;
        super.setBaseDao(wxConfigDao);
    }
}
