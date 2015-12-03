package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NewsEntity {
    private String description;
    private String title;
    private String putdate;
    private int articleId;
    private String contentSourceName;
    private String articleUrl;
    private List<String> imgUrlList;


    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPutdate(String putdate) {
        this.putdate = putdate;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setContentSourceName(String contentSourceName) {
        this.contentSourceName = contentSourceName;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }



    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPutdate() {
        return putdate;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getContentSourceName() {
        return contentSourceName;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }
}
