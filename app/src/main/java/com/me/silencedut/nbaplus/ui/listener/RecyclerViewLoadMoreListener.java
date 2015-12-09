package com.me.silencedut.nbaplus.ui.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by SilenceDut on 2015/12/8.
 */
public class RecyclerViewLoadMoreListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private OnLoadMoreListener onLoadMoreListener;
    private int limit;

    public RecyclerViewLoadMoreListener(@NonNull LinearLayoutManager linearLayoutManager, @NonNull OnLoadMoreListener onLoadMoreListener, int limit) {
        super();
        this.linearLayoutManager = linearLayoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
        this.limit = limit;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (linearLayoutManager.getItemCount() >= limit  && linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.getItemCount() - 1) { // 向下滑动，判断最后一个item是不是显示中
            onLoadMoreListener.onLoadMore();
        }
    }

    public interface OnLoadMoreListener {

        void onLoadMore();

    }

}
