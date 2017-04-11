package com.yqx.mamajh.interactor.impl;

import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SearchHistoryListEntity;
import com.yqx.mamajh.interactor.SearchHistoryInteractor;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/12/23.
 */

public class SearchHistoryInteractorImpl implements SearchHistoryInteractor {

    private Call<NetBaseEntity<SearchHistoryListEntity>> call;
    private BaseMultiLoadedListener<NetBaseEntity<SearchHistoryListEntity>> loadedListener = null;

    public SearchHistoryInteractorImpl(Call<NetBaseEntity<SearchHistoryListEntity>> call, BaseMultiLoadedListener<NetBaseEntity<SearchHistoryListEntity>> loadedListener) {
        this.call = call;
        this.loadedListener = loadedListener;
    }

    @Override
    public void getSearchHistory() {
        call = RetrofitService.getInstance().getSearchHistory(AppApplication.TOKEN);
        if(call == null){
            return;
        }
        call.enqueue(new Callback<NetBaseEntity<SearchHistoryListEntity>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<SearchHistoryListEntity>> response, Retrofit retrofit) {

                NetBaseEntity<SearchHistoryListEntity> searchListEntityNetBaseEntity = response.body();

                if(searchListEntityNetBaseEntity == null){
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody!=null) {
                        try {
                            loadedListener.onException(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loadedListener.onException("(responseBody = null)极有可能是json解析失败");
                    }
                }else{
                    loadedListener.onSuccess(0, searchListEntityNetBaseEntity); //参数0这里没用，主要是刷新根上拉加载的时候判断是上拉还是下拉刷新
                }

            }

            @Override
            public void onFailure(Throwable t) {
                loadedListener.onException(t.getMessage());
            }
        });
    }
}
