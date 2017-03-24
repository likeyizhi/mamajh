package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.MemberInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.fragment.MineFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.AppConstant;
import com.yqx.mamajh.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MineInfoActivity extends BaseActivity {

    @BindView(R.id.iv_info_avatar)
    CircleImageView ivInfoAvatar;
    @BindView(R.id.lay_info_avatar)
    LinearLayout    layInfoAvatar;
    @BindView(R.id.tv_info_jhid)
    TextView        tvInfoJhid;
    @BindView(R.id.tv_info_nickname)
    TextView        tvInfoNickname;
    @BindView(R.id.lay_info_nickname)
    LinearLayout    layInfoNickname;
    @BindView(R.id.tv_info_sex)
    TextView        tvInfoSex;
    @BindView(R.id.lay_info_sex)
    LinearLayout    layInfoSex;
    @BindView(R.id.tv_info_phone)
    TextView        tvInfoPhone;
    @BindView(R.id.lay_info_phone)
    LinearLayout    layInfoPhone;
    @BindView(R.id.lay_info_address)
    LinearLayout    layInfoAddress;
    @BindView(R.id.lay_info_account_safe)
    LinearLayout    layInfoAccountSafe;

    private MaterialDialog mMaterialDialog = null;

    public MemberInfo mMemberInfo;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_info;
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
        setTitle("个人资料");
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

    @OnClick({R.id.lay_info_avatar, R.id.lay_info_nickname, R.id.lay_info_sex, R.id.lay_info_address, R.id.lay_info_account_safe, R.id.btn_info_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_info_avatar:
                showToast("修改头像");
                break;
            case R.id.lay_info_nickname:
                mMaterialDialog = new MaterialDialog.Builder(this)
                        .title("修改昵称")
                        .content("请输入昵称")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .inputRange(2, 16)
                        .neutralText(R.string.cancel)
                        .positiveText(R.string.btn_ok)
                        .input("昵称", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                showToast(input.toString());
                            }
                        }).show();
                break;
            case R.id.lay_info_sex:
                mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                        .title("修改性别")
                        .neutralText(R.string.cancel)
                        .neutralColorRes(R.color.gray_text)
                        .negativeText("女")
                        .negativeColorRes(R.color.red_500)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast("女");
                            }
                        })
                        .positiveText("男")
                        .positiveColorRes(R.color.blue_500)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast("男");
                            }
                        })
                        .show();
                break;
            case R.id.lay_info_address:
                readyGo(MineAddressActivity.class);
                break;
            case R.id.lay_info_account_safe:
                readyGo(ForgetPwdActivity.class);
                break;
            case R.id.btn_info_loginout:
                mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                        .title("退出")
                        .content("是否退出登陆")
                        .negativeText(R.string.cancel)
                        .negativeColorRes(R.color.gray_text)
                        .positiveText(R.string.btn_ok)
                        .positiveColorRes(R.color.colorPrimary)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                AppApplication.TOKEN = null;
                                AppApplication.memeberIndex = null;
                                SPUtils.putString(getApplicationContext(), AppConstant.SP_TOKEN, "");
                                MineFragment.LOGOUT = true;
                                finish();
                            }
                        })
                        .show();

                break;

        }
    }

    private void getData(String token) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(MineInfoActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<MemberInfo>> mGetDataCallNet = RetrofitService.getInstance().memberInfo(token);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<MemberInfo>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<MemberInfo>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mMemberInfo = response.body().getRes();
                        Glide.with(getApplicationContext())
                                .load(mMemberInfo.getFace())
                                .error(R.mipmap.ic_launcher)
                                .placeholder(R.mipmap.ic_launcher)
                                .crossFade()
                                .into(ivInfoAvatar);

                        tvInfoJhid.setText(mMemberInfo.getAccount() + "");
                        tvInfoNickname.setText(mMemberInfo.getNickname());
                        tvInfoPhone.setText(mMemberInfo.getMobile());
                        if (mMemberInfo.getSex() == 1) {
                            tvInfoSex.setText("男");
                        } else {
                            tvInfoSex.setText("女");

                        }
                    } else {
                        showToast(response.body().getMes());
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
