package com.dreamer.service.pay;

import com.dreamer.domain.wechat.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import ps.mx.otter.utils.http.HttpClient;
import ps.mx.otter.utils.json.JsonUtil;

import java.util.HashMap;

@Service
public class GetOpenIdHandler {

    /**
     * 通过 appid 与 secrect获取token  这里的token是普通的token
     * @param wxConfig
     * @return
     */
	public HashMap<String,String> getToken(WxConfig wxConfig){
		UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
				GET_TOKEN_URL).buildAndExpand(wxConfig.getAppid()
				, wxConfig.getSecret());
		String res = HttpClient.httpGetForString(ucb.toUriString(), null);
		LOG.debug("获取用户 token ：{}", res);
		HashMap<String, String> map = JsonUtil.objectToMap(res);
		return map;
	}

    public HashMap<String,String> getTicket(String token){
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
                GET_TICKET_URL).buildAndExpand(token);
        String res = HttpClient.httpGetForString(ucb.toUriString(), null);
        LOG.debug("获取用户 ticket ：{}", res);
        HashMap<String, String> map = JsonUtil.objectToMap(res);
        return map;
    }

    /**
     * 网页授权获取code
     * @param appid
     * @param redirect_uri  重定向地址
     * @param scope     snsapi_base不弹出授权页面，snsapi_userinfo弹出授权页面
     * @param state     不必须 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @return
     */
    public String createGetCodeCallbackUrl(String appid,String redirect_uri ,String scope,String state){
            UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(GET_CODE_URL).buildAndExpand(appid,redirect_uri,scope,state);
            return ucb.toUriString();
    }

    /**
     * 通过code获取网页授权的access_token的URl
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public String createGetAccessTokenUrlByCode(String appid,String secret,String code){
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(GET_ACCESS_TOKEN_URL).buildAndExpand(appid,secret,code);
        return ucb.toUriString();
    }

    /**
     * 网页授权作用域为snsapi_userinfo，通过access_token和openid拉取用户信息了
     * @param accessToken
     * @param openId
     * @return
     */
    public String createGetUserInfoUrl(String accessToken, String openId){
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(GET_USERINFO_URL).buildAndExpand(accessToken,openId);
        return ucb.toUriString();
    }


    /**
     * 获取accessToken
     * @param wxConfig
     * @param code
     * @return {access_token,expires_in有效时间,,refresh_token用户刷新access_token,openid,scope,unionid}
     */
    public HashMap<String,String> getAccessTokenByCode(WxConfig wxConfig, String code){
            String url = createGetAccessTokenUrlByCode(wxConfig.getAppid(), wxConfig.getSecret(),code);
            String res = HttpClient.httpGetForString(url,null);
            return JsonUtil.objectToMap(res);
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return{openid,nickname,sex,province,,city,country,headimgurl}
     */
    public HashMap<String,String> getUserInfo(String accessToken,String openId){
        String res = HttpClient.httpGetForString(createGetUserInfoUrl(accessToken,openId),null);
        try {
            res = new String(res.getBytes("ISO-8859-1"), "UTF-8");
        }catch (Exception e){
        }
        return JsonUtil.objectToMap(res);
    }

    /**
     * snsapi_base 获取基本授权的url
     * @param wxConfig
     * @param redirect_uri
     * @param state
     * @return
     */
    public String createGetOpenIdCodeCallbackUrl(WxConfig wxConfig, String redirect_uri, String state){
            return createGetCodeCallbackUrl(wxConfig.getAppid(),redirect_uri,"snsapi_base",state);
    }

    /**
     * snsapi_userinfo  获取用户信息的授权url
     * @param wxConfig
     * @param redirect_uri
     * @param state
     * @return
     */
    public String createGetUserInfoCodeCallbackUrl(WxConfig wxConfig, String redirect_uri, String state){
        return createGetCodeCallbackUrl(wxConfig.getAppid(),redirect_uri,"snsapi_userinfo",state);
    }
    /*************/

	public static String createGetBaseOpenIdCallbackUrl(WxConfig wxConfig, String backUrl, Integer orderId){
		UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
				GET_CODE_URL).buildAndExpand(wxConfig.getAppid(),
				backUrl,"snsapi_base", orderId);
		return ucb.toUriString();
	}
	
	public static String createGetUserInfoOpenIdCallbackUrl(WxConfig wxConfig, String backUrl, Integer orderId){
		UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
				GET_CODE_URL).buildAndExpand(wxConfig.getAppid(),
				backUrl,"snsapi_userinfo", orderId);
		return ucb.toUriString();
	}
	
	public HashMap<String,String> getOpenId(WxConfig wxConfig, String code){
		UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
				GET_OPENID_URL).buildAndExpand(wxConfig.getAppid(),
				wxConfig.getSecret(), code);
		LOG.debug("获取用户 openid accesstoken url：{}", ucb.toUriString());
		String res = HttpClient.httpGetForString(ucb.toUriString(), null);
		LOG.debug("获取用户 openid accesstoken ：{}", res);
		HashMap<String, String> map = JsonUtil.objectToMap(res);
		return map;
	}



    private final static String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"
            + "?access_token={ACCESS_TOKEN}&type=jsapi";


    private final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token"
			+ "?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";

	private final static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize"
			+ "?appid={APPID}&redirect_uri={REDIRECT_URI}&response_type=code&scope={scope}&state={STATE}#wechat_redirect";

	//用户网页授权通过code获取access_token接口
	private final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";

	private final static String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";

	private static final String GET_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?"
			+ "appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";
	
	private final Logger LOG=LoggerFactory.getLogger(getClass());
}
