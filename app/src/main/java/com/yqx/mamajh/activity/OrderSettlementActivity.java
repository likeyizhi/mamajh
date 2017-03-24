package com.yqx.mamajh.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.ListViewUtil;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.CreateOrder;
import com.yqx.mamajh.bean.DeliveryAddress;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.OrderInfo;
import com.yqx.mamajh.network.RetrofitService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class OrderSettlementActivity extends BaseActivity {

    @BindView(R.id.tv_address_person)
    TextView        tvAddressPerson;
    @BindView(R.id.tv_address_info)
    TextView        tvAddressInfo;
    @BindView(R.id.iv_store_pic)
    CircleImageView ivStorePic;
    @BindView(R.id.tv_store_title)
    TextView        tvStoreTitle;
    @BindView(R.id.lv_item)
    ListView        lvItem;
    @BindView(R.id.tv_delivery_method)
    TextView        tvDeliveryMethod;
    @BindView(R.id.tv_coupon_info)
    TextView        tvCouponInfo;
    @BindView(R.id.lay_select_coupon)
    LinearLayout    laySelectCoupon;
    @BindView(R.id.et_remark)
    EditText        etRemark;
    @BindView(R.id.tv_total_info)
    TextView        tvTotalInfo;
    @BindView(R.id.tv_payment_method)
    TextView        tvPaymentMethod;
    @BindView(R.id.tv_item_price)
    TextView        tvItemPrice;
    @BindView(R.id.tv_express_price)
    TextView        tvExpressPrice;
    @BindView(R.id.tv_promotion_breaks)
    TextView        tvPromotionBreaks;
    @BindView(R.id.tv_coupon_breaks)
    TextView        tvCouponBreaks;
    @BindView(R.id.tv_payment_price)
    TextView        tvPaymentPrice;
    @BindView(R.id.tv_count)
    TextView        tvCount;
    @BindView(R.id.tv_total_price)
    TextView        tvTotalPrice;
    @BindView(R.id.tv_total_express_price)
    TextView        tvTotalExpressPrice;

    String mIds = "";
    private OrderInfo mOrderInfo;

    private MaterialDialog mMaterialDialog = null;

    public int RC_SELECT_ADDRESS  = 1;
    public int RC_SELECT_DELIVERY = 2;
    public int RC_SELECT_COUPON   = 3;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_settlement;
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
        setTitle("订单结算");
        mIds = getIntent().getExtras().getString("ids");
//        Toast.makeText(OrderSettlementActivity.this,""+mIds)
        Map<String, String> params = new HashMap<>();
        params.put("List", mIds);
        getDefaultOrderInfo(params);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SELECT_ADDRESS && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle          b       = data.getExtras();
                DeliveryAddress address = (DeliveryAddress) b.getSerializable("address");
                mOrderInfo.setDeliveryID(address.getId());
                updateParam();
            }
        } else if (requestCode == RC_SELECT_DELIVERY && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle b  = data.getExtras();
                int    id = b.getInt("delivery");
                mOrderInfo.getShoplist().get(0).setPostID(id);
                updateParam();
            }
        } else if (requestCode == RC_SELECT_COUPON && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle b  = data.getExtras();
                int    id = b.getInt("coupon");
                mOrderInfo.getShoplist().get(0).setGiftID(id);
                updateParam();
            }
        }
    }

    @OnClick({R.id.lay_select_address, R.id.lay_select_delivery_method, R.id.lay_select_coupon, R.id.lay_select_payment_method, R.id.btn_settlement})
    public void onClick(View view) {
        Bundle             bundle;
        OrderInfo.Shoplist shop;
        switch (view.getId()) {
            case R.id.lay_select_address:
                bundle = new Bundle();
                bundle.putBoolean("select", true);
                readyGoForResult(MineAddressActivity.class, RC_SELECT_ADDRESS, bundle);
                break;
            case R.id.lay_select_delivery_method:
                shop = mOrderInfo.getShoplist().get(0);
                bundle = new Bundle();
                bundle.putInt("id", shop.getShopID());
                readyGoForResult(DeliveryWayActivity.class, RC_SELECT_DELIVERY, bundle);
                break;
            case R.id.lay_select_coupon:
                shop = mOrderInfo.getShoplist().get(0);
                bundle = new Bundle();
                bundle.putInt("id", shop.getShopID());
                readyGoForResult(ShopOrderCouponActivity.class, RC_SELECT_COUPON, bundle);
                break;
            case R.id.lay_select_payment_method:
                break;
            case R.id.btn_settlement:
                shop = mOrderInfo.getShoplist().get(0);
                String remarks = "[{'ShopID':'" + shop.getShopID() +
                        "','PostID':'" + shop.getPostID() +
                        "','GiftID':'" + shop.getGiftID() +
                        "','Remark':'" + etRemark.getText().toString() + "'}]";
                Map<String, String> params = new HashMap<>();
                params.put("List", mIds);
                params.put("deliveryid", mOrderInfo.getDeliveryID() + "");
                params.put("remarks", remarks);
                params.put("source", 1 + "");
                createOrder(params);
                break;
        }
    }

    private void updateParam() {
        OrderInfo.Shoplist shop = mOrderInfo.getShoplist().get(0);
        String remarks = "[{'ShopID':'" + shop.getShopID() +
                "','PostID':'" + shop.getPostID() +
                "','GiftID':'" + shop.getGiftID() +
                "','Remark':'" + etRemark.getText().toString() + "'}]";
        Map<String, String> params = new HashMap<>();
        params.put("List", mIds);
        params.put("deliveryid", mOrderInfo.getDeliveryID() + "");
        params.put("remarks", remarks);

        getOrderTotal(params);
    }

    /**
     * 获得订单信息
     *
     * @param params
     */
    private void getDefaultOrderInfo(Map<String, String> params) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderSettlementActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<OrderInfo>> mGetDataCallNet = RetrofitService.getInstance().getDefaultOrderInfo(AppApplication.TOKEN, params);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<OrderInfo>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<OrderInfo>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mOrderInfo = response.body().getRes();
                        setData();
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
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
    }

    /**
     * 更新结算信息
     *
     * @param params
     */
    private void getOrderTotal(Map<String, String> params) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderSettlementActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<OrderInfo>> mGetDataCallNet = RetrofitService.getInstance().getOrderTotal(AppApplication.TOKEN, params);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<OrderInfo>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<OrderInfo>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mOrderInfo = response.body().getRes();
                        setData();
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
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
    }

    private void createOrder(Map<String, String> params) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderSettlementActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<CreateOrder>> mGetDataCallNet = RetrofitService.getInstance().createOrder(AppApplication.TOKEN, params);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<CreateOrder>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<CreateOrder>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        CreateOrder order  = response.body().getRes();
                        Bundle      bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        readyGoThenKill(CreateOrderSuccessActivity.class, bundle);
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
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
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        tvAddressPerson.setText("收货人：" + mOrderInfo.getDeliveryName() + " " + mOrderInfo.getDeliveryPhone());
        tvAddressInfo.setText("收货地址：" + mOrderInfo.getDeliveryAddress());

        OrderInfo.Shoplist shop = mOrderInfo.getShoplist().get(0);

        Glide.with(getApplicationContext()).load(shop.getShopLogo()).crossFade().into(ivStorePic);
        tvStoreTitle.setText(shop.getShopName());
        lvItem.setAdapter(new ProducAdapter(shop.getProductList()));
        ListViewUtil.setListViewHeightBasedOnChildren(lvItem);

        tvDeliveryMethod.setText(shop.getPostName());
        if (shop.getGiftID() == 0) {
            tvCouponInfo.setText("您有" + shop.getGiftCount()  + "张可用优惠券");
        }else{
            tvCouponInfo.setText("使用" + shop.getGiftPrice()  + "元优惠券");
        }

        tvTotalInfo.setText("共" + mOrderInfo.getProductCount() +
                "件，合计：￥" + mOrderInfo.getOrderTotalPrice() +
                "，运费：￥" + mOrderInfo.getFreight() +
                "，优惠：￥" + mOrderInfo.getPriceGiftPriceTotal());
        tvItemPrice.setText(mOrderInfo.getOrderTotalPrice() + "");
        tvExpressPrice.setText(mOrderInfo.getFreight() + "");
        tvPromotionBreaks.setText(0 + "");
        tvCouponBreaks.setText(mOrderInfo.getPriceGiftPriceTotal() + "");
        tvPaymentPrice.setText(mOrderInfo.getOrderPayPrice() + "");

        tvCount.setText(mOrderInfo.getProductCount() + "");
        tvTotalPrice.setText(mOrderInfo.getOrderPayPrice() + "");
        tvTotalExpressPrice.setText(mOrderInfo.getFreight() + "");

    }
    @SuppressLint("SetTextI18n")
    private void setData2() {
        tvAddressPerson.setText("收货人：" + mOrderInfo.getDeliveryName() + " " + mOrderInfo.getDeliveryPhone());
        tvAddressInfo.setText("收货地址：" + mOrderInfo.getDeliveryAddress());

        OrderInfo.Shoplist shop = mOrderInfo.getShoplist().get(0);

        Glide.with(getApplicationContext()).load(shop.getShopLogo()).crossFade().into(ivStorePic);
        tvStoreTitle.setText(shop.getShopName());
        lvItem.setAdapter(new ProducAdapter(shop.getProductList()));
        ListViewUtil.setListViewHeightBasedOnChildren(lvItem);

        tvDeliveryMethod.setText(shop.getPostName());
        if (shop.getGiftID() != 0) {
            tvCouponInfo.setText("您有" + shop.getGiftCount()  + "张可用优惠券");
        }
        tvCouponInfo.setText("使用" + shop.getGiftPrice()  + "元优惠券");
        tvTotalInfo.setText("共" + mOrderInfo.getProductCount() +
                "件，合计：￥" + mOrderInfo.getOrderTotalPrice() +
                "，运费：￥" + mOrderInfo.getFreight() +
                "，优惠：￥" + mOrderInfo.getPriceGiftPriceTotal());
        tvItemPrice.setText(mOrderInfo.getOrderTotalPrice() + "");
        tvExpressPrice.setText(mOrderInfo.getFreight() + "");
        tvPromotionBreaks.setText(0 + "");
        tvCouponBreaks.setText(mOrderInfo.getPriceGiftPriceTotal() + "");
        tvPaymentPrice.setText(mOrderInfo.getOrderPayPrice() + "");

        tvCount.setText(mOrderInfo.getProductCount() + "");
        tvTotalPrice.setText(mOrderInfo.getOrderPayPrice() + "");
        tvTotalExpressPrice.setText(mOrderInfo.getFreight() + "");

    }


    class ProducAdapter extends BaseAdapter {

        private List<OrderInfo.Shoplist.ProductListBean> productList;

        ProducAdapter(List<OrderInfo.Shoplist.ProductListBean> productList) {
            this.productList = productList;
        }

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int i) {
            return productList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final OrderInfo.Shoplist.ProductListBean entity = productList.get(i);
            final ViewHolder                         holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_settlement_order, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Glide.with(getApplicationContext()).load(entity.getImg()).crossFade().into(holder.ivItemPic);
            holder.tvItemTitle.setText(entity.getName());
            holder.tvItemPrice.setText("￥" + entity.getOrderPrice());
            holder.tvItemNum.setText("X" + entity.getCount());
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_item_title)
        TextView  tvItemTitle;
        @BindView(R.id.iv_item_pic)
        ImageView ivItemPic;
        @BindView(R.id.tv_item_price)
        TextView  tvItemPrice;
        @BindView(R.id.tv_item_num)
        TextView  tvItemNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    @Override
//    protected void onRestart() {
//        updateParam();
//        super.onRestart();
//    }
}
