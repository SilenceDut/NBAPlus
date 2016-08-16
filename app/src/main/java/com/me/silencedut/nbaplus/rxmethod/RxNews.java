package com.me.silencedut.nbaplus.rxmethod;

import android.util.Log;

import com.me.silencedut.greendao.GreenNews;
import com.me.silencedut.greendao.GreenNewsDao;
import com.me.silencedut.nbaplus.app.App;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsDetailEvent;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.NewsDetile;
import com.me.silencedut.nbaplus.utils.PreferenceUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by SilenceDut on 2015/12/2.
 */
public class RxNews {

    public static Subscription initNews(final String newsType) {

        return Observable.create(new Observable.OnSubscribe<News>() {
                    @Override
                    public void call(Subscriber<? super News> subscriber) {
                        News news = getCacheNews(newsType);
                        subscriber.onNext(news);
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        NewsEvent newsEvent= new NewsEvent(news, Constant.GETNEWSWAY.INIT,newsType);
                        if(news==null) {
                            newsEvent.setEventResult(Constant.Result.FAIL);
                        }
                        AppService.getBus().post(newsEvent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), Constant.GETNEWSWAY.INIT,newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
    }

    public static Subscription updateNews(final String newsType) {
        return  AppService.getNbaplus().updateNews(newsType)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        cacheNews(news, newsType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.UPDATE, newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), Constant.GETNEWSWAY.UPDATE,newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
    }

    public static Subscription loadMoreNews(final String newsType,final String newsId) {
        return AppService.getNbaplus().loadMoreNews(newsType, newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.LOADMORE,newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), Constant.GETNEWSWAY.LOADMORE,newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
    }

    public static Subscription getNewsDetail(final String date,final String detailId) {
        return AppService.getNewsDetileApi().getNewsDetile(date,detailId+".json")
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsDetile, String>() {
                    @Override
                    public String call(NewsDetile newsDetile) {
                        int imageCount = 0;
                        String assStyle = String.format(Constant.CSS_STYLE, PreferenceUtils.getPrefString(App.getContext()
                                , Constant.ACTILEFONTSIZE, "18"), "#333333");
                        String html = "<html><head>" + assStyle + "</head><body>"
                                + newsDetile.getContent() + "<p>（" + newsDetile.getAuthor() + "）<p></body></html>";
                        Pattern p = Pattern.compile("(<p class=\"reader_img_box\"><img id=\"img_\\d\" class=\"reader_img\" src=\"reader_img_src\" /></p>)+");
                        Matcher m = p.matcher(html);
                        while (m.find() && imageCount < newsDetile.getArticleMediaMap().size()) {
                            if (imageCount == 0) {
                                html = html.replace(html.substring(m.start(), (m.end())), "");
                            } else {
                                if (PreferenceUtils.getPrefBoolean(App.getContext(), Constant.LOADIMAGE, true)) {
                                    html = html.replace(html.substring(m.start(), (m.end())), "<img src="
                                            + newsDetile.getArticleMediaMap().get(String.format("img_%d", imageCount)).getUrl() + " "
                                            + "width=\"100%\" height=\"auto\">");
                                }
                            }
                            imageCount++;
                            m = p.matcher(html);
                        }
                        return html;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String newsContent) {
                        AppService.getBus().post(new NewsDetailEvent(newsContent));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsDetailEvent newsDetailEvent = new NewsDetailEvent(throwable.toString());
                        newsDetailEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsDetailEvent);
                    }
                });
    }

    private static void cacheNews(final News news,final String newsType) {

        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        DeleteQuery deleteQuery = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String newsList = AppService.getGson().toJson(news);
        GreenNews greenNews = new GreenNews(null, newsList, newsType);
        greenNewsDao.insert(greenNews);

    }

    private static News getCacheNews(String newsType) {
        News news=null;
        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        Query query = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .build();
        // 查询结果以 List 返回
        List<GreenNews> greenNewses = query.list();
        if(greenNewses!=null&&greenNewses.size()>0) {
            news = AppService.getGson().fromJson(greenNewses.get(0).getNewslist(), News.class);
        }
        return news;
    }



}
