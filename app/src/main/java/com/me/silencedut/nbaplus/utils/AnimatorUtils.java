package com.me.silencedut.nbaplus.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;


public class AnimatorUtils {
    public static Animator animViewFadeIn(View paramView) {
        return animViewFadeIn(paramView, 200L, null);
    }

    public static Animator animRotation(View paramView,int duration) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "rotation", 0f,360f);
        localObjectAnimator.setDuration(duration);
        localObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        localObjectAnimator.setRepeatMode(ValueAnimator.INFINITE);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static Animator animViewFadeIn(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{0.0F, 1.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static Animator animViewFadeOut(View paramView) {
        return animViewFadeOut(paramView, 200L, null);
    }

    public static Animator animViewFadeOut(View paramView, long paramLong, Animator.AnimatorListener paramAnimatorListener) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", new float[]{1.0F, 0.0F});
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }


    /**
     * 移动ScrollView的x轴
     *
     * @param view      要移动的ScrollView
     * @param toX       要移动到的X轴坐标
     * @param time      动画持续时间
     * @param delayTime 延迟开始动画的时间
     * @param isStart   是否开始动画
     * @return
     */
    public static Animator moveScrollViewToX(View view, int toX, int time, int delayTime, boolean isStart) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "scrollX", new int[]{toX});
        objectAnimator.setDuration(time);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setStartDelay(delayTime);
        if (isStart)
            objectAnimator.start();
        return objectAnimator;
    }

    /**
     * 将View的背景颜色更改，使背景颜色转换更和谐的过渡动画
     *
     * @param view      要改变背景颜色的View
     * @param preColor  上个颜色值
     * @param currColor 当前颜色值
     * @param duration  动画持续时间
     */
    public static void showBackgroundColorAnimation(View view, int preColor, int currColor, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{preColor, currColor});
        animator.setDuration(duration);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    /**
     * 将View的背景颜色更改，使CardView背景颜色转换更和谐的过渡动画
     *
     * @param view      要改变背景颜色的View
     * @param preColor  上个颜色值
     * @param currColor 当前颜色值
     * @param duration  动画持续时间
     */
    public static void showCardBackgroundColorAnimation(View view, int preColor, int currColor, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofInt(view, "cardBackgroundColor", new int[]{preColor, currColor});
        animator.setDuration(duration);
        animator.setEvaluator(new ArgbEvaluator());
        animator.start();
    }

    /**
     * @param view                需要设置动画的view
     * @param translationY        偏移量
     * @param animatorTime        动画时间
     * @param isStartAnimator     是否开启指示器
     * @param isStartInterpolator 是否开始动画
     * @return 平移动画
     */
    public static Animator showUpAndDownBounce(View view, int translationY, int animatorTime, boolean isStartAnimator, boolean isStartInterpolator) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", translationY);
        if (isStartInterpolator) {
            objectAnimator.setInterpolator(new OvershootInterpolator());
        }
        objectAnimator.setDuration(animatorTime);
        if (isStartAnimator) {
            objectAnimator.start();
        }
        return objectAnimator;
    }

}