package com.yqx.mamajh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.obsessive.library.eventbus.EventCenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.widget.LoadMoreListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by young on 2016/12/28.
 */

public class ShopHotClassFragment extends ListViewFragment {


    @BindView(R.id.lv_shop_hot_class)
    LoadMoreListView lvShopHotClass;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private String userId;

    @Override
    protected void onFirstUserVisible() {

        mListView = lvShopHotClass;

        userId = ((ShopActivity)mContext).getUserId();

        loadData();

    }

    private void loadData() {
        
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return llRoot;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shop_hot_class;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

}
