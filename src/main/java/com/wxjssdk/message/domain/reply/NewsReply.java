package com.wxjssdk.message.domain.reply;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by huangfei on 20/06/2017.
 * 图文消息
 */
public class NewsReply extends BaseReply{

    private Integer ArticleCount;

    List<Article> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }

    @Override
    public String toXml() {
        XStream xStream = new XStream();
        xStream.alias("xml",this.getClass());
        xStream.alias("item",Article.class);
        return xStream.toXML(this);
    }
}

