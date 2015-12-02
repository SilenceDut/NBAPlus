package com.me.silencedut.nbaplus.app;


import com.me.silencedut.nbaplus.model.News;

import retrofit.http.GET;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public interface Nbaplus {
    @GET("/news/update")
    News updateNews();
}