package com.me.silencedut.nbaplus.rxmethod;

import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.TeamSortEvent;
import com.me.silencedut.nbaplus.model.Teams;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by SlienceDut on 2015/12/23.
 */
public class RxTeamSort {
    public static Subscription getTeams() {
        Subscription subscription = AppService.getNbaplus().getTeamSort()
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Teams>() {
                    @Override
                    public void call(Teams teams) {
                        cacheTeams();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Teams>() {
                    @Override
                    public void call(Teams news) {
                        AppService.getBus().post(new TeamSortEvent(news, Constant.Result.SUCCESS));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AppService.getBus().post(new TeamSortEvent(null, Constant.Result.FAIL));
                    }
                });
        return subscription;
    }

    private static void cacheTeams() {

    }
}

