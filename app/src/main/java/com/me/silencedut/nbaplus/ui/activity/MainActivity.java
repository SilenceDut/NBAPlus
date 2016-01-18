package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;
import com.me.silencedut.nbaplus.ui.fragment.GamesFragment;
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
    private Map<String,BaseFragment> mBaseFragmentByName= new HashMap<>();
    private Map<Integer,String> mFragmentNameByDrawerId = new HashMap<>();


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
        mCurrentFragment= getFragment(mFragmentNameByDrawerId.get(R.string.news));
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
            mCurrentFragment = getFragment(mFragmentNameByDrawerId.get(mCurrentDrawId));
        }
        transactionSupportFragment(mCurrentFragment);
    }



    private void initDrawerMap() {
        mFragmentNameByDrawerId.put(R.string.news,MainFragment.class.getName());
        mFragmentNameByDrawerId.put(R.string.blog,BlogFragment.class.getName());
        mFragmentNameByDrawerId.put(R.string.sort, TeamSortFragment.class.getName());
        mFragmentNameByDrawerId.put(R.string.gameDate, GamesFragment.class.getName());

    }


    private BaseFragment getFragment(String fragmentName) {
        BaseFragment baseFragment = mBaseFragmentByName.get(fragmentName);
        if(mBaseFragmentByName.get(fragmentName)==null) {
            try {
                baseFragment=(BaseFragment)Class.forName(fragmentName).newInstance();
            } catch (Exception e) {
                baseFragment=MainFragment.newInstance();
            }
            mBaseFragmentByName.put(fragmentName,baseFragment);
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
