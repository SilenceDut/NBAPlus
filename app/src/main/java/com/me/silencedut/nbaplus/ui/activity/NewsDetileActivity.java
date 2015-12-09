package com.me.silencedut.nbaplus.ui.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.utils.LollipopUtils;

import butterknife.Bind;

/**
 * Created by asan on 2015/12/8.
 */
public class NewsDetileActivity extends SwipeBackActivity{
    @Bind(R.id.webview)
    WebView mWebView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Intent mGetIntent;
    private ImageView mTitleImage;
    public static final String TITLE ="TITLE";
    public static final String DETILE_URL="DETILE_URL";
    public static final String IMAGE_URL="IMAGE_URL";
    public static final String IMAGE_EXIST="IMAGE_EXIST";

    @Override
    protected int getContentViewId() {
        return hasTitleImage()? R.layout.activity_detile : R.layout.activity_detile_noimage;
    }

    @Override
    public void onEventMainThread(Event event) {

    }


    private boolean hasTitleImage() {
        return getIntent().getBooleanExtra(IMAGE_EXIST, false);
    }

    @Override
    void setTitle() {
        mToolBar.setTitle(mGetIntent.getStringExtra(TITLE));
    }

    @Override
    protected void initViews() {
        super.initViews();
        mGetIntent=getIntent();
        if(hasTitleImage()) {

            mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
            mCollapsingToolbarLayout.setTitle(mGetIntent.getStringExtra(TITLE));
            mTitleImage = (ImageView)findViewById(R.id.titleImage);
            mTitleImage.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(NewsDetileActivity.this).load(mGetIntent.getStringExtra(IMAGE_URL))
                    .placeholder(R.color.colorPrimary)
                    .into(mTitleImage);
                }
            });
//            Glide.with(this).load(mGetIntent.getStringExtra(IMAGE_URL))
//                    .placeholder(R.color.colorPrimary)
//                    .into(mTitleImage);

        }else {
            mToolBar.setBackgroundResource(R.color.colorPrimary);
        }
        LollipopUtils.setStatusBarColor(this, R.color.colorPrimary);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
    }
}
