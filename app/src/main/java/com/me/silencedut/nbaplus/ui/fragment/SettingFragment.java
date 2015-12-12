package com.me.silencedut.nbaplus.ui.fragment;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.Event;

/**
 * Created by SilenceDut on 2015/12/12.
 */
public class SettingFragment extends ToorbarBaseFragment{

    public static SettingFragment newInstance() {
        SettingFragment settingFragment =new SettingFragment();
        return settingFragment;
    }

    @Override
    protected int getTitle() {
        return R.string.action_settings;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setHasOptionsMenu(true);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_setting;
    }

    public void onEventMainThread(Event event) {

    }

}
