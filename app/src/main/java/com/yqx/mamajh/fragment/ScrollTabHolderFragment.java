package com.yqx.mamajh.fragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.interfaces.ScrollTabHolder;

/**
 * Created by young on 2016/12/26.
 */

public abstract class ScrollTabHolderFragment extends BaseFragment implements ScrollTabHolder {

    protected static final String ARG_POSITION = "position";

    protected ScrollTabHolder mScrollTabHolder;
    protected int mPosition;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mScrollTabHolder = (ScrollTabHolder) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ScrollTabHolder");
        }
    }

    @Override
    public void onDetach() {
        mScrollTabHolder = null;
        super.onDetach();
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount, int pagePosition) {}

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int scrollY, int pagePosition) {}
}
