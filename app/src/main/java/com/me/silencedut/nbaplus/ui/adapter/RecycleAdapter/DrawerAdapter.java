package com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by silenceDut on 2015/12/12.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerItemViewHolder> {

    private static final int[] icon_id = {R.drawable.drawer_icon_news, R.drawable.drawer_icon_blog,
            R.drawable.drawer_icon_summary, R.drawable.drawer_icon_sort};
    private static final int[] name_id = {R.string.news, R.string.blog,R.string.statistics, R.string.sort};
    private static final int TOPVIEW_POSITION = 0;
    private static final int BOTTOMVIEW_POSITION = 1;

    private LayoutInflater mInflater;
    private int mSelectedPosition;

    public DrawerAdapter(Context context) {
        super();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DrawerItemViewHolder(mInflater.inflate(R.layout.drawer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, int position) {
        holder.update(position);

    }


    class DrawerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            if (position == TOPVIEW_POSITION) {
                top_divider.setVisibility(View.VISIBLE);
            }

            if (position == BOTTOMVIEW_POSITION) {
                bottom_divider.setVisibility(View.VISIBLE);
            }
            iconIV.setImageResource(icon_id[position]);
            nameTV.setText(name_id[position]);

            if (mSelectedPosition == position) {
                drawer.setSelected(true);
            } else {
                drawer.setSelected(false);
            }
        }

        @Override
        public void onClick(View v) {
            DrawerClickEvent drawerClickEvent = new DrawerClickEvent(name_id[getLayoutPosition()]);
            drawerClickEvent.setEventResult(Constant.Result.FAIL);
            AppService.getBus().post(drawerClickEvent);
            mSelectedPosition = getLayoutPosition();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return icon_id.length;
    }

}