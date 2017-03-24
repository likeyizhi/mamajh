package com.yqx.mamajh.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yqx.mamajh.bean.BaseEntity;
import com.yqx.mamajh.fragment.OngoingFragment;

import java.util.List;

/**
 * Created by young on 15/11/24.
 */
public class SpecialtyContainerPagerAdapter extends FragmentPagerAdapter {

    private List<BaseEntity> mCategoryList;

    public SpecialtyContainerPagerAdapter(FragmentManager fm, List<BaseEntity> mCategoryList) {
        super(fm);
        this.mCategoryList = mCategoryList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment onGoingFragmeng = new OngoingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(OngoingFragment.type, 1);
                onGoingFragmeng.setArguments(bundle);
                return onGoingFragmeng;
            case 1:
                Fragment willGoingFragmeng = new OngoingFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt(OngoingFragment.type, 0);
                willGoingFragmeng.setArguments(bundle2);
                return willGoingFragmeng;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mCategoryList == null ? 0 : mCategoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryList.get(position).getName();
    }
}
