package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.base.BaseAppCompatActivity;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.RankingEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/8.
 */

public class RankingActivity extends BaseActivity implements LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fragment_rank_list_swipe_layout)
    XSwipeRefreshLayout xSwipeRefreshLayout;
    @BindView(R.id.fragment_rank_list_list_view)
    LoadMoreListView loadMoreListView;

    private ListViewDataAdapter<RankingEntity.ResEntity.SalesRankingEntity> listViewDataAdapter;
    private int page = 1;
    private String size = "20";

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_ranking;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return xSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        setTitle("销量排行");

        listViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<RankingEntity.ResEntity.SalesRankingEntity>() {
            @Override
            public ViewHolderBase<RankingEntity.ResEntity.SalesRankingEntity> createViewHolder(int position) {
                return new ViewHolderBase<RankingEntity.ResEntity.SalesRankingEntity>() {

                    ImageView img;
                    TextView about;
                    TextView money;
                    TextView num;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_ranking, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_name);
                        money = (TextView) convertView.findViewById(R.id.tv_money);
                        num = (TextView) convertView.findViewById(R.id.tv_num);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, RankingEntity.ResEntity.SalesRankingEntity itemData) {

                        if (itemData != null){
                            Glide.with(mContext).load(itemData.getImgSrc()).into(img);
                            about.setText(itemData.getTitle());
                            money.setText(itemData.getSellPrice() + "金币");
                            num.setText(itemData.getSaleCount() + "人兑换");
                        }

                    }
                };
            }
        });

        loadMoreListView.setAdapter(listViewDataAdapter);
        xSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4)
        );
        xSwipeRefreshLayout.setOnRefreshListener(this);
        loadMoreListView.setOnLoadMoreListener(this);

        loadData(page);

    }

    private void loadData(int page) {
        if(xSwipeRefreshLayout != null){
            xSwipeRefreshLayout.setRefreshing(false);
        }
        if(loadMoreListView != null){
            loadMoreListView.onLoadMoreComplete();
        }
        Call<RankingEntity> call = RetrofitService.getInstance().getRankingList(page, size);
        call.enqueue(new Callback<RankingEntity>() {
            @Override
            public void onResponse(Response<RankingEntity> response, Retrofit retrofit) {
                RankingEntity netBaseEntity = response.body();

                if(netBaseEntity == null){
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody!=null) {
                        try {
//                            loadedListener.onException(responseBody.string());
                            showError(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showError("(responseBody = null)极有可能是json解析失败");
                    }
                }else{
                    List<RankingEntity.ResEntity> listEntity = netBaseEntity.getRes();

                    listViewDataAdapter.getDataList().addAll(listEntity.get(0).getSalesRanking());
                    listViewDataAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onLoadMore() {
        page++;
        loadData(page);
    }

    @Override
    public void onRefresh() {
        page = 1;
        listViewDataAdapter.getDataList().clear();
        loadData(page);

    }
}
