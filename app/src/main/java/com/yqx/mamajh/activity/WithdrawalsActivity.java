package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.BankCard;
import com.yqx.mamajh.bean.UserCenterWithdrawal;
import com.yqx.mamajh.bean.WithdrawalsSave;
import com.yqx.mamajh.network.RetrofitService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 提现
 */
public class WithdrawalsActivity extends BaseActivity {

    @BindView(R.id.tv_balance)
    TextView        tvBalance;
    @BindView(R.id.tv_withdrawals_ing_price)
    TextView        tvWithdrawalsIngPrice;
    @BindView(R.id.iv_bank_img)
    ImageView       ivBankImg;
    @BindView(R.id.tv_bank_name)
    TextView        tvBankName;
    @BindView(R.id.tv_bank_card_no)
    TextView        tvBankCardNo;
    @BindView(R.id.et_withdrawals_price)
    EditText        etWithdrawalsPrice;
    @BindView(R.id.btn_submit)
    BootstrapButton btnSubmit;
    @BindView(R.id.tv_tip)
    TextView        tvTip;

    public static final int RC_SELECT_BANK = 100;

    private UserCenterWithdrawal mWithdrawal;

    private MaterialDialog mMaterialDialog = null;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawals;
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
        setTitle("申请提现");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_SELECT_BANK:
                if (data != null) {
                    BankCard.BankCardBean bank = (BankCard.BankCardBean) data.getExtras().getSerializable("bank");
                    mWithdrawal.setBankName(bank.getBankName());
                    mWithdrawal.setBankNum(bank.getNumber());
                    mWithdrawal.setUserName(bank.getName());
                    setData();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.tv_log, R.id.lay_bank, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_log:
                readyGo(WithdrawalsLogActivity.class);
                break;
            case R.id.lay_bank:
                readyGoForResult(WithdrawalsBankActivity.class, RC_SELECT_BANK);
                break;
            case R.id.btn_submit:
                String m = etWithdrawalsPrice.getText().toString();
                if (TextUtils.isEmpty(m)) {
                    showToast("请输入提现金额");
                    return;
                }
                double money = Double.parseDouble(m);
                if (money > mWithdrawal.getLimit()) {
                    showToast("请输入限额类金额");
                    return;
                }
                getSave(1, m, mWithdrawal.getBankNum());
                break;
        }
    }

    private void getData() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(WithdrawalsActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<UserCenterWithdrawal> mGetDataCallNet = RetrofitService.getInstance().userCenterWithdrawal(AppApplication.TOKEN, 0);
            mGetDataCallNet.enqueue(new Callback<UserCenterWithdrawal>() {
                @Override
                public void onResponse(Response<UserCenterWithdrawal> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mWithdrawal = response.body();
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

    private void setData() {
        tvBalance.setText(mWithdrawal.getBalance() + "");
        tvBankName.setText(mWithdrawal.getBankName());
        String s = mWithdrawal.getBankNum();
        tvBankCardNo.setText("尾号" + s.substring(s.length() - 4, s.length()) + "  " + mWithdrawal.getUserName());
        tvTip.setText("每日限额：" + mWithdrawal.getLimit() + "  今日还可以转出" + mWithdrawal.getCanOut());
    }

    private void getSave(int type, String money, String id) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(WithdrawalsActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<WithdrawalsSave> mGetDataCallNet = RetrofitService.getInstance().userCenterWithdrawal(AppApplication.TOKEN, type, money, id);
            mGetDataCallNet.enqueue(new Callback<WithdrawalsSave>() {
                @Override
                public void onResponse(Response<WithdrawalsSave> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        showToast("服务器出错");
                        mMaterialDialog.dismiss();
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        readyGo(WithdrawalsSuccessActivity.class);
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    showToast("服务器出错");
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
