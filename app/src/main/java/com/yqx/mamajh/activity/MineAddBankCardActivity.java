package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.rey.material.widget.CheckBox;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 添加银行卡
 */
public class MineAddBankCardActivity extends BaseActivity {

    @BindView(R.id.tv_card_name)
    EditText tvCardName;
    @BindView(R.id.tv_card_no)
    EditText tvCardNo;
    @BindView(R.id.spinner_card_bank)
    Spinner  spinnerCardBank;
    @BindView(R.id.cb_check)
    CheckBox cbCheck;

    private String bank = "";

    private MaterialDialog mMaterialDialog = null;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_add_bank_card;
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
        setTitle("添加银行卡");
        final String[] banks = getResources().getStringArray(R.array.bank);
        spinnerCardBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank = banks[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String cardName = tvCardName.getText().toString().trim();
        String cardNo   = tvCardNo.getText().toString().trim();
        if (TextUtils.isEmpty(cardName)) {
            showToast("持卡人不能为空");
            return;
        }
        if (TextUtils.isEmpty(cardNo)) {
            showToast("卡号不能为空");
            return;
        }
        if (TextUtils.isEmpty(bank) || TextUtils.equals(bank, "===请选择===")) {
            showToast("请选择银行");
            return;
        }
        if (!cbCheck.isChecked()) {
            showToast("请同意用户协议");
            return;
        }

        submit(cardName, cardNo);
    }

    private void submit(String cardName, String cardNo) {
        mMaterialDialog = new MaterialDialog.Builder(MineAddBankCardActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity> call = RetrofitService.getInstance().userCenterBankCardAdd(AppApplication.TOKEN, cardName, cardNo, bank);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    if (response.body().getMes() != null) {
                        showToast(response.body().getMes());
                    }
                } else {
                    showToast("服务器错误");
                }
                mMaterialDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                mMaterialDialog.dismiss();
                t.printStackTrace();
            }
        });
    }
}
