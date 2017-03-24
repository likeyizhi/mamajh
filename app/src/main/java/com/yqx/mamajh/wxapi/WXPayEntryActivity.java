package com.yqx.mamajh.wxapi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.WeiXinPay;

import butterknife.BindView;
import butterknife.OnClick;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    public static final String WX_PAY_KEY = "weixinpay";

    @BindView(R.id.tv_hint)
    TextView tvHint;

    private IWXAPI api;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wx_entry;
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
        setTitle("微信支付");
        WeiXinPay weiXinPay = getIntent().getExtras().getParcelable(WX_PAY_KEY);
        if (weiXinPay == null) {
            finish();
            return;
        }
        if (TextUtils.isEmpty(weiXinPay.getAppid())) {
            finish();
            return;
        }
        api = WXAPIFactory.createWXAPI(this, weiXinPay.getAppid(), false);
        api.handleIntent(getIntent(), this);

        PayReq payReq = new PayReq();
        payReq.appId = weiXinPay.getAppid();
        payReq.partnerId = weiXinPay.getPartnerid();
        payReq.prepayId = weiXinPay.getPrepayid();
        payReq.packageValue = weiXinPay.getPackageX();
        payReq.nonceStr = weiXinPay.getNoncestr();
        payReq.timeStamp = weiXinPay.getTimestamp();
        payReq.sign = weiXinPay.getSign();
        // 调用api接口发送数据到微信
        boolean b = api.sendReq(payReq);
        if (!b) {
            tvHint.setText("微信启动失败");
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                //完成支付
                tvHint.setText("支付完成");
                finish();
                break;
            default:
                tvHint.setText("支付失败");
                finish();
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                tvHint.setText("支付成功");
                break;
            case BaseResp.ErrCode.ERR_COMM:
                tvHint.setText("支付失败");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                tvHint.setText("支付被取消");
                break;
            default:
                showToast("支付失败");
                break;
        }
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

}