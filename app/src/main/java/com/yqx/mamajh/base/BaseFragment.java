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

import android.content.Intent;
import android.os.Bundle;

import com.github.obsessive.library.base.BaseLazyFragment;
import com.umeng.analytics.MobclickAgent;
import com.yqx.mamajh.view.BaseView;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    15/7/22
 * Description:
 */
public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LoginActivityWeChatnInfoEntity loginInfoEntity = AppApplication.getInstance().getUserInfo();
//        if(loginInfoEntity != null){
//            id = loginInfoEntity.getId();
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG_LOG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG_LOG);
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
        toggleShowLoading(true, null,coverView);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null, false);
    }
}
