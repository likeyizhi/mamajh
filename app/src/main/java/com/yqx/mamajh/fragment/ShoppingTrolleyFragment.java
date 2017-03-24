package com.yqx.mamajh.fragment;

import android.view.View;

import com.github.obsessive.library.eventbus.EventCenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseFragment;

/**
 * Created by young on 2016/11/6.
 * 购物车
 */

public class ShoppingTrolleyFragment extends BaseFragment {


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shopping_trolley;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

}
