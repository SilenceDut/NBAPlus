package com.me.silencedut.nbaplus.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;

import butterknife.Bind;

/**
 * Created by SilenceDUt on 2015/12/10.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerItemViewHolder> {

    private static final int[] icon_id=new int[]{R.id.news,R.id.blog};
    private static final int[] name_id=new int[]{R.string.news,R.string.blog};
    private Context mContext;
    private LayoutInflater mInflater;
    private int mSelectedPosition;

    public DrawerAdapter(Context context) {
        this.mContext = context;
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
        @Bind(R.id.drawer_icon)
        ImageView iconIV;
        @Bind(R.id.drawer_name)
        TextView nameTV;
        public DrawerItemViewHolder(View itemView) {
            super(itemView);
        }
        public void update(int position) {
            iconIV.setImageResource(icon_id[position]);
            nameTV.setText(name_id[position]);
            if(mSelectedPosition==position){
                drawer.setSelected(true);
            }
        }

        @Override
        public void onClick(View v) {
            mSelectedPosition=getLayoutPosition();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return icon_id.length;
    }
}
