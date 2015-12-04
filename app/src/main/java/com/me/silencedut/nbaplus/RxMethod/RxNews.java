package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.greendao.GreenNews;
import com.me.silencedut.greendao.GreenNewsDao;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;

import java.util.List;

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
    public static Subscription updateNews(String type) {
        Subscription subscription = AppService.getNbaPlus().updateNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("subscriptiononError","onError"+ e.toString());
                    }
                    @Override
                    public void onNext(News news) {

                        AppService.getBus().post(new NewsEvent(news));
                    }
                });
        return subscription;
    }

    public static Subscription initNews(String type) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
                Query query = greenNewsDao.queryBuilder()
                        .where(GreenNewsDao.Properties.Type.eq(Constant.NEWSTYPE.NEWS.name()))
                        .build();
                // 查询结果以 List 返回
                List<GreenNews> greenNewses = query.list();
                if(greenNewses!=null&&greenNewses.size()>0) {
                    News news = AppService.getGson().fromJson(greenNewses.get(0).getNewslist(),News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news));
                    }
                });

        return subscription;
    }

}
