package com.me.silencedut.nbaplus.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.me.silencedut.nbaplus.R;
import com.me.silencedut.nbaplus.event.DrawerClickEvent;
import com.me.silencedut.nbaplus.event.Event;
import com.me.silencedut.nbaplus.utils.PreferenceUtils;
import com.me.silencedut.nbaplus.utils.blur.BitmapUtils;
import com.me.silencedut.nbaplus.utils.blur.Blur;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public class DrawerFragment extends BaseFragment implements View.OnClickListener {

    private static final int DOWNSCALE=8;
    private static final int BLUR_RADIUS=15;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private int mPriviousSelectedPoosition=0;

    private View rootView;
    private Bitmap mBitmap ;
    private Bitmap bitmap ;
    @Bind(R.id.main_left_img_avatar)
    CircleImageView avatar;
    @Bind({R.id.first,R.id.second,R.id.third,R.id.fourth,R.id.fivth,R.id.sixth,R.id.seventh})
    View[] mDrawerMenus;
    private static final int[] drawerColor = new int[]{
            R.color.deep_purple700,R.color.green800, R.color.indigo800,
            R.color.amber800,R.color.cyan800,R.color.redA700
    };
    static final int SET_AVATAR = 0;

    public static final String TEAM_ID="TEAM_ID" ;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater,container,savedInstanceState);

        return rootView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void setUp( final FrameLayout mContentLayout,int fragmentId,final DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                if(mBitmap!=null){
                    mBitmap.recycle();
                    mBitmap=null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                mPriviousSelectedPoosition=mCurrentSelectedPosition;

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (Build.VERSION.SDK_INT > 16) {
                    if (mBitmap != null) {
                        bitmap = Bitmap.createBitmap(mBitmap, 0, 0, (int) (mBitmap.getWidth() * slideOffset) > 0 ? (int) (mBitmap.getWidth() * slideOffset) : 1, mBitmap.getHeight());
                        BitmapDrawable bd;
                        bd = new BitmapDrawable(bitmap);
                        drawerView.setBackground(bd);

                    } else {
                        mBitmap = BitmapUtils.drawViewToBitmap(mContentLayout,
                                rootView.getWidth(), rootView.getHeight(), DOWNSCALE, getResources().getString(drawerColor[2]));
                        mBitmap = Blur.fastblur(mContentLayout.getContext(), mBitmap, BLUR_RADIUS);
                    }
                }
            }

        };
        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        avatar.setImageResource(PreferenceUtils.getPrefInt(getActivity(), TEAM_ID, R.mipmap.lalakers));
        avatar.setOnClickListener(this);
        selectItem();

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_drawer;
    }


    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        //upDateView(drawerClickEvent);
    }


    private void selectItem() {

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
            if(mCurrentSelectedPosition!=6&&mCurrentSelectedPosition!=5) {
                unSelectLastItem();
                mDrawerMenus[mCurrentSelectedPosition].setSelected(true);
            }
        }
    }
    private  void unSelectLastItem() {
        if(mPriviousSelectedPoosition!=mCurrentSelectedPosition) {
            mDrawerMenus[mPriviousSelectedPoosition].setSelected(false);
        }
    }
    @Override
    public void onClick(View view) {
        if(!isAdded()) {
            return;
        }
//        Intent intent = new Intent(getActivity(), SelectIconActivity.class);
//        startActivityForResult(intent, SET_AVATAR);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==SET_AVATAR) {
            if(resultCode==getActivity().RESULT_OK) {
                int teamId = data.getIntExtra(TEAM_ID, R.mipmap.lalakers);
                avatar.setImageResource(teamId);
                PreferenceUtils.setPrefInt(getActivity(), TEAM_ID, teamId);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
    public void onDetach() {
        super.onDetach();
//        mCallbacks = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
