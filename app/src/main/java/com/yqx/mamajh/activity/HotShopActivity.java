package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ViewPagerAdapter;
import com.yqx.mamajh.fragment.HotShopByDistanceFragment;
import com.yqx.mamajh.fragment.HotShopBySaleTotalFragment;

import java.util.ArrayList;

/**
 * Created by likey on 2017/3/9.
 */

public class HotShopActivity extends FragmentActivity{
    private ImageButton ibBack;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter viewpageradapter;
    private ViewPager vpHotShop;
    private RadioGroup rgHotShop;
    private RadioButton rbSaleTotal;
    private RadioButton rbDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_shop);
        initView();
        setListeners();
        setAdapter();
    }

    private void setAdapter() {
        fragments=new ArrayList<Fragment>();
        fragments.add(new HotShopBySaleTotalFragment());
        fragments.add(new HotShopByDistanceFragment());
        viewpageradapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpHotShop.setAdapter(viewpageradapter);
        vpHotShop.setOffscreenPageLimit(2);
    }

    private void initView() {
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        vpHotShop=(ViewPager)findViewById(R.id.vp_hotShop);
        rgHotShop=(RadioGroup)findViewById(R.id.rg_hotShop);
        rbSaleTotal=(RadioButton)findViewById(R.id.rb_saleTotal);
        rbDistance=(RadioButton)findViewById(R.id.rb_distance);
    }

    private void setListeners() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vpHotShop.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rbSaleTotal.setChecked(true);
                        break;
                    case 1:
                        rbDistance.setChecked(true);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rgHotShop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_saleTotal:
                        vpHotShop.setCurrentItem(0);
                        break;
                    case R.id.rb_distance:
                        vpHotShop.setCurrentItem(1);
                        break;
                    default:
                        break;
                }
            }
        });

    }



}
