package com.me.silencedut.nbaplus.app;


import com.me.silencedut.nbaplus.RxMethod.RxNews;
import com.me.silencedut.nbaplus.data.Cache;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.NewsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class NbaplusService {
    private static final NbaplusService NBAPLUS_SERVICE=new NbaplusService();
    public  static EventBus mBus ;
    private Map<Integer,CompositeSubscription> mCompositeSubMap;
    private CompositeSubscription mCompositeSubscription ;
    private Cache mCache;
    private static NbaplusAPI mNbaPlus;
    private List<NewsEntity> mNewsList=null;


    public void initService() {
        mBus = new EventBus();
        mCompositeSubMap=new HashMap<Integer,CompositeSubscription>();
        backGroundInit();
    }

    private void backGroundInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNbaPlus=NbaplusFactory.getNbaplus();
                mCache= Cache.getInstance();
                mCache.initSnappyDB();
                mNewsList=(List<NewsEntity>)mCache.get(Cache.CACHEKEY.NEWSFEED.name(),List.class);
                if (mNewsList==null) {
                    mNewsList=new ArrayList<NewsEntity>();
                }
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

    public void updateNews() {

        mCompositeSubscription.add(RxNews.updateNews());
    }

    private NbaplusService(){}

    public static NbaplusService getInstance() {
        return NBAPLUS_SERVICE;
    }

    public static EventBus getBus() {
        return mBus;
    }

    public static NbaplusAPI getNbaPlus() {
        return mNbaPlus;
    }

}
