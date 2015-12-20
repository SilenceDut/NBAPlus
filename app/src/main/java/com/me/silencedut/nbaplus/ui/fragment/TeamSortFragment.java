package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.ui.fragment.base.BaseFragment;

/**
 * Created by SilenceDut on 2015/12/12.
 */
public class TeamSortFragment extends BaseFragment {

    public static TeamSortFragment newInstance() {
        TeamSortFragment teamSortFragment = new TeamSortFragment();
        return teamSortFragment;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_team_sort;
    }




    public void onEventMainThread(Event event) {

    }

}
