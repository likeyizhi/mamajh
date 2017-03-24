package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.ShopPresenter;
import com.yqx.mamajh.interactor.ShopInteractor;
import com.yqx.mamajh.interactor.impl.ShopInteractorImpl;
import com.yqx.mamajh.view.ShopView;

/**
 * Created by young on 2016/12/26.
 */

public class ShopPresenterImpl implements ShopPresenter {
    private Context mContext = null;
    private ShopView mHomeView = null;
    private ShopInteractor mHomeInteractor = null;

    public ShopPresenterImpl(Context context, ShopView homeView, String id) {
        if (null == homeView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }

        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new ShopInteractorImpl(id);
    }

    @Override
    public void initialized() {
        mHomeView.initializeViews(mHomeInteractor.getPagerFragments());
    }
}
