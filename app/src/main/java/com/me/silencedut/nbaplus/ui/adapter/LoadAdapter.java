package com.me.silencedut.nbaplus.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.event.AnimatEndEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.ui.activity.NewsDetileActivity;
import com.me.silencedut.nbaplus.utils.NumericalUtil;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public abstract class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.BaseViewHolder>{


    private static final int ANIMATED_ITEMS_DURATION=1000;
    private int lastAnimatedPosition=-1;
    protected Boolean mLoading=false;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<News.NewslistEntity> mNewsList;
    private boolean mAnimate=true;
    protected int mAnimateEndCount;

    protected LoadAdapter(Context context,List<News.NewslistEntity> newsList) {
        this.mContext = context;
        this.mNewsList=newsList;
        mInflater = LayoutInflater.from(context);
    }

    enum VIEWTYPE{
        NORMAL(0),NOPIC(1),MOREPIC(2),LOADMORE(3),ERROR(4);
        private int viewType;
        VIEWTYPE(int viewType) {
            this.viewType=viewType;
        }

        public int getViewType() {
            return viewType;
        }
    }

    protected void setAnimateEndCount(int animateEndCount) {
        this.mAnimateEndCount=animateEndCount;
    }

    @Override
    public int getItemViewType(int position) {

        int itemType;
        if(mNewsList==null||mNewsList.get(position)==null) {
            itemType= VIEWTYPE.ERROR.getViewType();
        }else if ( position == getItemCount() - 1) {
            itemType= VIEWTYPE.LOADMORE.getViewType();
        } else  if(mNewsList.get(position).getImgUrlList().size()==0){
            itemType= VIEWTYPE.NOPIC.getViewType();
        }else if(mNewsList.get(position).getImgUrlList().size()>=4){
            itemType= VIEWTYPE.MOREPIC.getViewType();
        }else {
            itemType= VIEWTYPE.NORMAL.getViewType();
        }
        return itemType;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.update(position);
        if(mAnimate) {
            runEnterAnimation(holder.itemView, position);
        }
    }


    protected abstract static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected abstract void update(int position);

    }

    protected void runEnterAnimation(View view, final int position) {

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(NumericalUtil.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.0f))
                    .setDuration(ANIMATED_ITEMS_DURATION)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
//                        startContentAnimation();
                            if (position == mAnimateEndCount||position>=getItemCount()-1) {
                                AppService.getInstance().getBus().post(new AnimatEndEvent());
                            }
                        }
                    })
                    .start();
        }
    }

    public void setLoading(boolean loading) {
        this.mLoading = loading;
    }

    public boolean canLoadMore() {
        return !mLoading;
    }

    public void updateItem(boolean animate) {
        this.mAnimate=animate;
        notifyDataSetChanged();
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
        public void onClick(View view) {

            Intent intent = new Intent(mContext, NewsDetileActivity.class);
            intent.putExtra(NewsDetileActivity.TITLE, newEntity.getTitle());
            intent.putExtra(NewsDetileActivity.DETILE_DATE, new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyyMMdd"));
            intent.putExtra(NewsDetileActivity.DETILE_ID, newEntity.getArticleId());
            intent.putExtra(NewsDetileActivity.IMAGE_EXIST, newEntity.getImgUrlList().size() > 0);
            if (newEntity.getImgUrlList().size()>0) {
                intent.putExtra(NewsDetileActivity.IMAGE_URL, newEntity.getImgUrlList().get(0));
            }
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                            (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                            0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏

            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());

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
            iconFail.setVisibility(mLoading ? View.GONE : View.VISIBLE);
        }

    }
}
