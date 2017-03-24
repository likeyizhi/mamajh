package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.HomeListPresenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.interactor.HomeListInteractor;
import com.yqx.mamajh.interactor.impl.HomeListInteractorImpl;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.view.HomeListView;

import retrofit.Call;

/**
 * Created by young on 2016/12/12.
 */

public class HomeListPresenterImpl implements HomeListPresenter, BaseMultiLoadedListener<NetBaseEntity<HomeInfoEntity>> {

    private Context mContext = null;
    private HomeListView mHomeListView = null;

    private HomeListInteractor homeListInteractor = null;

    public HomeListPresenterImpl(Context mContext, HomeListView mHomeListView, Call<NetBaseEntity<HomeInfoEntity>> call) {
        this.mContext = mContext;
        this.mHomeListView = mHomeListView;

        homeListInteractor = new HomeListInteractorImpl(call, this);
    }

    @Override
    public void loadListData(String requestTag, int event_tag, int page, String x, String y, boolean isSwipeRefresh) {
        mHomeListView.hideLoading();
        if (!isSwipeRefresh) {
            mHomeListView.showLoading(mContext.getString(R.string.common_loading_message), true);
        }
        homeListInteractor.getCommonListData(requestTag, event_tag, page, x, y);
    }

    @Override
    public void onSuccess(int event_tag, NetBaseEntity<HomeInfoEntity> data) {

        mHomeListView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mHomeListView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mHomeListView.addMoreListData(data);
        }

    }

    @Override
    public void onError(String msg) {
        mHomeListView.hideLoading();
        mHomeListView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mHomeListView.hideLoading();
        mHomeListView.showError(msg);
    }
}
