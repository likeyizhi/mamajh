package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.BankCard;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class WithdrawalsBankActivity extends BaseActivity {

    @BindView(R.id.list)
    ListView list;


    private List<BankCard.BankCardBean> mEntities;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_withdrawals_bank;
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
        setTitle("选择提现银行卡");
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BankCard.BankCardBean bankCard = mEntities.get(i);
                Intent                intent   = new Intent();
                Bundle                bundle   = new Bundle();
//                bundle.putSerializable("bank", bankCard);
                bundle.putInt("bankId",bankCard.getID());
                bundle.putString("bankName",bankCard.getBankName()+"");
                bundle.putString("bankNumber",bankCard.getNumber()+"");
                bundle.putString("name",bankCard.getName()+"");
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
            Call<NetBaseEntity<List<BankCard>>> mGetDataCallNet = RetrofitService.getInstance().userCenterBankCard(AppApplication.TOKEN);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<BankCard>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<BankCard>>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().get(0).getBankCard();
                        list.setAdapter(new BankCardAdapter());
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

    private class BankCardAdapter extends BaseAdapter {

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
            final BankCard.BankCardBean entity = mEntities.get(i);
            final ViewHolder            holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_bank_card, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvItemBankName.setText(entity.getBankName());
            holder.tvItemBankCardNo.setText(/*"尾号"*/"卡号" + entity.getNumber()/*.substring(entity.getNumber().length() - 4, entity.getNumber().length())
                    + " "*/ + entity.getName());
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_item_bank_name)
        TextView tvItemBankName;
        @BindView(R.id.tv_item_bank_card_no)
        TextView tvItemBankCardNo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
