package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SendRegMessage;
import com.yqx.mamajh.bean.Token;
import com.yqx.mamajh.network.RetrofitService;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_register_name)
    EditText        etRegisterName;
    @BindView(R.id.et_register_code)
    EditText        etRegisterCode;
    @BindView(R.id.et_register_pwd)
    EditText        etRegisterPwd;
    @BindView(R.id.et_register_pwd2)
    EditText        etRegisterPwd2;
    @BindView(R.id.btn_register_code)
    BootstrapButton btnRegisterCode;

    private MaterialDialog mMaterialDialog = null;
    private int            obj             = 0;
    private Timer          timer           = null;
    private int            time            = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
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
        setTitle(getString(R.string.register));
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

    @OnClick({R.id.btn_register_code, R.id.btn_register_submit, R.id.tv_register_go_login})
    public void onClick(View view) {
        String phone;
        switch (view.getId()) {
            case R.id.btn_register_code:
                phone = etRegisterName.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号不能为空");
                    break;
                }
                if (phone.length() != 11) {
                    showToast("请输入正确的手机号");
                    break;
                }
                getCode(phone);
                break;
            case R.id.btn_register_submit:
                phone = etRegisterName.getText().toString().trim();
                String code = etRegisterCode.getText().toString().trim();
                String pwd = etRegisterPwd.getText().toString().trim();
                String pwd2 = etRegisterPwd2.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号不能为空");
                    break;
                }
                if (phone.length() != 11) {
                    showToast("请输入正确的手机号");
                    break;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast("验证码不能为空");
                    break;
                }
                if (TextUtils.isEmpty(pwd)) {
                    showToast("密码不能为空");
                    break;
                }
                if (TextUtils.isEmpty(pwd2)) {
                    showToast("确认密码不能为空");
                    break;
                }
                if (pwd2.length() > 16 || pwd2.length() < 6) {
                    showToast("请确认密码的长度");
                    break;
                }
                if (!TextUtils.equals(pwd, pwd2)) {
                    showToast("两次输入的密码不相同");
                    break;
                }
                if (obj == 0) {
                    showToast("你确定有发送验证码吗？");
                    break;
                }
                register(phone, pwd, code, obj);
                break;
            case R.id.tv_register_go_login:
                readyGo(LoginActivity.class);
                finish();
                break;
        }
    }

    private void getCode(String phone) {
        mMaterialDialog = new MaterialDialog.Builder(RegisterActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<SendRegMessage>> call = RetrofitService.getInstance().sendRegMessage(phone, 1);
        call.enqueue(new Callback<NetBaseEntity<SendRegMessage>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<SendRegMessage>> response, Retrofit retrofit) {
                if (response.body().getMes() != null && response.body().getStatus() == 0) {
                    obj = response.body().getRes().getObj();
                    showToast(response.body().getMes());
                    timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    time--;
                                    btnRegisterCode.setClickable(false);
                                    btnRegisterCode.setBootstrapBrand(DefaultBootstrapBrand.REGULAR);
                                    btnRegisterCode.setText(time + "s后可重新发送");
                                    if (time < 0) {
                                        time = 60;
                                        timer.cancel();
                                        btnRegisterCode.setClickable(true);
                                        btnRegisterCode.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
                                        btnRegisterCode.setText("获取验证码");
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(timerTask, 1000, 1000);
                } else {
                    showToast(response.body().getMes());
                }
                mMaterialDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                showToast(t.getMessage());
                mMaterialDialog.dismiss();
            }
        });
    }

    private void register(final String phone, String pwd, String code, int obj) {
        mMaterialDialog = new MaterialDialog.Builder(RegisterActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<Token>> call = RetrofitService.getInstance().registered(phone, pwd, code, obj);
        call.enqueue(new Callback<NetBaseEntity<Token>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<Token>> response, Retrofit retrofit) {
                if (response.body().getMes() != null && response.body().getStatus() == 0) {
                    showToast("注册成功");
//                    Intent intent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("phone", phone);
//                    intent.putExtras(bundle);
//                    setResult(RESULT_OK, intent);
                    readyGo(LoginActivity.class);
                    finish();
                } else {
                    showToast(response.body().getMes());
                }
                mMaterialDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                showToast(t.getMessage());
                mMaterialDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        readyGo(LoginActivity.class);
        finish();
        super.onBackPressed();
    }
}
