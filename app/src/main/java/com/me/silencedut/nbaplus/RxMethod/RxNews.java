package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.greendao.GreenNews;
import com.me.silencedut.greendao.GreenNewsDao;
import com.me.silencedut.nbaplus.app.NbaplusService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.event.NewsEvent;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by SilenceDut on 2015/12/2.
 */
public class RxNews {
    public static Subscription initNews() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                GreenNewsDao greenNewsDao= NbaplusService.getDBHelper().getDaoSession().getGreenNewsDao();
                Query query = greenNewsDao.queryBuilder()
                        .where(GreenNewsDao.Properties.Type.eq(Constant.NEWS_TYPE))
                        .build();
                List<GreenNews> newslist = query.list();
                if(newslist!=null&&newslist.size()>0) {
                    News news = NbaplusService.getGson().fromJson(newslist.get(0).getNewslist(),News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        NbaplusService.getBus().post(new NewsEvent(news));
                    }
                });
        return subscription;
    }

    public static Subscription updateNews() {
        Subscription subscription = NbaplusService.getNbaPlus().updateNews()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        GreenNewsDao greenNewsDao = NbaplusService.getDBHelper().getDaoSession().getGreenNewsDao();
                        DeleteQuery<GreenNews> deleteQuery = greenNewsDao.queryBuilder()
                                .where(GreenNewsDao.Properties.Type.eq(Constant.NEWS_TYPE))
                                .buildDelete();
                        deleteQuery.executeDeleteWithoutDetachingEntities();
                        String newslist = NbaplusService.getGson().toJson(news);
                        greenNewsDao.insert(new GreenNews(null, newslist, Constant.NEWS_TYPE));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("subscriptiononError", "onError" + e.toString());
                    }

                    @Override
                    public void onNext(News news) {

                        NbaplusService.getBus().post(new NewsEvent(news));
                    }
                });
        return subscription;
    }


}
