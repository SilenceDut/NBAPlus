package com.me.silencedut.nbaplus.event;

/**
 * Created by asan on 2015/12/9.
 */
public class NewsDetileEvent extends Event {
    private String mNewsContent;
    public NewsDetileEvent(String newsContent) {
        this.mNewsContent=newsContent;
    }
    public String getContent() {
        return mNewsContent;
    }
}
