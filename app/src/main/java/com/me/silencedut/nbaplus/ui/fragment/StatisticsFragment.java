package com.me.silencedut.nbaplus.ui.fragment;

import android.graphics.Color;
import android.support.v4.view.ViewPager;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.RhythmPositonEvent;
import com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter.StatPageAdapter;
import com.me.silencedut.nbaplus.ui.wigdets.RhythmAdapter;
import com.me.silencedut.nbaplus.ui.wigdets.RhythmLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/16.
 */
public class StatisticsFragment extends ToorbarBaseFragment{

    @Bind(R.id.box_rhythm)
    RhythmLayout mRhythmLayout;
    @Bind(R.id.player_page)
    ViewPager mViewPager;
    List<BarFragment> mBatFrgments=new ArrayList<>(5);
    private int mCurrentPosition;
    private static final String[] sStatKinds={"points","rebs","assi","ste","blk"};
    private static final int[] sChartColors={Color.parseColor("#26a69a"),Color.parseColor("#5c6bc0"),
            Color.parseColor("#42a5f5"), Color.parseColor("#4dd0e1"),Color.parseColor("#66bb6a")};
    public static StatisticsFragment newInstance() {
        StatisticsFragment statisticsFragment = new StatisticsFragment();
        return statisticsFragment;
    }

    @Override
    protected int getTitle() {
        return R.string.statistics;
    }

    @Override
    protected void initViews() {
        super.initViews();
        for (int index=0;index<sStatKinds.length;index++) {
            mBatFrgments.add(BarFragment.newInstace(sStatKinds[index],sChartColors[index]));
        }
        initDate();
        RhythmAdapter adapter = new RhythmAdapter(getContext(),sChartColors);
        mRhythmLayout.setAdapter(adapter);
        mViewPager.setAdapter(new StatPageAdapter(getChildFragmentManager(), mBatFrgments));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mRhythmLayout.showRhythmAtPosition(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initDate() {

        AppService.getInstance().getPerStat(getTaskId(),sStatKinds[0]);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_playersort;
    }


    public void onEventMainThread(Event event) {
        if(event instanceof RhythmPositonEvent) {
            mCurrentPosition=((RhythmPositonEvent) event).getPosition();
            mViewPager.setCurrentItem(mCurrentPosition,true);
        }
    }


}