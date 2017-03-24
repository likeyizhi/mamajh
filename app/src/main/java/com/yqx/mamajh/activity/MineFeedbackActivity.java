package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.api.defaults.DefaultBootstrapBrand;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.FeedbackType;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 意见反馈
 */
public class MineFeedbackActivity extends BaseActivity {

    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.et_content)
    EditText etContent;

    private List<FeedbackType> mEntities = new ArrayList<>();

    private MaterialDialog mMaterialDialog = null;

    private String type = "";

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_feedback;
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
        setTitle("意见反馈");
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

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("反馈内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(type)) {
            showToast("请选择反馈类型");
            return;
        }
        submit(content);
    }

    private void getData() {
        mMaterialDialog = new MaterialDialog.Builder(MineFeedbackActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<List<FeedbackType>>> call = RetrofitService.getInstance().feedbackTypeList(AppApplication.TOKEN);
        call.enqueue(new Callback<NetBaseEntity<List<FeedbackType>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<List<FeedbackType>>> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes();
                        gv.setAdapter(new FeedbackAdapter());
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

    private void submit(String content) {
        mMaterialDialog = new MaterialDialog.Builder(MineFeedbackActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity> call = RetrofitService.getInstance().feedbackSubmit(AppApplication.TOKEN, type, content);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        showToast("提交成功");
                        etContent.setText("");
                    }else {
                        showToast(response.body().getMes());
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

    class FeedbackAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mEntities.size();
        }

        @Override
        public Object getItem(int i) {
            return mEntities.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final FeedbackType entity = mEntities.get(i);
            final ViewHolder   holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_feedback_type, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.btnItemType.setText(entity.getName());
            if (entity.getIsCheck()) {
                holder.btnItemType.setBootstrapBrand(DefaultBootstrapBrand.DANGER);
            } else {
                holder.btnItemType.setBootstrapBrand(DefaultBootstrapBrand.SECONDARY);
            }
            holder.btnItemType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    type = entity.getName();
                    for (int i1 = 0; i1 < mEntities.size(); i1++) {
                        mEntities.get(i1).setIsCheck(false);
                    }
                    mEntities.get(i).setIsCheck(true);
                    notifyDataSetChanged();
                }
            });
            return view;
        }


    }

    static class ViewHolder {
        @BindView(R.id.btn_item_type)
        BootstrapButton btnItemType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
