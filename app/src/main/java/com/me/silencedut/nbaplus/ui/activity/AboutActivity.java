package com.me.silencedut.nbaplus.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.wigdets.RevealBackgroundView;
import com.me.silencedut.nbaplus.utils.VersionUtils;
import butterknife.Bind;
import de.psdev.licensesdialog.LicensesDialog;
/**
 * Created by SilenceDut on 2015/9/28.
 */
public class AboutActivity extends SwipeBackActivity  implements RevealBackgroundView.OnStateChangeListener{
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.tv_version)
    TextView mVersionTextView;
    @Bind(R.id.revealBackgroundView) RevealBackgroundView mRevealBackgroundView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    public static void navigateFrom(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initViews() {
        super.initViews();
        setupRevealBackground();
    }

    @Override
    void setTitle() {
        mCollapsingToolbarLayout.setTitle(getResources().getString(R.string.about));
        mVersionTextView.setText("Version " + VersionUtils.setUpVersionName(this));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about;
    }


    public void onMultipleClick(final View view) {
        new LicensesDialog.Builder(this)
                .setNotices(R.raw.notices)
                .setIncludeOwnLicense(true)
                .build()
                .showAppCompat();
    }

    private void setupRevealBackground() {
        mRevealBackgroundView.setOnStateChangeListener(this);

        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        final int[] startingLocation = {screenWidth,0};
        mRevealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRevealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                mRevealBackgroundView.startFromLocation(startingLocation);
                return true;
            }
        });

    }


    @Override
    public void onStateChange(int state) {

        if (RevealBackgroundView.STATE_FINISHED == state) {
            mCoordinatorLayout.setVisibility(View.VISIBLE);
        }else {
            mCoordinatorLayout.setVisibility(View.INVISIBLE);
        }
    }


    public void onEventMainThread(Event event) {

    }

}
