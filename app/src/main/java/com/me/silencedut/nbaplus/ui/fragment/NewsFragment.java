package com.me.silencedut.nbaplus.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.NewsEvent;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/4.
 */
public abstract class NewsFragment extends SwipeRefreshBaseFragment {
    @Bind(R.id.rv_news)
    RecyclerView mNewsList;

    abstract void setAdapter();

    @Override
    protected void initViews() {
        super.initViews();
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsList.getContext());
        mNewsList.setLayoutManager(linearLayoutManager);
        setAdapter();
        AppService.getInstance().updateNews(Constant.NEWSTYPE.NEWS.name());
    }

    public void onEventMainThread(Event event) {
        if(event instanceof NewsEvent) {
            Log.d("onEventMainThread", ((NewsEvent) event).getNews().getNextId() + "");
        }
    }

    @Override
    protected void upDateView(Event event) {
        if(event!=null&&event instanceof NewsEvent) {

        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onRefresh() {
        //AppService.getInstance().updateNews();
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }
}
