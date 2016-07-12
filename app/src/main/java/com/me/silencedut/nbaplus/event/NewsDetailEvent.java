package com.me.silencedut.nbaplus.event;

/**
 * Created by SilenceDut on 2015/12/9.
 */
public class NewsDetailEvent extends Event {
    private String mNewsContent;
    public NewsDetailEvent(String newsContent) {
        this.mNewsContent=newsContent;
    }
    public String getContent() {
        return mNewsContent;
    }
}
