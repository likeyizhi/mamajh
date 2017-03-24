package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.HelpCenterContent;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 常用帮助 内容
 */
public class HelpCenterContentActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;

    private MaterialDialog mMaterialDialog = null;
    private HelpCenterContent mEntity;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_help_center_content;
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
        setTitle("常见问题");
        int id = getIntent().getExtras().getInt("id");
        getData(id);
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

    private void getData(int id) {
        mMaterialDialog = new MaterialDialog.Builder(HelpCenterContentActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<HelpCenterContent>> call = RetrofitService.getInstance().helpCenterContent(id);
        call.enqueue(new Callback<NetBaseEntity<HelpCenterContent>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<HelpCenterContent>> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        mEntity = response.body().getRes();
                        tvTitle.setText(mEntity.getTitle());
                        RichText.fromHtml(mEntity.getContent()).into(tvContent);
                    }
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
