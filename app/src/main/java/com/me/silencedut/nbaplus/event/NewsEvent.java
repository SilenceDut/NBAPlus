package com.me.silencedut.nbaplus.event;

import com.me.silencedut.nbaplus.model.News;

/**
 * Created by asan on 2015/11/28.
 */
public class NewsEvent extends Event {
    private News mNews;
    NewsEvent(News news) {
        this.mNews=news;
    }

    public News getNews() {
        return mNews;
    }
}
