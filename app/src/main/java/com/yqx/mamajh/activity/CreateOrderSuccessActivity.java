package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.rey.material.widget.CheckBox;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.CreateOrder;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.WeiXinPay;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CreateOrderSuccessActivity extends BaseActivity {

    @BindView(R.id.tv_price)
    TextView        tvPrice;
    @BindView(R.id.tv_delivery_method)
    TextView        tvDeliveryMethod;
    @BindView(R.id.tv_address_person)
    TextView        tvAddressPerson;
    @BindView(R.id.tv_address_info)
    TextView        tvAddressInfo;
    @BindView(R.id.tv_balance)
    TextView        tvBalance;
    @BindView(R.id.cb_balance)
    CheckBox        cbBalance;
    @BindView(R.id.tv_balance_pay_info)
    TextView        tvBalancePayInfo;
    @BindView(R.id.et_balance_pay_pwd)
    EditText        etBalancePayPwd;
    @BindView(R.id.lay_balance)
    LinearLayout    layBalance;
    @BindView(R.id.btn_pay)
    BootstrapButton btnPay;

    private CreateOrder mOrder;

    private boolean isBalance = false;

    private MaterialDialog mMaterialDialog = null;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_create_order_success;
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
        setTitle("订单支付");

        mOrder = (CreateOrder) getIntent().getExtras().getSerializable("order");

        layBalance.setVisibility(View.GONE);
        cbBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    layBalance.setVisibility(View.VISIBLE);
                    btnPay.setText("确认支付");
                    isBalance = true;
                } else {
                    layBalance.setVisibility(View.GONE);
                    btnPay.setText("微信支付（" + mOrder.getPrice() + "）");
                    isBalance = false;
                }
            }
        });

        tvPrice.setText("￥" + mOrder.getPrice());
        tvDeliveryMethod.setText(mOrder.getPostPay());
        tvAddressPerson.setText(mOrder.getName()+"("+mOrder.getPhone()+")");
        tvAddressInfo.setText(mOrder.getAddress());
        tvBalance.setText(AppApplication.memeberIndex.getMainPrice() + "");
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


    @OnClick({R.id.btn_back_home, R.id.btn_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_home:
                readyGoThenKill(HomeActivity.class);
                break;
            case R.id.btn_pay:
                if (isBalance) {
                    weiXinPay(mOrder.getOrderNumber(), 1 + "");
                } else {
                    //微信支付
                    weiXinPay(mOrder.getOrderNumber(), 0 + "");
                }
                break;
        }
    }

    private void weiXinPay(String no, String useBlance) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(CreateOrderSuccessActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<WeiXinPay>> mGetDataCallNet = RetrofitService.getInstance().getOrderWeiXinPayParam(AppApplication.TOKEN, no, useBlance);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<WeiXinPay>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<WeiXinPay>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        mMaterialDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        WeiXinPay weiXinPay = response.body().getRes();
                        Bundle    bundle    = new Bundle();
                        bundle.putParcelable(WXPayEntryActivity.WX_PAY_KEY, weiXinPay);
                        readyGo(WXPayEntryActivity.class, bundle);
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
}
