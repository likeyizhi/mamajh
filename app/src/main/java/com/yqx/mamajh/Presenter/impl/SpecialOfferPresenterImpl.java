package com.yqx.mamajh.Presenter.impl;

import android.content.Context;


import com.yqx.mamajh.Presenter.SpecialOfferPresenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;
import com.yqx.mamajh.interactor.SpecialOfferInteractor;
import com.yqx.mamajh.interactor.impl.SpecialOfferInteractorImpl;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.view.SpecialOfferView;

import java.util.ArrayList;

import retrofit.Call;

/**
 * Created by young on 16/1/8.
 */
public class SpecialOfferPresenterImpl implements SpecialOfferPresenter, BaseMultiLoadedListener<ArrayList<SpecialChannelGoodsEntity>> {

    private Context context;
    private SpecialOfferView specialOfferView;
    private SpecialOfferInteractor specialOfferInteractor;


    public SpecialOfferPresenterImpl(Context context, SpecialOfferView specialOfferView, Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> call) {
        this.context = context;
        this.specialOfferView = specialOfferView;
        specialOfferInteractor = new SpecialOfferInteractorImpl(this, call);
    }

    @Override
    public void loadListData(int state, int event_tag, int page, boolean isSwipeRefresh) {
        specialOfferView.hideLoading();
        if (!isSwipeRefresh) {
            specialOfferView.showLoading(context.getString(R.string.common_loading_message),true);
        }
        specialOfferInteractor.getCommonListData(state, event_tag, page);
    }

    @Override
    public void onItemClickListener(int position, SpecialChannelGoodsEntity itemData) {
        specialOfferView.navigateToNewsDetail(position, itemData);
    }

    @Override
    public void onSuccess(int event_tag, ArrayList<SpecialChannelGoodsEntity> data) {
        specialOfferView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            specialOfferView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            specialOfferView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        specialOfferView.hideLoading();
        specialOfferView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        specialOfferView.hideLoading();
        specialOfferView.showError(msg);
    }
}
