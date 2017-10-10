package com.wxjssdk;

import com.dreamer.domain.user.AccountsRecord;
import com.hfpay.util.Encrypt;
import com.hfpay.util.HttpTookit;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.menu.domain.WxMenu;
import com.wxjssdk.util.XmlUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * Created by huangfei on 16/05/2017.
 */
public class JSAPI {

    /**
     * 创建获取网页授权code地址
     *
     * @param appid
     * @param redirect_uri
     * @param scope        snsapi_base  snsapi_userinfo
     * @param state
     * @return
     */
    public static String createGetCodeUrl(String appid, String redirect_uri, String scope, String state) {
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.GET_CODE_URL).buildAndExpand(appid, redirect_uri, scope, state);
        return ucb.toUriString();
    }

    /**
     * 通过code获取token与openid
     *
     * @param appid
     * @param secret
     * @param code
     * @return data {"access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN","openid":"OPENID","scope":"SCOPE","unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    public static SdkResult getTokenAndOpenId(String appid, String secret, String code) {
        String url = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.GET_ACCESS_TOKEN_URL).buildAndExpand(appid, secret, code).toString();
        String res = getResultFromUrl(url);
        return handleError("openid", res);
    }

    /**
     * 网页授权获取用户基本信息，一般用户用户绑定信息
     *
     * @param accessToken code换取的accessToken
     * @param openId
     * @return
     */
    public static SdkResult getSnsUserInfo(String accessToken, String openId) {
        String url = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.GET_SNSAPI_USERINFO_URL).buildAndExpand(accessToken, openId).toString();
//        try {
//           url=URLEncoder.encode(url,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String res = getResultFromUrl(url);
        return handleError("nickname", res);
    }


    /**
     * 获取用户信息
     * 通过openid与jssdk的accesstoken，与openid获取
     * 这种方法用户用户关注了公众号产生了交互才能获取
     *
     * @param accessToken
     * @param openId
     * @return {"openid":"OPENID"," nickname": NICKNAME,"sex":"1","province":"PROVINCE""city":"CITY","country":"COUNTRY","headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46","privilege":["PRIVILEGE1""PRIVILEGE2"],"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"}
     */
    public static SdkResult getUserInfo(String accessToken, String openId) {
        String url = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.GET_USERINFO_URL).buildAndExpand(accessToken, openId).toString();
        String res = getResultFromUrl(url);
        return handleError("nickname", res);
    }


    /**
     * 获取 jssdk签名需要的ticket需要的token
     * 因为会超时 所以需要中央处理器过段时间一直刷新获取
     *
     * @param appid
     * @param secrect
     * @return {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    public static SdkResult getToken(String appid, String secrect) {
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
                SdkUrl.GET_TOKEN_URL).buildAndExpand(appid, secrect);
        String res = getResultFromUrl(ucb.toString());
        return handleError("access_token", res);
    }

    /**
     * 获取jsapi签名需要的零时票据
     * 因为会超时 所以需要中央处理器过段时间一直刷新获取
     *
     * @param token
     * @return {"errcode":0,"errmsg":"ok","ticket":"","expires_in":7200
     * }
     */
    public static SdkResult getTicket(String token) {
        UriComponents ucb = ServletUriComponentsBuilder.fromHttpUrl(
                SdkUrl.GET_TICKET_URL).buildAndExpand(token);
        String res = getResultFromUrl(ucb.toString());
        return handleError("ticket", res);
    }

    /**
     * 获取带参数的公众号二维码
     *
     * @param expire_seconds
     * @param action_name    QR_SCENE  QR_LIMIT_STR_SCENE
     * @param scene_str
     * @param scene_id
     * @return {"ticket":"3sUw==","expire_seconds":60,"url":"http:\/\/weixin.qq.com\/q\/kZgfwMTm72WWPkovabbI"}
     * https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket= 前缀加上 ticket  就是二维码地址
     */
    public static SdkResult getQrcode(String access_token, String action_name, String scene_id) {
        String path = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.CREATE_QROCDE).buildAndExpand(access_token).toString();
        JSONObject objectMap = new JSONObject();
        objectMap.put("action_name", action_name);
        if (action_name.equals("QR_SCENE")) {//零时
            objectMap.put("expire_seconds", "2592000");
        }
        JSONObject map = new JSONObject();
        map.put("scene_id", scene_id);
        JSONObject scene = new JSONObject();
        scene.put("scene", map);
        objectMap.put("action_info", scene);
        String res = getResultFromUrl(path, objectMap);
        return handleError("ticket", res);
    }

    /**
     * 利用微信发送模板消息
     *
     * @param access_token
     * @param openid
     * @param template_id
     * @param url
     * @param data
     * @return 返回xml
     */
    public static SdkResult sendTemplateMessage(String access_token, String openid, String template_id, String url, Map<String, Object> data) {
        Map<String, Object> map = new HashedMap();
        map.put("touser", openid);
        map.put("template_id", template_id);
        map.put("url", url);
        map.put("topcolor", "#FF0000");//默认黑色
        map.put("data", data);//不同模板数据不同
        String path = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.SEND_TEMPLATE_MESSAGE).buildAndExpand(access_token).toString();
//        String params = XmlUtil.mapToXml(map);
        String params = JSONObject.fromObject(map).toString();
        String res = getResultFromUrl(path, params);
        return handleError("msgid", res);
    }

    /**
     * 创建微信菜单
     *
     * @param access_token
     * @param wxMenu
     * @return
     */
    public static SdkResult createMenu(String access_token, String wxMenu) {
        String path = ServletUriComponentsBuilder.fromHttpUrl(SdkUrl.CREATE_MENU_URL).buildAndExpand(access_token).toString();
        String json = JSONObject.fromObject(wxMenu).toString();
        String res = getResultFromUrl(path, json);
        return handleError("errmsg", "ok", res);
    }


    /**
     * 获取jsapi wxconfig需要的签名
     *
     * @return
     */
    private static String getSignNature(String jsapi_ticket, String url, String noncestr, String timestamp) {
        List<String> list = new ArrayList();
        list.add("noncestr="+noncestr+"&");
        list.add("jsapi_ticket="+jsapi_ticket+"&");
        list.add("timestamp="+timestamp+"&");
        list.add("url="+url);
        Collections.sort(list);
        String result ="";
        for(String str : list){
            result+=str;
        }
//        System.out.println(result+"----");
        return Encrypt.Encode(result, "sha1");

    }

    /**
     * 创建WxConfig需要的参数
     *
     * @param appId
     * @param ticket
     * @param token
     * @param url
     * @return {“appId”,"timestamp","nonceStr","signature"}
     */
    public static JSONObject createWxConfig(String appId, String ticket, String token, String url) {
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = getSignNature(ticket, url, nonceStr, timestamp);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", appId);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("nonceStr", nonceStr);
        jsonObject.put("signature", signature);
        return jsonObject;
    }


    public static void main(String[] args) {
        HashMap<String, String> st = new HashMap<>();
        st.put("abc", "abc");
        HashMap<String, Object> bb = new HashMap<>();
        bb.put("bb", st);
        JSONObject jsonObject = JSONObject.fromObject(bb);
        System.out.println(jsonObject.toString());
    }


    /**
     * 处理结果
     *
     * @param needParam
     * @param res
     * @return
     */
    private static SdkResult handleError(String needParam, String res) {
        JSONObject jsonObject = JSONObject.fromObject(res);
        SdkResult sdkResult = new SdkResult();
        if (jsonObject.containsKey(needParam)) {
            sdkResult.setSuccess(true);
            sdkResult.setData(jsonObject);
            return sdkResult;
        }

        sdkResult.setSuccess(false);
        if (jsonObject.containsKey("errcode")) {
            sdkResult.setSuccess(false);
            sdkResult.setError("errcode:" + jsonObject.get("errcode") + ",errmsg:" + jsonObject.get("errmsg"));
        } else {
            sdkResult.setError("未知错误,返回res" + res);
        }
        return sdkResult;
    }


    /**
     * 处理结果
     *
     * @param needParam
     * @param res
     * @return
     */
    private static SdkResult handleError(String needParam, String value, String res) {
        JSONObject jsonObject = JSONObject.fromObject(res);
        SdkResult sdkResult = new SdkResult();
        if (jsonObject.containsKey(needParam)) {
            if (jsonObject.get(needParam).equals(value)) {
                sdkResult.setSuccess(true);
                sdkResult.setData(jsonObject);
                return sdkResult;
            }
        }
        sdkResult.setSuccess(false);
        if (jsonObject.containsKey("errcode")) {
            sdkResult.setSuccess(false);
            sdkResult.setError("errcode:" + jsonObject.get("errcode") + ",errmsg:" + jsonObject.get("errmsg"));
        } else {
            sdkResult.setError("未知错误,返回res" + res);
        }
        return sdkResult;
    }


    /**
     * 通过接口获取需要的JSAPI信息
     *
     * @param url
     * @return
     */
    private static String getResultFromUrl(String url) {
        String res = HttpTookit.doGet(url, null);
        return res;
    }


    /**
     * 通过接口获取需要的JSAPI信息
     *
     * @param url
     * @return
     */
    private static String getResultFromUrl(String url, JSONObject jsonObject) {
        String res = getResultFromUrl(url, jsonObject.toString());
        return res;
    }

    /**
     * 通过接口获取需要的JSAPI信息
     *
     * @param url
     * @return
     */
    private static String getResultFromUrl(String url, String str) {
        String res = HttpTookit.doPostStr(url, str);
        return res;
    }


}
