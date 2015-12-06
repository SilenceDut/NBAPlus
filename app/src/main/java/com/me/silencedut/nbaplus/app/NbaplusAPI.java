package com.me.silencedut.nbaplus.app;


import com.me.silencedut.nbaplus.model.News;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public interface NbaplusAPI {
    @GET("{type}/update")
    Observable<News> updateNews(@Path("type") String type);
    @GET("{type}/{newsId}")
    Observable<News> loadmore(@Path("type") String type,@Path("newsId") String newsId );
}