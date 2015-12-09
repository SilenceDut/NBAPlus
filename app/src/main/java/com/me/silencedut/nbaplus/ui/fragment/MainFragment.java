package com.me.silencedut.nbaplus.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.AnimatEndEvent;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.model.News;
import com.me.silencedut.nbaplus.ui.adapter.MainAdapter;
import com.me.silencedut.nbaplus.utils.AppUtils;
import com.me.silencedut.nbaplus.utils.NumericalUtil;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class MainFragment extends NewsFragment{

    private static final int MIN_ITEM_SIZE=10;
    private static final int ANIM_DURATION_TOOLBAR = 300;

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }


    @Override
    void setAdapter() {
        mLoadAdapter=new MainAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
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
        if(newsEvent!=null) {
            if(Constant.Result.FAIL.equals(newsEvent.getEventResult())) {
                updateView(newsEvent);
            }else {
                News news = newsEvent.getNews();
                mNewsId = news.getNextId();
                switch (newsEvent.getNewsWay()) {
                    case INIT:
                        mNewsListEntity.clear();
                        mNewsListEntity.addAll(news.getNewslist());
                        mLoadAdapter.updateItem(true);
                        break;
                    case UPDATE:
                        mNewsListEntity.clear();
                        mNewsListEntity.addAll(news.getNewslist());
                        if (mNewsListEntity.size() < MIN_ITEM_SIZE) {
                            AppService.getInstance().loadMoreNews(getTaskId(), Constant.NEWSTYPE.NEWS.getNewsType(), mNewsId);
                        } else {
                            stopRefreshing();
                            mLoadAdapter.updateItem(false);
                        }
                        break;
                    case LOADMORE:
                        mNewsListEntity.addAll(news.getNewslist());
                        stopAll();
                        mLoadAdapter.updateItem(false);
                        break;
                    default:
                        break;
                }
                if(Constant.GETNEWSWAY.UPDATE.equals(newsEvent.getNewsWay())){
                    AppUtils.showSnackBar(newsContainer, R.string.load_success);
                }

            }


        }
    }

    @Override
    protected String getToolBarTitle() {
        return "";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);


        startIntroAnimation();
    }



    private void startIntroAnimation() {
        int actionbarSize = NumericalUtil.dp2px(56);
        refreshButton.setTranslationY(2*actionbarSize);
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
                        //                        startContentAnimation();
                        initCaChe();
                    }
                }).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
//            case R.id.action_mode: {
//                return true;
//            }
            case R.id.share :
                //ShareUtils.share(getActivity());
                AppService.getInstance().updateNews(getTaskId(),Constant.NEWSTYPE.NEWS.getNewsType());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
