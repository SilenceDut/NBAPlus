package com.me.silencedut.nbaplus.ui.fragment;

import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.me.silencedut.nbaplus.event.PlaceholderEvent;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/16.
 */
public class BarFragment extends BaseFragment {

    @Bind(R.id.cardItem)
    View mCardItem;
    @Bind(R.id.barchart1)
    BarChartView mChartOne;
    @Bind(R.id.change)
    ImageView mChange;
    private static final String STATKIND="STATKIND";
    private static final String CURRENTCOLOR="CURRENTCOLOR";
    private int mLastColor=Color.parseColor("#448AFF");
    private int mCurrentColor;
    private String mStatKind;
    private  String[] mLabelsOne= { "科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)","科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)","克雷汤普森\n勇士","克雷汤普森\n勇士","科比\n布莱\n恩特\n克雷\n汤普森\n(勇士)"};
    private  float [] mValuesOne = {16, 7.5f, 5.5f, 4.5f,12f};
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
        //parseArguments();
        mChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismissChart(0, mChartOne, mChange);
            }
        });
        showChart(0, mChartOne, mChange);
    }



    public void showChat() {
        showChart(0, mChartOne, mChange);
    }

    private void parseArguments() {
        Bundle args = getArguments();
        mStatKind = args.getString(STATKIND);
        mCurrentColor = args.getInt(CURRENTCOLOR);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_bar;
    }

    public void onEventMainThread(PlaceholderEvent event) {

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

    public void produceOne(ChartView chart, Runnable action){
        barChart = (BarChartView) chart;

        Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        }
        barChart.setTooltips(tooltip);

        BarSet barSet = new BarSet(mLabelsOne, mValuesOne);
        barSet.setColor(Color.parseColor("#795548"));
        barChart.addData(barSet);
        barChart.setSetSpacing(Tools.fromDpToPx(-15));
        barChart.setBarSpacing(Tools.fromDpToPx(20));
        barChart.setRoundCorners(Tools.fromDpToPx(4));

        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#e3f2fd"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        barChart.setBorderSpacing(5)
                .setAxisBorderValues(0, mMax, 2)
                .setGrid(BarChartView.GridType.FULL, mMax, 10, gridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#e3f2fd"))
                .setAxisColor(Color.parseColor("#e3f2fd"));

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
        // AnimatorUtils.showCardBackgroundColorAnimation(mCardItem, mLastColor, mCurrentColor, 400);

    }

    public void dismissOne(ChartView chart, Runnable action){

        dismissTooltipOne();

        int[] order = {0, 4, 1, 3, 2};
        chart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }


    private void showTooltipOne(){

        ArrayList<Rect> areas = mChartOne.getEntriesArea(0);

        for(int i = 0; i < areas.size(); i++) {

                Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip, R.id.value);
                tooltip.prepare(areas.get(i), mValuesOne[i]);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
                    tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
                }
                mChartOne.showTooltip(tooltip, true);

        }

    }

    private void dismissTooltipOne(){
        mChartOne.dismissAllTooltips();
    }

}
