package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.nbaplus.app.NbaplusService;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
}
