/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yqx.mamajh.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.obsessive.library.base.BaseAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.view.BaseView;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by young on 2015/09/08.
 */
public abstract class BaseActivity extends BaseAppCompatActivity implements BaseView {

    protected Toolbar     mToolbar;
    private   boolean     isShowLoading;
    protected ImageButton leftBtn;
    protected ImageButton rightBtn;
    protected ImageButton rightBtn2;

    protected TextView rightText;

    protected TextView rightTv;

    protected TextView textViewTitle;
    private   int      id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.color.colorPrimary));
        }

    }


    //splash页面使用
    public int getSplaseId() {
        return id;
    }

    public void setTitle(String title) {

//        TextPaint tp = textView.getPaint();
//        tp.setFakeBoldText(true);
        textViewTitle.setText(title);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        textViewTitle = ButterKnife.findById(this, R.id.tv_toolbar);

        leftBtn = ButterKnife.findById(this, R.id.ib_leftbtn);
        rightBtn = ButterKnife.findById(this, R.id.ib_rightbtn);
        rightBtn2 = ButterKnife.findById(this, R.id.ib_rightbtn2);

        rightText = ButterKnife.findById(this, R.id.ib_rightTxt);

        rightTv = ButterKnife.findById(this, R.id.ib_righttv);

        if (null != mToolbar) {
            mToolbar.showOverflowMenu();
            mToolbar.setContentInsetsRelative(0, 0);

            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(null);
        }
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    protected AppApplication getBaseApplication() {
        return (AppApplication) getApplication();
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    @Override
    public void showNetError() {
        toggleNetworkError(true, null);
    }

    @Override
    public void showLoading(String msg, boolean coverView) {
        isShowLoading = true;
        toggleShowLoading(true, null, coverView);
    }

    @Override
    public void hideLoading() {
        isShowLoading = false;
        toggleShowLoading(false, null, false);
    }

    //默认返回true
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            super.openOptionsMenu();  // 调用这个，就可以弹出菜单
            return true;
        }
        return super.onKeyDown(keyCode, event); // 返回super，让其他键默认
    }

    /**
     * 隐藏输入法
     */
    public void hideInputMethod() {
        // 隐藏输入法
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) { //是否打开输入法
            //隐藏输入法
            try {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isShowLoading() {
        return isShowLoading;
    }
}
