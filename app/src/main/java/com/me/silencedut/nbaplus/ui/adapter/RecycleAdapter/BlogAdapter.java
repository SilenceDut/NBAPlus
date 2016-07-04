package com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.model.News;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2015/12/10.
 */
public class BlogAdapter extends LoadAdapter {

    public BlogAdapter(Context context,List<News.NewslistEntity> newsList) {
        super(context,newsList);
        setAnimateEndCount(1);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LoadAdapter.BaseViewHolder viewHolder=null;
        switch (VIEWTYPE.values()[viewType]) {
            case LOADMORE:
                viewHolder= new LoadMoreViewHolder(mInflater.inflate(R.layout.item_fragment_news_load_more, parent,false));break;
            case NOPIC:
                viewHolder= new NormalBlogViewHolder(mInflater.inflate(R.layout.item_fragment_blog_normal, parent, false),false);break;
            case NORMAL:
                viewHolder= new NormalBlogViewHolder(mInflater.inflate(R.layout.item_fragment_blog_normal, parent, false),true);break;
            default:
                break;
        }
        return viewHolder;
    }

     private class NormalBlogViewHolder extends EntityHolder {

        @Bind(R.id.newsImage)
        ImageView mNewsImage;
        @Bind(R.id.newsTitle)
        TextView mNwsTitle;
        @Bind(R.id.newsTime) TextView mNewsTime;
        @Bind(R.id.description) TextView description;
        @Bind(R.id.profile_image)
        CircleImageView profileImage;
        @Bind(R.id.author) TextView author;
        View itemView;
        boolean hasImage=true;
        String showTime;
        public NormalBlogViewHolder(View view,Boolean hasImage) {
            super(view);
            itemView = view;
            this.hasImage = hasImage;
            if (!hasImage) {
                mNewsImage.setVisibility(View.GONE);
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            }

        }
        @Override
        protected void update(int position) {
            super.update(position);
            boolean isZhangJiaWei = newEntity.getContentSourceName().equals("张佳玮的博客");
            if(hasImage) {
                mNewsImage.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(newEntity.getImgUrlList().get(0))
                        .placeholder(R.mipmap.placeholder_biger)
                        .into(mNewsImage);
            }
            profileImage.setImageResource(isZhangJiaWei ? R.mipmap.zhangjiawei : R.mipmap.suqun);
            author.setText(isZhangJiaWei ? "张佳玮  " : "苏群  ");
            mNwsTitle.setText(newEntity.getTitle());
            showTime = new DateTime(Long.parseLong(newEntity.getPutdate())).toString("yyyy年MM月dd日");
            mNewsTime.setText(showTime);
            description.setText(newEntity.getDescription() + "......");
        }
    }



}
