package com.me.silencedut.nbaplus.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter.FragmentSortAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/12.
 */
public class SortFragment extends ToorbarBaseFragment{
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    List<String> tableTitles = new ArrayList<>();
    List<BaseFragment> mSortTableFrahment=new ArrayList<>();

    public static SortFragment newInstance() {
        SortFragment sortFragment = new SortFragment();
        return sortFragment;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tableTitles.add(getResources().getString(R.string.team));
        tableTitles.add(getResources().getString(R.string.player));
        mTabLayout.addTab(mTabLayout.newTab().setText(tableTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(tableTitles.get(1)));

        mSortTableFrahment.add(TeamSortFragment.newInstance());
        mSortTableFrahment.add(PlayerSortFragment.newInstance());
        FragmentSortAdapter adapter =
                new FragmentSortAdapter(getChildFragmentManager(), mSortTableFrahment, tableTitles);
        mViewPager.setAdapter(adapter);
        // mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sort;
    }

    public void onEventMainThread(Event event) {

    }

    @Override
    protected int getTitle() {
        return R.string.sort;
    }

    public static String getClassName() {
        return "com.me.silencedut.nbaplus.ui.fragment.SortFragment";
    }

}
