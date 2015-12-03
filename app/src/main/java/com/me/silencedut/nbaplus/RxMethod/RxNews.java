package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.greendao.GreenNewsDao;
import com.me.silencedut.nbaplus.app.NbaplusService;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.data.News;

import java.util.List;

import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.Subscribers;
import rx.schedulers.Schedulers;

/**
 * Created by SilenceDut on 2015/12/2.
 */
public class RxNews {
    public static Subscription updateNews() {
        Subscription subscription = NbaplusService.getNbaPlus().updateNews()
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

                        NbaplusService.getBus().post(new NewsEvent(news));
                    }
                });
        return subscription;
    }

    public static Subscription initNews() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                News news;
                GreenNewsDao greenNewsDao= NbaplusService.getDBHelper().getDaoSession().getGreenNewsDao();
                Query query = greenNewsDao().queryBuilder()
                        .where(GreenNewsDao.Properties.Text.eq("news"))
                        .orderAsc(NoteDao.Properties.Date)
                        .build();
                // 查询结果以 List 返回
                List notes = query.list();

            }
        }).subscribeOn(Schedulers.io())
    }
}
