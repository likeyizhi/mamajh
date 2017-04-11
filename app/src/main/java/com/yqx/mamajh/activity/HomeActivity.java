package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.obsessive.library.base.BaseLazyFragment;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.MyTabWidget;
import com.github.obsessive.library.widgets.XViewPager;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.Presenter.HomePresenter;
import com.yqx.mamajh.Presenter.impl.HomePresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.VPFragmentAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.view.HomeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by young on 2016/11/6.
 */

public class HomeActivity extends BaseActivity implements MyTabWidget.OnTabSelectedListener, HomeView {


    @BindView(R.id.tab_widget)
    public MyTabWidget tabWidget;
    @BindView(R.id.home_container)
    public XViewPager  viewPager;
    @BindView(R.id.fl_viewpager_root)
    FrameLayout viewPagerRoot;
    @BindView(R.id.iv_hyy)
    ImageView ivhyy;

    private static HomeActivity instance;


    public final static  int RC_LOGIN    = 1;
    private final static int RC_SHOPCART = 2;

    public static HomeActivity getInstance() {
        return instance;
    }
    private long exitTime = 0;
//    private RadioGroup rgMain=(RadioGroup)findViewById(R.id.rg_main);
//    private RadioButton rbHome=(RadioButton)findViewById(R.id.rb_home);
//    private RadioButton rbFind=(RadioButton)findViewById(R.id.rb_find);
//    private RadioButton rbCart=(RadioButton)findViewById(R.id.rb_cart);
//    private RadioButton rbMine=(RadioButton)findViewById(R.id.rb_mine);

    //从其他页面携带来的Bundle数据
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    //在这里返回layout布局
    @Override
    protected int getContentViewLayoutID() {
        getHyy();
        return R.layout.activity_home;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出江湖！！！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //使用EventBus时这里回调
    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    //返回loading动画覆盖的view（一般返回根布局）
    @Override
    protected View getLoadingTargetView() {
        return viewPagerRoot;
    }

    //从这里开始写具体逻辑
    @Override
    protected void initViewsAndEvents() {
        instance = this;
        tabWidget.setOnTabSelectedListener(this);
        mToolbar.setVisibility(View.GONE);
        leftBtn.setVisibility(View.GONE);
        HomePresenter mHomePresenter = new HomePresenterImpl(this, this);
        mHomePresenter.initialized();
//        myTab.setIndicateDisplay(mContext, 1, true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    //此页面是否使用EventBus
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    //返回true后会调用下边的getOverridePendingTransitionMode方法，使页面跳转动画改变
    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    //返回页面跳转的动画枚举
    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onTabSelected(int index) {
        Bundle bundle;
        //这里以后应添加登陆判断
        switch (index) {
            case 0:
                mToolbar.setVisibility(View.GONE);
                viewPager.setCurrentItem(index, false);
                break;
            case 1:
                mToolbar.setVisibility(View.GONE);
                viewPager.setCurrentItem(index, false);
                setTitle("发现");
//                mToolbar.setVisibility(View.GONE);
//                viewPager.setCurrentItem(index, false);
//                setTitle("分类");
                break;
            case 2:
                mToolbar.setVisibility(View.GONE);
                if (TextUtils.isEmpty(AppApplication.TOKEN) || AppApplication.memeberIndex == null) {
                    bundle = new Bundle();
                    bundle.putInt("index", index);
                    readyGoForResult(LoginActivity.class, RC_LOGIN, bundle);
                } else {
                    viewPager.setCurrentItem(index, false);
//                    readyGoForResult(ShopCartActivity.class, RC_SHOPCART);
                }
                break;
            case 3:
                mToolbar.setVisibility(View.GONE);
                if (TextUtils.isEmpty(AppApplication.TOKEN) || AppApplication.memeberIndex == null) {
                    bundle = new Bundle();
                    bundle.putInt("index", index);
                    readyGoForResult(LoginActivity.class, RC_LOGIN, bundle);
                } else {
                    viewPager.setCurrentItem(index, false);
                }
                break;
        }
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments) {
        if (null != fragments && !fragments.isEmpty()) {
            viewPager.setEnableScroll(false);
            viewPager.setOffscreenPageLimit(fragments.size());
            VPFragmentAdapter vpFragmentAdapter = new VPFragmentAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(vpFragmentAdapter);

        }

    }
    protected void  getHyy() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivhyy.setVisibility(View.GONE);
            }
        }, 3000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_LOGIN:
                if (TextUtils.isEmpty(AppApplication.TOKEN) || AppApplication.memeberIndex == null) {
                    tabWidget.setClickItem(0);
                    viewPager.setCurrentItem(0, false);
                }  else {
                    int index = data.getExtras().getInt("index");
                    tabWidget.setClickItem(index);
                    viewPager.setCurrentItem(index, false);
                }
                break;
            case RC_SHOPCART:
                tabWidget.setClickItem(0);
                viewPager.setCurrentItem(0, false);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
