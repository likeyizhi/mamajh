package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.SearchHistoryPresenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SearchHistoryListEntity;
import com.yqx.mamajh.interactor.SearchHistoryInteractor;
import com.yqx.mamajh.interactor.impl.SearchHistoryInteractorImpl;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.view.SearchHistoryView;

import retrofit.Call;

/**
 * Created by young on 2016/12/23.
 */

public class SearchHistoryPresenterImpl implements SearchHistoryPresenter, BaseMultiLoadedListener<NetBaseEntity<SearchHistoryListEntity>> {

    private Context mContext = null;
    private SearchHistoryView mSearchHistoryView = null;

    private SearchHistoryInteractor mSearchHistoryInteractor = null;

    public SearchHistoryPresenterImpl(Context mContext, SearchHistoryView mSearchHistoryView, Call<NetBaseEntity<SearchHistoryListEntity>> call) {
        this.mContext = mContext;
        this.mSearchHistoryView = mSearchHistoryView;

        mSearchHistoryInteractor = new SearchHistoryInteractorImpl(call, this);
    }

    @Override
    public void loadListData(String requestTag) {
        mSearchHistoryView.showLoading(mContext.getString(R.string.common_loading_message), true);
        mSearchHistoryInteractor.getSearchHistory();

    }

    @Override
    public void onSuccess(int event_tag, NetBaseEntity<SearchHistoryListEntity> data) {
        mSearchHistoryView.hideLoading();
        mSearchHistoryView.refreshListData(data);
    }

    @Override
    public void onError(String msg) {
        mSearchHistoryView.hideLoading();
        mSearchHistoryView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mSearchHistoryView.hideLoading();
        mSearchHistoryView.showError(msg);
    }
}
