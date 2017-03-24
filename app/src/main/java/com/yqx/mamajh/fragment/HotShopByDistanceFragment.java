package com.yqx.mamajh.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.obsessive.library.utils.CommonUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.activity.StoreActivity;
import com.yqx.mamajh.adapter.HotShopAdapter;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopList;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/9.
 */

public class HotShopByDistanceFragment extends android.support.v4.app.Fragment {
    private View view;
    private ListView lvHotShop;
    private  int p;
    private List<ShopList.ShoplistBean> shoplist;
    private HotShopAdapter adapter;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_hotshop,null);
        p=1;
        initView();
        loadData();
        setListeners();
        return view;
    }

    private void setListeners() {
        lvHotShop.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isBottom;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        //Log.i("info", "SCROLL_STATE_FLING");
                        break;
                    case SCROLL_STATE_IDLE:
                        //Log.i("info", "SCROLL_STATE_IDLE");
                        //如果已经到底了  加载下一页
                        if (isBottom) {
                            p=p+1;
                            Call<NetBaseEntity<ShopList>> call=RetrofitService.getInstance().getHotShopByD(HomeFragment.x,HomeFragment.y,"a",""+p,10);
                            call.enqueue(new Callback<NetBaseEntity<ShopList>>() {
                                @Override
                                public void onResponse(Response<NetBaseEntity<ShopList>> response, Retrofit retrofit) {
                                    if (response.body()==null){
                                        return;
                                    }
                                    if(response.body().getStatus()==0){
                                        List<ShopList.ShoplistBean> addShoplist = response.body().getRes().getShoplist();
                                        if(!addShoplist.isEmpty()){
                                            shoplist.addAll(addShoplist);
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            Toast.makeText(getActivity(),"没有数据",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                        }
                        break;

                    default:
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

    private void loadData() {
        Call<NetBaseEntity<ShopList>> call=RetrofitService.getInstance().getHotShopByD(HomeFragment.x,HomeFragment.y,"a",""+p,10);
        call.enqueue(new Callback<NetBaseEntity<ShopList>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ShopList>> response, Retrofit retrofit) {
                if(response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    shoplist = response.body().getRes().getShoplist();
                    setAdapter(shoplist);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    private void setAdapter(List<ShopList.ShoplistBean> shoplist) {
        adapter = new HotShopAdapter(getActivity(), shoplist);
        lvHotShop.setAdapter(adapter);
        adapter.setOnItemClickSetHS(new HotShopAdapter.OnItemClickSetHS() {
            @Override
            public void OnItemClickSetHS(String id) {
                Intent intent=new Intent(getActivity(),StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ShopActivity.IDBUNDLE, id+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        lvHotShop=(ListView)view.findViewById(R.id.lv_hotShop);
    }
}
