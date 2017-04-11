package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.UserCenterAccount;
import com.yqx.mamajh.network.RetrofitService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 我的资产
 */
public class MineAssetActivity extends BaseActivity {

    @BindView(R.id.tv_asset_price)
    TextView tvAssetPrice;
    @BindView(R.id.tv_asset_coupon_count)
    TextView tvAssetCouponCount;
    @BindView(R.id.tv_asset_bank_card_count)
    TextView tvAssetBankCardCount;
    @BindView(R.id.tv_asset_withdrawals)
    TextView tvAssetWithdrawals;
    @BindView(R.id.tv_asset_score)
    TextView tvAssetScore;
    @BindView(R.id.tv_asset_score2)
    TextView tvAssetScore2;
    private UserCenterAccount mAccount;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_asset;
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
        setTitle("我的资产");
        getData(AppApplication.TOKEN);
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


    private void getData(String token) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<UserCenterAccount>> mGetDataCallNet = RetrofitService.getInstance().userCenterAccount(token);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<UserCenterAccount>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<UserCenterAccount>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mAccount = response.body().getRes();
                        tvAssetPrice.setText(mAccount.getMainPrice() + "");
                        tvAssetCouponCount.setText(mAccount.getVouchersCount() + "");
                        tvAssetBankCardCount.setText(mAccount.getCardCount() + "");
                        tvAssetWithdrawals.setText(mAccount.getWithdrawals() + "");
                        tvAssetScore.setText(mAccount.getScore() + "");
                        tvAssetScore2.setText(mAccount.getScore2() + "");
                    }
                }

                @Override
                public void onFailure(Throwable t) {
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


    @OnClick({R.id.lay_price, R.id.lay_coupon, R.id.lay_bank_card, R.id.lay_withdrawals, R.id.lay_score, R.id.lay_set_payment_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_price:
                readyGo(MineAccountBalanceActivity.class);
                break;
            case R.id.lay_coupon:
                readyGo(CouponActivity.class);
                break;
            case R.id.lay_bank_card:
                readyGo(MineBankCardActivity.class);
                break;
            case R.id.lay_withdrawals:

               readyGo(WithdrawalsActivity.class);
                break;
            case R.id.lay_score:
                readyGo(MineAccountIntegralActivity.class);
                break;
            case R.id.lay_set_payment_pwd:
                Bundle bu=new Bundle();
                bu.putString("changeType","修改支付密码");
                readyGo(ForgetPwdActivity.class, bu);
                break;
        }
    }

    @Override
    protected void onRestart() {
        getData(AppApplication.TOKEN);
        super.onRestart();
    }
}
