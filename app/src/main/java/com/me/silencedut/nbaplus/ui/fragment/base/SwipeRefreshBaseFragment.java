package com.me.silencedut.nbaplus.ui.fragment.base;

import android.support.v4.widget.SwipeRefreshLayout;

import com.me.silencedut.nbaplus.R;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public abstract class SwipeRefreshBaseFragment extends ToorbarBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryLight);
    }


}
