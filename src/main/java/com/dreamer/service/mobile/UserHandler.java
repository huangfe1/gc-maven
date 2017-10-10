package com.dreamer.service.mobile;

import com.dreamer.domain.user.User;

/**
 * Created by huangfei on 06/07/2017.
 */
public interface UserHandler extends BaseHandler<User>{

        User login(String loginName, String paw);




}
