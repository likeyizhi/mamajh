package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.MemeberIndex;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.Token;
import com.yqx.mamajh.fragment.MineFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.AppConstant;
import com.yqx.mamajh.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.yqx.mamajh.utils.AppConstant.SP_PHONE;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;

    private MaterialDialog mMaterialDialog = null;

    public final static int RC_REGISTER = 2;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
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
        setTitle(getString(R.string.login));
//        etLoginName.setText(SPUtils.getString(getApplicationContext(), SP_PHONE, ""));
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
    public void onBackPressed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("index", 1);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.onBackPressed();

    }

    @OnClick({R.id.btn_login_submit, R.id.tv_login_register, R.id.tv_login_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_submit:
                String name = etLoginName.getText().toString().trim();
                String pwd = etLoginPwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("帐号不能为空");
                    break;
                }
                if (TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    break;
                }
                login(name, pwd);
                break;
            case R.id.tv_login_register:
                readyGo(RegisterActivity.class);
                finish();
//                readyGoForResult(RegisterActivity.class, RC_REGISTER);
                break;
            case R.id.tv_login_forget:
                Bundle bu=new Bundle();
                bu.putString("changeType","修改账户密码");
                readyGo(ForgetPwdActivity.class, bu);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_REGISTER:
                if (data != null) {
                    String phone = data.getExtras().getString("phone");
                    if (!TextUtils.isEmpty(phone)) {
                        etLoginName.setText(phone);
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void login(final String name, final String pwd) {
        mMaterialDialog = new MaterialDialog.Builder(LoginActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<Token>> call = RetrofitService.getInstance().login(name, pwd);
        call.enqueue(new Callback<NetBaseEntity<Token>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<Token>> response, Retrofit retrofit) {
                if (response.body().getMes() != null && response.body().getStatus() == 0) {
                    AppApplication.TOKEN = response.body().getRes().getToken();
                    SPUtils.putString(getApplicationContext(), AppConstant.SP_TOKEN, AppApplication.TOKEN);
                    SPUtils.putString(getApplicationContext(), AppConstant.SP_PHONE, name);
                    getData(AppApplication.TOKEN);
                } else {
                    showToast(response.body().getMes());
                    mMaterialDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showToast(t.getMessage());
                mMaterialDialog.dismiss();
            }
        });
    }


    private void getData(String token) {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<MemeberIndex>> mGetDataCallNet = RetrofitService.getInstance().memeberIndex(token);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<MemeberIndex>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<MemeberIndex>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        AppApplication.memeberIndex = response.body().getRes();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", getIntent().getExtras().getInt("index"));
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        MineFragment.LOGOUT = false;
                        finish();
                    }
                    mMaterialDialog.dismiss();
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
}
