package com.yqx.mamajh.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.Presenter.SpecialOfferPresenter;
import com.yqx.mamajh.Presenter.impl.SpecialOfferPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;
import com.yqx.mamajh.view.SpecialOfferView;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;


/**
 * Created by young on 15/11/24.
 * 正在进行（今日特卖页）
 */
public class OngoingFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, SpecialOfferView {

    @BindView(R.id.srl_ongoning)
    XSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lv_ongoing)
    LoadMoreListView listView;

    private int state;
    public static final String type = "state";
    private ListViewDataAdapter<SpecialChannelGoodsEntity> adapter;
    private Call<NetBaseEntity<ArrayList<SpecialChannelGoodsEntity>>> call;

    private SpecialOfferPresenter specialOfferPresenter;
    private int mCurrentPage = 1;


    @Override
    protected void onFirstUserVisible() {
        Bundle bundle = getArguments();
        state = bundle.getInt(OngoingFragment.type);

        specialOfferPresenter = new SpecialOfferPresenterImpl(mContext,this,call);

        if(NetUtils.isNetworkConnected(mContext)){
            if (null != swipeRefreshLayout) {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showLoading("",true);
                        specialOfferPresenter.loadListData(state, Constants.EVENT_REFRESH_DATA,
                                mCurrentPage, false);
                    }
                }, Constants.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        }else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                    specialOfferPresenter.loadListData(state, Constants.EVENT_REFRESH_DATA,
                            mCurrentPage, false);
                }
            });
        }

    }

    @Override
    public void showError(String msg) {
        mCurrentPage = 1;
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specialOfferPresenter.loadListData(state, Constants.EVENT_REFRESH_DATA,
                        mCurrentPage, false);
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return swipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4)
        );
        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new ListViewDataAdapter<>(new ViewHolderCreator<SpecialChannelGoodsEntity>() {
            @Override
            public ViewHolderBase<SpecialChannelGoodsEntity> createViewHolder(int position) {
                return new ViewHolderBase<SpecialChannelGoodsEntity>() {

                    ImageView img;
                    TextView time;
                    TextView introduce;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {

                        View convertView = layoutInflater.inflate(R.layout.going_item, null);

                        img = ButterKnife.findById(convertView, R.id.iv_img);
                        time = ButterKnife.findById(convertView, R.id.tv_time);
                        introduce = ButterKnife.findById(convertView, R.id.tv_introduce);

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(mScreenWidth, mScreenWidth/2);
                        img.setLayoutParams(layoutParams);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, SpecialChannelGoodsEntity itemData) {

                        if(itemData != null){
                            Glide.with(mContext).load(itemData.getImg()).into(img);
                            time.setText(itemData.getEndTime());
                            introduce.setText(itemData.getDesc());
                        }

                    }
                };
            }
        });

        listView.setAdapter(adapter);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_ongoing;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void refreshListData(ArrayList<SpecialChannelGoodsEntity> responseProductListEntity) {
        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (null != responseProductListEntity) {
            if (null != adapter) {
                adapter.getDataList().clear();
                adapter.getDataList().addAll(responseProductListEntity);
                adapter.notifyDataSetChanged();
            }

            if (null != listView) {
                if (responseProductListEntity.size() >= Constants.PAGE_SIZE) {
                    listView.setCanLoadMore(true);
                } else {
                    listView.setCanLoadMore(false);
                }
            }
        }
    }

    @Override
    public void addMoreListData(ArrayList<SpecialChannelGoodsEntity> responseProductListEntity) {
        if (null != listView) {
            listView.onLoadMoreComplete();
        }

        if (null != responseProductListEntity) {
            if (null != adapter) {
                adapter.getDataList().addAll(responseProductListEntity);
                adapter.notifyDataSetChanged();
            }

            if (null != listView) {
                if (responseProductListEntity.size() >= Constants.PAGE_SIZE) {
                    listView.setCanLoadMore(true);
                } else {
                    listView.setCanLoadMore(false);
                }
            }
        }
    }

    @Override
    public void navigateToNewsDetail(int position, SpecialChannelGoodsEntity entity) {

    }

    @Override
    public void onDestroy() {
        if(call != null){
            call.cancel();
        }
        super.onDestroy();
    }
}
