package com.me.silencedut.nbaplus.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;

import butterknife.Bind;

/**
 * Created by asan on 2015/11/28.
 */
public abstract class SwipeRefreshBaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    protected abstract void onEventMainThread(Event event);
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

    @Override
    protected void initViews() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getToolBarTitle());
       // ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationIcon(R.mipmap.ic_menu_white);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryLight);
    }

    protected abstract String getToolBarTitle();
}
