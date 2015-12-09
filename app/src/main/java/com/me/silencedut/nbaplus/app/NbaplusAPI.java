package com.me.silencedut.nbaplus.app;


import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.NewsDetile;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public interface NbaplusAPI {
    @GET("api/v1.0/update/{type}")
    Observable<News> updateNews(@Path("type") String type);
    @GET("api/v1.0/loadmore/{type}/{newsId}")
    Observable<News> loadMoreNews(@Path("type") String type,@Path("newsId") String newsId );
    @GET("{contentUrl}")
    Observable<NewsDetile> getNewsDetile(@Path("contentUrl") String contenturl);
}