package com.yqx.mamajh.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.yqx.mamajh.R;
import com.yqx.mamajh.widget.MyListview;
import com.yqx.mamajh.widget.NotifyingScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by young on 2016/12/26.
 */

public class ShopEvaluateFragment extends ScrollViewFragment {


    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.ratingBar_serve)
    RatingBar ratingBarServe;
    @BindView(R.id.ratingBar_quality)
    RatingBar ratingBarQuality;
    @BindView(R.id.ratingBar_speed)
    RatingBar ratingBarSpeed;
    @BindView(R.id.chtv_img)
    CheckedTextView chtvImg;
    @BindView(R.id.chtv_none_img)
    CheckedTextView chtvNoneImg;
    @BindView(R.id.tv_good_reputation)
    TextView tvGoodReputation;
    @BindView(R.id.lv_evaluate)
    MyListview lvEvaluate;
    @BindView(R.id.scrollview)
    NotifyingScrollView scrollview;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return llRoot;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shop_evaluate;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
