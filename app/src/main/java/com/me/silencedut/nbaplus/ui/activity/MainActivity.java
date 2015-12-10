package com.me.silencedut.nbaplus.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.fragment.BlogFragment;
import com.me.silencedut.nbaplus.ui.fragment.DrawerFragment;
import com.me.silencedut.nbaplus.ui.fragment.MainFragment;
import com.me.silencedut.nbaplus.ui.fragment.NewsFragment;
import com.me.silencedut.nbaplus.utils.AppUtils;

public class MainActivity extends BaseActivity {
    private DrawerFragment mNavigationFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void initViews() {
        mNavigationFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp((FrameLayout) findViewById(R.id.main_content),
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer));
        mCurrentFragment= BlogFragment.newInstance();
        transactionFragment(mCurrentFragment);


    }

    private void transactionFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    public void onEventMainThread(Event event) {
//        if(event instanceof NewsEvent) {
//            Log.d("onEventMainThread",((NewsEvent) event).getNews().getNextId()+"");
//        }
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
