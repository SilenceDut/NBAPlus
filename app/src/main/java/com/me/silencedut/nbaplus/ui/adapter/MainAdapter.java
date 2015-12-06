package com.me.silencedut.nbaplus.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
public class MainAdapter extends LoadAdapter {

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

    public MainAdapter(Context context,List<News.NewslistEntity> newsList) {
        this.mContext = context;
        this.mNewsList=newsList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(mNewsList==null||mNewsList.get(position)==null) {
            return VIEWTYPE.ERROR.getViewType();
        }
        if ( position == getItemCount() - 1) {
            return VIEWTYPE.LOADMORE.getViewType();
        } else if(mNewsList.get(position).getImgUrlList().size()==0){
            return VIEWTYPE.NOPIC.getViewType();
        }else if(mNewsList.get(position).getImgUrlList().size()>=3){
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
                viewHolder= new NomalNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_normal, parent, false));break;
            case NORMAL: // TYPE_NORMAL
                viewHolder= new NomalNewsViewHolder(mInflater.inflate(R.layout.fragment_news_item_normal, parent, false));break;
            default:
                break;
        }
        return viewHolder;
    }

    class NomalNewsViewHolder extends BaseViewHolder  {
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
        }

    }

     class NoPicNewsViewHolder extends BaseViewHolder {
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
            News.NewslistEntity newEntity= mNewsList.get(position);
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

    static class MorePicNewsViewHolder extends BaseViewHolder {

        public MorePicNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void update(int position) {

        }
    }


}
