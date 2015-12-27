package com.me.silencedut.nbaplus.rxmethod;

import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.GamesEvent;
import com.me.silencedut.nbaplus.model.Games;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by SilenceDut on 2015/12/26.
 */
public class RxGames {
    public static Subscription getTeams(String date) {

        Subscription subscription = AppService.getNbaplus().getGames(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Games>() {
                    @Override
                    public void call(Games games) {
                        AppService.getBus().post(new GamesEvent(games, Constant.Result.SUCCESS));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AppService.getBus().post(new GamesEvent(null, Constant.Result.FAIL));
                    }
                });
        return subscription;
    }
}
