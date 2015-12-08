package com.me.silencedut.nbaplus.event;

import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.data.Constant.GETNEWSWAY;
/**
 * Created by Silencedut on 2015/11/28.
 */
public class NewsEvent extends Event {

    private News news;
    private GETNEWSWAY getNewsWay;

    public NewsEvent(News news,GETNEWSWAY getNewsWay) {
        this.news=news;
        this.getNewsWay=getNewsWay;

    }

    public News getNews() {
        return news;
    }

    public GETNEWSWAY getNewsWay() {
        return getNewsWay;
    }
}
