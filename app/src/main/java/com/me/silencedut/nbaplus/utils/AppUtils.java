package com.me.silencedut.nbaplus.utils;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.app.App;


/**
 * Created by Administrator on 2015/9/21.
 */
public class AppUtils {
    public static boolean isDay() {
        return PreferenceUtils.getPrefBoolean(App.getContext(), "theme", true);
    }

    public static void showSnackBar(View view,int id) {
        Resources resources  =App.getContext().getResources();
        Snackbar sb = Snackbar.make(view, resources.getString(id), Snackbar.LENGTH_SHORT);
        sb.getView().setBackgroundColor(resources.getColor(isDay() ? R.color.colorPrimaryDark : R.color.theme_dark_color));
        sb.show();
    }

}
