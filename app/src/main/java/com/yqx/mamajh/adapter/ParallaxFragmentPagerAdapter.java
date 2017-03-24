package com.yqx.mamajh.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.yqx.mamajh.interfaces.ScrollTabHolder;

/**
 * Created by young on 2016/12/26.
 */

public abstract class ParallaxFragmentPagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private int mNumFragments;

    public ParallaxFragmentPagerAdapter(FragmentManager fm, int numFragments) {
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<>();
        mNumFragments = numFragments;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        mScrollTabHolders.put(position, (ScrollTabHolder) object);

        return object;
    }

    @Override
    public int getCount() {
        return mNumFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}

