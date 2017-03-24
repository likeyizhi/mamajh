package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.HomePresenter;
import com.yqx.mamajh.interactor.HomeInteractor;
import com.yqx.mamajh.interactor.impl.HomeInteractorImpl;
import com.yqx.mamajh.view.HomeView;

/**
 * Created by young on 2016/11/6.
 */

public class HomePresenterImpl implements HomePresenter {
    private Context mContext = null;
    private HomeView mHomeView = null;
    private HomeInteractor mHomeInteractor = null;

    public HomePresenterImpl(Context context, HomeView homeView) {
        if (null == homeView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void initialized() {
        mHomeView.initializeViews(mHomeInteractor.getPagerFragments());
    }
}
