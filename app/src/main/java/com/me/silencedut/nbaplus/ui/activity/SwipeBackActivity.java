package com.me.silencedut.nbaplus.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.ui.wigdets.SwipeBackLayout;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/8.
 */
public abstract class SwipeBackActivity extends BaseActivity {
    @Bind(R.id.swipBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;


    abstract void setTitle();

    @Override
    protected void initViews() {

        initToolBar();
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });

    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle();
    }



}
