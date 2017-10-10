package com.dreamer.service.mobile;

import com.dreamer.domain.user.MutedUser;

/**
 * Created by huangfei on 06/07/2017.
 */
public interface MuteUserHandler extends BaseHandler<MutedUser> {

    MutedUser getMuteUser();

}
