package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.View;

import com.github.obsessive.library.base.BaseAppCompatActivity;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.smartlayout.SmartTabLayout;
import com.github.obsessive.library.widgets.XViewPager;
import com.yqx.mamajh.Presenter.Presenter;
import com.yqx.mamajh.Presenter.impl.SpecialtyListPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.SpecialtyContainerPagerAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.BaseEntity;
import com.yqx.mamajh.view.CommonContainerView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by young on 15/11/23.
 * 今日特卖
 */
public class SpecialtyTodayActivity extends BaseActivity implements CommonContainerView {

    @BindView(R.id.stl_specialtytoday)
    SmartTabLayout mSmartTab;
    @BindView(R.id.xvp_specialtytoday)
    XViewPager mViewPager;

    private Presenter mSpecialtyPresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_specialtytoday;
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

        setTitle("今日特卖");

        mSpecialtyPresenter = new SpecialtyListPresenterImpl(mContext, this);
        mSpecialtyPresenter.initialized();

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
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
    protected BaseAppCompatActivity.TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void initializePagerViews(List<BaseEntity> categoryList) {
        if(categoryList != null && !categoryList.isEmpty()){
            mViewPager.setOffscreenPageLimit(categoryList.size());
            mViewPager.setAdapter(new SpecialtyContainerPagerAdapter(getSupportFragmentManager(), categoryList));
            mSmartTab.setViewPager(mViewPager);
//            mSmartTab.setOnPageChangeListener();

        }
    }
}
