package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.Presenter.ShopPresenter;
import com.yqx.mamajh.Presenter.impl.ShopPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ViewPagerAdapter;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopInfoEntity;
import com.yqx.mamajh.fragment.HotShopByDistanceFragment;
import com.yqx.mamajh.fragment.HotShopBySaleTotalFragment;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/13.
 */

public class StoreNewActivity extends FragmentActivity{
    private String userId;
    private ImageView iv_shop;
    private ImageButton ib_back;
    private TextView tv_shopName;
    private RadioGroup rg_shop;
    private RadioButton rb_remen;
    private RadioButton rb_quanbu;
    private RadioButton rb_pingjia;
    private RadioButton rb_shangjiaxinxi;
    private ViewPager vp_store;
    private LinearLayout ll_contsct;
    private LinearLayout ll_collent;
    private ImageView iv_collect;
    private boolean iscollect;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter vpAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_new);
        Intent intent=getIntent();
        userId=intent.getStringExtra(ShopActivity.IDBUNDLE);
        initView();
        loadData();
        setListeners();
        setAdapter();
    }

    private void setListeners() {
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        iv_shop=(ImageView)findViewById(R.id.iv_shop);
        ib_back=(ImageButton)findViewById(R.id.ib_back);
        tv_shopName=(TextView)findViewById(R.id.tv_shopName);
        ll_contsct=(LinearLayout)findViewById(R.id.ll_contsct);
        ll_collent=(LinearLayout)findViewById(R.id.ll_collent);
        rg_shop=(RadioGroup)findViewById(R.id.rg_shop);
        rb_remen=(RadioButton)findViewById(R.id.rb_remen);
        rb_quanbu=(RadioButton)findViewById(R.id.rb_quanbu);
        rb_pingjia=(RadioButton)findViewById(R.id.rb_pingjia);
        rb_shangjiaxinxi=(RadioButton)findViewById(R.id.rb_shangjiaxinxi);
        vp_store=(ViewPager)findViewById(R.id.vp_store);
        iv_collect=(ImageView)findViewById(R.id.iv_collect);
    }

    private void setAdapter() {
        fragments=new ArrayList<Fragment>();
        fragments.add(new HotShopBySaleTotalFragment());
        fragments.add(new HotShopByDistanceFragment());
        vpAdapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vp_store.setAdapter(vpAdapter);
    }

    private void loadData() {
        Call<NetBaseEntity<ShopInfoEntity>> call = RetrofitService.getInstance().getShopInfo(AppApplication.TOKEN,userId);
        call.enqueue(new Callback<NetBaseEntity<ShopInfoEntity>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ShopInfoEntity>> response, Retrofit retrofit) {
                NetBaseEntity<ShopInfoEntity> netBaseEntity = response.body();
                if(netBaseEntity == null){
                    return;
                }
                if (netBaseEntity.getMes().equals("成功")){
                    final ShopInfoEntity shopRes = netBaseEntity.getRes();
                    Glide.with(StoreNewActivity.this).load(shopRes.getLogo()+"").into(iv_shop);
                    tv_shopName.setText(shopRes.getName());
                    ll_contsct.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shopRes.getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    iscollect=shopRes.iscollect();
                    iv_collect.setImageResource(isCollect());
                    ll_collent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(iscollect){
                                unCollect(userId);
                            }else{
                                collectShop(userId);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private int isCollect() {
        if (iscollect){
            return R.mipmap.sch;
        }else{
            return R.mipmap.dsc;
        }
    }

    private void unCollect(String shopid) {
        Call<NetBaseEntity> unCollectCall=RetrofitService.getInstance().deleteShopCollect(AppApplication.TOKEN,Integer.parseInt(shopid));
        unCollectCall.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if(response.body().getStatus()==0){
                    Toast.makeText(StoreNewActivity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
                    iscollect=false;
                    iv_collect.setImageResource(isCollect());
                }else{
                    Toast.makeText(StoreNewActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void collectShop(String shopid) {
        Call<NetBaseEntity> collectCall=RetrofitService.getInstance().addShopCollect(AppApplication.TOKEN,Integer.parseInt(shopid));
        collectCall.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if(response.body().getStatus()==0){
                    Toast.makeText(StoreNewActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                    iscollect=true;
                    iv_collect.setImageResource(isCollect());
                }else{
                    Toast.makeText(StoreNewActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
