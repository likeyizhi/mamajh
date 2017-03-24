package com.yqx.mamajh.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.text.Layout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.base.BaseLazyFragment;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XViewPager;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.Presenter.ShopPresenter;
import com.yqx.mamajh.Presenter.impl.ShopPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ShopFragmentAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopInfoEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.view.ShopView;
import com.yqx.mamajh.widget.RoundImageView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/12.
 */

public class StoreActivity extends BaseActivity implements View.OnClickListener, ShopView {

    @BindView(R.id.iv_toolbar_line)
    ImageView line;
    @BindView(R.id.cl_root)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsingtoolbarlayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
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
    //    @BindView(R.id.tv_contsct)
//    TextView tvContsct;
//    @BindView(R.id.tv_collect)
//    TextView tvCollect;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ll_contsct)
    LinearLayout ll_contsct;
    @BindView(R.id.ll_collent)
    LinearLayout ll_collent;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;

    private String userId;
    private ShopFragmentAdapter mAdapter;
    private int clickPosition;
    private String shopPhone;
    private boolean iscollect;


    @Override
    protected void getBundleExtras(Bundle extras) {
        if(extras != null){
            userId = extras.getString(ShopActivity.IDBUNDLE);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_store;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return coordinatorLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        ll_contsct.setOnClickListener(this);
        ll_collent.setOnClickListener(this);

        mToolbar.setBackgroundColor(Color.parseColor("#00000000"));
        line.setVisibility(View.GONE);

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
                        ShopPresenter shopPresenter = new ShopPresenterImpl(mContext, StoreActivity.this, userId);
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
            shopPhone=res.getPhone();
            iscollect=res.iscollect();
            iv_collect.setImageResource(isCollect());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_contsct:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shopPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.ll_collent:
                if (AppApplication.TOKEN==null){
                    Toast.makeText(StoreActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",1);
                    readyGo(LoginActivity.class,bundle);
                }else{
                    if (iscollect){
                        unCollect(userId);
                    }else{
                        collectShop(userId);
                    }
                }
                break;
        }
    }

    @Override
    public void initializeViews(List<BaseLazyFragment> fragments) {
        if (null != fragments && !fragments.isEmpty()) {
            viewPager.setEnableScroll(false);
            viewPager.setOffscreenPageLimit(fragments.size());
            mAdapter = new ShopFragmentAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(mAdapter);

            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) {
                    tab.setCustomView(mAdapter.getTabView(i));
                    if (tab.getCustomView() != null) {
                        View tabView = (View) tab.getCustomView().getParent();
                        tabView.setTag(i);
                        tabView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = (int) view.getTag();
                                if (pos == 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(ShopActivity.IDBUNDLE, userId);
                                    readyGo(ShopClassifyActivity.class, bundle);
                                } else if(pos == 1){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("title","商品列表");
                                    bundle.putString(ShopActivity.IDBUNDLE, userId);
                                    readyGo(GoodsListActivity.class, bundle);
                                }else {
                                    clickPosition = pos;
                                }
                                TabLayout.Tab tab = tabLayout.getTabAt(clickPosition);
                                if (tab != null) {
                                    tab.select();
                                }
                            }
                        });
                    }
                }
            }
//            viewPager.setCurrentItem(1);
        }
    }
    private int isCollect() {
        if (iscollect){
            return R.mipmap.sch;
        }else{
            return R.mipmap.dsc;
        }
    }

    private void unCollect(String shopid) {
        if (AppApplication.TOKEN==null){
            Toast.makeText(StoreActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }else {
            Call<NetBaseEntity> unCollectCall = RetrofitService.getInstance().deleteShopCollect(AppApplication.TOKEN, Integer.parseInt(shopid));
            unCollectCall.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(StoreActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        iscollect = false;
                        iv_collect.setImageResource(isCollect());
                    } else {
                        Toast.makeText(StoreActivity.this, "操作失败un", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    private void collectShop(String shopid) {
        if (AppApplication.TOKEN==null){
            Toast.makeText(StoreActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }else {
            Call<NetBaseEntity> collectCall = RetrofitService.getInstance().addShopCollect(AppApplication.TOKEN, Integer.parseInt(shopid));
            collectCall.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        Toast.makeText(StoreActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        iscollect = true;
                        iv_collect.setImageResource(isCollect());
                    } else {
                        Toast.makeText(StoreActivity.this, "操作失败co"+AppApplication.TOKEN, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

}
