package com.me.silencedut.nbaplus.RxMethod;

import com.me.silencedut.greendao.GreenNews;
import com.me.silencedut.greendao.GreenNewsDao;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;

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
    public static Subscription updateNews(final String newsType) {
        Subscription subscription = AppService.getNbaPlus().updateNews(newsType)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                       cacheNews(news,newsType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.UPDATE));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(null,null);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
        return subscription;
    }

    public static Subscription loadMoreNews(final String newsType,final String newsId) {
        Subscription subscription = AppService.getNbaPlus().loadMoreNews(newsType,newsId)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        cacheNews(news,newsType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.LOADMORE));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(null,null);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
        return subscription;
    }

    public static Subscription initNews(final String newsType) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                List<GreenNews> greenNewses = getCacheNews(newsType);
                if (greenNewses != null && greenNewses.size() > 0) {
                    News news = AppService.getGson().fromJson(greenNewses.get(0).getNewslist(), News.class);
                    subscriber.onNext(news);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.INIT));
                    }
                });

        return subscription;
    }
    private static void cacheNews(News news, String newsType) {
        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        DeleteQuery deleteQuery = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String newsList = AppService.getGson().toJson(news);
        GreenNews greenNews = new GreenNews(null,newsList,newsType);
        greenNewsDao.insert(greenNews);
    }

    private static List<GreenNews> getCacheNews(String newsType) {
        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        Query query = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .build();
        // 查询结果以 List 返回
        List<GreenNews> greenNewses = query.list();
        return greenNewses;
    }



}
