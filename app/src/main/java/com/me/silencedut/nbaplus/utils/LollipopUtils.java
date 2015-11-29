package com.me.silencedut.nbaplus.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by asan on 8/1/15.
 */
public class LollipopUtils {

  public static int getStatusBarHeight(Context context) {
    Context appContext = context.getApplicationContext();
    int result = 0;
    int resourceId =
        appContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = appContext.getResources().getDimensionPixelSize(resourceId);
    }
    Log.d("ScreenUtils", result + "");
    return result;
  }

  public static void setStatusBarColor(Activity activity,int statusBarColor) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      // If both system bars are black, we can remove these from our layout,
      // removing or shrinking the SurfaceFlinger overlay required for our views.

      Window window = activity.getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(statusBarColor);
    }
  }

  public static void setStatusbarColor(Activity activity, View holder) {

    //对于Lollipop 的设备，只需要在style.xml中设置colorPrimaryDark即可

    //对于4.4的设备，如下设置padding即可，颜色同样在style.xml中配置
    Window w = activity.getWindow();
    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      int statusBarHeight = getStatusBarHeight(activity);
      holder.setPadding(0, statusBarHeight, 0, 0);
      return;
    }
  }

  public static void hideStatusbar(Activity activity) {

    //对于Lollipop的设备，只需要在style.xml中设置colorPrimaryDark即可

    //对于4.4的设备，如下即可
    Window w = activity.getWindow();

    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
      w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
          WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      return;
    }
  }
}
