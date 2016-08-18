package com.me.silencedut.nbaplus.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import im.fir.sdk.FIR;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class App extends Application {
    private static Application sInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
//        mRefWatcher = LeakCanary.install(this);
        sInstance = this;
        AppService.getInstance().initService();

    }
    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

}
