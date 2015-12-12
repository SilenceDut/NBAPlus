package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;
import com.me.silencedut.nbaplus.ui.fragment.BlogFragment;
import com.me.silencedut.nbaplus.ui.fragment.DrawerFragment;
import com.me.silencedut.nbaplus.ui.fragment.MainFragment;
import com.me.silencedut.nbaplus.ui.fragment.SettingFragment;
import com.me.silencedut.nbaplus.ui.fragment.SortFragment;

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
        transactionSupportFragment(mCurrentFragment);
    }

    private void transactionSupportFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(Constant.Result.FAIL.equals(drawerClickEvent.getEventResult())||drawerClickEvent.getDrawId()==mCurrentDrawId) {
            return;
        }
        mCurrentDrawId=drawerClickEvent.getDrawId();
        switch (mCurrentDrawId) {
            case R.string.news:
                mCurrentFragment=MainFragment.newInstance();
                break;
            case R.string.blog:
                mCurrentFragment=BlogFragment.newInstance();
                break;
            case R.string.sort:
                mCurrentFragment= SortFragment.newInstance();
                break;
            case R.string.action_settings:
                mCurrentFragment= SettingFragment.newInstance();
                break;
            default:
                break;
        }

        transactionSupportFragment(mCurrentFragment);

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
