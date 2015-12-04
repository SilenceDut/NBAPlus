package com.me.silencedut.nbaplus.app;


import com.me.silencedut.nbaplus.model.News;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public interface NbaplusAPI {
    @GET("news/update")
    Observable<News> updateNews();
}