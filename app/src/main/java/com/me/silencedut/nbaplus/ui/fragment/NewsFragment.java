package com.me.silencedut.nbaplus.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.News.NewslistEntity;
import com.me.silencedut.nbaplus.ui.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/4.
 */
public abstract class NewsFragment extends SwipeRefreshBaseFragment {
    @Bind(R.id.rv_news)
    RecyclerView mNewsListView;
    @Bind(R.id.newsContainer)
    CoordinatorLayout newsContainer;

    protected List<NewslistEntity> mNewsListEntity = new ArrayList<NewslistEntity>();
    protected MainAdapter mMainAdapter;
    protected String mNextnewsId;

    abstract void setAdapter();

    @Override
    protected void initViews() {
        super.initViews();
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsListView.getContext());
        mNewsListView.setLayoutManager(linearLayoutManager);
        setAdapter();
        AppService.getInstance().updateNews(Constant.NEWSTYPE.NEWS.getNewsType());
    }

    public void onEventMainThread(Event event) {
        if(event!=null&&event instanceof NewsEvent) {
            Log.d("onEventMainThread", ((NewsEvent) event).getNews().getNextId() + "");
            News news=((NewsEvent) event).getNews();
            mNextnewsId=news.getNextId();
            switch (((NewsEvent) event).getNewsWay()) {
                case INIT:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    setRefreshing();
                    break;
                case UPDATE:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    stopRefreshing();
                    break;
                case LOADMORE:
                    mNewsListEntity.addAll(news.getNewslist());
                    stopLoading();
                    break;
                default:
                    break;
            }
            mMainAdapter.notifyDataSetChanged();
        }
    }

    public void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    protected void stopLoading() {
        mMainAdapter.setLoading(false);
        mMainAdapter.notifyItemChanged(mMainAdapter.getItemCount() - 1);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }



    @Override
    protected String getToolBarTitle() {
        return "";
    }

    public View getContainer() {
        return newsContainer;
    }



}
