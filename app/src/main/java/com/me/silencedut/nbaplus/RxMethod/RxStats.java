package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.model.Statistics;
import com.me.silencedut.nbaplus.utils.DateFormatter;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by asan on 2015/12/15.
 */
public class RxStats {
    public static Subscription getPerStat(String ...statKind) {
        final String[] kind = new String[1];
        Subscription subscription = Observable.from(statKind)
                .flatMap(new Func1<String, Observable<Statistics>>() {
                    @Override
                    public Observable<Statistics> call(String s) {
                        kind[0] =s;
                        return AppService.getNbaplus().getPerStats(DateFormatter.formatDate("yyyyMMdd"), s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Statistics>() {
                    @Override
                    public void call(Statistics statistics) {
Log.d("getAllStats",kind[0]);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Statistics>() {
                    @Override
                    public void call(Statistics statistics) {
                        Log.d("getAllStats", statistics.getDailyStat().size() + ";;;" + statistics.getDailyStat().get(0).getName());
                        // AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.UPDATE, newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("getAllStats", throwable + ";;;");
//                        NewsEvent newsEvent = new NewsEvent(new News(), Constant.GETNEWSWAY.UPDATE, newsType);
//                        newsEvent.setEventResult(Constant.Result.FAIL);
//                        AppService.getBus().post(newsEvent);
                    }
                });
                return subscription;
    }
}
