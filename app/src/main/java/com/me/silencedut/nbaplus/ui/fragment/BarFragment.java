package com.me.silencedut.nbaplus.ui.fragment;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.db.chart.Tools;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.StatEvent;
import com.me.silencedut.nbaplus.utils.AnimatorUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/16.
 */
public class BarFragment extends BaseFragment {

    @Bind(R.id.cardItem)
    View mCardItem;
    @Bind(R.id.barchart1)
    BarChartView mStatChart;
    @Bind(R.id.change)
    ImageView mChange;
    private static final String STATKIND="STATKIND";
    private static final String CURRENTCOLOR="CURRENTCOLOR";
    private static final int PRECOLOR=Color.parseColor("#bcaaa4");
    private static final int CHART_TEXT_COLOR=Color.parseColor("#e3f2fd");
    private static final int STEP=2;
    private int mCurrentColor;
    private String mStatKind;
    private boolean mShowDaily=true;
    private String[] mLables;
    private float [] mStatValues ;
    private String[] mLablesDaily= { "科比布莱\n恩特克雷\n汤普森\n(勇士)","科比布莱\n恩特克雷\n汤普森\n(勇士)","克雷汤普森\n勇士","克雷汤普森\n勇士","科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)"};
    private float [] mStatValuesDaily = {17, 6f, 12f, 8.5f,12f};
    private String[] mLablesEverage= { "科比布莱\n恩特克雷\n汤普森\n(勇士)","科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)","克雷汤普森\n勇士","克雷汤普森\n勇士","科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)"};
    private float [] mStatValuesEverage = {4, 3.5f, 5.5f, 4.5f,3f};
    private int mMax=16;
    Paint gridPaint;
    BarChartView barChart;

    public static BarFragment newInstace(String statKind,int currentColor) {
        BarFragment barFragment = new BarFragment();
        Bundle args = new Bundle();
        args.putString(STATKIND, statKind);
        args.putInt(CURRENTCOLOR, currentColor);
        barFragment.setArguments(args);
        return barFragment;
    }


    public BarFragment() {}

    @Override
    protected void initViews() {
        parseArguments();
        mChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissChart(0, mStatChart, mChange);
            }
        });
        showChart(0, mStatChart, mChange);

    }

    public void showChat() {
        showChart(0, mStatChart, mChange);
    }

    private void parseArguments() {
        Bundle args = getArguments();
        if(args==null) {
            return;
        }
        mStatKind = args.getString(STATKIND);
        mCurrentColor = args.getInt(CURRENTCOLOR);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_bar;
    }

    public void onEventMainThread(StatEvent statEvent) {
        if(mStatKind.equals(statEvent.getStatKind())) {
            mLablesDaily=statEvent.getLables()[0];
            mLablesEverage=statEvent.getLables()[1];
            mStatValuesDaily=statEvent.getStatValues()[0];
            mStatValuesEverage=statEvent.getStatValues()[1];
//            mLables=mShowDaily?mLablesDaily:mLablesEverage;
//            mStatValues=mShowDaily?mStatValuesDaily:mStatValuesEverage;

            Log.d("onEventMainThread",mLablesDaily.length+";;;"+mLablesEverage.length);
          //  mMax=((int)mStatValues[0]/STEP+1)*STEP;
          //  mShowDaily=!mShowDaily;
            showChart(0, mStatChart, mChange);
        }
    }

    /**
     * Show a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void showChart(final int tag, final ChartView chart, final View btn){
        
        dismissPlay(btn);
        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                    }
                }, 500);
            }
        };
        switch(tag) {
            case 0:
                produceOne(chart, action); break;
            default:
        }
    }
    /**
     * Dismiss a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void dismissChart(final int tag, final ChartView chart, final View btn){

        dismissPlay(btn);

        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                        showChart(tag, chart, btn);
                    }
                }, 500);
            }
        };
        switch(tag){
            case 0:
                dismissOne(chart, action); break;
            default:
        }
    }


    /**
     * Show CardView play button
     * @param btn    Play button
     */
    private void showPlay(View btn){
        btn.setEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(1).scaleX(1).scaleY(1);
        else
            btn.setVisibility(View.VISIBLE);
    }

    
    
    /**
     * Dismiss CardView play button
     * @param btn    Play button
     */
    private void dismissPlay(View btn){
        btn.setEnabled(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(0).scaleX(0).scaleY(0);
        else
            btn.setVisibility(View.INVISIBLE);
    }

    private void prepareStat() {
        mLables=mShowDaily?mLablesDaily:mLablesEverage;
        mStatValues=mShowDaily?mStatValuesDaily:mStatValuesEverage;
        mMax=((int)mStatValues[0]/STEP+1)*STEP;
        mShowDaily=!mShowDaily;
    }


    public void produceOne(ChartView chart, Runnable action){
        int currentColor;
        int preClor;
        preClor=mShowDaily?PRECOLOR:mCurrentColor;
        currentColor=mShowDaily?mCurrentColor:PRECOLOR;
        prepareStat();
        AnimatorUtils.showCardBackgroundColorAnimation(mCardItem, preClor, currentColor, 400);
        if(mLables.length<5||mStatValues.length<5) {
            return;
        }
        barChart = (BarChartView) chart;
        Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        }
        barChart.setTooltips(tooltip);

        BarSet barSet = new BarSet(mLables, mStatValues);
        barSet.setColor(preClor);
        barChart.addData(barSet);
        barChart.setSetSpacing(Tools.fromDpToPx(-15));
        barChart.setBarSpacing(Tools.fromDpToPx(20));
        barChart.setRoundCorners(Tools.fromDpToPx(4));

        gridPaint = new Paint();
        gridPaint.setColor(CHART_TEXT_COLOR);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        barChart.setBorderSpacing(5)
                .setAxisBorderValues(0, mMax, STEP)
                .setGrid(BarChartView.GridType.FULL, mMax, 6, gridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.OUTSIDE)
                .setLabelsColor(CHART_TEXT_COLOR)
                .setAxisColor(CHART_TEXT_COLOR);

        int[] order = {2, 1, 3, 0, 4};
        final Runnable auxAction = action;
        Runnable chartOneAction = new Runnable() {
            @Override
            public void run() {
                showTooltipOne();
                auxAction.run();
            }
        };
        barChart.show(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(chartOneAction));


    }

    public void dismissOne(ChartView chart, Runnable action){

        dismissTooltipOne();

        int[] order = {0, 4, 1, 3, 2};
        chart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }


    private void showTooltipOne(){

        ArrayList<Rect> areas = mStatChart.getEntriesArea(0);

        for(int i = 0; i < areas.size(); i++) {

                Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip, R.id.value);
                tooltip.prepare(areas.get(i), mStatValues[i]);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
                    tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
                }
                mStatChart.showTooltip(tooltip, true);

        }

    }

    private void dismissTooltipOne(){
        mStatChart.dismissAllTooltips();
    }

}
