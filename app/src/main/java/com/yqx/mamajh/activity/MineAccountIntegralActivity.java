package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.AccountIntegral;
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
 * 江湖币
 */
public class MineAccountIntegralActivity extends BaseActivity {

    @BindView(R.id.tv_score_hint1)
    TextView            tvScoreHint1;
    @BindView(R.id.tv_score)
    TextView            tvScore;
    @BindView(R.id.tv_score_hint2)
    TextView            tvScoreHint2;
    @BindView(R.id.btn_score)
    BootstrapButton     btnScore;
    @BindView(R.id.btn_score1)
    BootstrapButton     btnScore1;
    @BindView(R.id.lv)
    LoadMoreListView    lv;
    @BindView(R.id.swipe_refresh)
    XSwipeRefreshLayout swipeRefresh;

    private static int page = 1;
    private static int type = 0;

    private List<AccountIntegral> mEntities = new ArrayList<>();
    private AccountIntegralAdapter mAdapter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_account_integral;
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
        setTitle("我的江湖币");

        tvScore.setText(AppApplication.memeberIndex.getScore() + "");

        swipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(false);
            }
        });
        lv.setAdapter(mAdapter = new AccountIntegralAdapter());
        lv.setCanLoadMore(true);
        lv.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getData(true);
            }
        });
        getData(false);
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


    @OnClick({R.id.btn_score, R.id.btn_score1, R.id.btn_pay, R.id.btn_convert})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.btn_score:
                tvScore.setText(AppApplication.memeberIndex.getScore() + "");
                btnScore.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                btnScore1.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                tvScoreHint1.setText("当前金币总数");
                tvScoreHint2.setText("金币可以兑换好多优质物品哦");
                type = 0;
                getData(false);
                break;
            case R.id.btn_score1:
                tvScore.setText(AppApplication.memeberIndex.getScore2() + "");
                btnScore.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                btnScore1.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                tvScoreHint1.setText("当前银币总数");
                tvScoreHint2.setText("银币可以兑换金币哦");
                type = 1;
                getData(false);
                break;
            case R.id.btn_pay:
                bundle = new Bundle();
                bundle.putInt(MineTopUpPriceActivity.TITLE_RES, R.string.top_up_integral);
                readyGo(MineTopUpPriceActivity.class,bundle);
                break;
            case R.id.btn_convert:
                readyGo(CreditActivity.class);
//                showToast("积分兑换");
                break;
        }
    }

    private void getData(final boolean isLoadMore) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<List<AccountIntegral>>> call = RetrofitService.getInstance().userCenterAccountIntegral(AppApplication.TOKEN, type, page, 10);
            call.enqueue(new Callback<NetBaseEntity<List<AccountIntegral>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<AccountIntegral>>> response, Retrofit retrofit) {
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

    class AccountIntegralAdapter extends BaseAdapter {

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
            final AccountIntegral entity = mEntities.get(i);
            final ViewHolder      holder;
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
