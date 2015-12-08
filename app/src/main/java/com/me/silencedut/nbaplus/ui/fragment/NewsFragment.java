package com.me.silencedut.nbaplus.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.News.NewslistEntity;
import com.me.silencedut.nbaplus.ui.adapter.LoadAdapter;
import com.me.silencedut.nbaplus.ui.listener.RecyclerViewLoadMoreListener;
import com.me.silencedut.nbaplus.ui.listener.RecyclerViewLoadMoreListener.OnLoadMoreListener;
import com.me.silencedut.nbaplus.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/12/4.
 */
public abstract class NewsFragment extends SwipeRefreshBaseFragment implements OnLoadMoreListener {
    @Bind(R.id.rv_news)
    RecyclerView mNewsListView;
    @Bind(R.id.newsContainer)
    CoordinatorLayout newsContainer;

    protected List<NewslistEntity> mNewsListEntity = new ArrayList<NewslistEntity>();
    protected LoadAdapter mLoadAdapter;
    protected String mNewsId="";

    abstract void setAdapter();

    @Override
    protected void initViews() {
        super.initViews();
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsListView.getContext());
        mNewsListView.setLayoutManager(linearLayoutManager);
        mNewsListView.addOnScrollListener(new RecyclerViewLoadMoreListener(linearLayoutManager, this, 0));
        setAdapter();

    }

    public void updateViews(NewsEvent newsEvent) {

        if(Constant.Result.FAIL.equals(newsEvent.getEventResult())) {
            stopRefreshing();
            stopLoading();
            AppUtils.showSnackBar(newsContainer, R.string.load_fail);
            return;
        }
        News news= newsEvent.getNews();
        mNewsId=news.getNextId();
        switch (newsEvent.getNewsWay()) {
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
        mLoadAdapter.notifyDataSetChanged();
        AppUtils.showSnackBar(newsContainer, R.string.load_success);


    }

    protected void stopLoading() {
        mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
        mLoadAdapter.setLoading(false);
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
