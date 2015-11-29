package com.me.silencedut.nbaplus.model;

import java.util.List;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class News {

    /**
     * nextId : 116101001
     * newslist : [{"contentType":"ARTICLE","description":"据美联社消息，步行者今天以102-91击败凯尔特人，保罗-乔治又轰下了26分、10个篮板，近3场比赛","title":"王者归来！泡椒最近3战场均32分","putdate":"20151112","topicVoteJson":null,"randomNum":1447317851518,"articleId":116101823,"contentSourceName":"NBA","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151112/116101823.json","type":"IMAGETEXT","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1112/65/0850ff17/769b4428/bac63d9e/e798428d/original"],"sourceType":"ZAKER"},{"contentType":"ARTICLE","description":"NBA官方今日宣布，孟菲斯灰熊队前锋马特-巴恩斯在与洛杉矶快船队的比赛中假摔，他因此收到假摔警告，如","title":"如此铁军？灰熊包揽新赛季两次假摔","putdate":"20151112","topicVoteJson":null,"randomNum":1447317786380,"articleId":116101824,"contentSourceName":"NBA","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151112/116101824.json","type":"IMAGETEXT","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1112/50/d8df230d/a3ee45df/ac089820/93aaf630/original"],"sourceType":"ZAKER"},{"contentType":"ARTICLE","description":"拉塞尔是湖人的榜眼秀，科比也很关注他的成长。 今天湖人对阵魔术，榜眼秀拉塞尔再度在末节比赛一开始","title":"科比鼓励榜眼：我当时机会还不如你多","putdate":"20151112","topicVoteJson":null,"randomNum":1447317795268,"articleId":116101825,"contentSourceName":"NBA","articleUrl":"http://reader.res.meizu.com/reader/articlecontent/20151112/116101825.json","type":"IMAGETEXT","imgUrlList":["http://img.res.meizu.com/img/download/reader/2015/1112/124/0ca68dac/cb224600/a63f6ff0/e962da0f/original"],"sourceType":"ZAKER"}]
     */

    private String nextId;
    /**
     * contentType : ARTICLE
     * description : 据美联社消息，步行者今天以102-91击败凯尔特人，保罗-乔治又轰下了26分、10个篮板，近3场比赛
     * title : 王者归来！泡椒最近3战场均32分
     * putdate : 20151112
     * topicVoteJson : null
     * randomNum : 1447317851518
     * articleId : 116101823
     * contentSourceName : NBA
     * articleUrl : http://reader.res.meizu.com/reader/articlecontent/20151112/116101823.json
     * type : IMAGETEXT
     * imgUrlList : ["http://img.res.meizu.com/img/download/reader/2015/1112/65/0850ff17/769b4428/bac63d9e/e798428d/original"]
     * sourceType : ZAKER
     */

    private List<NewsEntity> newslist;

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public void setNewslist(List<NewsEntity> newslist) {
        this.newslist = newslist;
    }

    public String getNextId() {
        return nextId;
    }

    public List<NewsEntity> getNewslist() {
        return newslist;
    }


}

