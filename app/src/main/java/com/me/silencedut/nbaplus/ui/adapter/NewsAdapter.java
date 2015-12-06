package com.me.silencedut.nbaplus.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.utils.DateFormatter;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/12/4.
 */
public class NewsAdapter extends LoadAdapter {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MOME_PIC=1;
    public static final int TYPE_LOAD_MORE = 2;
    public static final int TYPE_ERROR=4;

    public NewsAdapter(Context context,List<News.NewslistEntity> newsList) {
        this.mContext = context;
        this.mNewsList=newsList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(mNewsList==null||mNewsList.get(position)==null) {
            return TYPE_ERROR;
        }
        if ( position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        } else if(mNewsList.get(position).getImgUrlList().size()<3){
            return TYPE_NORMAL;
        }else {
            return TYPE_MOME_PIC;
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LoadAdapter.BaseViewHolder viewHolder=null;
        switch (viewType) {
            case TYPE_LOAD_MORE:
                viewHolder= new LoadMoreViewHolder(mInflater.inflate(R.layout.fragment_news_item_load_more, parent,false));break;
            case TYPE_NORMAL: // TYPE_NORMAL
                viewHolder= new NomalNewsViewHolder(mInflater.inflate(R.layout.fragment_news_list_item, parent, false));break;
            case TYPE_MOME_PIC: // TYPE_NORMAL
                viewHolder= new NomalNewsViewHolder(mInflater.inflate(R.layout.fragment_news_list_item, parent, false));break;
            default:
                break;
        }
        return viewHolder;
    }

     class NomalNewsViewHolder extends BaseViewHolder  {
        @Bind(R.id.cardItem)
        CardView cardView;
        @Bind(R.id.newsImage)
        ImageView newsImage;
        @Bind(R.id.newsTitle)
        TextView newsTitleTV;
        @Bind(R.id.newsTime)
        TextView newsTimeTV;
        View itemView;
        String showTime;
        public NomalNewsViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
        }
        @Override
        protected void update(int position) {
            News.NewslistEntity newEntity= mNewsList.get(position);

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
            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NomalNewsViewHolder", "NomalNewsViewHolder");
                }
            });

        }

     }

    static class MorePicNewsViewHolder extends BaseViewHolder {

        public MorePicNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {

        }
    }


}
