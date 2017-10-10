package com.wxjssdk.util;

import com.wxjssdk.message.domain.reply.Article;
import com.wxjssdk.message.domain.reply.NewsReply;
import com.wxjssdk.message.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangfei on 20/06/2017.
 */
public class XmlUtilTest {

    public static void main(String[] args) {
//        String xml = "<xml>\n" +
//                "  <ToUserName><![CDATA[gh_fbe8a958756e]]></ToUserName>\n" +
//                "  <FromUserName><![CDATA[otAzGjrS4AYCmeJM1GhEOcHXXTAo]]></FromUserName>\n" +
//                "  <CreateTime>1433259128</CreateTime>\n" +
//                "  <MsgType><![CDATA[event]]></MsgType>\n" +
//                "  <Event><![CDATA[user_scan_product_enter_session]]></Event>\n" +
//                "  <KeyStandard><![CDATA[ena13]]></KeyStandard>\n" +
//                "  <KeyStr><![CDATA[6954767461373]]></KeyStr>\n" +
//                "  <ExtInfo><![CDATA[]]></ExtInfo>\n" +
//                "</xml>";
//        Map<String, String> subscribeEvent = XmlUtil.xmlToMap(xml);
//        System.out.println(subscribeEvent.get("ToUserName"));


        NewsReply newsReply = new NewsReply();
        newsReply.setFromUserName("123");
        newsReply.setToUserName("1");
        newsReply.setCreateTime(new Date().getTime());
        newsReply.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        Article article = new Article();
        article.setTitle("测试");
        article.setDescription("测试描述");
        article.setPicUrl("https://tp");
        article.setUrl("https://wqwq");
        Article article1 = new Article();
        article1.setTitle("测试");
        article1.setDescription("测试描述");
        article1.setPicUrl("https://tp");
        article1.setUrl("https://wqwq");
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article);
        newsReply.setArticleCount(articles.size());
        newsReply.setArticles(articles);
        System.out.println(newsReply.toXml());
    }



}