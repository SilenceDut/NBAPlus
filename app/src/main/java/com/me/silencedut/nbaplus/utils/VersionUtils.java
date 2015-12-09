package com.me.silencedut.nbaplus.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by SlienceDut on 2015/12/15.
 */
public class VersionUtils {

    public static String setUpVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
