package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter.BlogAdapter;
import com.me.silencedut.nbaplus.utils.AppUtils;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class BlogFragment extends NewsFragment {

    public static BlogFragment newInstance() {
        BlogFragment blogFragment = new BlogFragment();
        return blogFragment;
    }

    @Override
    void setAdapter() {
        mSwipeRefreshLayout.setBackgroundResource(R.color.main_bg);
        mLoadAdapter=new BlogAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        mNewsListView.setVerticalScrollBarEnabled(false);
        initCaChe();
    }

    private void initCaChe() {
        AppService.getInstance().initNews(getTaskId(), Constant.NEWSTYPE.BLOG.getNewsType());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateNews(getTaskId(), Constant.NEWSTYPE.BLOG.getNewsType());
    }


    @Override
    public void onLoadMore() {
        if (mLoadAdapter.canLoadMore()) {
            mLoadAdapter.setLoading(true);
            mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(getTaskId(),Constant.NEWSTYPE.BLOG.getNewsType(), mNewsId);
        }
    }

    public void onEventMainThread(NewsEvent newsEvent) {
        if(newsEvent!=null&&Constant.NEWSTYPE.BLOG.getNewsType().equals(newsEvent.getNewsType())) {
            mNewsEvent=newsEvent;
            if (Constant.Result.FAIL.equals(newsEvent.getEventResult())) {
                updateView(newsEvent);
            } else {
                News news = newsEvent.getNews();
                mNewsId = news.getNextId();
                switch (newsEvent.getNewsWay()) {
                    case INIT:
                        mNewsListEntity.clear();
                        mNewsListEntity.addAll(news.getNewslist());
                        mLoadAdapter.updateItem(true);
                        break;
                    case UPDATE:
                        mNewsListEntity.clear();
                        mNewsListEntity.addAll(news.getNewslist());
                        stopRefreshing();
                        mLoadAdapter.updateItem(false);
                        break;
                    case LOADMORE:
                        mNewsListEntity.addAll(news.getNewslist());
                        stopAll();
                        mLoadAdapter.updateItem(false);
                        break;
                    default:
                        break;
                }
                if (Constant.GETNEWSWAY.UPDATE.equals(newsEvent.getNewsWay())) {
                    AppUtils.showSnackBar(newsContainer, R.string.load_success);
                }

            }
        }
    }

    @Override
    protected int getTitle() {
        return R.string.blog;
    }
}
