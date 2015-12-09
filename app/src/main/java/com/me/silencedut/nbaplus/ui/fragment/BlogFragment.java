package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.NewsEvent;

/**
 * Created by Administrator on 2015/12/4.
 */
public class BlogFragment extends NewsFragment {

    public static BlogFragment newInstance() {
        BlogFragment blogFragment = new BlogFragment();
        return blogFragment;
    }

    @Override
    void setAdapter() {

    }


    @Override
    public void onLoadMore() {
//        if (mMainAdapter.canLoadMore()) {
//            mMainAdapter.setLoading(true);
//            mMainAdapter.notifyItemChanged(mMainAdapter.getItemCount() - 1);
//            AppService.getInstance().loadMoreNews(Constant.NEWSTYPE.NEWS.getNewsType(), mNewsId);
//        }

    }

    @Override
    public void onRefresh() {

    }

    protected void onEventMainThread(Event event) {

    }

    @Override
    protected String getToolBarTitle() {
        return getString(R.string.blog);
    }
}
