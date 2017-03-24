package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

/**
 * 个人中心优惠券
 */
public class CouponActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.spinner)
    Spinner  mSpinner;
    private List<Coupon.GiftlistBean> mEntities       = new ArrayList<>();
    private MaterialDialog            mMaterialDialog = null;
    private CouponAdapter mAdapter;

    private int state = 1;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_coupon;
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
        setTitle("优惠卡券");

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = i + 1;
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getData();

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

    private void getData() {
        if (NetUtils.isNetworkConnected(mContext)) {
            //mMaterialDialog = new MaterialDialog.Builder(CouponActivity.this)
            //        .content(R.string.loading)
            //        .cancelable(false)
            //        .progress(true, 0)
            //        .progressIndeterminateStyle(false)
            //        .show();
            Call<NetBaseEntity<Coupon>> mGetDataCallNet = RetrofitService.getInstance().userCenterCouponList(AppApplication.TOKEN, state);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<Coupon>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<Coupon>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        //mMaterialDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().getGiftlist();
                        if (mAdapter == null) {
                            lv.setAdapter(mAdapter = new CouponAdapter());
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    //mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    //mMaterialDialog.dismiss();
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
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_coupon, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            switch (state) {
                case 1:
                    holder.lay_root.setBackgroundResource(R.color.colorPrimary);
                    break;
                case 2:
                case 3:
                    holder.lay_root.setBackgroundResource(R.color.gray_500);
                    break;
            }
            holder.ivItemTitle.setText(entity.getDesc());
            holder.tvItemPrice.setText("￥" + entity.getPrice());
            holder.ivItemDesc.setText(entity.getDesc());
            holder.ivItemTime.setText(entity.getBegin() + "\n" + entity.getEnd());
            return view;
        }
    }

    static class ViewHolder {
        @BindView(R.id.lay_item_root)
        LinearLayout lay_root;
        @BindView(R.id.iv_item_title)
        TextView     ivItemTitle;
        @BindView(R.id.tv_item_price)
        TextView     tvItemPrice;
        @BindView(R.id.iv_item_desc)
        TextView     ivItemDesc;
        @BindView(R.id.iv_item_time)
        TextView     ivItemTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
