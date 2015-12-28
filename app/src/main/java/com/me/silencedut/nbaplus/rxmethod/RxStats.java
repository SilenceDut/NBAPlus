package com.me.silencedut.nbaplus.rxmethod;

import android.util.Log;

import com.me.silencedut.greendao.GreenStat;
import com.me.silencedut.greendao.GreenStatDao;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.StatEvent;
import com.me.silencedut.nbaplus.model.Statistics;

import java.util.List;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by asan on 2015/12/15.
 */
public class RxStats {
    public static Subscription getPerStat(String ...statKinds) {

        Subscription subscription = Observable.from(statKinds)
                .flatMap(new Func1<String, Observable<Statistics>>() {
                    @Override
                    public Observable<Statistics> call(String s) {
                        return AppService.getNbaplus().getPerStats(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<Statistics>() {
                    @Override
                    public void call(Statistics statistics) {
                        cacheStat(statistics,statistics.getDailyStat().get(0).getStatkind());
                    }
                })
                .subscribe(new Action1<Statistics>() {
                    @Override
                    public void call(Statistics statistics) {
                        String[][] lables = getLables(statistics);
                        float[][] ststValues=getStatValues(statistics);
                        String[][] playerUrls=getPlayerUrls(statistics);
                        AppService.getBus().post(new StatEvent(statistics.getDailyStat().get(0).getStatkind(), lables,ststValues,playerUrls));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        StatEvent statEvent = new StatEvent(throwable.toString(), null, null,null);
                        statEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(statEvent);

                    }
                });
                return subscription;
    }

    public static Subscription initStat(final String statKind) {

        Subscription subscription = Observable.create(new Observable.OnSubscribe<Statistics>() {
            @Override
            public void call(Subscriber<? super Statistics> subscriber) {
                Statistics statistics = getCacheStat(statKind);
                subscriber.onNext(statistics);
                subscriber.onCompleted();

            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<Statistics>() {
                    @Override
                    public void call(Statistics statistics) {
                        String[][] lables = getLables(statistics);
                        float[][] ststValues=getStatValues(statistics);
                        String[][] playerUrls=getPlayerUrls(statistics);
                        StatEvent statEvent = new StatEvent(statistics.getDailyStat().get(0).getStatkind(), lables, ststValues,playerUrls);
                        statEvent.setGetNewsWay(Constant.GETNEWSWAY.INIT);
                        AppService.getBus().post(statEvent);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        StatEvent statEvent = new StatEvent(statKind, null, null,null);
                        statEvent.setEventResult(Constant.Result.FAIL);
                        statEvent.setGetNewsWay(Constant.GETNEWSWAY.INIT);
                        AppService.getBus().post(statEvent);
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
        for(int index=0;index<playerName.length()-3;index+=4) {
            lable.append(playerName.substring(index,index+4));
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

    private static String[][] getPlayerUrls(Statistics statistics) {
        String[][] statValues=new String[2][5];
        for(int index=0;index<5;index++) {
            statValues[0][index]=statistics.getDailyStat().get(index).getPlayerurl();
            statValues[1][index]=statistics.getEverageStat().get(index).getPlayerurl();
        }
        return statValues;
    }

    public static void cacheStat(final Statistics stat,final String statKind) {

        GreenStatDao greenStatDao = AppService.getDBHelper().getDaoSession().getGreenStatDao();
        DeleteQuery deleteQuery = greenStatDao.queryBuilder()
                .where(GreenStatDao.Properties.Statentity.eq(statKind))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String statEntity = AppService.getGson().toJson(stat);
        GreenStat greenStat = new GreenStat(null, statEntity, statKind);

        greenStatDao.insert(greenStat);
        Log.d("StatEvent", greenStat.getStatkind() + "getStatkind");

    }

    private static Statistics getCacheStat(String statKind) {
        Statistics statistics=null;
        GreenStatDao greenStatDao = AppService.getDBHelper().getDaoSession().getGreenStatDao();
        Query query = greenStatDao.queryBuilder()
                .where(GreenStatDao.Properties.Statkind.eq(statKind))
                .build();
        // 查询结果以 List 返回
        List<GreenStat> greenStats = query.list();
        if(greenStats!=null&&greenStats.size()>0) {
            statistics = AppService.getGson().fromJson(greenStats.get(0).getStatentity(), Statistics.class);
        }
        return statistics;
    }


}
