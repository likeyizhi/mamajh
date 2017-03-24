package com.yqx.mamajh.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.adapter.MyGvAdapter;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.HotGoodsEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.LoadMoreListView;

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

public class HotGoodsFragment extends BaseFragment implements LoadMoreListView.OnLoadMoreListener {


    private TextView more;
    @BindView(R.id.fragment_hot_goods)
    LoadMoreListView loadMoreListView;
    @BindView(R.id.ll_root)
    LinearLayout root;
    @BindView(R.id.gv_reXiaoPro)
    GridView gv_reXiaoPro;

    private ListViewDataAdapter<HotGoodsEntity.ResEntity.ProlistEntity> listViewDataAdapter;

    private String id;
    private int mCurrentPage;
    private MyGvAdapter gvAdapter;
    private List<HotGoodsEntity.ResEntity.ProlistEntity> proList;

    public void scrollHeader(int scrollY) {
        root.setTranslationY(-scrollY);
    }
    private String cid="0";
    private int p=1;
    private String size="10";

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return loadMoreListView;
    }

    @Override
    protected void initViewsAndEvents() {
//
//        mPosition = 0;
//        mListView = loadMoreListView;
//        root.setMinimumHeight(mScreenHeight*2);

        Bundle bundle = getArguments();
        if(bundle != null ){
            id = bundle.getString(ShopActivity.IDBUNDLE);
            loadMyGvData(false);
            gv_reXiaoPro.setOnScrollListener(new AbsListView.OnScrollListener() {
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
                                loadMyGvData(true);
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
        }

        loadMoreListView.setCanLoadMore(true);

        listViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<HotGoodsEntity.ResEntity.ProlistEntity>() {
            @Override
            public ViewHolderBase<HotGoodsEntity.ResEntity.ProlistEntity> createViewHolder(int position) {
                return new ViewHolderBase<HotGoodsEntity.ResEntity.ProlistEntity>() {

                    ImageView img;
                    TextView ablout;
                    TextView price;
                    TextView priceOld;
                    ImageView cart;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {

                        View convertView = layoutInflater.inflate(R.layout.item_hot_goods, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        ablout = (TextView) convertView.findViewById(R.id.tv_about);
                        price = (TextView) convertView.findViewById(R.id.tv_price);
                        priceOld = (TextView) convertView.findViewById(R.id.tv_price_old);
                        cart = (ImageView) convertView.findViewById(R.id.iv_cart);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, HotGoodsEntity.ResEntity.ProlistEntity itemData) {

                        if(itemData != null){
                            Glide.with(mContext).load(itemData.getImg()).into(img);
                            ablout.setText(itemData.getName());
                            price.setText(itemData.getPrice() + "");
                            priceOld.getPaint().setAntiAlias(true);//抗锯齿
                            priceOld.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线
                            cart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 加入购物车
                                }
                            });

                        }

                    }
                };
            }
        });

//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View placeHolderView = inflater.inflate(R.layout.header_placeholder2, loadMoreListView, false);
//        more = (TextView) placeHolderView.findViewById(R.id.tv_hot_more);
//        loadMoreListView.addHeaderView(placeHolderView);

        MultiItemRowListAdapter mMultiItemRowListAdapter = new MultiItemRowListAdapter(mContext, listViewDataAdapter, 2, 0);
        loadMoreListView.setAdapter(mMultiItemRowListAdapter);
        loadMoreListView.setOnLoadMoreListener(this);
//        setListViewOnScrollListener();


        loadData(mCurrentPage);

    }

    private void loadMyGvData(final boolean isLoadMore) {
        Call<HotGoodsEntity> EShopCall=RetrofitService.getInstance().getHotGoods(id+"",cid,p,size);
        EShopCall.enqueue(new Callback<HotGoodsEntity>() {
            @Override
            public void onResponse(Response<HotGoodsEntity> response, Retrofit retrofit) {
                if (response.body()==null){
//                    Toast.makeText(getActivity(),"arr",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body().getStatus()==0){
                    if (isLoadMore){
                        List<HotGoodsEntity.ResEntity.ProlistEntity> proListAdd = response.body().getRes().getProlist();
                        if (!proListAdd.isEmpty()){
                            proList.addAll(proListAdd);
                            gvAdapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"正在加载...",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(),"就这些产品啦...",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        proList=response.body().getRes().getProlist();
                        setGvAdapter(response.body().getRes().getProlist());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setGvAdapter(List<HotGoodsEntity.ResEntity.ProlistEntity> prolist) {
        gvAdapter = new MyGvAdapter(prolist, getActivity());
        gv_reXiaoPro.setAdapter(gvAdapter);
        gvAdapter.setOnItemClickSetGL(new MyGvAdapter.OnItemClickSetGL() {
            @Override
            public void OnItemClickSetGL(int proId) {
                Intent intent=new Intent(getActivity(), ProductInfoActivity.class);
                intent.putExtra("_id",proId);
                startActivity(intent);
            }
        });

    }

    private void loadData(final int page) {

        if(page == 1){
            showLoading("", true);
        }
        Call<HotGoodsEntity> call = RetrofitService.getInstance().getHotGoods(id, "", page, "20");
        call.enqueue(new Callback<HotGoodsEntity>() {
            @Override
            public void onResponse(Response<HotGoodsEntity> response, Retrofit retrofit) {
                HotGoodsEntity hotGoodsEntity = response.body();

                if(page == 1){
                    hideLoading();
                }
                if(hotGoodsEntity == null){
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
                    if(hotGoodsEntity.getStatus() != 0){
                        showError(hotGoodsEntity.getMes());
                    }else{
                        List<HotGoodsEntity.ResEntity.ProlistEntity> prodectItemEntities = hotGoodsEntity.getRes().getProlist();

                        listViewDataAdapter.getDataList().addAll(prodectItemEntities);
                        listViewDataAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onFailure(Throwable t) {

                if(page == 1){
                    hideLoading();
                }

            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_hot_goods;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        loadData(mCurrentPage);
    }
}
