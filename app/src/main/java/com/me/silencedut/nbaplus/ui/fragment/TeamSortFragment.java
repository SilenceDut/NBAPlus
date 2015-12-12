package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;

/**
 * Created by SilenceDut on 2015/12/12.
 */
public class TeamSortFragment extends SwipeRefreshBaseFragment{

    public static TeamSortFragment newInstance() {
        TeamSortFragment teamSortFragment = new TeamSortFragment();
        return teamSortFragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_team_sort;
    }


    @Override
    public void onRefresh() {

    }

    public void onEventMainThread(Event event) {

    }

    @Override
    protected int getTitle() {
        return R.string.sort;
    }
}
