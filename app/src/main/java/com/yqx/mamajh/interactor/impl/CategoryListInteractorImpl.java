package com.yqx.mamajh.interactor.impl;

import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.bean.ClassifivationInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.interactor.CategoryListInteractor;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/12/19.
 */

public class CategoryListInteractorImpl implements CategoryListInteractor {
    private Call<NetBaseEntity<ClassifivationInfoEntity>> call;
    private BaseMultiLoadedListener<NetBaseEntity<ClassifivationInfoEntity>> loadedListener = null;

    public CategoryListInteractorImpl(Call<NetBaseEntity<ClassifivationInfoEntity>> call, BaseMultiLoadedListener<NetBaseEntity<ClassifivationInfoEntity>> loadedListener) {
        this.call = call;
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(String requestTag) {
        call = RetrofitService.getInstance().getClassificationList();
        if (call == null) {
            return;
        }
        call.enqueue(new Callback<NetBaseEntity<ClassifivationInfoEntity>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ClassifivationInfoEntity>> response, Retrofit retrofit) {

                NetBaseEntity<ClassifivationInfoEntity> adListEntityNetBaseEntity = response.body();

                if (adListEntityNetBaseEntity == null) {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            loadedListener.onException(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loadedListener.onException("(responseBody = null)极有可能是json解析失败");
                    }
                } else {
                    // 200
                    loadedListener.onSuccess(0, adListEntityNetBaseEntity); //参数0这里没用
                }

            }

            @Override
            public void onFailure(Throwable t) {
                loadedListener.onException(t.getMessage());
            }
        });
    }
}
