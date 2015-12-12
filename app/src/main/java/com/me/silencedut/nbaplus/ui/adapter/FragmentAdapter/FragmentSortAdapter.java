package com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.me.silencedut.nbaplus.ui.fragment.BaseFragment;

import java.util.List;

public class FragmentSortAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    public FragmentSortAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
