package com.yqx.mamajh.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.ShopCategoryEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/9.
 */

public class ShopClassifyActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout root;
    @BindView(R.id.fragment_shop_classify)
    ListView listView;
    @BindView(R.id.iv_more)
    ImageView moreInfo;

    private String id;

    @Override
    protected View getLoadingTargetView() {
        return root;
    }

    @Override
    protected void initViewsAndEvents() {

        setTitle("热门分类");

        showLoading("", true);
        Call<ShopCategoryEntity> call = RetrofitService.getInstance().getShopClassify(id);

        call.enqueue(new Callback<ShopCategoryEntity>() {
            @Override
            public void onResponse(Response<ShopCategoryEntity> response, Retrofit retrofit) {
                ShopCategoryEntity shopCategoryEntity = response.body();
                hideLoading();
                if(shopCategoryEntity == null){
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody!=null) {
                        try {
//                            loadedListener.onException(responseBody.string());
                            showError(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showError("(responseBody = null)极有可能是json解析失败");
                    }
                }else{
                    if(shopCategoryEntity.getStatus() != 0){
                        showError(shopCategoryEntity.getMes());
                    }else{
                        List<ShopCategoryEntity.ResEntity.CatelistEntity> prodectItemEntities = shopCategoryEntity.getRes().getCatelist();

                        ListViewAdapter listViewAdapter = new ListViewAdapter(prodectItemEntities, mContext);
                        listView.setAdapter(listViewAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();
                showError(t.getMessage());
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
    protected void getBundleExtras(Bundle extras) {
        if(extras != null){
            id = extras.getString(ShopActivity.IDBUNDLE);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shop_classify;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

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


    public class ListViewAdapter extends BaseAdapter {
        private List<ShopCategoryEntity.ResEntity.CatelistEntity> mList;
        private Context mContext;

        public ListViewAdapter(List<ShopCategoryEntity.ResEntity.CatelistEntity> mList, Context mContext) {
            super();
            this.mList = mList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            if (mList == null) {
                return 0;
            } else {
                return this.mList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (mList == null) {
                return null;
            } else {
                return this.mList.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ListViewAdapter.ViewHolder();
                convertView = LayoutInflater.from
                        (this.mContext).inflate(R.layout.item_lv_classify, null, false);
                holder.title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.gridView = (GridView) convertView.findViewById(R.id.gv_classify);
                convertView.setTag(holder);
            } else {
                holder = (ListViewAdapter.ViewHolder) convertView.getTag();
            }


            if (this.mList != null) {
                if (holder.title != null) {
                    holder.title.setText(mList.get(position).getName());
                }
                if (holder.gridView != null) {
                    List<ShopCategoryEntity.ResEntity.CatelistEntity.ChildListEntity> childListEntities = this.mList.get(position).getChildList();
                    GridViewAdapter gridViewAdapter=new GridViewAdapter(mContext, childListEntities);
                    holder.gridView.setAdapter(gridViewAdapter);
                }

            }

            return convertView;

        }


        private class ViewHolder {
            TextView title;
            GridView gridView;
        }

    }

    private class GridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<ShopCategoryEntity.ResEntity.CatelistEntity.ChildListEntity> mList;

        public GridViewAdapter(Context mContext,List<ShopCategoryEntity.ResEntity.CatelistEntity.ChildListEntity> mList) {
            super();
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public int getCount() {
            if (mList == null) {
                return 0;
            } else {
                return this.mList.size();
            }
        }

        @Override
        public Object getItem(int position) {
            if (mList == null) {
                return null;
            } else {
                return this.mList.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            GridViewAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new GridViewAdapter.ViewHolder();
                convertView = LayoutInflater.from
                        (this.mContext).inflate(R.layout.item_gv_classify, null, false);
                holder.item = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);

            } else {
                holder = (GridViewAdapter.ViewHolder) convertView.getTag();
            }


            if (this.mList != null) {
                final ShopCategoryEntity.ResEntity.CatelistEntity.ChildListEntity childListEntity = this.mList.get(position);
                if (holder.item != null) {
                    holder.item.setText(childListEntity.getName());
                    holder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO
//                            Toast.makeText(mContext, "第"+(position+1)+"个" + childListEntity.getID(), Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString(ShopActivity.IDBUNDLE, id);
                            bundle.putString("title",childListEntity.getName());
                            bundle.putString("cid",childListEntity.getID()+"");
                            readyGo(GoodsListActivity.class, bundle);
                        }
                    });
                }
            }

            return convertView;

        }


        private class ViewHolder {
            TextView item;
        }

    }
}
