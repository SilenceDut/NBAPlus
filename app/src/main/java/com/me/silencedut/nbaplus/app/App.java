package com.me.silencedut.nbaplus.app;

import android.app.Application;
import android.content.Context;

import im.fir.sdk.FIR;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        FIR.init(this);
        super.onCreate();
        sContext = getApplicationContext();
        NbaplusService.getInstance().initService();

    }
    public static Context getContext() {
        return sContext;
    }

}
