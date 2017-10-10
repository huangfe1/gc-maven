package com.wxjssdk;

/**
 * Created by huangfei on 16/05/2017.
 * jssdk需要用到的相关Url
 */
public class SdkUrl {

    //获取jsapi_ticket零时票据需要的token
    public final static String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token"
            + "?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";

    //通过上面获取到的token  获取零时票据  用于jsapi签名
    public final static String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket"
            + "?access_token={ACCESS_TOKEN}&type=jsapi";

    //网页授权 获取授权code的地址
    public final static String GET_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize"
            + "?appid={APPID}&redirect_uri={REDIRECT_URI}&response_type=code&scope={scope}&state={STATE}#wechat_redirect";

    //用户通过网页授权获取了code  用code获取openid与accesstoken
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";


    //全局方法unionId机制获取用户信息  此处的accesstoken是jsdk的accesstoken  用APPID与secrect换取
    public final static String GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";

    public final static String GET_SNSAPI_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";

    //获取带参数的公众号二维码地址
    public final static String CREATE_QROCDE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={ACCESS_TOKEN}";

    //模板通知接口
    public final static String SEND_TEMPLATE_MESSAGE="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";

    //创建链接接口
    public final static String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token={ACCESS_TOKEN}";

}
