package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;
import com.me.silencedut.nbaplus.ui.fragment.TeamSortFragment;
import com.me.silencedut.nbaplus.ui.fragment.base.BaseFragment;
import com.me.silencedut.nbaplus.ui.fragment.BlogFragment;
import com.me.silencedut.nbaplus.ui.fragment.DrawerFragment;
import com.me.silencedut.nbaplus.ui.fragment.MainFragment;
import com.me.silencedut.nbaplus.ui.fragment.playersort.StatisticsFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private DrawerFragment mNavigationFragment;
    private BaseFragment mCurrentFragment;
    private int mCurrentDrawId=R.string.news;
    private Map<String,BaseFragment> mBaseFragmentMap = new HashMap<>();
    private Map<Integer,String> mDrawerIdMap = new HashMap<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        getWindow().setBackgroundDrawable(null);
        mNavigationFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp((FrameLayout) findViewById(R.id.main_content),
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer));
        initDrawerMap();
        mCurrentFragment= getFragment(mDrawerIdMap.get(R.string.news));
        transactionSupportFragment(mCurrentFragment);
    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(Constant.Result.FAIL.equals(drawerClickEvent.getEventResult())||drawerClickEvent.getDrawId()==mCurrentDrawId) {
            return;
        }

        mCurrentDrawId=drawerClickEvent.getDrawId();
        if(mCurrentDrawId==R.string.statistics) {
            mCurrentFragment=StatisticsFragment.newInstance();
        }else {
            mCurrentFragment = getFragment(mDrawerIdMap.get(mCurrentDrawId));
        }
        transactionSupportFragment(mCurrentFragment);
    }



    private void initDrawerMap() {
        mDrawerIdMap.put(R.string.news,MainFragment.getClassName());
        mDrawerIdMap.put(R.string.blog,BlogFragment.getClassName());
        mDrawerIdMap.put(R.string.sort, TeamSortFragment.getClassName());

    }


    private BaseFragment getFragment(String fragmentName) {
        BaseFragment baseFragment = mBaseFragmentMap.get(fragmentName);
        if(mBaseFragmentMap.get(fragmentName)==null) {
            try {
                baseFragment=(BaseFragment)Class.forName(fragmentName).newInstance();
            } catch (Exception e) {
                baseFragment=MainFragment.newInstance();
            }
            mBaseFragmentMap.put(fragmentName,baseFragment);
        }
        return baseFragment;
    }


    private void transactionSupportFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
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
