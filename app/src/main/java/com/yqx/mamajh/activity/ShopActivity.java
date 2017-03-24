package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.base.BaseLazyFragment;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.smartlayout.SmartTabLayout;
import com.github.obsessive.library.utils.TLog;
import com.github.obsessive.library.widgets.XViewPager;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.Presenter.ShopPresenter;
import com.yqx.mamajh.Presenter.impl.ShopPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopInfoEntity;
import com.yqx.mamajh.fragment.EvaluateFragment;
import com.yqx.mamajh.fragment.HotGoodsFragment;
import com.yqx.mamajh.fragment.ShopInfoFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.view.ShopView;
import com.yqx.mamajh.widget.RoundImageView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/12/25.
 */

public class ShopActivity extends ParallaxViewPagerBaseActivity implements View.OnClickListener, ShopView {

    @BindView(R.id.view_pager)
    XViewPager viewPager;
    @BindView(R.id.iv_bg)
    ImageView bg;
    @BindView(R.id.iv_shop_icon)
    RoundImageView ivShopIcon;
    @BindView(R.id.fl_personal_icon)
    FrameLayout flPersonalIcon;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(R.id.rl_level)
    RelativeLayout rlLevel;
    @BindView(R.id.tv_contsct)
    TextView tvContsct;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.images_tab_smart)
    SmartTabLayout imagesTabSmart;
    @BindView(R.id.rl_smarttab_root)
    RelativeLayout rlSmarttabRoot;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.parent_view)
    RelativeLayout parentView;

    private ArgbEvaluator evaluator = new ArgbEvaluator();
    public static final String IDBUNDLE = "id";
    private String userId;

    private List<BaseLazyFragment> fragments;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if(extras != null){
            userId = extras.getString(ShopActivity.IDBUNDLE);
        }
    }

    public String getUserId() {
        return userId;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shop;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return parentView;
    }

    @Override
    protected void initViewsAndEvents() {

        mViewPager = viewPager;
        mHeader = ButterKnife.findById(this, R.id.header);
        mToolbar.setVisibility(View.GONE);
        tvContsct.setOnClickListener(this);
        tvCollect.setOnClickListener(this);

//        initValues();

        load();

    }

    private void load() {

        if(NetUtils.isNetworkConnected(mContext)){
            loadData();
        }else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                    loadData();
                }
            });
        }

    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData() {
        showLoading("加载中", true);
        Call<NetBaseEntity<ShopInfoEntity>> call = RetrofitService.getInstance().getShopInfo(AppApplication.TOKEN,userId);
        call.enqueue(new Callback<NetBaseEntity<ShopInfoEntity>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ShopInfoEntity>> response, Retrofit retrofit) {
                hideLoading();
                NetBaseEntity<ShopInfoEntity> netBaseEntity = response.body();
                if(netBaseEntity != null){
                    if(Constants.STATUS_0 == netBaseEntity.getStatus()){
                        setShopInfo(netBaseEntity.getRes());
                        ShopPresenter shopPresenter = new ShopPresenterImpl(mContext, ShopActivity.this, userId);
                        shopPresenter.initialized();

                    }else{
                        showToast(netBaseEntity.getMes());
                    }
                }else{
                    try {
                        showError(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void setShopInfo(ShopInfoEntity res) {

        if(res != null){
            tvShopname.setText(res.getName());
            Glide.with(mContext).load(res.getLogo()).crossFade().into(ivShopIcon);
            Glide.with(mContext).load(res.getBigimg()).crossFade().into(bg);

        }

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

    /*@Override
    protected void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.fiftythree);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.thirty_hundred);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.thirty_hundred);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight*2;

        mNumFragments = 4;
    }

    @Override
    protected void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        TLog.e("translationY", translationY + "");
//        int evaluate = (Integer) evaluator.evaluate(translationY/mMinHeaderTranslation, 0X00FFFFFF,0XFFFFFFFF);
//        TLog.e("evaluate", evaluate+"......."+mMinHeaderTranslation);
//        if(evaluate == -1){
//            setTitle(userName);
//        }else{
//            setTitle("");
//        }
//        mToolbar.setBackgroundColor(evaluate);
        mHeader.setTranslationY(translationY);
//        mTopBg.setTranslationY(-translationY / 3);
    }

    @Override
    protected void setupAdapter() {

    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contsct:

                break;
            case R.id.tv_collect:

                break;
        }
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments) {
        if (null != fragments && !fragments.isEmpty()) {
            this.fragments = fragments;
            viewPager.setEnableScroll(false);
            viewPager.setOffscreenPageLimit(fragments.size());
//            mAdapter = new ShopFragmentAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(mAdapter);
            imagesTabSmart.setOnPageChangeListener(getViewPagerChangeListener());
            imagesTabSmart.setViewPager(viewPager);
            imagesTabSmart.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if(position == 1){
                        Bundle bundle = new Bundle();
                        bundle.putString(ShopActivity.IDBUNDLE, userId);
                        readyGo(ShopClassifyActivity.class, bundle);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    protected void initValues() {
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.fiftythree);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.slidding_menu_width);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.slidding_menu_width);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight*2;

        mNumFragments = 4;
    }

    @Override
    protected void scrollHeader(int scrollY, int pagePosition) {
//        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
//        TLog.e("translationY", translationY + "");
        TLog.e("scrollY", scrollY + "");
//        int evaluate = (Integer) evaluator.evaluate(translationY/mMinHeaderTranslation, 0X00FFFFFF,0XFFFFFFFF);
//        TLog.e("evaluate", evaluate+"......."+mMinHeaderTranslation);

//        mToolbar.setBackgroundColor(evaluate);
//        mHeader.setTranslationY(translationY);
        if(scrollY == 0){
            return;
        }

        mHeader.setTranslationY(-scrollY);

//        mTopBg.setTranslationY(-translationY / 3);

        switch (pagePosition){
            case 0:
                ((EvaluateFragment)(fragments.get(2))).scrollHeader(scrollY);
                ((ShopInfoFragment)(fragments.get(3))).scrollHeader(scrollY);
                break;
            case 2:
                ((HotGoodsFragment)(fragments.get(0))).scrollHeader(scrollY);
                ((ShopInfoFragment)(fragments.get(3))).scrollHeader(scrollY);
                break;
            case 3:
                ((HotGoodsFragment)(fragments.get(0))).scrollHeader(scrollY);
                ((EvaluateFragment)(fragments.get(2))).scrollHeader(scrollY);
                break;
        }

    }

    @Override
    protected void setupAdapter() {

    }
}
