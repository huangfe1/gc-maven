package com.dreamer.util;

import com.dreamer.repository.redis.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangfei on 16/6/14.
 */
@Component
public class TokenInfo {

    private static String TOKEN_NAME = "token";

    private static String TICKET_NAME = "ticket";

    private static String getTOKEN_NAME(String appId) {
        return TOKEN_NAME+"_"+appId;
    }

    private static String getTICKET_NAME(String appId) {
        return TICKET_NAME+"_"+appId;
    }


    @Autowired
    public static RedisDao redisDao;

    public static String IMG_HEAD_PATH = "http://ht.gcyy365.com/gc";

    //通过appId获取TOKEN
    public static String getAccessToken(String appId) {
      return   redisDao.getStr(getTOKEN_NAME(appId));
//        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String appId,String accessToken) {
        redisDao.putStr(getTOKEN_NAME(appId), accessToken);
//        ACCESS_TOKEN = accessToken;
    }

    public static String getJsapiTicket(String appId) {
      return   redisDao.getStr(getTICKET_NAME(appId));
//        return JSAPI_TICKET;
    }

    public static void setJsapiTicket(String appId,String jsapiTicket) {
        redisDao.putStr(getTICKET_NAME(appId), jsapiTicket);
//        JSAPI_TICKET = jsapiTicket;
    }


    public static RedisDao getRedisDao() {
        return redisDao;
    }

    public static void setRedisDao(RedisDao redisDao) {
        TokenInfo.redisDao = redisDao;
    }
}
