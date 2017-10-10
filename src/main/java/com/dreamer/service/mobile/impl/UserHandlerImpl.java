package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.User;
import com.dreamer.repository.mobile.UserDao;
import com.dreamer.service.mobile.UserHandler;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.mx.otter.exception.ApplicationException;

import java.util.List;
import java.util.Map;

/**
 * Created by huangfei on 06/07/2017.
 */
@Service
public class UserHandlerImpl extends BaseHandlerImpl<User> implements UserHandler {

    @Override
    public User login(String loginName, String paw) {
        //编号或者手机号都可以登录
        User user = findByAgentCodeOrMobileOrLoginName(loginName);
        if (!user.getPassword().equals(paw)) {
            throw new ApplicationException("账号密码不相匹配！");
        }
        return user;
    }


    /**
     * 手机号/登录名/编号
     * @param name
     * @return
     */
    private User findByAgentCodeOrMobileOrLoginName(String name) {
        //通过code或者id查找
        Map<String, Object> map = new HashedMap();
        map.put("agentCode", name);
        map.put("mobile", name);
        map.put("loginName", name);
        List<User> list = userDao.getOr(map);
        //验证登陆
        if (list == null || list.isEmpty()) {
            throw new ApplicationException("不存在此用户");
        } else if (list.size() > 1) {
            throw new ApplicationException("用户异常，存在多个账号,相关参数:" + name);
        }
        return list.get(0);
    }

    @Autowired
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        super.setBaseDao(userDao);
    }
}
