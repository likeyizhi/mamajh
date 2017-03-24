package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.ProfitEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.AppConstant;
import com.yqx.mamajh.utils.SPUtils;
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

public class ProfitActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnLoadMoreListener {

    @BindView(R.id.tv_num)
    TextView num;
    @BindView(R.id.tv_gold)
    TextView gold;
    @BindView(R.id.tv_silver)
    TextView silver;
    @BindView(R.id.fragment_rank_list_swipe_layout)
    XSwipeRefreshLayout xSwipeRefreshLayout;
    @BindView(R.id.fragment_rank_list_list_view)
    LoadMoreListView loadMoreListView;

    private ListViewDataAdapter<ProfitEntity.ResEntity.PointEarnEntity> listViewDataAdapter;
    private String token ;
    private String size = "20";

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_profit;
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

        setTitle("江湖币赚取");
        token = SPUtils.getString(mContext, AppConstant.SP_TOKEN, "");

        listViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ProfitEntity.ResEntity.PointEarnEntity>() {
            @Override
            public ViewHolderBase<ProfitEntity.ResEntity.PointEarnEntity> createViewHolder(int position) {
                return new ViewHolderBase<ProfitEntity.ResEntity.PointEarnEntity>() {

                    TextView status;
                    TextView title;
                    TextView about;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_profit, null);

                        status = (TextView) convertView.findViewById(R.id.tv_status);
                        title = (TextView) convertView.findViewById(R.id.tv_title);
                        about = (TextView) convertView.findViewById(R.id.tv_about);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, ProfitEntity.ResEntity.PointEarnEntity itemData) {

                        if (itemData != null){
                            status.setText(itemData.getText());
                            title.setText(itemData.getTitle());
                            about.setText(itemData.getRemark());
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
        loadMoreListView.setCanLoadMore(false);

        loadData(token);

    }

    private void loadData(String page) {
        Call<ProfitEntity> call = RetrofitService.getInstance().getProfit(page);
        call.enqueue(new Callback<ProfitEntity>() {
            @Override
            public void onResponse(Response<ProfitEntity> response, Retrofit retrofit) {
                ProfitEntity netBaseEntity = response.body();

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
                    List<ProfitEntity.ResEntity> listEntity = netBaseEntity.getRes();

                    listViewDataAdapter.getDataList().addAll(listEntity.get(0).getPointEarn());
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
        loadData(token);
    }

    @Override
    public void onRefresh() {
        listViewDataAdapter.getDataList().clear();
        loadData(token);

    }
}
