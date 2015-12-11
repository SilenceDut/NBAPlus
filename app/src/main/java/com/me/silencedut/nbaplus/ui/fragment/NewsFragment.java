package com.me.silencedut.nbaplus.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.AnimatEndEvent;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.News.NewslistEntity;
import com.me.silencedut.nbaplus.ui.activity.AboutActivity;
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


    protected List<NewslistEntity> mNewsListEntity = new ArrayList<NewslistEntity>();
    protected NewsEvent mNewsEvent;
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

    @Override
    public void onStop() {
        super.onStop();
        // sometimes refresh maybe get no date ,so cache the date when fragment onstop
        if(mNewsEvent==null){
            return;
        }
        News news=new News();
        news.setNewslist(mNewsListEntity);
        news.setNextId(mNewsId);
        AppService.getInstance().cacheNews(news,mNewsEvent.getNewsType());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.about :
                AboutActivity.navigateFrom(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEventMainThread(AnimatEndEvent animatEndEvent) {
        setRefreshing();
    }

}
