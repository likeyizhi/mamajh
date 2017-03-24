package com.yqx.mamajh.interactor.impl;

import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;
import com.yqx.mamajh.interactor.SpecialOfferInteractor;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 16/1/8.
 */
public class SpecialOfferInteractorImpl implements SpecialOfferInteractor {

    private BaseMultiLoadedListener<ArrayList<SpecialChannelGoodsEntity>> loadedListener = null;
    private Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> call;

    public SpecialOfferInteractorImpl(BaseMultiLoadedListener<ArrayList<SpecialChannelGoodsEntity>> loadedListener, Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> call) {
        this.call = call;
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(int state, final int event_tag, int page) {
        call = RetrofitService.getInstance().getSpecialOfferList(state, page);
        call.enqueue(new Callback<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> response, Retrofit retrofit) {
                NetBaseEntity <ArrayList<SpecialChannelGoodsEntity>> netBaseEntity = response.body();
                if(netBaseEntity == null){
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
                    // 200
                    loadedListener.onSuccess(event_tag, netBaseEntity.getRes()); //参数0这里没用，主要是刷新根上拉加载的时候判断是上拉还是下拉刷新
                }
            }

            @Override
            public void onFailure(Throwable t) {
                loadedListener.onException(t.getMessage());
            }
        });
    }
}
