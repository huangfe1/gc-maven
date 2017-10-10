package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.MutedUser;
import com.dreamer.repository.mobile.MutedUserDao;
import com.dreamer.service.mobile.MuteUserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangfei on 06/07/2017.
 */
@Service
public class MuteUserHandlerImpl extends BaseHandlerImpl<MutedUser> implements MuteUserHandler {
    @Override
    public MutedUser getMuteUser() {
        return mutedUserDao.get(3);
    }

    private MutedUserDao mutedUserDao;

    public MutedUserDao getMutedUserDao() {
        return mutedUserDao;
    }

    @Autowired
    public void setMutedUserDao(MutedUserDao mutedUserDao) {
        this.mutedUserDao = mutedUserDao;
        super.setBaseDao(mutedUserDao);
    }
}
