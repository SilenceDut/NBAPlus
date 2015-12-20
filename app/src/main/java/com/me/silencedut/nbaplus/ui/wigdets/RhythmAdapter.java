package com.me.silencedut.nbaplus.ui.wigdets;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;

public class RhythmAdapter extends BaseAdapter {

    /**
     * item的宽度
     */
    private float itemWidth;
    /**
     * 数据源
     */
    private int[] mColorList;
private static final String[] sStatNames={"得分","篮板","助攻","抢断","盖帽"};
    private LayoutInflater mInflater;
    private Context mContext;


    public RhythmAdapter(Context context, int[] colorList) {
        this.mContext = context;
        this.mColorList=colorList;
        if (context != null)
            this.mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.mColorList.length;
    }

    public Object getItem(int position) {
        return this.mColorList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relativeLayout = (RelativeLayout) this.mInflater.inflate(R.layout.adapter_rhythm_icon, null);

        //设置item布局的大小以及Y轴的位置
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) itemWidth, mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_item_height)));
        relativeLayout.setTranslationY(itemWidth*3/7);

        //设置第二层RelativeLayout布局的宽和高
        RelativeLayout childRelativeLayout = (RelativeLayout) relativeLayout.getChildAt(0);
        CardView cardRhythm = (CardView)relativeLayout.findViewById(R.id.card_rhythm);
        TextView statName=(TextView)relativeLayout.findViewById(R.id.stat_name);
        cardRhythm.setCardBackgroundColor(mColorList[position]);
        statName.setText(sStatNames[position]);
        int relativeLayoutWidth = (int) itemWidth - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_icon_margin);
        childRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(relativeLayoutWidth, mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_item_height) - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_icon_margin)));
        return relativeLayout;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    /**
     * 设置每个item的宽度
     */
    public void setItemWidth(float width) {
        this.itemWidth = width;
    }
}