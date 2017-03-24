package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.rey.material.widget.CheckBox;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.DeliveryAddress;
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

public class MineAddressActivity extends BaseActivity {

    @BindView(R.id.lay_null)
    LinearLayout layNull;
    @BindView(R.id.list)
    ListView     list;

    private List<DeliveryAddress> mEntities = new ArrayList<>();
    private AddressAdapter mAdapter;

    private static final int            RC_EDITADDRESS  = 3;
    private              MaterialDialog mMaterialDialog = null;

    private boolean mSelect = false;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_address;
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
        setTitle(mContext.getString(R.string.manager_delivery_address));
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setBackgroundDrawable(getResources().getDrawable(R.mipmap.address_xj));
        if (getIntent().getExtras() == null) {
            mSelect = false;
        } else {
            mSelect = getIntent().getExtras().getBoolean("select");
        }

        list.setAdapter(mAdapter = new AddressAdapter());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSelect) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", mEntities.get(i));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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


    @OnClick({R.id.ib_rightbtn, R.id.lay_null})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_rightbtn:
                readyGoForResult(MineEditAddressActivity.class, RC_EDITADDRESS);
                break;
            case R.id.lay_null:
                break;
        }
    }

    private void getData() {
        mMaterialDialog = new MaterialDialog.Builder(MineAddressActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<List<DeliveryAddress>>> call = RetrofitService.getInstance().deliveryInfoList(AppApplication.TOKEN, 1);
        call.enqueue(new Callback<NetBaseEntity<List<DeliveryAddress>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<List<DeliveryAddress>>> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes();
                        mAdapter.notifyDataSetChanged();
                        layNull.setVisibility(View.GONE);
                    } else {
                        layNull.setVisibility(View.VISIBLE);
                    }
                } else {
                    showToast(response.body().getMes());
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

    /**
     * 设置默认地址
     */
    private void setDefaultAddress(String id) {
        mMaterialDialog = new MaterialDialog.Builder(MineAddressActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity> call = RetrofitService.getInstance().setDefaultAddress(AppApplication.TOKEN, id);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body().getStatus() == 0) {
                    showToast("默认地址设置成功");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_EDITADDRESS:
//                showToast("RC_EDITADDRESS");
                getData();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class AddressAdapter extends BaseAdapter {

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
            final DeliveryAddress entity = mEntities.get(i);
            final ViewHolder      holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_address_list, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tvItemAddressConsignee.setText("收货人：" + entity.getName());
            holder.tvItemAddressPhone.setText(entity.getPhone());
            holder.tvItemAddressInfo.setText("收货地址：" + entity.getArea() + entity.getAddress());
            if (TextUtils.equals(entity.getMoren(), "1")) {
                holder.cbItemAddressSelect.setVisibility(View.VISIBLE);
                holder.cbItemAddressSelect.setChecked(true);
            } else {
                holder.cbItemAddressSelect.setVisibility(View.VISIBLE);
                holder.cbItemAddressSelect.setChecked(false);
            }

            holder.cbItemAddressSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.cbItemAddressSelect.isChecked()) {
                        int size = mEntities.size();
                        for (int j = 0; j < size; j++) {
                            mEntities.get(j).setIsCheck(false);
                            mEntities.get(j).setMoren("");
                        }
                        mEntities.get(i).setIsCheck(true);
                        mEntities.get(i).setMoren("1");
                        setDefaultAddress(entity.getId() + "");
                    } else {
                        mEntities.get(i).setIsCheck(true);
                        mEntities.get(i).setMoren("1");
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            holder.btnItemAddressEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", entity.getId());
//                    bundle.putSerializable(MineEditAddressActivity.KEY_ENTITY, entity);
                    readyGoForResult(MineEditAddressActivity.class, RC_EDITADDRESS, bundle);
                }
            });
            holder.btnItemAddressDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    entity.getId()
                    Call<NetBaseEntity> call=RetrofitService.getInstance().deleteDeliveryInfo(AppApplication.TOKEN,entity.getId()+"");
                    call.enqueue(new Callback<NetBaseEntity>() {
                        @Override
                        public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                            mEntities.remove(i);
                            mAdapter.notifyDataSetChanged();
                            showToast("删除成功");
                            if (response.body()!=null){
                                return;
                            }
                            if (response.body().getStatus()==0){

                            }
                        }
                        @Override
                        public void onFailure(Throwable t) {
                        }
                    });

                }
            });
            return view;
        }


    }

    static class ViewHolder {
        @BindView(R.id.tv_item_address_consignee)
        TextView        tvItemAddressConsignee;
        @BindView(R.id.tv_item_address_phone)
        TextView        tvItemAddressPhone;
        @BindView(R.id.tv_item_address_info)
        TextView        tvItemAddressInfo;
        @BindView(R.id.cb_item_address_select)
        CheckBox        cbItemAddressSelect;
        @BindView(R.id.btn_item_address_edit)
        BootstrapButton btnItemAddressEdit;
        @BindView(R.id.btn_item_address_del)
        BootstrapButton btnItemAddressDel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
