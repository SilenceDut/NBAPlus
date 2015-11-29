package com.me.silencedut.nbaplus.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.silencedut.nbaplus.event.Event;

import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public abstract class BaseFragment extends Fragment {
    protected abstract void initViews();
    protected abstract void upDateView(Event event);
    protected abstract int getContentViewId();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(),container,false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }
}
