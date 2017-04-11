package com.yqx.mamajh.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ViewPagerAdapter;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ProInfo;
import com.yqx.mamajh.bean.ShopCartCount;
import com.yqx.mamajh.fragment.ProInfo01Fragmenr;
import com.yqx.mamajh.fragment.ProInfo02Fragmenr;
import com.yqx.mamajh.fragment.ProInfo03Fragmenr;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/11.
 */

public class ProductInfoActivity extends FragmentActivity{
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter viewpageradapter;
    private ViewPager vp_proinfo;
    private ImageButton ibBack;
    private RadioGroup rg_proinfo;
    private RadioButton rb_proinfo01;
    private RadioButton rb_proinfo02;
    private RadioButton rb_proinfo03;
    private int id;
    private LinearLayout ll_proInfo_lianximaijia;
    private LinearLayout ll_proInfo_shoucang;
    private RelativeLayout ll_proInfo_gouwuche;
    private TextView tv_proInfo_cart;
    private TextView tv_tianjiagouwuche;
    private ImageView iv_shoucang;
    private boolean iscollect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        Intent intent=getIntent();
        id=intent.getIntExtra("_id",0);
//        Toast.makeText(this, id+"",Toast.LENGTH_SHORT).show();
        loadData();
        setShopCartCount();
        initView();
        setAdapter();
        setListeners();
//        Toast.makeText(this, AppApplication.TOKEN+"",Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        Call<ProInfo> call= RetrofitService.getInstance().getProInfo(id,AppApplication.TOKEN+"");
        call.enqueue(new Callback<ProInfo>() {
            @Override
            public void onResponse(Response<ProInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    setLianXiMaiJia(response.body().getRes().getPhone());
                    iscollect=response.body().getRes().iscollect();
                    iv_shoucang.setImageResource(setCollect());
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private int setCollect() {
        if (iscollect){
            return R.mipmap.shoucang;
        }else{
            return R.mipmap.sch2;
        }
    }

    private void setLianXiMaiJia(final String phone) {
        ll_proInfo_lianximaijia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setListeners() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vp_proinfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_proinfo01.setChecked(true);
                        break;
                    case 1:
                        rb_proinfo02.setChecked(true);
                        break;
                    case 2:
                        rb_proinfo03.setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg_proinfo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_proinfo01:
                        vp_proinfo.setCurrentItem(0);
                        break;
                    case R.id.rb_proinfo02:
                        vp_proinfo.setCurrentItem(1);
                        break;
                    case R.id.rb_proinfo03:
                        vp_proinfo.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

        ll_proInfo_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppApplication.TOKEN==null){
                    Toast.makeText(ProductInfoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProductInfoActivity.this,LoginActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    if (iscollect) {
                        Call<NetBaseEntity> call = RetrofitService.getInstance().deleteCollectProduct(AppApplication.TOKEN, id);
                        call.enqueue(new Callback<NetBaseEntity>() {
                            @Override
                            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                                if (response.body() == null) {
                                    return;
                                }
                                if (response.body().getStatus() == 0) {
//                                    Toast.makeText(ProductInfoActivity.this, "收藏商品成功", Toast.LENGTH_SHORT).show();
                                    iscollect = false;
                                    iv_shoucang.setImageResource(setCollect());
                                } else {
                                    Toast.makeText(ProductInfoActivity.this, "收藏失败,请重试", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    } else {
                        Call<NetBaseEntity> call = RetrofitService.getInstance().addProductCollect(AppApplication.TOKEN, id);
                        call.enqueue(new Callback<NetBaseEntity>() {
                            @Override
                            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                                if (response.body() == null) {
                                    return;
                                }
                                if (response.body().getStatus() == 0) {
//                                    Toast.makeText(ProductInfoActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                    iscollect = true;
                                    iv_shoucang.setImageResource(setCollect());
                                } else {
                                    Toast.makeText(ProductInfoActivity.this, "取消失败,请重试", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                    }
                }
            }
        });
        ll_proInfo_gouwuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppApplication.TOKEN==null){
                    Toast.makeText(ProductInfoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProductInfoActivity.this,LoginActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ProductInfoActivity.this, ShopCartActivity.class);
                    startActivity(intent);
                }
            }
        });
        tv_tianjiagouwuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppApplication.TOKEN==null){
                    Toast.makeText(ProductInfoActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProductInfoActivity.this,LoginActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Call<NetBaseEntity> call=RetrofitService.getInstance().addShopCart(AppApplication.TOKEN,id);
                    call.enqueue(new Callback<NetBaseEntity>() {
                        @Override
                        public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                            if (response.body()==null){
                                return;
                            }
                            if (response.body().getStatus()==0){
                                setShopCartCount();
                                Toast.makeText(ProductInfoActivity.this,"添加购物车成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ProductInfoActivity.this,"添加失败,请重试",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            }
        });

    }

    private void setShopCartCount() {
        Call<ShopCartCount> cartCountCall=RetrofitService.getInstance().getShopCartCount(AppApplication.TOKEN);
        cartCountCall.enqueue(new Callback<ShopCartCount>() {
            @Override
            public void onResponse(Response<ShopCartCount> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    tv_proInfo_cart.setText(response.body().getRes().getCount()+"");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setAdapter() {
        fragments=new ArrayList<Fragment>();
        fragments.add(new ProInfo01Fragmenr());
        fragments.add(new ProInfo02Fragmenr());
        fragments.add(new ProInfo03Fragmenr());
        viewpageradapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vp_proinfo.setAdapter(viewpageradapter);
        vp_proinfo.setOffscreenPageLimit(2);
    }

    private void initView() {
        vp_proinfo=(ViewPager)findViewById(R.id.vp_proinfo);
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        rg_proinfo=(RadioGroup)findViewById(R.id.rg_proinfo);
        rb_proinfo01=(RadioButton)findViewById(R.id.rb_proinfo01);
        rb_proinfo02=(RadioButton)findViewById(R.id.rb_proinfo02);
        rb_proinfo03=(RadioButton)findViewById(R.id.rb_proinfo03);

        ll_proInfo_lianximaijia=(LinearLayout)findViewById(R.id.ll_proInfo_lianximaijia);
        ll_proInfo_shoucang=(LinearLayout)findViewById(R.id.ll_proInfo_shoucang);
        ll_proInfo_gouwuche=(RelativeLayout)findViewById(R.id.ll_proInfo_gouwuche);
        tv_proInfo_cart=(TextView)findViewById(R.id.tv_proInfo_cart);
        tv_tianjiagouwuche=(TextView)findViewById(R.id.tv_tianjiagouwuche);
        iv_shoucang=(ImageView)findViewById(R.id.iv_shoucang);
    }

    public int getId(){
        return id;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setShopCartCount();
    }
}
