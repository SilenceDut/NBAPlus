package com.me.silencedut.nbaplus.app;


import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusRetrofit {
    final Nbaplus nbaplus ;
    NbaplusRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nbaplus.sinaapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nbaplus=retrofit.create(Nbaplus.class);

    }

    public Nbaplus getService() {
        return nbaplus;
    }
}
