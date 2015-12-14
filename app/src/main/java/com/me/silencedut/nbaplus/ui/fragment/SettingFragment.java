package com.me.silencedut.nbaplus.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.App;
import com.me.silencedut.nbaplus.data.Constant;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.utils.AppUtils;
import com.me.silencedut.nbaplus.utils.DataClearManager;
import com.me.silencedut.nbaplus.utils.PreferenceUtils;

import java.util.Arrays;

import butterknife.Bind;
import im.fir.sdk.FIR;
import im.fir.sdk.callback.VersionCheckCallback;
import im.fir.sdk.version.AppVersion;

/**
 * Created by SilenceDut on 2015/12/12.
 */
public class SettingFragment extends ToorbarBaseFragment implements View.OnClickListener{

    @Bind(R.id.rl_back)
    View rl_back;
    @Bind(R.id.rl_load_image)
    View rl_load_image;
    @Bind(R.id.sc_load_image)
    SwitchCompat sc_load_image;
    @Bind(R.id.rl_font_size)
    View rl_font_size;
    @Bind(R.id.tv_font_size)
    TextView tv_font_size;
    @Bind(R.id.cache_size)
    TextView tv_cache_size;
    @Bind(R.id.rl_clear_cache)
    View rl_clear_cache;
    @Bind(R.id.rl_update_version)
    View rl_update_version;
    @Bind(R.id.tv_version)
    TextView tv_version;

    private static final String[] fontSizeName={"大号字体","中号字体","小号字体"};
    private static final String[] fontSizeValue={"22","18","16"};
    private boolean mIsLoadImage;
    private String mFontSize;

    public static SettingFragment newInstance() {
        SettingFragment settingFragment =new SettingFragment();
        return settingFragment;
    }

    @Override
    protected int getTitle() {
        return R.string.setting;
    }

    @Override
    protected void initViews() {
        super.initViews();
        setHasOptionsMenu(true);
        rl_load_image.setOnClickListener(this);
        sc_load_image.setOnClickListener(this);
        rl_font_size.setOnClickListener(this);
        rl_update_version.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        tv_cache_size.setText(DataClearManager.getApplicationDataSize(App.getContext()));
        mFontSize=PreferenceUtils.getPrefString(getActivity(), Constant.ACTILEFONTSIZE, "18");
        int select=Arrays.asList(fontSizeValue).indexOf(mFontSize);
        tv_font_size.setText(fontSizeName[select]);
        mIsLoadImage=PreferenceUtils.getPrefBoolean(getActivity(), Constant.LOADIMAGE, true);
        sc_load_image.setChecked(mIsLoadImage);
        tv_version.setText(AppUtils.getVersionName(getActivity()));
        checkUpdateVersion();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_setting;
    }

    public void onEventMainThread(Event event) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_load_image:
            case R.id.sc_load_image:
                mIsLoadImage=!mIsLoadImage;
                PreferenceUtils.setPrefBoolean(getActivity(),Constant.LOADIMAGE,mIsLoadImage);
                sc_load_image.setChecked(mIsLoadImage);
                break;
            case R.id.rl_font_size:
                setFontSize();
                break;
            case R.id.rl_clear_cache:
                clearCache();
                break;
            case R.id.rl_update_version:
                jumpToWeb();
                break;
            default:
                break;
        }
    }



    private void setFontSize() {

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.actile_font_size)
                .setSingleChoiceItems(fontSizeName, Arrays.asList(fontSizeValue).indexOf(mFontSize),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mFontSize=fontSizeValue[which];
                                tv_font_size.setText(fontSizeName[which]);
                                PreferenceUtils.setPrefString(getActivity(), Constant.ACTILEFONTSIZE, mFontSize);
                                dialog.dismiss();
                            }
                        })
               .show();
    }


    private void clearCache(){
        DataClearManager.cleanApplicationData(App.getContext());
        tv_cache_size.setText(DataClearManager.getApplicationDataSize(App.getContext()));
        AppUtils.showSnackBar(rl_back, R.string.data_cleared);
    }

    private void checkUpdateVersion() {
Log.d("checkUpdateVersion","checkUpdateVersion");
        FIR.checkForUpdateInFIR("3cc3405d811293a22ec506387c452a3d", new VersionCheckCallback() {
            @Override
            public void onSuccess(AppVersion appVersion, boolean b) {
                super.onSuccess(appVersion, b);
                Log.d("checkUpdateVersion", "appVersion"+appVersion.getVersionName());
                if (AppUtils.getVersionName(getActivity()).equals(appVersion.getVersionName())) {
                    return;
                }
                tv_version.setText("可更新至 v" + appVersion.getVersionName());
            }

        });
    }

    private void jumpToWeb() {

        Uri uri = Uri.parse(Constant.APP_FIR_IM_URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public static String getClassName() {
        return "com.me.silencedut.nbaplus.ui.fragment.SettingFragment";
    }
}
