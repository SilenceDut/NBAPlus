package com.me.silencedut.nbaplus.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.model.News.NewslistEntity;
import com.me.silencedut.nbaplus.ui.activity.NewsDetileActivity;
import com.me.silencedut.nbaplus.utils.DateFormatter;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class MainAdapter extends LoadAdapter {
    private static final int ANIMATED_END_COUNT = 3;
    @Override
    protected void setAnimateEndCount(int animateEndCount) {
        this.mAnimateEndCount=animateEndCount;
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

    public MainAdapter(Context context,List<NewslistEntity> newsList) {
        this.mContext = context;
        this.mNewsList=newsList;
        mInflater = LayoutInflater.from(context);
        setAnimateEndCount(ANIMATED_END_COUNT);
    }

    @Override
    public int getItemViewType(int position) {
        if(mNewsList==null||mNewsList.get(position)==null) {
            return VIEWTYPE.ERROR.getViewType();
        }
        if ( position == getItemCount() - 1&&mLoading) {
            return VIEWTYPE.LOADMORE.getViewType();
        } else if(mNewsList.get(position).getImgUrlList().size()==0){
            return VIEWTYPE.NOPIC.getViewType();
        }else if(mNewsList.get(position).getImgUrlList().size()>=4){
            return VIEWTYPE.MOREPIC.getViewType();
        }else {
            return VIEWTYPE.NORMAL.getViewType();
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LoadAdapter.BaseViewHolder viewHolder=null;
        switch (VIEWTYPE.values()[viewType]) {
            case LOADMORE:
                viewHolder= new LoadMoreViewHolder(mInflater.inflate(R.layout.fragment_news_item_load_more, parent,false));break;
            case NOPIC: // TYPE_NORMAL
                viewHolder= new NoPicNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_nopic, parent, false));break;
            case MOREPIC: // TYPE_NORMAL
                viewHolder=new MorePicNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_morepic, parent, false));break;
            case NORMAL: // TYPE_NORMAL
                viewHolder= new NomalNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_normal, parent, false));break;
            default:
                break;
        }
        return viewHolder;
    }

    class NomalNewsViewHolder extends EntityHolder  {
        @Bind(R.id.newsImage)
        ImageView newsImage;
        @Bind(R.id.newsTitle)
        TextView newsTitleTV;
        @Bind(R.id.newsTime)
        TextView newsTimeTV;
        String showTime;

        public NomalNewsViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void update(int position) {
            super.update(position);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                    .placeholder(R.mipmap.placeholder_small)
                    .into(newsImage);
            newsTitleTV.setText(newEntity.getTitle());
            if((Long.parseLong(newEntity.getPutdate()))<20151207){
                showTime=newEntity.getPutdate().substring(4,6)+"月"+newEntity.getPutdate().substring(6,8)+"日";
            }else{
                showTime = DateFormatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(mContext, NewsDetileActivity.class);
            intent.putExtra(NewsDetileActivity.TITLE, newEntity.getTitle());
            intent.putExtra(NewsDetileActivity.DETILE_URL, newEntity.getArticleUrl());
            intent.putExtra(NewsDetileActivity.IMAGE_EXIST, newEntity.getImgUrlList().size() > 0);
            if (newEntity.getImgUrlList().size()>0) {
                intent.putExtra(NewsDetileActivity.IMAGE_URL, newEntity.getImgUrlList().get(0));
            }
            ActivityOptionsCompat options =
            ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                    (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                    0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());

//效果不理想
//            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
//            ActivityOptionsCompat options =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
//                            newsImage, mContext.getString(R.string.secondTab));
//
//            ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());


        }

    }
     class NoPicNewsViewHolder extends EntityHolder {
        @Bind(R.id.newsTitle)
        TextView newsTitleTV;
        @Bind(R.id.newsTime)
        TextView newsTimeTV;
        @Bind(R.id.newsDescription)
        TextView newsDescriptionTV;
        String showTime;
        public NoPicNewsViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void update(int position) {
            super.update(position);
            newsTitleTV.setText(newEntity.getTitle());
            newsDescriptionTV.setText(newEntity.getDescription());
            if((Long.parseLong(newEntity.getPutdate()))<20151207){
                showTime=newEntity.getPutdate().substring(4,6)+"月"+newEntity.getPutdate().substring(6,8)+"日";
            }else{
                showTime = DateFormatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }

     }

     class MorePicNewsViewHolder extends EntityHolder {

        @Bind(R.id.newsImage1)
        ImageView newsImage1;
        @Bind(R.id.newsImage2)
        ImageView newsImage2;
        @Bind(R.id.newsImage3)
        ImageView newsImage3;
        @Bind(R.id.newsTitle)
        TextView newsTitleTV;
        @Bind(R.id.newsTime)
        TextView newsTimeTV;
        String showTime;

        public MorePicNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {
            super.update(position);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(1))
                    .placeholder(R.mipmap.placeholder_small)
                    .into(newsImage1);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(2))
                    .placeholder(R.mipmap.placeholder_small)
                    .into(newsImage2);
            Glide.with(mContext).load(newEntity.getImgUrlList().get(3))
                    .placeholder(R.mipmap.placeholder_small)
                    .into(newsImage3);
            newsTitleTV.setText(newEntity.getTitle());
            if ((Long.parseLong(newEntity.getPutdate())) < 20151207) {
                showTime = newEntity.getPutdate().substring(4, 6) + "月" + newEntity.getPutdate().substring(6, 8) + "日";
            } else {
                showTime = DateFormatter.getRecentlyTimeFormatText(new DateTime(Long.parseLong(newEntity.getPutdate())));
            }
            newsTimeTV.setText(showTime);
        }
     }


}
