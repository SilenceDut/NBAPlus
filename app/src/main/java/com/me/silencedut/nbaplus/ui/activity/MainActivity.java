package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;
import com.me.silencedut.nbaplus.ui.fragment.BlogFragment;
import com.me.silencedut.nbaplus.ui.fragment.DrawerFragment;
import com.me.silencedut.nbaplus.ui.fragment.MainFragment;

public class MainActivity extends BaseActivity {
    private DrawerFragment mNavigationFragment;
    private Fragment mCurrentFragment;
    private int mCurrentDrawId=R.string.news;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mNavigationFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp((FrameLayout) findViewById(R.id.main_content),
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer));
        mCurrentFragment= MainFragment.newInstance();
        transactionFragment(mCurrentFragment);
    }

    private void transactionFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(Constant.Result.FAIL.equals(drawerClickEvent.getEventResult())||drawerClickEvent.getDrawId()==mCurrentDrawId) {
            return;
        }

        switch (drawerClickEvent.getDrawId()) {
            case R.string.news:
                mCurrentFragment=MainFragment.newInstance();
                mCurrentDrawId=drawerClickEvent.getDrawId();
                break;
            case R.string.blog:
                mCurrentFragment=BlogFragment.newInstance();
                mCurrentDrawId=drawerClickEvent.getDrawId();
                break;
            default:
                break;
        }
        transactionFragment(mCurrentFragment);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationFragment.isDrawerOpen()) {
            mNavigationFragment.closeDrawer();
        } else {
            finish();
        }
    }




}
