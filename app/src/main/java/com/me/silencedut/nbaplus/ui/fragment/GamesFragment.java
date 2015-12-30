package com.me.silencedut.nbaplus.ui.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.AppService;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.GamesEvent;
import com.me.silencedut.nbaplus.model.Games;
import com.me.silencedut.nbaplus.ui.adapter.RecycleAdapter.GamesAdapter;
import com.me.silencedut.nbaplus.ui.fragment.base.SwipeRefreshBaseFragment;
import com.me.silencedut.nbaplus.utils.DateFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by asan on 2015/12/26.
 */
public class GamesFragment extends SwipeRefreshBaseFragment {

    @Bind(R.id.rv_news)
    RecyclerView mGamesListView;
    private GamesAdapter mGamesAdapter;

    private String mDate;
    private String mDateToday =DateFormatter.formatDate("yyyy-MM-dd");
    private List<Games.GamesEntity> mGamesEntity = new ArrayList<>();
    public static GamesFragment newInstance() {
        GamesFragment gamesFragment = new GamesFragment();
        return gamesFragment;
    }

    @Override
    protected int getTitle() {
        return R.string.gameDate;
    }

    @Override
    protected void initViews() {
        super.initViews();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mGamesListView.getContext());
        mGamesListView.setLayoutManager(linearLayoutManager);
        mGamesAdapter=new GamesAdapter(getActivity(),mGamesEntity);
        mGamesListView.setAdapter(mGamesAdapter);
        mDate=mDateToday;
        setRefreshing();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }


    public void onEventMainThread(GamesEvent gamesEvent) {
        stopRefreshing();
        if(Constant.Result.FAIL.equals(gamesEvent.getEventResult())){
            return;
        }
        mGamesEntity.clear();
        mGamesEntity.addAll(gamesEvent.getAllGames().getGames());
        mGamesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_date, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.date :
                chooseDate();
                break;
            default:break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseDate() {
        if(isRefreshing()) {
            return;
        }
        DatePickerPopWin datePicker = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                mDate=dateDesc;
                setRefreshing();
            }
        }).colorConfirm(Color.parseColor("#448AFF"))//color of confirm button
                .minYear(2015) //min year in loop
                .maxYear(2017) // max year in loop
                .build();
        datePicker.showPopWin(getActivity());
    }

    @Override
    public void onRefresh() {
        AppService.getInstance().getGames(getTaskId(),mDate);
        mDate=mDateToday;
    }

    public static String getClassName() {
        return "com.me.silencedut.nbaplus.ui.fragment.GamesFragment";
    }
}

