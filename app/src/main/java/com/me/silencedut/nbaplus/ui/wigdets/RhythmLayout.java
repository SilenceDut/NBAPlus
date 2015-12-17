package com.me.silencedut.nbaplus.ui.wigdets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.me.silencedut.nbaplus.utils.AnimatorUtils;

import java.util.ArrayList;
import java.util.List;

public class RhythmLayout extends HorizontalScrollView {
    /**
     * ScrollView的子控件
     */
    private LinearLayout mLinearLayout;
    /**
     * item的宽度，为屏幕的1/5
     */
    private float mItemWidth;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 当前被选中的的Item的位置
     */
    private int mCurrentItemPosition;
    /**
     * 上一次所选中的item的位置
     */
    private int mLastDisplayItemPosition;
    /**
     * 适配器
     */
    private RhythmAdapter mAdapter;
    /**
     * item在Y轴位移的单位，以这个值为基础开始阶梯式位移动画
     */
    private int mIntervalHeight;
    /**
     * item在Y轴位移最大的高度
     */
    private int mMaxTranslationHeight;
    private Context mContext;
    private Handler mHandler;

    public RhythmLayout(Context context) {
        this(context, null);
    }

    public RhythmLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    private void init() {
        //获得屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        //获取Item的宽度，为屏幕的七分之一
        mItemWidth = mScreenWidth /5;
        //初始化时将手指当前所在的位置置为-1
        mCurrentItemPosition = -1;
        mLastDisplayItemPosition = -1;
        mMaxTranslationHeight = (int) mItemWidth*4/7;
        mIntervalHeight = (mMaxTranslationHeight / 4);
        mHandler = new Handler();
    }

    public void setAdapter(RhythmAdapter adapter) {
        this.mAdapter = adapter;
        //如果获取HorizontalScrollView下的LinearLayout控件
        if (mLinearLayout == null) {
            mLinearLayout = (LinearLayout) getChildAt(0);
        }
        //循环获取adapter中的View，设置item的宽度并且add到mLinearLayout中
        mAdapter.setItemWidth(mItemWidth);
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            mLinearLayout.addView(mAdapter.getView(i, null, null));
        }
    }

    public void invalidateData() {
        int childCount = this.mLinearLayout.getChildCount();
        if (childCount < this.mAdapter.getCount())
            for (int i = childCount; i < this.mAdapter.getCount(); i++)
                this.mLinearLayout.addView(this.mAdapter.getView(i, null, null));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE://移动
                updateItemHeight(ev.getX());
                break;
            case MotionEvent.ACTION_DOWN://按下
                updateItemHeight(ev.getX());
                break;
            case MotionEvent.ACTION_UP://抬起
                actionUp();
                break;
        }
        return true;
    }



    /**
     * 位移到所选中的item位置，并进行相应的动画
     *
     * @param position 前往的item位置
     */
    public void showRhythmAtPosition(int position) {
        //如果所要移动的位置和上一次一样则退出方法
        if (this.mLastDisplayItemPosition == position)
            return;
        //item的弹起动画
        Animator bounceUpAnimator;
        //item的降下动画
        Animator shootDownAnimator;

        //获取对应item升起动画
        bounceUpAnimator = bounceUpItem(position, false);
        //获取对应item降下动画
        shootDownAnimator = shootDownItem(mLastDisplayItemPosition, false);
        //动画合集 弹起动画和降下动画的组合
        AnimatorSet animatorSet1 = new AnimatorSet();
        if (bounceUpAnimator != null) {
            animatorSet1.playTogether(bounceUpAnimator);
        }
        if (shootDownAnimator != null) {
            animatorSet1.playTogether(shootDownAnimator);
        }
        //3个动画的组合
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playSequentially(animatorSet1);
        animatorSet2.start();
        mLastDisplayItemPosition = position;
    }

    //更新钢琴按钮的高度
    private void updateItemHeight(float scrollX) {
        //得到屏幕上可见的7个钢琴按钮的视图
        List viewList = getVisibleViews();
        //当前手指所在的item
        int position = (int) (scrollX / mItemWidth);
        //如果手指位置没有发生变化或者大于childCount的则跳出方法不再继续执行
        if (position == mCurrentItemPosition || position >= mLinearLayout.getChildCount())
            return;
        mCurrentItemPosition = position;
        makeItems(position, viewList);
    }

    /**
     * 得到当前可见的5个钢琴按钮
     */
    private List<View> getVisibleViews() {
        ArrayList arrayList = new ArrayList();
        if (mLinearLayout == null)
            return arrayList;
        //当前可见的第一个钢琴按钮的位置
        int firstPosition = getFirstVisibleItemPosition();
        //当前可见的最后一个钢琴按钮的位置
        int lastPosition = firstPosition + 5;
        if (mLinearLayout.getChildCount() < 5) {
            lastPosition = mLinearLayout.getChildCount();
        }
        //取出当前可见的7个钢琴按钮
        for (int i = firstPosition; i < lastPosition; i++)
            arrayList.add(mLinearLayout.getChildAt(i));
        return arrayList;
    }

    /**
     * 得到可见的第一个钢琴按钮的位置
     */
    public int getFirstVisibleItemPosition() {
        if (mLinearLayout == null) {
            return 0;
        }
        //获取钢琴按钮的数量
        int size = mLinearLayout.getChildCount();
        for (int i = 0; i < size; i++) {
            View view = mLinearLayout.getChildAt(i);
            //当出现钢琴按钮的x轴比当前ScrollView的x轴大时，这个钢琴按钮就是当前可见的第一个
            if (getScrollX() < view.getX() + mItemWidth / 2.0F)
                return i;
        }
        return 0;
    }

    /**
     * 计算出个个钢琴按钮需要的高度并开始动画
     */
    private void makeItems(int fingerPosition, List<View> viewList) {
        if (fingerPosition >= viewList.size()) {
            return;
        }
        int size = viewList.size();
        for (int i = 0; i < size; i++) {
            //根据钢琴按钮的位置计算出在Y轴需要位移的大小
            int translationY = Math.min(Math.max(Math.abs(fingerPosition - i) * mIntervalHeight, 10), mMaxTranslationHeight);
            //位移动画
            updateItemHeightAnimator(viewList.get(i), translationY);
        }

    }

    /*
    * 得到每个钢琴按钮的宽度
    */
    public float getRhythmItemWidth() {
        return mItemWidth;
    }

    /**
     * 根据给定的值进行Y轴位移的动画
     *
     * @param view
     * @param translationY
     */
    private void updateItemHeightAnimator(View view, int translationY) {
        if (view != null)
            AnimatorUtils.showUpAndDownBounce(view, translationY, 180, true, true);
    }

    /**
     * 手指抬起时将其他钢琴按钮落下，重置到初始位置
     */
    private void actionUp() {
        if (mCurrentItemPosition < 0) {
            return;
        }
        int firstPosition = getFirstVisibleItemPosition();
        int lastPosition = firstPosition + mCurrentItemPosition;
        final List viewList = getVisibleViews();
        int size = viewList.size();
        //将当前钢琴按钮从要落下的ViewList中删除
        if (size > mCurrentItemPosition) {
            viewList.remove(mCurrentItemPosition);
        }
        if (firstPosition - 1 >= 0) {
            viewList.add(mLinearLayout.getChildAt(firstPosition - 1));
        }
        if (lastPosition + 1 <= mLinearLayout.getChildCount()) {
            viewList.add(mLinearLayout.getChildAt(lastPosition + 1));
        }
        //200毫秒后执行动画
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                for (int i = 0; i < viewList.size(); i++) {
                    View downView = (View) viewList.get(i);
                    shootDownItem(downView, true);
                }
            }
        }, 200L);
        mCurrentItemPosition = -1;
        //使设备震动
        //vibrate(20L);
    }

    public int getSize() {
        if (mLinearLayout == null) {
            return 0;
        }
        return mLinearLayout.getChildCount();
    }

    /**
     * 位移到Y轴'最低'的动画
     *
     * @param viewPosition view的位置
     * @param isStart      是否开始动画
     * @return
     */
    public Animator shootDownItem(int viewPosition, boolean isStart) {
        if ((viewPosition >= 0) && (mLinearLayout != null) && (getSize() > viewPosition))
            return shootDownItem(getItemView(viewPosition), isStart);
        return null;
    }

    public Animator shootDownItem(View view, boolean isStart) {
        if (view != null)
            return AnimatorUtils.showUpAndDownBounce(view, mMaxTranslationHeight, 350, isStart, true);
        return null;
    }

    public View getItemView(int position) {
        return mLinearLayout.getChildAt(position);
    }


    /**
     * 位移到Y轴'最高'的动画
     *
     * @param viewPosition view的位置
     * @param isStart      是否开始动画
     * @return
     */
    public Animator bounceUpItem(int viewPosition, boolean isStart) {
        if (viewPosition >= 0)
            return bounceUpItem(getItemView(viewPosition), isStart);
        return null;
    }

    public Animator bounceUpItem(View view, boolean isStart) {
        if (view != null)
            return AnimatorUtils.showUpAndDownBounce(view, 10, 350, isStart, true);
        return null;
    }

    /**
     * 让移动设备震动
     *
     * @param l 震动的时间
     */
    private void vibrate(long l) {
        ((Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0L, l}, -1);
    }

}