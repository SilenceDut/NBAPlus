package com.me.silencedut.nbaplus.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.App;
import com.me.silencedut.nbaplus.ui.wigdets.RevealBackgroundView;
import com.me.silencedut.nbaplus.ui.wigdets.SwipeBackLayout;
import com.me.silencedut.nbaplus.utils.AppUtils;
import com.me.silencedut.nbaplus.utils.DataClearManager;
import com.me.silencedut.nbaplus.utils.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 2015/12/18.
 */
public class SettingsActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener{
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.swipBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @Bind(R.id.revealBackgroundView)
    RevealBackgroundView mRevealBackgroundView;
    @Bind(R.id.settings_layout)
    View mSettingView;

    private SettingsFragment mSettingsFragment;

    public static void navigateFrom(Context context) {

        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mSettingsFragment = new SettingsFragment();
        getFragmentManager().beginTransaction().replace(R.id.settings_container, mSettingsFragment).commit();
        initViews();

    }

    void initViews() {

        initToolBar();
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
        setupRevealBackground();
    }

    private void initToolBar() {
        mToolBar.setTitle(R.string.setting);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupRevealBackground() {
        mRevealBackgroundView.setOnStateChangeListener(this);
        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        final int[] startingLocation = {screenWidth,0};
        mRevealBackgroundView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRevealBackgroundView.getViewTreeObserver().removeOnPreDrawListener(this);
                mRevealBackgroundView.startFromLocation(startingLocation);
                return true;
            }
        });

    }

    @Override
    public void onStateChange(int state) {

        if (RevealBackgroundView.STATE_FINISHED == state) {
            mSettingView.setVisibility(View.VISIBLE);
        }else {
            mSettingView.setVisibility(View.INVISIBLE);
        }
    }


    public static class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener,Preference.OnPreferenceClickListener{

        Preference fontSizePre;
        Preference clearCachePre;
        Preference updatePre;
        Resources resources;
        View view;
        @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                           Bundle savedInstanceState) {
            view = super.onCreateView(inflater, container, savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
            resources=getResources();
            fontSizePre=findPreference(resources.getString(R.string.pre_fontsize_key));
            clearCachePre=findPreference(resources.getString(R.string.clear_cache));
            updatePre=findPreference(resources.getString(R.string.version_update));
            String font_size= PreferenceUtils.getPrefString(getActivity(),getString(R.string.pre_fontsize_key), "16");
            fontSizePre.setSummary(showFontSize(font_size));
            fontSizePre.setOnPreferenceChangeListener(this);
            clearCachePre.setOnPreferenceClickListener(this);
            updatePre.setSummary("V" + AppUtils.getVersionName(view.getContext()));
            updateCache();
            return view;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }
        String showFontSize(String value) {
            switch (value) {
                case "16" :value="小号字体";break;
                case "18" :value="中号字体";break;
                case "20" :value="大号字体";break;
                default:break;
            }
            return value;
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference.equals(fontSizePre)) {
                String prefsValue = newValue.toString();
                fontSizePre.setSummary(showFontSize(prefsValue));
            }
            return true;
        }

        void updateCache() {
            clearCachePre.setSummary(DataClearManager.getApplicationDataSize(App.getContext()));
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference.equals(clearCachePre)) {
                DataClearManager.cleanApplicationData(App.getContext());
                updateCache();
                AppUtils.showSnackBar(view, R.string.data_cleared);
            }
            if(preference.equals(fontSizePre)) {
                fontSizePre.setDefaultValue(PreferenceUtils.getPrefString(getActivity(), "font_size", "16"));
            }
            return false;
        }
    }


}
