package com.me.silencedut.nbaplus.ui.fragment.playersort;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.BarSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.StatEvent;
import com.me.silencedut.nbaplus.ui.fragment.base.BaseFragment;
import com.me.silencedut.nbaplus.utils.AnimatorUtils;
import com.thefinestartist.finestwebview.FinestWebView;

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
    @Bind(R.id.rl_change)
    View mChangeLayout;
    @Bind(R.id.type)
    TextView mTypeTV;
    @Bind(R.id.change)
    ImageView mChange;
    private static final String STATKIND="STATKIND";
    private static final String CURRENTCOLOR="CURRENTCOLOR";
    private static final int PRECOLOR=Color.parseColor("#bcaaa4");
    private static final int CHART_TEXT_COLOR=Color.parseColor("#ffffff");
    private static final int STEP=2;
    private boolean mIsAnimating;
    private int mCurrentColor;
    private String mStatKind;
    private boolean mShowDaily=true;
    private String[] mLables;
    private String[] mPlayerUrls;
    private float [] mStatValues ;
    private String[] mPlayerUrlsDaily;
    private String[] mLablesDaily;
    private float [] mStatValuesDaily;
    private String[] mLablesEverage;
    private String[] mPlayerUrlsEverage;
    private float [] mStatValuesEverage ;
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


    @Override
    protected void initViews() {
        parseArguments();
        mChangeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mLables!=null&&mStatValues!=null) {
                    dismissChart(0, mStatChart, mChange);
                }
            }
        });
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
            mPlayerUrlsDaily=statEvent.getPlayerUrls()[0];
            mPlayerUrlsEverage=statEvent.getPlayerUrls()[1];
            mStatValuesDaily=statEvent.getStatValues()[0];
            mStatValuesEverage=statEvent.getStatValues()[1];
            if(barChart!=null) { 
                mShowDaily=true;
                dismissChart(0,mStatChart,mChange);
            }else {
                showChart(0, mStatChart, mChange); //first in,init barchart
            }
        }
    }

    /**
     * Show a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void showChart(final int tag, final ChartView chart, final View btn){
        if(mIsAnimating) {
            return;
        }
        mIsAnimating=true;
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
        mPlayerUrls=mShowDaily?mPlayerUrlsDaily:mPlayerUrlsEverage;
        mStatValues = mShowDaily ? mStatValuesDaily : mStatValuesEverage;
        mTypeTV.setText(mShowDaily?R.string.daily:R.string.everage);
        mMax=((int)mStatValues[0]/STEP+1)*STEP;
        mShowDaily=!mShowDaily;
    }


    public void produceOne(ChartView chart, Runnable action){
        if(getActivity()==null) {
            return;
        }
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
        barChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                new FinestWebView.Builder(getActivity())
                        .gradientDivider(false)
                        .show(mPlayerUrls[entryIndex]);

            }
        });

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
            if(i==areas.size()-1) {
                mIsAnimating=false;
            }

        }

    }

    private void dismissTooltipOne(){
        mStatChart.dismissAllTooltips();
    }

}
