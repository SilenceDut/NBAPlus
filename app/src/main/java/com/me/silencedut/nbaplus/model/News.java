package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class News {
    /**
     * nextId : 116409785
     * newslist : [{"contentType":"ARTICLE","description":"央视体育频道女主播\u201c乌贼刘\u201d刘语熙在社交网络发文\u201c人生若只如初见，再见最前线！\u201d，以此正式宣布离开《","title":"告别NBA！最美女主播宣布退出","putdate":"20151204","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1204/8/92edcd73/452f4cc1/81686264/66092ed4/original"],"randomNum":1449186657638,"articleId":116409942,"contentSourceName":"NBA","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151204/116409942.json","type":"IMAGETEXT","topicVoteJson":null,"sourceType":"ZAKER"}]
     */

    private String nextId;
    /**
     * contentType : ARTICLE
     * description : 央视体育频道女主播“乌贼刘”刘语熙在社交网络发文“人生若只如初见，再见最前线！”，以此正式宣布离开《
     * title : 告别NBA！最美女主播宣布退出
     * putdate : 20151204
     * imgUrlList : ["http://img.res.meizu.com/img/download/reader/2015/1204/8/92edcd73/452f4cc1/81686264/66092ed4/original"]
     * randomNum : 1449186657638
     * articleId : 116409942
     * contentSourceName : NBA
     * articleUrl : http://reader.res.meizu.com/reader/articlecontent/20151204/116409942.json
     * type : IMAGETEXT
     * topicVoteJson : null
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
        private Object topicVoteJson;
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

        public void setTopicVoteJson(Object topicVoteJson) {
            this.topicVoteJson = topicVoteJson;
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

        public Object getTopicVoteJson() {
            return topicVoteJson;
        }

        public List<String> getImgUrlList() {
            return imgUrlList;
        }
    }
}

