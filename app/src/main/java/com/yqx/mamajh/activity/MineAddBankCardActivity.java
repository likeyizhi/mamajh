package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.rey.material.widget.CheckBox;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.DictionaryBank;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

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
    @BindView(R.id.tv_one_bank)
    TextView tvOneBank;

    private String bank = "";

    private MaterialDialog mMaterialDialog = null;
    private int bankId;
    private List<DictionaryBank.DictionaryBankRes.DictionaryBankList> bankList;
    private String[] banks;

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
        getBankCardList();
//        final String[] banks = getResources().getStringArray(R.array.bank);
    }

    private void getBankCardList() {
        Call<DictionaryBank> call=RetrofitService.getInstance().dictionaryBankList();
        call.enqueue(new Callback<DictionaryBank>() {
            @Override
            public void onResponse(Response<DictionaryBank> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    bankList=response.body().getRes().getList();
                    banks = new String[bankList.size()];
                    for (int i=0;i<bankList.size();i++){
                        banks[i]=bankList.get(i).getName();
                    }
                    spinnerCardBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            bank = banks[i];
                            bankId=i;
                            tvOneBank.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    tvCardNo.addTextChangedListener(watcher);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            for (int i=0;i<bankList.size();i++){
                if (bankList.get(i).getBankNo().equals((tvCardNo.getText()+""))){
//                    Toast.makeText(MineAddBankCardActivity.this,""+bankList.get(i).getName(),Toast.LENGTH_SHORT).show();
                    tvOneBank.setText(bankList.get(i).getName());
                    tvOneBank.setVisibility(View.VISIBLE);
                    bankId=bankList.get(i).getId();
                    bank=bankList.get(i).getName();
                    return;
                }
            }
        }
    };

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
        if (TextUtils.isEmpty(bank)) {
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
        Call<NetBaseEntity> call = RetrofitService.getInstance().userCenterBankCardAdd(AppApplication.TOKEN, cardName, cardNo, bankId+"");
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    if (response.body().getMes() != null) {
                        showToast(response.body().getMes());
                        finish();
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
