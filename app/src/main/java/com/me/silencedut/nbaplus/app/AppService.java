package com.me.silencedut.nbaplus.app;


import com.google.gson.Gson;
import com.me.silencedut.greendao.DBHelper;
import com.me.silencedut.nbaplus.RxMethod.RxNews;
import com.me.silencedut.nbaplus.data.Cache;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class AppService {
    private static final AppService NBAPLUS_SERVICE=new AppService();
    private static Gson sGson;
    private static EventBus sBus ;
    private static DBHelper sDBHelper;
    private static NbaplusAPI sNbaplus;
    private Map<Integer,CompositeSubscription> mCompositeSubMap;
    private CompositeSubscription mCompositeSubscription ;
    private Cache mCache;



    public void initService() {
        sBus = new EventBus();
        sGson=new Gson();
        mCompositeSubMap=new HashMap<Integer,CompositeSubscription>();
        backGroundInit();
    }

    private void backGroundInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sNbaplus=NbaplusFactory.getNbaplus();
                sDBHelper=DBHelper.getInstance(App.getContext());
                mCache= Cache.getInstance();
                mCache.initSnappyDB();

            }
        }).start();

    }

    public void addCompositeSub(int taskId) {
        mCompositeSubscription= new CompositeSubscription();

        mCompositeSubMap.put(taskId, mCompositeSubscription);
    }

    public void removeCompositeSub(int taskId) {
        mCompositeSubscription= mCompositeSubMap.get(taskId);
        mCompositeSubscription.unsubscribe();
        mCompositeSubMap.remove(taskId);
    }

    public void updateNews(String type) {

        mCompositeSubscription.add(RxNews.updateNews(type));
    }

    private AppService(){}

    public static AppService getInstance() {
        return NBAPLUS_SERVICE;
    }

    public static EventBus getBus() {
        return sBus;
    }

    public static NbaplusAPI getNbaPlus() {
        return sNbaplus;
    }

    public static DBHelper getDBHelper() {
        return sDBHelper;
    }

    public static Gson getGson() {
        return sGson;
    }

}
