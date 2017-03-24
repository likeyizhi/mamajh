package com.yqx.mamajh.interactor.impl;

import com.github.obsessive.library.base.BaseLazyFragment;
import com.yqx.mamajh.fragment.DiscoveryFragment;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.fragment.MineFragment;
import com.yqx.mamajh.fragment.ShoppingTrolleyFragment;
import com.yqx.mamajh.interactor.HomeInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 2016/11/6.
 */

public class HomeInteractorImpl implements HomeInteractor {
    @Override
    public List<BaseLazyFragment> getPagerFragments() {
        List<BaseLazyFragment> fragments = new ArrayList<>();

        fragments.add(new HomeFragment());
//        fragments.add(new ClassificationFragment());
        fragments.add(new DiscoveryFragment());
        fragments.add(new ShoppingTrolleyFragment());
        fragments.add(new MineFragment());

        return fragments;
    }
}
