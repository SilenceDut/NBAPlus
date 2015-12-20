package com.me.silencedut.nbaplus.ui.fragment.playersort;

import android.animation.Animator;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.RhythmPositonEvent;
import com.me.silencedut.nbaplus.event.StatEvent;
import com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter.StatPageAdapter;
import com.me.silencedut.nbaplus.ui.fragment.base.ToorbarBaseFragment;
import com.me.silencedut.nbaplus.ui.wigdets.RhythmAdapter;
import com.me.silencedut.nbaplus.ui.wigdets.RhythmLayout;
import com.me.silencedut.nbaplus.utils.AnimatorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/16.
 */
public class StatisticsFragment extends ToorbarBaseFragment implements View.OnClickListener{
    @Bind(R.id.rl_playersort)
    View rl_playersort;
    @Bind(R.id.box_rhythm)
    RhythmLayout mRhythmLayout;
    @Bind(R.id.player_page)
    ViewPager mViewPager;
    @Bind(R.id.refresh)
    ImageView mRefresh;
    List<BarFragment> mBatFrgments=new ArrayList<>(5);
    private int mCurrentPosition;
    private Animator mAnimator;
    private static final String[] sStatKinds={"points","reb","assi","ste","blk"};
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
        mRefresh.setOnClickListener(this);
        refreshData();

    }

    private void refreshData() {
        if(mAnimator!=null) {
            return;
        }
        mAnimator=AnimatorUtils.animRotation(mRefresh,500);
        AppService.getInstance().getPerStat(getTaskId(), sStatKinds);
    }
    // no need initdata
    private void initData() {

        AppService.getInstance().initPerStat(getTaskId(), sStatKinds[0]);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_playersort;
    }

    public void onEventMainThread(Event event) {
        if(event instanceof RhythmPositonEvent) {
            mCurrentPosition=((RhythmPositonEvent) event).getPosition();
            mViewPager.setCurrentItem(mCurrentPosition,true);
        }else if(event instanceof StatEvent ){

            if(mAnimator!=null) {
                mAnimator.cancel();
                mAnimator=null;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh :
                refreshData();
                break;
            default:break;
        }
    }
}