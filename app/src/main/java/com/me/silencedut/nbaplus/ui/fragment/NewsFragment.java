package com.me.silencedut.nbaplus.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.AnimatEndEvent;
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
 * Created by SilenceDut on 2015/12/4.
 */
public abstract class NewsFragment extends SwipeRefreshBaseFragment implements OnLoadMoreListener {
    @Bind(R.id.rv_news)
    RecyclerView mNewsListView;
    @Bind(R.id.newsContainer)
    CoordinatorLayout newsContainer;
    @Bind(R.id.refresh)
    View refreshButton;
    @Bind(R.id.mian_title)
    View mainTitle;

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

    protected void stopAll() {
        stopRefreshing();
        stopLoading();
    }

    protected void stopLoading() {
        mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
        mLoadAdapter.setLoading(false);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }


    protected void updateView(NewsEvent newsEvent) {
        if(newsEvent.getNewsWay().equals(Constant.GETNEWSWAY.INIT)) {
            setRefreshing();
        }else {
            stopRefreshing();
            stopLoading();
            AppUtils.showSnackBar(newsContainer, R.string.load_fail);
        }
    }



    public void onEventMainThread(AnimatEndEvent animatEndEvent) {
        setRefreshing();
    }

}
