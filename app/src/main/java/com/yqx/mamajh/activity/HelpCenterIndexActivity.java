package com.yqx.mamajh.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.HelpCenterIndex;
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

/**
 * 常见问题
 */
public class HelpCenterIndexActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;

    private List<HelpCenterIndex> mEntities = new ArrayList<>();

    private MaterialDialog mMaterialDialog = null;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_help_center_index;
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
        mMaterialDialog = new MaterialDialog.Builder(HelpCenterIndexActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<NetBaseEntity<List<HelpCenterIndex>>> call = RetrofitService.getInstance().helpCenterIndex();
        call.enqueue(new Callback<NetBaseEntity<List<HelpCenterIndex>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<List<HelpCenterIndex>>> response, Retrofit retrofit) {
                if (response.body().getMes() != null) {
                    if (response.body().getStatus() == 0) {
                        mEntities = response.body().getRes();
                        for (int i = 0; i < mEntities.size(); i++) {
                            final HelpCenterIndex entity = mEntities.get(i);
                            switch (entity.getTitle()) {
                                case "购物指南":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_a);
                                    break;

                                case "订单百事通":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_b);
                                    break;

                                case "配送方式":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_c);
                                    break;

                                case "支付流程":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_d);
                                    break;

                                case "售后服务":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_e);
                                    break;

                                case "账户及会员":
                                    mEntities.get(i).setImgRes(R.mipmap.cjwt_f);
                                    break;

                            }
                        }
                        lv.setAdapter(new HelpAdapter());
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


    class HelpAdapter extends BaseAdapter {

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
            final HelpCenterIndex                      entity = mEntities.get(i);
            final List<HelpCenterIndex.HelpCenterList> list   = entity.getList();
            final ViewHolder                           holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_help_center_index, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (entity.getImgRes() != 0) {
                Glide.with(mContext).load(entity.getImgRes()).crossFade().into(holder.ivItemImg);
            }
            holder.tv1.setText(list.get(0).getTitle());
            holder.tv2.setText(list.get(1).getTitle());
            holder.tv3.setText(list.get(2).getTitle());
            holder.tv4.setText(list.get(3).getTitle());

            holder.ivItemImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("cid", entity.getId());
                    readyGo(HelpCenterListActivity.class, bundle);
                }
            });
            holder.tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(0).getId());
                    readyGo(HelpCenterContentActivity.class, bundle);
                }
            });
            holder.tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(1).getId());
                    readyGo(HelpCenterContentActivity.class, bundle);
                }
            });
            holder.tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(2).getId());
                    readyGo(HelpCenterContentActivity.class, bundle);
                }
            });
            holder.tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", list.get(3).getId());
                    readyGo(HelpCenterContentActivity.class, bundle);
                }
            });
            return view;
        }

    }

    static class ViewHolder {
        @BindView(R.id.iv_item_img)
        ImageView ivItemImg;
        @BindView(R.id.tv_1)
        TextView  tv1;
        @BindView(R.id.tv_3)
        TextView  tv3;
        @BindView(R.id.tv_2)
        TextView  tv2;
        @BindView(R.id.tv_4)
        TextView  tv4;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
