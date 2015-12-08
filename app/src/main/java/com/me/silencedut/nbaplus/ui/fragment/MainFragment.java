package com.me.silencedut.nbaplus.ui.fragment;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.event.NewsEvent;
import com.me.silencedut.nbaplus.ui.adapter.MainAdapter;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class MainFragment extends NewsFragment{

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }


    @Override
    void setAdapter() {
        mLoadAdapter=new MainAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        initCaChe();

    }

    private void initCaChe() {
        AppService.getInstance().initNews(Constant.NEWSTYPE.NEWS.getNewsType());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().updateNews(Constant.NEWSTYPE.NEWS.getNewsType());
    }


    @Override
    public void onLoadMore() {
        if (mLoadAdapter.canLoadMore()) {
            mLoadAdapter.setLoading(true);
            mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(Constant.NEWSTYPE.NEWS.getNewsType(), mNewsId);
        }

    }

    @Override
    public void onEventMainThread(Event newsEvent) {
        if(newsEvent!=null&&newsEvent instanceof NewsEvent) {
            updateViews((NewsEvent) newsEvent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
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
                AppService.getInstance().updateNews(Constant.NEWSTYPE.NEWS.getNewsType());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
