package com.me.silencedut.nbaplus.app;


import com.google.gson.Gson;
import com.me.silencedut.greendao.DBHelper;
import com.me.silencedut.nbaplus.RxMethod.RxNews;

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
    private static NewsDetileAPI sNewsDetileApi;
    private Map<Integer,CompositeSubscription> mCompositeSubMap;
    private CompositeSubscription mCompositeSubscription ;

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
                sNewsDetileApi=NbaplusFactory.getNewsDetileInstance();
                sDBHelper=DBHelper.getInstance(App.getContext());

            }
        }).start();

    }

    public void addCompositeSub(int taskId) {
        if(mCompositeSubMap.get(taskId)==null) {
            mCompositeSubscription = new CompositeSubscription();
            mCompositeSubMap.put(taskId, mCompositeSubscription);
        }
    }

    public void removeCompositeSub(int taskId) {
        if(mCompositeSubMap!=null&& mCompositeSubMap.get(taskId)!=null){
            mCompositeSubscription= mCompositeSubMap.get(taskId);
            mCompositeSubscription.unsubscribe();
            mCompositeSubMap.remove(taskId);
        }
    }

    public void initNews(int taskId,String type) {
        getCompositeSubscription(taskId);
        mCompositeSubscription.add(RxNews.initNews(type));
    }

    public void updateNews(int taskId,String type) {
        getCompositeSubscription(taskId);
        mCompositeSubscription.add(RxNews.updateNews(type));
    }

    public void loadMoreNews(int taskId,String type,String newsId) {
        getCompositeSubscription(taskId);
        mCompositeSubscription.add(RxNews.loadMoreNews(type, newsId));
    }

    public void getNewsDetile(int taskId,String date,String detielId) {
        getCompositeSubscription(taskId);
        mCompositeSubscription.add(RxNews.getNewsDetile(date,detielId));
    }

    public void getCompositeSubscription(int taskId) {

        if(mCompositeSubMap.get(taskId)==null) {
            mCompositeSubscription = new CompositeSubscription();
            mCompositeSubMap.put(taskId, mCompositeSubscription);
        }else {
            mCompositeSubscription= mCompositeSubMap.get(taskId);
        }
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

    public static NewsDetileAPI getNewsDetileApi() {
        return sNewsDetileApi;
    }

    public static DBHelper getDBHelper() {
        return sDBHelper;
    }

    public static Gson getGson() {
        return sGson;
    }

}
