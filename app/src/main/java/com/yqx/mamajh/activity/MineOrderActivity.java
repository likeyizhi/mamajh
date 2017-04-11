package com.yqx.mamajh.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.ListViewUtil;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.CreateOrder;
import com.yqx.mamajh.bean.MemberOrder;
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
 * 个人中心 - 订单界面
 */
public class MineOrderActivity extends BaseActivity {

    public static  String TITLE_RES = "titleRes";
    private static int    page      = 1;
    private static String type;
    private        int    titleSrc;

    @BindView(R.id.lv_mine_order)
    LoadMoreListView    mLv;
    @BindView(R.id.srl_mine_order)
    XSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.lay_null)
    LinearLayout        layNull;


    private List<MemberOrder> mOrderEntities = new ArrayList<>();
    private OrderItmeAdapter mAdapter;
    private MaterialDialog mMaterialDialog = null;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_order;
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
        titleSrc = getIntent().getExtras().getInt(TITLE_RES);
        setTitle(mContext.getString(titleSrc));
        switch (titleSrc) {
            //待付款
            case R.string.wait_payment:
                page = 1;
                type = "p";
                break;
            //待发货
            case R.string.wait_deliver:
                page = 1;
                type = "s";
                break;
            //待收货
            case R.string.wait_receipt:
                page = 1;
                type = "r";
                break;
            //待评价
            case R.string.wait_comment:
                page = 1;
                type = "d";
                break;
            //全部订单
            case R.string.all_order:
                page = 1;
                type = "null";
                break;
        }
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData(page, false);
            }
        });
        mLv.setAdapter(mAdapter = new OrderItmeAdapter());
        mLv.setCanLoadMore(true);
        mLv.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
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

    private void getData(int page, final boolean isLoadMore) {
        if (NetUtils.isNetworkConnected(mContext)) {
            if (!isLoadMore){
                mMaterialDialog = new MaterialDialog.Builder(MineOrderActivity.this)
                        .content(R.string.loading)
                        .cancelable(false)
                        .progress(true, 0)
                        .progressIndeterminateStyle(false)
                        .show();
            }
            Call<NetBaseEntity<List<MemberOrder>>> mGetDataCallNet = RetrofitService.getInstance().memberOrderAll(AppApplication.TOKEN, type, page, 10);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<MemberOrder>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<MemberOrder>>> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        if (isLoadMore) {
                            mOrderEntities.addAll(response.body().getRes());
                            mLv.onLoadMoreComplete();
                        } else {
                            mOrderEntities = response.body().getRes();
                            mLv.onLoadMoreComplete();
                            mMaterialDialog.dismiss();
                        }
                        if (mOrderEntities.size() > 0) {
                            layNull.setVisibility(View.GONE);
                        } else {
                            layNull.setVisibility(View.VISIBLE);
                        }
                        mMaterialDialog.dismiss();
                        mLv.onLoadMoreComplete();
                        mAdapter.notifyDataSetChanged();
                    } else {
                        //showToast(response.body().getMes());
                        mLv.onLoadMoreComplete();
                        mMaterialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 取消订单
     *
     * @param orderid
     */
    private void orderCancel(String orderid) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity> mGetDataCallNet = RetrofitService.getInstance().orderCancel(AppApplication.TOKEN, orderid);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        showToast("取消成功");
                        getData(1, false);
                    } else {
                        showToast("取消失败:"+response.body().getMes());
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
        mSwipeRefreshLayout.setRefreshing(false);
    }

    class OrderItmeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mOrderEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return mOrderEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final MemberOrder entity = mOrderEntities.get(i);
            final ViewHolder  holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mine_order, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvItemTitle.setText(entity.getShopName());
            holder.tvItemOrderState.setText(entity.getOrderState());
            holder.tvItemTime.setText(entity.getTime());
            holder.tvItemOrderPriceInfo.setText(String.format("共%s商品 合计：￥%s (含运费￥%s)", entity.getProductCount(), entity.getPrice(), entity.getPostPrice()));
            ProductItmeAdapter productItmeAdapter = new ProductItmeAdapter(mContext, entity.getProductlist());
            holder.lvItemOrderProductlist.setAdapter(productItmeAdapter);
            ListViewUtil.setListViewHeightBasedOnChildren(holder.lvItemOrderProductlist);
            /**
             * 按钮状态：
             * 1：支付和取消
             * 2：取消
             * 3：确认收货
             * 4：评价
             */
            switch (entity.getButtonState()) {
                case 1:
                    holder.btnItemOrderState1.setText("去支付");
                    holder.btnItemOrderState1.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    holder.btnItemOrderState1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CreateOrder order = new CreateOrder();
                            order.setOrderNumber(entity.getPayData().getOrderNumber());
                            order.setAddress(entity.getPayData().getAddress());
                            order.setName(entity.getPayData().getName());
                            order.setPay(entity.getPayData().getPay());
                            order.setPhone(entity.getPayData().getPhone());
                            order.setPostPay(entity.getPayData().getPostPay());
                            order.setPrice(entity.getPayData().getPrice());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);
                            readyGo(CreateOrderSuccessActivity.class, bundle);
                        }
                    });
                    holder.btnItemOrderState2.setText("取消订单");
                    holder.btnItemOrderState2.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                    holder.btnItemOrderState2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderCancel(entity.getID() + "");
                        }
                    });
                    break;
                case 2:
                    holder.btnItemOrderState1.setText("取消订单");
                    holder.btnItemOrderState1.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                    holder.btnItemOrderState1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderCancel(entity.getID() + "");
                        }
                    });
                    holder.btnItemOrderState2.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.btnItemOrderState1.setText("确认收货");
                    holder.btnItemOrderState1.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    holder.btnItemOrderState1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            orderSign(entity.getID()+"",i);
                        }
                    });
//                    holder.btnItemOrderState1.setVisibility(View.GONE);
                    holder.btnItemOrderState2.setVisibility(View.GONE);
                    break;
                case 4:
                    holder.btnItemOrderState1.setText("去评价");
                    holder.btnItemOrderState1.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                    holder.btnItemOrderState1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("order", entity);
                            bundle.putString("orderId", entity.getNumber());
                            readyGo(OrderCommentActivity.class, bundle);
                        }
                    });
                    holder.btnItemOrderState2.setVisibility(View.GONE);
                    break;
                default:
                    holder.btnItemOrderState1.setVisibility(View.GONE);
                    holder.btnItemOrderState2.setVisibility(View.GONE);
                    break;
            }
            if (mOrderEntities.get(i)!=null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(MineOrderActivity.this,""+entity.getNumber(),Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MineOrderActivity.this,OrderInfoActivity.class);
                        intent.putExtra("_orderId",entity.getNumber()+"");
                        intent.putExtra("_shopId",entity.getShopID()+"");
                        startActivity(intent);
                    }
                });
            }
            return view;
        }
    }

    private void orderSign(String orderId,final int i) {
        Call<NetBaseEntity> call=RetrofitService.getInstance().orderSign(AppApplication.TOKEN,orderId);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    showToast("确认成功");
                    getData(1, false);
                        Call<NetBaseEntity<List<MemberOrder>>> mGetDataCallNet = RetrofitService.getInstance().memberOrderAll(AppApplication.TOKEN, type, page, 20);
                        mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<MemberOrder>>>() {
                            @Override
                            public void onResponse(Response<NetBaseEntity<List<MemberOrder>>> response, Retrofit retrofit) {
                                if (response.body().getStatus() == 0) {
                                    mOrderEntities.remove(i);
                                    mAdapter.notifyDataSetChanged();
                                }else {
                                    //showToast(response.body().getMes());
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println(t.getMessage());
                            }
                        });
                }else{
                    showToast(response.body().getMes()+"");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showToast(t+"");
            }
        });
    }

    class ProductItmeAdapter extends BaseAdapter {
        Context                   context;
        List<MemberOrder.Product> entityList;

        public ProductItmeAdapter(Context context, List<MemberOrder.Product> entityList) {
            super();
            this.context = context;
            this.entityList = entityList;
        }

        @Override
        public int getCount() {
            return this.entityList.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final MemberOrder.Product entity = this.entityList.get(i);
            final ProductViewHolder   holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_mine_order_product, null);
                holder = new ProductViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ProductViewHolder) view.getTag();
            }
            Glide.with(getApplicationContext()).load(entity.getImg()).crossFade().into(holder.ivMineOrderProductItemImg);
            holder.tvItemOrderProductTitle.setText(entity.getName());
            holder.tvItemOrderProductStandard.setText("规格："+entity.getStandard());
            holder.tvItemOrderProductPrice.setText("￥" + entity.getPrice());
            holder.tvItemOrderProductPrice2.setText("￥" + entity.getPrice2());
            holder.tvItemOrderProductCount.setText("X " + entity.getCount());
            holder.tvItemOrderProductPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            if (entityList.get(i)!=null){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(MineOrderActivity.this,""+entity.getID(),Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(MineOrderActivity.this,ProductInfoActivity.class);
//                        intent.putExtra("_id", Integer.parseInt(entity.getID()+""));
//                        startActivity(intent);
                        Intent intent=new Intent(MineOrderActivity.this,OrderInfoActivity.class);
                        intent.putExtra("_orderId",entity.getOrderNumber()+"");
                        intent.putExtra("_shopId",entity.getShopId()+"");
                        startActivity(intent);
                    }
                });
            }
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView        tvItemTitle;
        @BindView(R.id.tv_item_time)
        TextView        tvItemTime;
        @BindView(R.id.tv_item_order_state)
        TextView        tvItemOrderState;
        @BindView(R.id.lv_item_order_productlist)
        ListView        lvItemOrderProductlist;
        @BindView(R.id.tv_item_order_price_info)
        TextView        tvItemOrderPriceInfo;
        @BindView(R.id.btn_item_order_state_1)
        BootstrapButton btnItemOrderState1;
        @BindView(R.id.btn_item_order_state_2)
        BootstrapButton btnItemOrderState2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ProductViewHolder {
        @BindView(R.id.iv_mine_order_product_item_img)
        ImageView ivMineOrderProductItemImg;
        @BindView(R.id.tv_item_order_product_title)
        TextView  tvItemOrderProductTitle;
        @BindView(R.id.tv_item_order_product_standard)
        TextView  tvItemOrderProductStandard;
        @BindView(R.id.tv_item_order_product_price)
        TextView  tvItemOrderProductPrice;
        @BindView(R.id.tv_item_order_product_price2)
        TextView  tvItemOrderProductPrice2;
        @BindView(R.id.tv_item_order_product_count)
        TextView  tvItemOrderProductCount;

        ProductViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected void onRestart() {
        getData(1,false);
        super.onRestart();
    }
}
