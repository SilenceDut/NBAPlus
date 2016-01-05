package com.me.silencedut.nbaplus.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter.MainAdapter;
import com.me.silencedut.nbaplus.utils.NumericalUtil;

import butterknife.Bind;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class MainFragment extends NewsFragment{

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static boolean mFirstAnimate=true;
    @Bind(R.id.mian_title)
    View mainTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MainFragment newInstance() {

        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    void setAdapter() {
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);
        mainTitle.setVisibility(View.VISIBLE);
        mLoadAdapter=new MainAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        mLoadAdapter.setAnimate(mFirstAnimate);
        if(mFirstAnimate) {
            startIntoAnimation();
            mFirstAnimate=false;
        }else {
            initCaChe();
        }
    }
    private void initCaChe() {
        AppService.getInstance().initNews(getTaskId(), Constant.NEWSTYPE.NEWS.getNewsType());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateNews(getTaskId(), Constant.NEWSTYPE.NEWS.getNewsType());
    }


    @Override
    public void onLoadMore() {
        if (mLoadAdapter.canLoadMore()) {
            mLoadAdapter.setLoading(true);
            mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(getTaskId(),Constant.NEWSTYPE.NEWS.getNewsType(), mNewsId);
        }
    }


    public void onEventMainThread(NewsEvent newsEvent) {
        if(newsEvent!=null&&Constant.NEWSTYPE.NEWS.getNewsType().equals(newsEvent.getNewsType())) {

            updateView(newsEvent);
        }
    }


    @Override
    protected int getTitle() {
        return R.string.main;
    }

    private void startIntoAnimation() {

        int actionbarSize = NumericalUtil.dp2px(56);
        mToolBar.setTranslationY(-actionbarSize);
        mainTitle.setTranslationY(-actionbarSize);

        mToolBar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mainTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // startContentAnimation();
                        initCaChe();
                    }
                }).start();
    }

    public static String getClassName() {
        return "com.me.silencedut.nbaplus.ui.fragment.MainFragment";
    }


}
