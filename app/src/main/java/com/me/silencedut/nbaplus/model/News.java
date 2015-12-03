package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by SilenceDut on 2015/12/3.
 */
public class News {
    /**
     * nextId : 116406468
     * newslist : [{"contentType":"ARTICLE","description":"北京时间12月3日，天津媒体爆料称，天津男篮俱乐部宣布，天津男篮教练纳什由于身体状况不佳，主帅位置由","title":"曝天津主帅纳什因健康退居2线","putdate":"20151203","topicVoteJson":null,"randomNum":1449143673231,"articleId":116407325,"contentSourceName":"3G门户.体育","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151203/116407325.json","type":"IMAGETEXT","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1203/114/277b949e/87c94c34/868e1767/acfcb30d/original"],"sourceType":"ZAKER"},{"contentType":"ARTICLE","description":"昨晚，浙江广厦坐镇主场迎战三外援的四川队，第三节结束时曾一度落后19分，但凭借末节的疯狂反扑，以10","title":"李春江点名1人表现越来越好了","putdate":"20151203","topicVoteJson":null,"randomNum":1449143555785,"articleId":116407326,"contentSourceName":"3G门户.体育","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151203/116407326.json","type":"IMAGETEXT","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1203/10/0476c4e4/a75340c4/bcc336b4/c7a5c5c5/original"],"sourceType":"ZAKER"}]
     */

    private String nextId;
    /**
     * contentType : ARTICLE
     * description : 北京时间12月3日，天津媒体爆料称，天津男篮俱乐部宣布，天津男篮教练纳什由于身体状况不佳，主帅位置由
     * title : 曝天津主帅纳什因健康退居2线
     * putdate : 20151203
     * topicVoteJson : null
     * randomNum : 1449143673231
     * articleId : 116407325
     * contentSourceName : 3G门户.体育
     * articleUrl : http://reader.res.meizu.com/reader/articlecontent/20151203/116407325.json
     * type : IMAGETEXT
     * imgUrlList : ["http://img.res.meizu.com/img/download/reader/2015/1203/114/277b949e/87c94c34/868e1767/acfcb30d/original"]
     * sourceType : ZAKER
     */

    private List<NewslistEntity> newslist;

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public void setNewslist(List<NewslistEntity> newslist) {
        this.newslist = newslist;
    }

    public String getNextId() {
        return nextId;
    }

    public List<NewslistEntity> getNewslist() {
        return newslist;
    }

    public static class NewslistEntity {
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
}
