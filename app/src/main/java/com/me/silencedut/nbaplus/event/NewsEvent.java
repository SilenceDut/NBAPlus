package com.me.silencedut.nbaplus.event;

import com.me.silencedut.nbaplus.model.News;

/**
 * Created by Silencedut on 2015/11/28.
 */
public class NewsEvent extends Event {

    public enum GETNEWSWAY {
        INIT,UPDATE,LOADMORE;
    }
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
