package com.me.silencedut.nbaplus.ui.fragment;

import android.support.v4.view.ViewPager;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.adapter.FragmentAdapter.StatPageAdapter;
import com.me.silencedut.nbaplus.ui.wigdets.Card;
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
        List<BarFragment> list = new ArrayList<>();

        list.add(new BarFragment());
        list.add(new BarFragment());
        list.add(new BarFragment());
        list.add(new BarFragment());
        list.add(new BarFragment());
        List<Card> cardList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Card card = new Card();
            cardList.add(card);
        }

        RhythmAdapter adapter = new RhythmAdapter(getContext(), cardList);
        mRhythmLayout.setAdapter(adapter);
        mViewPager.setAdapter(new StatPageAdapter(getFragmentManager(), list));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mRhythmLayout.showRhythmAtPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

               // AnimatorUtils.showBackgroundColorAnimation(rl_changeing_color, Color.parseColor("#448AFF"), Color.parseColor("#00838f"), 500);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_playersort;
    }


    public void onEventMainThread(Event event) {

    }


}
