package com.me.silencedut.nbaplus.RxMethod;

import com.me.silencedut.nbaplus.app.NbaplusCilent;
import com.me.silencedut.nbaplus.app.NbaplusService;
import com.me.silencedut.nbaplus.model.News;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by SilenceDut on 2015/12/2.
 */
public class RxNews {
    public static Subscription updateNews() {
        Subscription subscription = NbaplusCilent.getCilent().updateNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        NbaplusService.getBus().post(News);
                    }
                });
        return subscription;
    }
}
