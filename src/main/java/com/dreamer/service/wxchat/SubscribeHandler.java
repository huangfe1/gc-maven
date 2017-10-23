package com.dreamer.service.wxchat;

import com.dreamer.domain.user.Agent;
import com.dreamer.service.mobile.AgentHandler;
import com.dreamer.service.mobile.factory.WxConfigFactory;
import com.dreamer.util.TokenInfo;
import com.wxjssdk.JSAPI;
import com.wxjssdk.dto.SdkResult;
import com.wxjssdk.message.dispatcher.Hander.EventHandler;
import com.wxjssdk.message.domain.event.SubscribeEvent;
import com.wxjssdk.message.domain.reply.Article;
import com.wxjssdk.message.domain.reply.NewsReply;
import com.wxjssdk.message.util.MessageUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 21/06/2017.
 * 处理未关注的用户 关注了带参数的二维码
 */
@Service("subscribeHandler")
public class SubscribeHandler extends EventHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AgentHandler agentHandler;

    @Autowired
    private WxConfigFactory wxConfigFactory;

    @Override
    public String handSUBSCRIBE(SubscribeEvent subscribeEvent) {
        logger.info("处理扫码关注事件openId:{}", subscribeEvent.getFromUserName());

        try {
            String openId = subscribeEvent.getFromUserName();//当前用户的  openid
            String parentId = subscribeEvent.getEventKey().replace("qrscene_", "");
            logger.info("处理扫码关注事件parentId:{}", parentId);
            //获取用户信息
            SdkResult<JSONObject> sdkResult = JSAPI.getUserInfo(TokenInfo.getAccessToken(wxConfigFactory.getBaseConfig().getAppid()), openId);
            if (sdkResult.isSuccess()) {//成功
                JSONObject jsonObject = sdkResult.getData();
                String unionId = jsonObject.getString("unionid");
                String nickname = jsonObject.getString("nickname");
                String headimgurl = jsonObject.getString("headimgurl");
                //创建用户
                Agent agent = agentHandler.createVisitor(unionId, openId, nickname, headimgurl,parentId);
                //创建回复消息
                NewsReply newsReply = initUserSubReply(agent);
                logger.debug("收到扫码:{}", "回复" + newsReply.toXml());
                return newsReply.toXml();//回复
            } else {
                logger.error("扫码获取用户信息失败：{}", sdkResult.getError());
            }
        } catch (Exception e) {
            logger.error("扫码消息回复异常：{}", e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    //创建新用户关注消息
    private NewsReply initUserSubReply(Agent agent) {
        NewsReply newsReply = new NewsReply();
        newsReply.setFromUserName(wxConfigFactory.getBaseConfig().getWxid());
        newsReply.setToUserName(agent.getWxOpenid());
        newsReply.setCreateTime(new Date().getTime());
        newsReply.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        List<Article> articles = new ArrayList<>();
        if (agent.getAgentCode() != null) {//有编号的用户
            Article article = new Article();
            article.setTitle("欢迎回来！亲爱的" + agent.getRealName());
            article.setDescription("让更多的人用上优惠的好产品");
            article.setPicUrl("http://ht.gcyy365.com/gc/resources/mallimages/template.jpg");
//            article.setUrl("http://ht.gcyy365.com/gc/dmz/pmall/show.html");
            article.setUrl("http://ht.gcyy365.com/gc/dmz/mobile/index.html");
            articles.add(article);
        } else {//新用户
            Article article = new Article();
            article.setTitle("高臣药业商城--让更多的人用上优惠的好产品！");
            article.setDescription("");
            article.setPicUrl("http://ht.gcyy365.com/gc/resources/mallimages/template.jpg");
            article.setUrl("http://ht.gcyy365.com/gc/dmz/mobile/index.html");
            //信息
            Article info = new Article();
            info.setTitle(
                    "名字 : " + agent.getRealName() + "\r\n\r\n" +
                            "ID号 : " + agent.getId() + "\r\n\r\n" +
                            "备注 : 您扫了" + agent.getParent().getRealName() + "的分享码"
            );
            info.setDescription("");
            info.setPicUrl(agent.getHeadimgurl());
            info.setUrl("http://ht.gcyy365.com/gc/mobile/my.html");
            //直接购买
            Article buy = new Article();
            buy.setTitle("【点击进入】购买产品");
            buy.setDescription("");
            buy.setPicUrl("http://ht.gcyy365.com/gc/resources/mallimages/template.jpg");
            buy.setUrl("http://ht.gcyy365.com/gc/dmz/mobile/index.html");
            //完善信息
            Article register = new Article();
            register.setTitle("【点击进入】完善信息");
            register.setDescription("");
            register.setPicUrl("http://ht.gcyy365.com/gc/resources/mallimages/template.jpg");
            //带refCode，openid，unionId
            register.setUrl("http://ht.gcyy365.com/gc/mobile/register.html?refCode="+agent.getParent().getId()+"&s_openid="+agent.getWxOpenid()+"&s_unionid="+agent.getWxUnionID());
//            //直接登录
//            Article login = new Article();
//            login.setTitle("老客户点击此处直接登录");
//            login.setDescription("");
//            login.setPicUrl("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1089399937,1684001946&fm=26&gp=0.jpg");
//            login.setUrl("http://ht.gcyy365.com/gc/login.html?");

            articles.add(article);
            articles.add(info);
            articles.add(buy);
            articles.add(register);
//            articles.add(login);

        }
        newsReply.setArticleCount(articles.size());
        newsReply.setArticles(articles);
        return newsReply;
    }

}
