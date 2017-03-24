package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.base.BaseAppCompatActivity;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.MyGvAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.EShopProductListBean;
import com.yqx.mamajh.bean.HotGoodsEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ProdectItemEntity;
import com.yqx.mamajh.bean.ShopInfoEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/6.
 */

public class GoodsListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener {

    @BindView(R.id.fragment_rank_list_swipe_layout)
    XSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fragment_rank_list_list_view)
    LoadMoreListView mListView;

    @BindView(R.id.drawerlayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rl_filter)
    RelativeLayout filter;
    @BindView(R.id.gv_goods)
    GridView gv_goods;


    private String id;
    private ListViewDataAdapter<ProdectItemEntity> mListViewAdapter;
    private int page = 1;
    private MultiItemRowListAdapter mMultiItemRowListAdapter;
    private String title;
    private MyGvAdapter EAdapter;
    private String cid="0";
    private int p=1;
    private String size="10";
    private List<HotGoodsEntity.ResEntity.ProlistEntity> EShopList;
    @Override
    protected void getBundleExtras(Bundle extras) {

        if(extras != null){
            id = extras.getString("id");
            if (id==null){
                id="";
            }
            title=extras.getString("title");
            if (title==null){
                title="商品列表";
            }
            cid=extras.getString("cid");
            if (cid==null){
                cid="";
            }

        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        setTitle(title+"");

        loadData(false);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        //配置刷新颜色
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mListViewAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ProdectItemEntity>() {
            @Override
            public ViewHolderBase<ProdectItemEntity> createViewHolder(int position) {
                return new ViewHolderBase<ProdectItemEntity>() {

                    ImageView img;
                    TextView about;
                    TextView money;
                    TextView num;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {

                        View convertView = layoutInflater.inflate(R.layout.item_goods, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_about);
                        money = (TextView) convertView.findViewById(R.id.tv_money);
                        num = (TextView) convertView.findViewById(R.id.tv_num);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, ProdectItemEntity itemData) {

                        Glide.with(mContext).load(itemData.getImg()).into(img);
                        about.setText(itemData.getName());
                        money.setText(itemData.getMinPrice() + "~" + itemData.getMaxPrice());
                        num.setText(itemData.getShopCount() == null? "0" : itemData.getShopCount() + "家门店在售");

                    }
                };
            }
        });

        mMultiItemRowListAdapter = new MultiItemRowListAdapter(mContext, mListViewAdapter, 2, 0);
        mListView.setAdapter(mMultiItemRowListAdapter);
        mListView.setOnLoadMoreListener(this);
        mListView.setOnItemClickListener(this);
        gv_goods.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isBottom;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        //Log.i("info", "SCROLL_STATE_FLING");
                        break;
                    case SCROLL_STATE_IDLE:
                        if (isBottom) {
                            p++;
                            loadData(true);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount){
                    //Log.i("info", "到底了....");
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        });
        showLoading("", true);

        Call<NetBaseEntity<ArrayList<ProdectItemEntity>>> call =  RetrofitService.getInstance().getProdectList(id, "", page + "", "20", "", "", "", "", "");
        call.enqueue(new Callback<NetBaseEntity<ArrayList<ProdectItemEntity>>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ArrayList<ProdectItemEntity>>> response, Retrofit retrofit) {
                NetBaseEntity<ArrayList<ProdectItemEntity>> netBaseEntity = response.body();
                hideLoading();

                if(netBaseEntity == null){
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
                    ArrayList<ProdectItemEntity> prodectItemEntities = netBaseEntity.getRes();

                    mListViewAdapter.getDataList().addAll(prodectItemEntities);
                    mListViewAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();

            }
        });
//        call.enqueue(new Callback<NetBaseEntity<ShopInfoEntity>>() {
//            @Override
//            public void onResponse(Response<NetBaseEntity<ShopInfoEntity>> response, Retrofit retrofit) {
//                NetBaseEntity<ShopInfoEntity> shopInfoEntityNetBaseEntity = response.body();
//
//                if(shopInfoEntityNetBaseEntity == null){
//                    ResponseBody responseBody = response.errorBody();
//                    if (responseBody!=null) {
//                        try {
////                            loadedListener.onException(responseBody.string());
//                            showError(responseBody.string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        showError("(responseBody = null)极有可能是json解析失败");
//                    }
//                }else{
//                    ShopInfoEntity shopInfoEntity = shopInfoEntityNetBaseEntity.getRes();
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });

    }

    private void loadData(final boolean isLoadMore) {
        Call<HotGoodsEntity> EShopCall=RetrofitService.getInstance().getHotGoods(id+"",cid,p,size);
        EShopCall.enqueue(new Callback<HotGoodsEntity>() {
            @Override
            public void onResponse(Response<HotGoodsEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if(response.body().getStatus()==0){
                    if (isLoadMore){
                        List<HotGoodsEntity.ResEntity.ProlistEntity> EShopListAdd=response.body().getRes().getProlist();
                        if (!EShopListAdd.isEmpty()){
                            EShopList.addAll(EShopListAdd);
                            EAdapter.notifyDataSetChanged();
                            Toast.makeText(GoodsListActivity.this,"正在加载...",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(GoodsListActivity.this,"就这些产品啦...",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        EShopList=response.body().getRes().getProlist();
                        setGvAdapter(EShopList);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setGvAdapter(List<HotGoodsEntity.ResEntity.ProlistEntity> eShopList) {
        EAdapter = new MyGvAdapter(eShopList, GoodsListActivity.this);
        gv_goods.setAdapter(EAdapter);
        EAdapter.setOnItemClickSetGL(new MyGvAdapter.OnItemClickSetGL() {
            @Override
            public void OnItemClickSetGL(int proId) {
//                Toast.makeText(GoodsListActivity.this,""+proId,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(GoodsListActivity.this,ProductInfoActivity.class);
                intent.putExtra("_id",proId);
                startActivity(intent);
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

    @Override
    public void onRefresh() {
        Toast.makeText(GoodsListActivity.this,"onRefresh",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        Toast.makeText(GoodsListActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
        loadData(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
