package com.me.silencedut.nbaplus.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.me.silencedut.nbaplus.app.NbaplusService;

import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initViews();
    protected abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        NbaplusService.getInstance().addCompositeSub(getTaskId());
        NbaplusService.getInstance().getBus().register(this);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NbaplusService.getInstance().removeCompositeSub(getTaskId());
        NbaplusService.getInstance().getBus().unregister(this);
    }
}
