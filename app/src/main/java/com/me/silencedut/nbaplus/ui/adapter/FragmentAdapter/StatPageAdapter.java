package com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.me.silencedut.nbaplus.ui.fragment.BarFragment;

import java.util.List;

/**
 * Created by SlienceDut on 2015/12/16.
 */
public class StatPageAdapter extends FragmentPagerAdapter {

    private List<BarFragment> mBarFragments;

    public StatPageAdapter(FragmentManager fm, List<BarFragment> barFragments) {
        super(fm);
        this.mBarFragments=barFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mBarFragments.get(position);
    }

    @Override
    public int getCount() {
        return mBarFragments.size();
    }
}
