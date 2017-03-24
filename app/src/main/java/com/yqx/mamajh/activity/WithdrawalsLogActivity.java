package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.WithdrawalLog;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WithdrawalsLogActivity extends BaseActivity {


    @BindView(R.id.list)
    ListView list;
    private MaterialDialog mMaterialDialog = null;

    private List<WithdrawalLog.ResBean.WithdrawalListBean> mEntities = new ArrayList<>();

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawals_log;
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
        setTitle("提现记录");
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

    private void getData() {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(WithdrawalsLogActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<WithdrawalLog> mGetDataCallNet = RetrofitService.getInstance().userCenterWithdrawalList(AppApplication.TOKEN);
            mGetDataCallNet.enqueue(new Callback<WithdrawalLog>() {
                @Override
                public void onResponse(Response<WithdrawalLog> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().get(0).getWithdrawalList();
                        list.setAdapter(new WithdrawalLogAdapter());
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
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

    private class WithdrawalLogAdapter extends BaseAdapter {

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
            final WithdrawalLog.ResBean.WithdrawalListBean entity = mEntities.get(i);
            final ViewHolder                               holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_bank_withdrawal_log, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String no = entity.getNumber().substring(entity.getNumber().length() - 4, entity.getNumber().length());
            holder.tvItemBankName.setText(entity.getBankName() + " 尾号" + no);
            holder.tvItemTime.setText(entity.getTime());
            holder.tvItemPrice.setText(entity.getMoney());
            return view;
        }


    }

    static class ViewHolder {
        @BindView(R.id.tv_item_bank_name)
        TextView tvItemBankName;
        @BindView(R.id.tv_item_time)
        TextView tvItemTime;
        @BindView(R.id.tv_item_price)
        TextView tvItemPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
