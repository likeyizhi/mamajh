package com.yqx.mamajh.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.obsessive.library.base.BaseLazyFragment;
import com.yqx.mamajh.R;

import java.util.List;

import static com.github.obsessive.library.utils.Utils.context;

/**
 * Created by young on 2016/12/26.
 */

public class ShopFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseLazyFragment> mListFragments = null;
    private int[] imageResId = {R.mipmap.rmfl, R.mipmap.qbbb, R.mipmap.pj1, R.mipmap.sjxx};

    public ShopFragmentAdapter(FragmentManager fm, List<BaseLazyFragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }

    @Override
    public Fragment getItem(int index) {
        if (mListFragments != null && index > -1 && index < mListFragments.size()) {
            return mListFragments.get(index);
        } else {
            return null;
        }
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv= (TextView) view.findViewById(R.id.custom_text);
        tv.setText(getPageTitle(position));
        ImageView img = (ImageView) view.findViewById(R.id.custom_img);
        img.setImageResource(imageResId[position]);

        return view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mListFragments.size() > 1){
            switch (position){
                case 0:
                    return "热门分类";
                case 1:
                    return "全部宝贝";
                case 2:
                    return "评价";
                case 3:
                    return "商家信息";
            }
        }
        return "其他";
    }

}
