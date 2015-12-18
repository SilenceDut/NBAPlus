package com.me.silencedut.nbaplus.RxMethod;

import android.util.Log;

import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.event.StatEvent;
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
                        String[][] lables = getLables(statistics);
                        float[][] ststValues=getStatValues(statistics);
                         Log.d("getAllStats", lables[0][0] + ";;;" + statistics.getDailyStat().get(0).getName());
                         AppService.getBus().post(new StatEvent(kind[0], lables,ststValues));
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

    private static String[][] getLables(Statistics statistics) {
        String[][] players=new String[2][5];
        for(int index=0;index<5;index++) {
            players[0][index]=parseLable(statistics.getDailyStat().get(index));
            players[1][index]=parseLable(statistics.getEverageStat().get(index));
        }
        return players;
    }

    private static String parseLable(Statistics.StatEntity statEntity) {
        StringBuilder lable =new StringBuilder();
        String playerName=statEntity.getName();
        for(int index=0;index<playerName.length()/4;index++) {
            lable.append(playerName.substring(index*4,index+4));
            lable.append("\n");
        }
        if(playerName.length()%4!=0) {
            lable.append(playerName.substring(4 * (playerName.length() / 4), playerName.length()));
            lable.append("\n");
        }
        lable.append("(");
        lable.append(statEntity.getTeam());
        lable.append(")");
        return lable.toString();

    }

    private static float[][] getStatValues(Statistics statistics) {
        float[][] statValues=new float[2][5];
        for(int index=0;index<5;index++) {
            statValues[0][index]=Float.parseFloat(statistics.getDailyStat().get(index).getStatdata());
            statValues[1][index]=Float.parseFloat(statistics.getEverageStat().get(index).getStatdata());
        }
        return statValues;
    }
}
