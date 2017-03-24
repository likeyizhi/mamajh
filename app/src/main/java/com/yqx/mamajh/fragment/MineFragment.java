package com.yqx.mamajh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.CouponActivity;
import com.yqx.mamajh.activity.ForgetPwdActivity;
import com.yqx.mamajh.activity.HelpCenterIndexActivity;
import com.yqx.mamajh.activity.HomeActivity;
import com.yqx.mamajh.activity.LoginActivity;
import com.yqx.mamajh.activity.MineAboutActivity;
import com.yqx.mamajh.activity.MineAccountBalanceActivity;
import com.yqx.mamajh.activity.MineAccountIntegralActivity;
import com.yqx.mamajh.activity.MineAddressActivity;
import com.yqx.mamajh.activity.MineAssetActivity;
import com.yqx.mamajh.activity.MineCollectActivity;
import com.yqx.mamajh.activity.MineFeedbackActivity;
import com.yqx.mamajh.activity.MineInfoActivity;
import com.yqx.mamajh.activity.MineOrderActivity;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.MemeberIndex;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/11/6.
 * 我的
 */

public class MineFragment extends BaseFragment {

    public static final int RC_MINE_INFO = 1;

    @BindView(R.id.iv_mine_avatar)
    CircleImageView ivMineAvatar;
    @BindView(R.id.tv_mine_name)
    TextView        tvMineName;
    @BindView(R.id.tv_mine_chenghao)
    TextView        tvMineChenghao;
    @BindView(R.id.lay_mine_address)
    LinearLayout    layMineAddress;
    @BindView(R.id.lay_mine_point)
    LinearLayout    layMinePoint;
    @BindView(R.id.lay_mine_balance)
    LinearLayout    layMineBalance;
    @BindView(R.id.lay_mine_check_all_order)
    LinearLayout    layMineCheckAllOrder;
    @BindView(R.id.lay_mine_wait_payment)
    LinearLayout    layMineWaitPayment;
    @BindView(R.id.lay_mine_wait_deliver)
    LinearLayout    layMineWaitDeliver;
    @BindView(R.id.lay_mine_wait_receipt)
    LinearLayout    layMineWaitReceipt;
    @BindView(R.id.lay_mine_wait_evaluate)
    LinearLayout    layMineWaitEvaluate;
    @BindView(R.id.lay_mine_check_all_asset)
    LinearLayout    layMineCheckAllAsset;
    @BindView(R.id.lay_mine_account_balance)
    LinearLayout    layMineAccountBalance;
    @BindView(R.id.lay_mine_account_point)
    LinearLayout    layMineAccountPoint;
    @BindView(R.id.lay_mine_account_coupon)
    LinearLayout    layMineAccountCoupon;
    @BindView(R.id.lay_mine_account_safe)
    LinearLayout    layMineAccountSafe;
    @BindView(R.id.lay_mine_collect)
    LinearLayout    layMineCollect;
    @BindView(R.id.lay_mine_personal_data)
    LinearLayout    layMinePersonalData;
    @BindView(R.id.lay_mine_problem)
    LinearLayout    layMineProblem;
    @BindView(R.id.lay_mine_feedback)
    LinearLayout    layMineFeedback;
    @BindView(R.id.lay_mine_contact)
    LinearLayout    layMineContact;
    @BindView(R.id.lay_mine_about)
    LinearLayout    layMineAbout;
    @BindView(R.id.sv_mine_content)
    ScrollView      svMineContent;
    @BindView(R.id.tv_mine_Score)
    TextView        tvMineScore;
    @BindView(R.id.tv_mine_mainprice)
    TextView        tvMineMainprice;
    @BindView(R.id.badge_mine_wait_payment)
    BGABadgeView    badgeMineWaitPayment;
    @BindView(R.id.badge_mine_wait_deliver)
    BGABadgeView    badgeMineWaitDeliver;
    @BindView(R.id.badge_mine_wait_receipt)
    BGABadgeView    badgeMineWaitReceipt;
    @BindView(R.id.badge_mine_wait_evaluate)
    BGABadgeView    badgeMineWaitEvaluate;


    public static boolean LOGOUT = false;

    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
        getData(AppApplication.TOKEN);
        initView();
//        Toast.makeText(getActivity(),"vis",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onResume() {
        if (AppApplication.memeberIndex == null) {
            getData(AppApplication.TOKEN);
        } else {
            initView();
        }
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_MINE_INFO:
                HomeActivity.getInstance().tabWidget.setClickItem(0);
                HomeActivity.getInstance().viewPager.setCurrentItem(0, false);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @OnClick({R.id.iv_mine_avatar, R.id.tv_mine_name, R.id.tv_mine_chenghao, R.id.lay_mine_address, R.id.lay_mine_point, R.id.lay_mine_balance, R.id.lay_mine_check_all_order, R.id.lay_mine_wait_payment, R.id.lay_mine_wait_deliver, R.id.lay_mine_wait_receipt, R.id.lay_mine_wait_evaluate, R.id.lay_mine_check_all_asset, R.id.lay_mine_account_balance, R.id.lay_mine_account_point, R.id.lay_mine_account_coupon, R.id.lay_mine_account_safe, R.id.lay_mine_collect, R.id.lay_mine_personal_data, R.id.lay_mine_problem, R.id.lay_mine_feedback, R.id.lay_mine_contact, R.id.lay_mine_about, R.id.sv_mine_content})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_mine_avatar:
                readyGoForResult(MineInfoActivity.class, RC_MINE_INFO);
                break;
            case R.id.tv_mine_name:
                readyGo(MineInfoActivity.class);
                break;
            case R.id.tv_mine_chenghao:
                readyGo(MineInfoActivity.class);
                break;
            case R.id.lay_mine_address:
                readyGo(MineAddressActivity.class);
                break;
            case R.id.lay_mine_point:
                readyGo(MineAccountIntegralActivity.class);
                break;
            case R.id.lay_mine_balance:
                readyGo(MineAccountBalanceActivity.class);
                break;
            case R.id.lay_mine_check_all_order:
                bundle = new Bundle();
                bundle.putInt(MineOrderActivity.TITLE_RES, R.string.all_order);
                readyGo(MineOrderActivity.class, bundle);
                break;
            case R.id.lay_mine_wait_payment:
                bundle = new Bundle();
                bundle.putInt(MineOrderActivity.TITLE_RES, R.string.wait_payment);
                readyGo(MineOrderActivity.class, bundle);
                break;
            case R.id.lay_mine_wait_deliver:
                bundle = new Bundle();
                bundle.putInt(MineOrderActivity.TITLE_RES, R.string.wait_deliver);
                readyGo(MineOrderActivity.class, bundle);
                break;
            case R.id.lay_mine_wait_receipt:
                bundle = new Bundle();
                bundle.putInt(MineOrderActivity.TITLE_RES, R.string.wait_receipt);
                readyGo(MineOrderActivity.class, bundle);
                break;
            case R.id.lay_mine_wait_evaluate:
                bundle = new Bundle();
                bundle.putInt(MineOrderActivity.TITLE_RES, R.string.wait_comment);
                readyGo(MineOrderActivity.class, bundle);
                break;
            case R.id.lay_mine_check_all_asset:
                readyGo(MineAssetActivity.class);
                break;
            case R.id.lay_mine_account_balance:
                readyGo(MineAccountBalanceActivity.class);
                break;
            case R.id.lay_mine_account_point:
                readyGo(MineAccountIntegralActivity.class);
                break;
            case R.id.lay_mine_account_coupon:
                readyGo(CouponActivity.class);
                break;
            case R.id.lay_mine_account_safe:
                readyGo(ForgetPwdActivity.class);
                break;
            case R.id.lay_mine_collect:
                readyGo(MineCollectActivity.class);
                break;
            case R.id.lay_mine_personal_data:
                readyGo(MineInfoActivity.class);
                break;
            case R.id.lay_mine_problem:
                readyGo(HelpCenterIndexActivity.class);
                break;
            case R.id.lay_mine_feedback:
                readyGo(MineFeedbackActivity.class);
                break;
            case R.id.lay_mine_contact:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4006656950"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.lay_mine_about:
                readyGo(MineAboutActivity.class);
                break;
        }
    }

    private void initView() {
        Glide.with(getContext())
                .load(AppApplication.memeberIndex.getHeadPhoto())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(ivMineAvatar);
        tvMineName.setText(AppApplication.memeberIndex.getNickName());
        tvMineMainprice.setText(AppApplication.memeberIndex.getMainPrice() + "");
        tvMineScore.setText(AppApplication.memeberIndex.getScore() + "");
        if (AppApplication.memeberIndex.getDfk() > 0) {
            badgeMineWaitPayment.showTextBadge(AppApplication.memeberIndex.getDfk() + "");
            badgeMineWaitPayment.setVisibility(View.VISIBLE);
        } else {
            badgeMineWaitPayment.setVisibility(View.GONE);
        }
        if (AppApplication.memeberIndex.getDfh() > 0) {
            badgeMineWaitDeliver.showTextBadge(AppApplication.memeberIndex.getDfh() + "");
            badgeMineWaitDeliver.setVisibility(View.VISIBLE);
        } else {
            badgeMineWaitDeliver.setVisibility(View.GONE);
        }
        if (AppApplication.memeberIndex.getDsh() > 0) {
            badgeMineWaitReceipt.showTextBadge(AppApplication.memeberIndex.getDsh() + "");
            badgeMineWaitReceipt.setVisibility(View.VISIBLE);
        } else {
            badgeMineWaitReceipt.setVisibility(View.GONE);
        }
        if (AppApplication.memeberIndex.getDpj() > 0) {
            badgeMineWaitEvaluate.showTextBadge(AppApplication.memeberIndex.getDpj() + "");
            badgeMineWaitEvaluate.setVisibility(View.VISIBLE);
        } else {
            badgeMineWaitEvaluate.setVisibility(View.GONE);
        }
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
                        initView();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", 4);
                        readyGoForResult(LoginActivity.class, HomeActivity.RC_LOGIN, bundle);
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


}
