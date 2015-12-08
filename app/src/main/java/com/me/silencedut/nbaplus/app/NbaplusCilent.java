package com.me.silencedut.nbaplus.app;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusCilent {
    final NbaplusAPI nbaplus ;
    NbaplusCilent() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nbaplus.sinaapp.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nbaplus=retrofit.create(NbaplusAPI.class);

    }

    public NbaplusAPI getCilent() {
        return nbaplus;
    }
}
