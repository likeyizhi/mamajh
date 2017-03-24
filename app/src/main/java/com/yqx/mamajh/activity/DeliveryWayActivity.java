package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.yqx.mamajh.bean.DeliveryWay;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DeliveryWayActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;
    private List<DeliveryWay.DeliverylistBean> mEntities = new ArrayList<>();

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_delivery_way;
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
        setTitle("配送方式");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("delivery", mEntities.get(i).getTID());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

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
        if (NetUtils.isNetworkConnected(mContext)) {
            Call<NetBaseEntity<DeliveryWay>> mGetDataCallNet = RetrofitService.getInstance().deliveryWayList(AppApplication.TOKEN, id);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<DeliveryWay>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<DeliveryWay>> response, Retrofit retrofit) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes().getDeliverylist();
                        lv.setAdapter(new DeliveryAdapter());
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

    class DeliveryAdapter extends BaseAdapter {

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
            final DeliveryWay.DeliverylistBean entity = mEntities.get(i);
            final ViewHolder                   holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_delivery_way, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Glide.with(getApplicationContext()).load(entity.getPrice()).crossFade().into(holder.ivItemPic);
            holder.tvItemTitle.setText(entity.getTitle());
            holder.tvItemDeliveryPrice.setText(entity.getPrice() + "");
            holder.tvItemDeliveryDesc.setText(entity.getDesc());
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.iv_item_pic)
        ImageView ivItemPic;
        @BindView(R.id.iv_item_title)
        TextView  tvItemTitle;
        @BindView(R.id.iv_item_delivery_price)
        TextView  tvItemDeliveryPrice;
        @BindView(R.id.iv_item_delivery_desc)
        TextView  tvItemDeliveryDesc;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
