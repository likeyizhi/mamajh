package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XViewPager;
import com.rey.material.widget.TabPageIndicator;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.fragment.CollectProductFragment;
import com.yqx.mamajh.fragment.CollectScoreProductFragment;
import com.yqx.mamajh.fragment.CollectShopFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的收藏
 */
public class MineCollectActivity extends BaseActivity {

    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.view_pager)
    XViewPager       viewPager;
    private Tab[] mItems = new Tab[]{Tab.A, Tab.B, Tab.C};

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_collect;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mToolbar.setVisibility(View.GONE);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), mItems));
        indicator.setViewPager(viewPager, 0);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    @OnClick({R.id.ib_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }

    public enum Tab {
        A("商品"),
        B("店铺"),
        C("积分商品"),
        D("文章");
        private final String name;

        Tab(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName != null) && name.equals(otherName);
        }

        public String toString() {
            return name;
        }

    }

    private static class PagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] mFragments;
        Tab[]      mTabs;

        private static final Field sActiveField;

        static {
            Field f = null;
            try {
                Class<?> c = Class.forName("android.support.v4.app.FragmentManagerImpl");
                f = c.getDeclaredField("mActive");
                f.setAccessible(true);
            } catch (Exception e) {
            }

            sActiveField = f;
        }

        public PagerAdapter(FragmentManager fm, Tab[] tabs) {
            super(fm);
            mTabs = tabs;
            mFragments = new Fragment[mTabs.length];


            //dirty way to get reference of cached fragment
            try {
                ArrayList<Fragment> mActive = (ArrayList<Fragment>) sActiveField.get(fm);
                if (mActive != null) {
                    for (Fragment fragment : mActive) {
                        if (fragment instanceof CollectProductFragment)
                            setFragment(Tab.A, fragment);
                        else if (fragment instanceof CollectShopFragment)
                            setFragment(Tab.B, fragment);
                        else if (fragment instanceof CollectScoreProductFragment)
                            setFragment(Tab.C, fragment);
                        //else if(fragment instanceof SwitchesFragment)
                        //    setFragment(Tab.D, fragment);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void setFragment(Tab tab, Fragment f) {
            for (int i = 0; i < mTabs.length; i++)
                if (mTabs[i] == tab) {
                    mFragments[i] = f;
                    break;
                }
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments[position] == null) {
                switch (mTabs[position]) {
                    case A:
                        mFragments[position] = CollectProductFragment.newInstance();
                        break;
                    case B:
                        mFragments[position] = CollectShopFragment.newInstance();
                        break;
                    case C:
                        mFragments[position] = CollectScoreProductFragment.newInstance();
                        break;
                    //case D:
                    //    mFragments[position] = CollectScoreProductFragment.newInstance();
                    //    break;
                }
            }

            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position].toString().toUpperCase();
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}
