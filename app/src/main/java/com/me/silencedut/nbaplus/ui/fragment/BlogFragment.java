package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter.BlogAdapter;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class BlogFragment extends NewsFragment {
    private static boolean mFirstAnimate=true;
    public static BlogFragment newInstance() {
        BlogFragment blogFragment = new BlogFragment();
        return blogFragment;
    }

    @Override
    void setAdapter() {
        mLoadAdapter=new BlogAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        mNewsListView.setVerticalScrollBarEnabled(false);
        mLoadAdapter.setAnimate(mFirstAnimate);
        mFirstAnimate=false;
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
            updateView(newsEvent);
        }
    }

    @Override
    protected int getTitle() {
        return R.string.blog;
    }

}
