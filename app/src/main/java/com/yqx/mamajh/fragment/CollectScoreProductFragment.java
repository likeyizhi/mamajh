package com.yqx.mamajh.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.CollectScoreProduct;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 我的收藏 积分商品
 */

public class CollectScoreProductFragment extends BaseFragment {

    @BindView(R.id.list_view)
    LoadMoreListView    listView;
    @BindView(R.id.swipe_layout)
    XSwipeRefreshLayout swipeLayout;
    @BindView(R.id.lay_collect_null)
    LinearLayout        layCollectNull;

    private List<CollectScoreProduct> mEntities = new ArrayList<>();
    private CollectAdapter mAdapter;

    private int page = 1;
    private int size = 20;

    public static CollectScoreProductFragment newInstance() {
        return new CollectScoreProductFragment();
    }

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
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        swipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(false);
            }
        });
        listView.setCanLoadMore(true);
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                getData(true);
                listView.onLoadMoreComplete();
            }
        });
        mAdapter = new CollectAdapter();
        listView.setAdapter(mAdapter);
        getData(false);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_collect_score_product;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    public void getData(final boolean isLoadMore) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<List<CollectScoreProduct>>> mGetDataCallNet = RetrofitService.getInstance().collectScoreProductList(AppApplication.TOKEN, page, size);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<CollectScoreProduct>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<CollectScoreProduct>>> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        if (isLoadMore) {
                            mEntities.addAll(response.body().getRes());
                            listView.onLoadMoreComplete();
                        } else {
                            mEntities = response.body().getRes();
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast(response.body().getMes());
                    }
                    if (mEntities.size() > 0) {
                        layCollectNull.setVisibility(View.GONE);
                    } else {
                        layCollectNull.setVisibility(View.VISIBLE);
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
        swipeLayout.setRefreshing(false);
    }

    class CollectAdapter extends BaseAdapter {

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
            final CollectScoreProduct entity = mEntities.get(i);
            final ViewHolder          holder;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_collect_score_product, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Glide.with(getContext()).load(entity.getImg()).crossFade().into(holder.ivItemImg);
            holder.tvItemTitle.setText(entity.getTitle());
            holder.tvItemPrice.setText(entity.getPrice() + "金币");
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.iv_item_img)
        ImageView ivItemImg;
        @BindView(R.id.tv_item_title)
        TextView  tvItemTitle;
        @BindView(R.id.tv_item_price)
        TextView  tvItemPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
