package com.me.silencedut.nbaplus.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/12/10.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerItemViewHolder> {

    private static final int[] icon_id={R.drawable.drawer_icon_news,R.drawable.drawer_icon_blog,R.drawable.drawer_icon_setting};
    private static final int[] name_id={R.string.news,R.string.blog,R.string.action_settings};
    private static final int TOPVIEW_POSITION=0;
    private static final int BOTTOMVIEW_POSITION=1;
    private static final int SETTING_POSITION=2;


    private LayoutInflater mInflater;
    private int mSelectedPosition;

    public DrawerAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerItemViewHolder(mInflater.inflate(R.layout.drawer_item,parent,false));
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, int position) {
        holder.update(position);
    }


    class DrawerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.drawer)
        View drawer;
        @Bind(R.id.top_divider)
        View top_divider;
        @Bind(R.id.bottom_divider)
        View bottom_divider;
        @Bind(R.id.drawer_icon)
        ImageView iconIV;
        @Bind(R.id.drawer_name)
        TextView nameTV;
        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        public void update(int position) {
            if(position==TOPVIEW_POSITION) {
                top_divider.setVisibility(View.VISIBLE);
            }

            if(position==BOTTOMVIEW_POSITION) {
                bottom_divider.setVisibility(View.VISIBLE);
            }
            iconIV.setImageResource(icon_id[position]);
            nameTV.setText(name_id[position]);

            if(mSelectedPosition==position){
                drawer.setSelected(true);
            }else {
                drawer.setSelected(false);
            }
        }

        @Override
        public void onClick(View v) {
            DrawerClickEvent drawerClickEvent = new DrawerClickEvent(name_id[getLayoutPosition()]);
            drawerClickEvent.setEventResult(Constant.Result.FAIL);
            AppService.getBus().post(drawerClickEvent);
            if(getLayoutPosition()== SETTING_POSITION) {
                return;
            }
            mSelectedPosition=getLayoutPosition();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return icon_id.length;
    }
}
