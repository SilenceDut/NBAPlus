package com.me.silencedut.nbaplus.app;

import com.me.silencedut.nbaplus.model.NewsDetile;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by SilenceDut on 2015/12/10.
 */
public interface NewsDetileAPI {
    @GET("{date}/{detileId}")
    Observable<NewsDetile> getNewsDetile(@Path("date") String type,@Path("detileId") String newsId);
}
