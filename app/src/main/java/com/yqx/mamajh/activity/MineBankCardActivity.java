package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.BankCard;
import com.yqx.mamajh.bean.DictionaryBank;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.UserCenterBankCard;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MineBankCardActivity extends BaseActivity {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.lv)
    ListView lv;

    private List<DictionaryBank.DictionaryBankRes.DictionaryBankList> bankCards;
    private List<UserCenterBankCard.UserCenterBankCardRes.UserCenterBankCards> mEntities;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_bank_card;
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
        setTitle("我的银行卡");
        getBankCardList();
        getData();
    }

    private void getBankCardList() {
        Call<DictionaryBank> call=RetrofitService.getInstance().dictionaryBankList();
        call.enqueue(new Callback<DictionaryBank>() {
            @Override
            public void onResponse(Response<DictionaryBank> response, Retrofit retrofit) {
                if (response==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    bankCards=response.body().getRes().getList();
                }
            }

            @Override
            public void onFailure(Throwable t) {

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


    @OnClick({R.id.lay_mingxi, R.id.lay_tixian, R.id.lay_add_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_mingxi:
                readyGo(MineAccountBalanceActivity.class);
                break;
            case R.id.lay_tixian:
                readyGo(WithdrawalsActivity.class);
                break;
            case R.id.lay_add_card:
                readyGo(MineAddBankCardActivity.class);
                break;
        }
    }

    private void getData() {
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<UserCenterBankCard> mGetDataCallNet = RetrofitService.getInstance().userCenterBankCardNew(AppApplication.TOKEN);
            mGetDataCallNet.enqueue(new Callback<UserCenterBankCard>() {
                @Override
                public void onResponse(Response<UserCenterBankCard> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().get(0).getBankCard();
                        tvMoney.setText(response.body().getAccount()+"");
                        lv.setAdapter(new BankCardAdapter());
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
//            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<List<BankCard>>>() {
//                @Override
//                public void onResponse(Response<NetBaseEntity<List<BankCard>>> response, Retrofit retrofit) {
//                    if (response.body() == null) {
//                        return;
//                    }
//                    if (response.body().getStatus() == 0) {
//                        mEntities = response.body().getRes().get(0).getBankCard();
//                        lv.setAdapter(new BankCardAdapter());
//                    }
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                }
//            });
//        } else {
//            toggleNetworkError(true, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //错误后的点击屏幕的处理
//                }
//            });
//        }
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
            UserCenterBankCard.UserCenterBankCardRes.UserCenterBankCards entity = mEntities.get(i);
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
            Glide.with(getApplicationContext()).load(entity.getImg()).error(R.mipmap.mmjhicon512).into(holder.ivBank);
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.tv_item_bank_name)
        TextView tvItemBankName;
        @BindView(R.id.tv_item_bank_card_no)
        TextView tvItemBankCardNo;
        @BindView(R.id.iv_bank)
        ImageView ivBank;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    protected void onRestart() {
        getData();
        super.onRestart();
    }
}
