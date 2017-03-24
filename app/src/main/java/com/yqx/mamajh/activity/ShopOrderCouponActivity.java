package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.Coupon;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ShopOrderCouponActivity extends BaseActivity {
    @BindView(R.id.lv)
    ListView lv;
    private List<Coupon.GiftlistBean> mEntities       = new ArrayList<>();
    private MaterialDialog            mMaterialDialog = null;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_shop_order_coupon;
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
        setTitle("优惠券");
        int id = getIntent().getExtras().getInt("id");
        getData(id);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("coupon", mEntities.get(i).getID());
                bundle.putInt("state", 1);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
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

    private void getData(int id) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(ShopOrderCouponActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<Coupon>> mGetDataCallNet = RetrofitService.getInstance().shopOrderCoupon(AppApplication.TOKEN, id);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<Coupon>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<Coupon>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        showToast("服务器出错");
                        mMaterialDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().getGiftlist();
                        lv.setAdapter(new CouponAdapter());
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

    private class CouponAdapter extends BaseAdapter {

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
            final Coupon.GiftlistBean entity = mEntities.get(i);
            final ViewHolder          holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_shop_order_coupon, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.ivItemTitle.setText(entity.getDesc());
            holder.tvItemPrice.setText("￥" + entity.getPrice());
            holder.ivItemDesc.setText(entity.getDesc());
            holder.ivItemTime.setText(entity.getBegin() + "\n" + entity.getEnd());
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.iv_item_title)
        TextView ivItemTitle;
        @BindView(R.id.tv_item_price)
        TextView tvItemPrice;
        @BindView(R.id.iv_item_desc)
        TextView ivItemDesc;
        @BindView(R.id.iv_item_time)
        TextView ivItemTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
