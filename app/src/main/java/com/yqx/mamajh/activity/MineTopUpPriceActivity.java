package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.TopUpIntegral;
import com.yqx.mamajh.bean.TopUpOrder;
import com.yqx.mamajh.bean.TopUpPrice;
import com.yqx.mamajh.bean.WeiXinPay;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.wxapi.WXPayEntryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 余额充值/金币
 */
public class MineTopUpPriceActivity extends BaseActivity {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.grid_view)
    GridView gridView;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_unit)
    TextView tvUnit;

    private MaterialDialog mMaterialDialog = null;

    public static String TITLE_RES = "titleRes";

    private int titleSrc;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_top_up_price;
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
            //余额充值
            case R.string.top_up_price:
                tvHint.setText("充值金额");
                tvUnit.setText("元");
                Call<NetBaseEntity<List<TopUpPrice>>> call = RetrofitService.getInstance().topUpPriceList(AppApplication.TOKEN);
                call.enqueue(new Callback<NetBaseEntity<List<TopUpPrice>>>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity<List<TopUpPrice>>> response, Retrofit retrofit) {
                        if (response.body().getStatus() == 0) {
                            List<TopUpPrice> entities = response.body().getRes();
                            tvPrice.setText(entities.get(0).getPrice() + "");
                            gridView.setAdapter(new TopUpPriceItmeAdapter(entities));
                        } else {
                            //showToast("加载失败");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
            //金币充值
            case R.string.top_up_integral:
                tvHint.setText("充值金币");
                tvUnit.setText("个");
                Call<NetBaseEntity<TopUpIntegral>> call1 = RetrofitService.getInstance().getIntegralMallRechargeList(AppApplication.TOKEN);
                call1.enqueue(new Callback<NetBaseEntity<TopUpIntegral>>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity<TopUpIntegral>> response, Retrofit retrofit) {
                        if (response.body().getStatus() == 0) {
                            List<TopUpIntegral.Pricelist> entities = response.body().getRes().getPricelist();
                            tvPrice.setText(entities.get(0).getPrice() + "");
                            gridView.setAdapter(new TopUpIntegralItmeAdapter(entities));
                        } else {
                            //showToast("加载失败");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                });
                break;
        }

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

    @OnClick(R.id.btn_submit)
    public void onClick() {
        mMaterialDialog = new MaterialDialog.Builder(MineTopUpPriceActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        switch (titleSrc) {
            //余额充值
            case R.string.top_up_price:
                Call<NetBaseEntity<TopUpOrder>> call = RetrofitService.getInstance().createTopUpOrder(AppApplication.TOKEN, tvPrice.getText().toString());
                call.enqueue(new Callback<NetBaseEntity<TopUpOrder>>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity<TopUpOrder>> response, Retrofit retrofit) {
                        if (response.body().getStatus() == 0) {
                            TopUpOrder.PayInfo payInfo   = response.body().getRes().getPayInfo();
                            WeiXinPay          weiXinPay = new WeiXinPay();
                            weiXinPay.setAppid(payInfo.getAppId());
                            weiXinPay.setPartnerid(payInfo.getPartnerId());
                            weiXinPay.setPrepayid(payInfo.getPrepayId());
                            weiXinPay.setPackageX(payInfo.getPackageX());
                            weiXinPay.setNoncestr(payInfo.getNonceStr());
                            weiXinPay.setTimestamp(payInfo.getTimeStamp());
                            weiXinPay.setSign(payInfo.getPaySign());

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(WXPayEntryActivity.WX_PAY_KEY, weiXinPay);
                            readyGo(WXPayEntryActivity.class, bundle);
                        } else {
                            //showToast("加载失败");
                        }
                        mMaterialDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mMaterialDialog.dismiss();
                        t.printStackTrace();
                    }
                });
                break;
            //金币充值
            case R.string.top_up_integral:
                Call<NetBaseEntity<TopUpOrder>> call1 = RetrofitService.getInstance().createIntegralMallRechargeOrder(AppApplication.TOKEN, tvPrice.getText().toString());
                call1.enqueue(new Callback<NetBaseEntity<TopUpOrder>>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity<TopUpOrder>> response, Retrofit retrofit) {
                        if (response.body().getStatus() == 0) {
                            TopUpOrder.PayInfo payInfo   = response.body().getRes().getPayInfo();
                            WeiXinPay          weiXinPay = new WeiXinPay();
                            weiXinPay.setAppid(payInfo.getAppId());
                            weiXinPay.setPartnerid(payInfo.getPartnerId());
                            weiXinPay.setPrepayid(payInfo.getPrepayId());
                            weiXinPay.setPackageX(payInfo.getPackageX());
                            weiXinPay.setNoncestr(payInfo.getNonceStr());
                            weiXinPay.setTimestamp(payInfo.getTimeStamp());
                            weiXinPay.setSign(payInfo.getPaySign());

                            Bundle bundle = new Bundle();
                            bundle.putParcelable(WXPayEntryActivity.WX_PAY_KEY, weiXinPay);
                            readyGo(WXPayEntryActivity.class, bundle);
                        } else {
                            //showToast("加载失败");
                        }
                        mMaterialDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mMaterialDialog.dismiss();
                        t.printStackTrace();
                    }
                });
                break;
        }

    }

    class TopUpPriceItmeAdapter extends BaseAdapter {

        private List<TopUpPrice> entities = new ArrayList<>();
        private int selectorPosition;
        public TopUpPriceItmeAdapter(List<TopUpPrice> entities) {
            this.entities = entities;
        }

        @Override
        public int getCount() {
            return entities.size();
        }

        @Override
        public Object getItem(int i) {
            return entities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final TopUpPrice entity = entities.get(i);
            final ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_top_up_price, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (selectorPosition ==i) {
                holder.btnPrice.setBackgroundResource(R.drawable.xuanzhong);
            } else {
                holder.btnPrice.setBackgroundResource(R.drawable.weixuanzhong);
            }
            holder.btnPrice.setText(entity.getPrice() + "");

            holder.btnPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tvPrice.setText(entity.getPrice() + "");
                    changeState(i);
                }
            });
            return view;
        }
        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }
    }

    class TopUpIntegralItmeAdapter extends BaseAdapter {
        private List<TopUpIntegral.Pricelist> entities = new ArrayList<>();
        private int selectorPosition;


        public TopUpIntegralItmeAdapter(List<TopUpIntegral.Pricelist> entities) {
            this.entities = entities;
        }

        @Override
        public int getCount() {
            return entities.size();
        }

        @Override
        public Object getItem(int i) {
            return entities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final TopUpIntegral.Pricelist entity = entities.get(i);
            final ViewHolder              holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_top_up_price, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (selectorPosition ==i) {
                holder.btnPrice.setBackgroundResource(R.drawable.xuanzhong);
            } else {
                holder.btnPrice.setBackgroundResource(R.drawable.weixuanzhong);
            }
            holder.btnPrice.setText(entity.getPrice() + "金币");
            holder.btnPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    tvPrice.setText(entity.getPrice() + "");
                    changeState(i);
                }
            });
            return view;
        }
        public void changeState(int pos) {
            selectorPosition = pos;
            notifyDataSetChanged();

        }
    }

    static class ViewHolder {
        @BindView(R.id.btn_price)
        TextView btnPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
