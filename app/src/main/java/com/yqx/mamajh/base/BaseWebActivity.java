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
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.obsessive.library.base.BaseSwipeBackCompatActivity;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.github.obsessive.library.widgets.BrowserLayout;

import butterknife.ButterKnife;

public class BaseWebActivity extends BaseSwipeBackCompatActivity {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";
    public static final String BUNDLE_KEY_SHOW_BOTTOM_BAR = "BUNDLE_KEY_SHOW_BOTTOM_BAR";
    public static final String BUNDLE_KEY_SHOW_TOP_BAR = "BUNDLE_KEY_SHOW_TOP_BAR";

    private String mWebUrl = null;
    private String mWebTitle = null;
    private boolean isShowBottomBar = true;
    private boolean isShowTopBar = true;
    private TextView title;

    private Toolbar mToolBar = null;
    private BrowserLayout mBrowserLayout = null;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mWebTitle = extras.getString(BUNDLE_KEY_TITLE);
        mWebUrl = extras.getString(BUNDLE_KEY_URL);
        isShowBottomBar = extras.getBoolean(BUNDLE_KEY_SHOW_BOTTOM_BAR);
        isShowTopBar = extras.getBoolean(BUNDLE_KEY_SHOW_TOP_BAR,true);
    }

    @Override
    protected int getContentViewLayoutID() {
        return com.github.obsessive.library.R.layout.activity_common_web;
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
        setSystemBarTintDrawable(getResources().getDrawable(com.github.obsessive.library.R.drawable.sr_primary));

        mToolBar = ButterKnife.findById(this, com.github.obsessive.library.R.id.common_toolbar);
        mBrowserLayout = ButterKnife.findById(this, com.github.obsessive.library.R.id.common_web_browser_layout);
        title = ButterKnife.findById(this, com.github.obsessive.library.R.id.tv_toolbar);

        ImageButton leftBtn = ButterKnife.findById(this, com.github.obsessive.library.R.id.ib_leftbtn);
        ImageButton rightBtn = ButterKnife.findById(this, com.github.obsessive.library.R.id.ib_rightbtn);

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBrowserLayout.canGoBack()){
                    mBrowserLayout.goBack();
                }else{
                    ((Activity)mContext).finish();
                }
            }
        });
        if (null != mToolBar) {
            setSupportActionBar(mToolBar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mToolBar.setNavigationIcon(null);
            mToolBar.showOverflowMenu() ;
            mToolBar.setContentInsetsRelative(0, 0);
        }

        if (!CommonUtils.isEmpty(mWebTitle)) {
//            setTitle(mWebTitle);
            title.setText(mWebTitle);
        } else {
//            setTitle("网页");
        }

        if (!CommonUtils.isEmpty(mWebUrl)) {
            mBrowserLayout.loadUrl(mWebUrl);
        } else {
            showToast("获取URL地址失败");
        }

        if (!isShowBottomBar) {
            mBrowserLayout.hideBrowserController();
        } else {
            mBrowserLayout.showBrowserController();
        }

        if(!isShowTopBar){
            mToolBar.setVisibility(View.GONE);
        }
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
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.PUSH_LEFT;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && mBrowserLayout.canGoBack()){
            mBrowserLayout.goBack();
            return true;
        }else{
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
