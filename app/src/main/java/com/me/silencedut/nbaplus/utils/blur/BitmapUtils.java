package com.me.silencedut.nbaplus.utils.blur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;

/**
 * Created by asanBrother on 1/8/2015
 */
public class BitmapUtils {
    public static Bitmap drawViewToBitmap(View view, int width, int height, int downSampling,String color) {
        return drawViewToBitmap(view, width, height, 0f, 0f, downSampling,color);
    }

    public static Bitmap drawViewToBitmap(View view, int width, int height, float translateX,
                                          float translateY, int downSampling,String color) {
        float scale = 1f / downSampling;
        int bmpWidth = (int) (width * scale - translateX / downSampling);
        int bmpHeight = (int) (height * scale - translateY / downSampling);
        Bitmap dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dest);
        canvas.translate(-translateX / downSampling, -translateY / downSampling);
        if (downSampling > 1) {
            canvas.scale(scale, scale);
        }
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
        PorterDuffColorFilter filter =
                new PorterDuffColorFilter(Color.parseColor(color),PorterDuff.Mode.SRC_ATOP);
        paint.setColorFilter(filter);
        view.buildDrawingCache();
        Bitmap cache = view.getDrawingCache();
        canvas.drawBitmap(cache, 0, 0, paint);
        cache.recycle();
        view.destroyDrawingCache();

        return dest;
    }
}
