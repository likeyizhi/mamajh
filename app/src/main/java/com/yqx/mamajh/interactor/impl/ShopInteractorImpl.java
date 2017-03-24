package com.yqx.mamajh.interactor.impl;

import android.os.Bundle;

import com.github.obsessive.library.base.BaseLazyFragment;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.fragment.EvaluateFragment;
import com.yqx.mamajh.fragment.HotGoodsFragment;
import com.yqx.mamajh.fragment.ShopInfoFragment;
import com.yqx.mamajh.interactor.ShopInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 2016/12/26.
 */

public class ShopInteractorImpl implements ShopInteractor {

    private String id;

    public ShopInteractorImpl(String id) {
        this.id = id;
    }

    @Override
    public List<BaseLazyFragment> getPagerFragments() {
        List<BaseLazyFragment> fragments = new ArrayList<>();

        HotGoodsFragment hotGoodsFragment = new HotGoodsFragment();
        HotGoodsFragment hotGoodsFragment2 = new HotGoodsFragment();
        EvaluateFragment evaluateFragment = new EvaluateFragment();
        ShopInfoFragment shopInfoFragment = new ShopInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ShopActivity.IDBUNDLE, id);
        Bundle bundle2 = new Bundle();
        bundle2.putString(ShopActivity.IDBUNDLE, id);
        Bundle bundle3 = new Bundle();
        bundle3.putString(ShopActivity.IDBUNDLE, id);
        Bundle bundle4 = new Bundle();
        bundle4.putString(ShopActivity.IDBUNDLE, id);
        hotGoodsFragment.setArguments(bundle);
        hotGoodsFragment2.setArguments(bundle2);
        evaluateFragment.setArguments(bundle3);
        shopInfoFragment.setArguments(bundle4);
//        fragments.add(hotGoodsFragment);
        fragments.add(hotGoodsFragment);
        fragments.add(hotGoodsFragment2);
        fragments.add(evaluateFragment);
        fragments.add(shopInfoFragment);

        return fragments;
    }
}
