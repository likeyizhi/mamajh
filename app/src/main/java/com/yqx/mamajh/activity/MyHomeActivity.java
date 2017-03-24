package com.yqx.mamajh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ViewPagerAdapter;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.fragment.MineFragment;

import java.util.ArrayList;

/**
 * Created by likey on 2017/3/10.
 */

public class MyHomeActivity extends FragmentActivity{
    private RadioGroup rgMain;
    private RadioButton rbHome;
    private RadioButton rbFind;
    private RadioButton rbCart;
    private RadioButton rbMine;
    private ViewPager vpMain;
    private ArrayList<Fragment> fragments;
    private ViewPagerAdapter viewpageradapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome);
        initView();
        setAdapter();
        setListeners();
    }

    private void setAdapter() {
        fragments=new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());
        viewpageradapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpMain.setOffscreenPageLimit(4);
        vpMain.setEnabled(false);
        vpMain.setAdapter(viewpageradapter);
    }

    private void setListeners() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        vpMain.setCurrentItem(0);
                        break;
                    case R.id.rb_find:
                        vpMain.setCurrentItem(1);
                        break;
                    case R.id.rb_cart:
                        vpMain.setCurrentItem(2);
                        break;
                    case R.id.rb_mine:
                        vpMain.setCurrentItem(3);
                        break;

                }
            }
        });
    }

    private void initView() {
        rgMain=(RadioGroup)findViewById(R.id.rg_main);
        rbHome=(RadioButton)findViewById(R.id.rb_home);
        rbFind=(RadioButton)findViewById(R.id.rb_find);
        rbCart=(RadioButton)findViewById(R.id.rb_cart);
        rbMine=(RadioButton)findViewById(R.id.rb_mine);
        vpMain=(ViewPager)findViewById(R.id.vp_main);
    }
}
