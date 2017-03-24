package com.yqx.mamajh.Presenter.impl;

import android.content.Context;

import com.yqx.mamajh.Presenter.Presenter;
import com.yqx.mamajh.interactor.CommonContainerInteractor;
import com.yqx.mamajh.interactor.impl.SpecialtyListInteractorImpl;
import com.yqx.mamajh.view.CommonContainerView;

/**
 * Created by young on 15/11/24.
 */
public class SpecialtyListPresenterImpl implements Presenter {

    private Context mContext;
    private CommonContainerInteractor mSpecialtyListInteractor;
    private CommonContainerView mCommonContainerView;

    public SpecialtyListPresenterImpl(Context context, CommonContainerView mCommonContainerView) {
        this.mContext = context;
        this.mCommonContainerView = mCommonContainerView;
        mSpecialtyListInteractor = new SpecialtyListInteractorImpl();
    }

    @Override
    public void initialized() {
        mCommonContainerView.initializePagerViews(mSpecialtyListInteractor.getCommonCategoryList(mContext));
    }
}
