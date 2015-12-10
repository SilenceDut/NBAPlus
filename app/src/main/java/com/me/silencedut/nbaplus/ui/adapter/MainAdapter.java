package com.me.silencedut.nbaplus.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.News.NewslistEntity;
import com.me.silencedut.nbaplus.utils.DateFormatter;
import org.joda.time.DateTime;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class MainAdapter extends LoadAdapter {

    public MainAdapter(Context context,List<NewslistEntity> newsList) {
        super(context,newsList);
        setAnimateEndCount(4);
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LoadAdapter.BaseViewHolder viewHolder=null;
        switch (VIEWTYPE.values()[viewType]) {
            case LOADMORE:
                viewHolder= new LoadMoreViewHolder(mInflater.inflate(R.layout.fragment_news_item_load_more, parent,false));break;
            case NOPIC:
                viewHolder= new NoPicNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_nopic, parent, false));break;
            case MOREPIC:
                viewHolder=new MorePicNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_morepic, parent, false));break;
            case NORMAL:
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
