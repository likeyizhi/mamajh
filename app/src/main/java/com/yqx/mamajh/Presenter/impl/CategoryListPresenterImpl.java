package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.CategoryListPresenter;
import com.yqx.mamajh.bean.ClassifivationInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.interactor.CategoryListInteractor;
import com.yqx.mamajh.interactor.impl.CategoryListInteractorImpl;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.view.ClassificationView;

import retrofit.Call;

/**
 * Created by young on 2016/12/19.
 */

public class CategoryListPresenterImpl implements CategoryListPresenter, BaseMultiLoadedListener<NetBaseEntity<ClassifivationInfoEntity>> {

    private Context mContext = null;
    private ClassificationView mClassificationView = null;

    private CategoryListInteractor listInteractor = null;

    public CategoryListPresenterImpl(Context mContext, ClassificationView mHomeListView, Call<NetBaseEntity<ClassifivationInfoEntity>> call) {
        this.mContext = mContext;
        this.mClassificationView = mHomeListView;

        listInteractor = new CategoryListInteractorImpl(call, this);
    }


    @Override
    public void onSuccess(int event_tag, NetBaseEntity<ClassifivationInfoEntity> data) {
        mClassificationView.hideLoading();
        mClassificationView.addLoadListData(data);
    }

    @Override
    public void onError(String msg) {
        mClassificationView.hideLoading();
        mClassificationView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mClassificationView.hideLoading();
        mClassificationView.showError(msg);
    }

    @Override
    public void loadListData(String requestTag) {
//        mClassificationView.hideLoading();
        listInteractor.getCommonListData(requestTag);
    }
}
