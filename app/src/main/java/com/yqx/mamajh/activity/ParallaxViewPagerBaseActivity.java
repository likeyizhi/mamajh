package com.yqx.mamajh.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.github.obsessive.library.widgets.XViewPager;
import com.yqx.mamajh.adapter.ParallaxFragmentPagerAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.interfaces.ScrollTabHolder;
import com.yqx.mamajh.listener.ParallaxViewPagerChangeListener;

/**
 * Created by young on 2016/12/26.
 */

public abstract class ParallaxViewPagerBaseActivity extends BaseActivity implements ScrollTabHolder {

    public static final String TAG = ParallaxViewPagerBaseActivity.class.getSimpleName();

    protected static final String IMAGE_TRANSLATION_Y = "image_translation_y";
    protected static final String HEADER_TRANSLATION_Y = "header_translation_y";

    protected View mHeader;
    protected XViewPager mViewPager;
    protected ParallaxFragmentPagerAdapter mAdapter;

    protected int mMinHeaderHeight;
    protected int mHeaderHeight;
    protected int mMinHeaderTranslation;
    protected int mNumFragments;

    protected abstract void initValues();
    protected abstract void scrollHeader(int scrollY, int position);
    protected abstract void setupAdapter();

    protected int getScrollYOfListView(AbsListView view) {
        View child = view.getChildAt(0);
        if (child == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = child.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * child.getHeight() + headerHeight;
    }

    protected ParallaxViewPagerChangeListener getViewPagerChangeListener() {
        return new ParallaxViewPagerChangeListener(mViewPager, mAdapter, mHeader);
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(getScrollYOfListView(view), pagePosition);
        }
    }

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(view.getScrollY(), pagePosition);
        }
    }

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            scrollHeader(scrollY, pagePosition);
        }
    }
}

