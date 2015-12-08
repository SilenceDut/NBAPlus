package com.me.silencedut.nbaplus.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.ui.activity.NewsDetileActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public abstract class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.BaseViewHolder>{

    protected Boolean mLoading=false;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<News.NewslistEntity> mNewsList;



    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.update(position);
    }


    protected abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void update(int position);

    }

    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public boolean canLoadMore() {
        return !mLoading;
    }

    protected  abstract class EntityHolder extends BaseViewHolder implements View.OnClickListener {
        News.NewslistEntity newEntity;
        public EntityHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        protected void update(int position) {
            newEntity= mNewsList.get(position);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, NewsDetileActivity.class);
            intent.putExtra(NewsDetileActivity.TITLE, newEntity.getTitle());
            intent.putExtra(NewsDetileActivity.DETILE_URL, newEntity.getArticleUrl());
            intent.putExtra(NewsDetileActivity.IMAGE_EXIST, newEntity.getImgUrlList().size() > 0);
            if (newEntity.getImgUrlList().size()>0) {
                intent.putExtra(NewsDetileActivity.IMAGE_URL, newEntity.getImgUrlList().get(0));
            }
            mContext.startActivity(intent);

        }

    }

    protected class LoadMoreViewHolder extends BaseViewHolder {

        @Bind(R.id.item_load_more_icon_loading)
        protected View iconLoading;

        @Bind(R.id.item_load_more_icon_fail)
        protected View iconFail;

        protected LoadMoreViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            iconLoading.setVisibility(mLoading ? View.VISIBLE : View.GONE);
            //iconFail.setVisibility(mLoading ? View.GONE : View.VISIBLE);
        }

    }
}
