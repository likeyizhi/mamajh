package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.AccountBalance;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 账户余额
 */
public class MineAccountBalanceActivity extends BaseActivity {

    @BindView(R.id.tv_balance)
    TextView            tvBalance;
    @BindView(R.id.lv)
    LoadMoreListView    lv;
    @BindView(R.id.swipe_refresh)
    XSwipeRefreshLayout swipeRefresh;

    private List<AccountBalance> mEntities = new ArrayList<>();
    private AccountBalanceAdapter mAdapter;

    private static int page = 1;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_account_balance;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("账户余额");

        tvBalance.setText(AppApplication.memeberIndex.getMainPrice() + "");

        swipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(page, false);
            }
        });
        lv.setAdapter(mAdapter = new AccountBalanceAdapter());
        lv.setCanLoadMore(true);
        lv.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getData(page, true);
            }
        });
        getData(page, false);
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


    @OnClick(R.id.btn_pay_balance)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(MineTopUpPriceActivity.TITLE_RES, R.string.top_up_price);
        readyGo(MineTopUpPriceActivity.class, bundle);
    }

    private void getData(int page, final boolean isLoadMore) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<List<AccountBalance>>> mGetDataCallNet = RetrofitService.getInstance().userCenterAccountBalance(AppApplication.TOKEN, page, 10);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<AccountBalance>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<AccountBalance>>> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        if (isLoadMore) {
                            mEntities.addAll(response.body().getRes());
                            lv.onLoadMoreComplete();
                        } else {
                            mEntities = response.body().getRes();
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        //showToast(response.body().getMes());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
        swipeRefresh.setRefreshing(false);
    }

    class AccountBalanceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return mEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final AccountBalance entity = mEntities.get(i);
            final ViewHolder     holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mine_account_balance, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvItemTitle.setText(entity.getDes());
            holder.tvItemTime.setText(entity.getTime());
            holder.tvItemPrice.setText(entity.getPrice());
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.tv_item_price)
        TextView tvItemPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
