package com.yqx.mamajh.interactor.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopList;
import com.yqx.mamajh.bean.ShopPositionList;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.interactor.HomeListInteractor;
import com.yqx.mamajh.listener.BaseMultiLoadedListener;
import com.yqx.mamajh.network.RetrofitService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/12/12.
 */

public class HomeListInteractorImpl implements HomeListInteractor {

    private Call<NetBaseEntity<HomeInfoEntity>> call;
    private Call<NetBaseEntity<ShopPositionList>> shopCall;
    private BaseMultiLoadedListener<NetBaseEntity<HomeInfoEntity>> loadedListener = null;
    private List<ShopList.ShoplistBean> shopList;
    private List<ShopList.ShoplistBean> shopListAdd;

    public HomeListInteractorImpl(Call<NetBaseEntity<HomeInfoEntity>> call, BaseMultiLoadedListener<NetBaseEntity<HomeInfoEntity>> loadedListener) {
        this.call = call;
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(String requestTag, final int event_tag, int page, final String x, final String y) {
        int size=5;
        if (page!=1){
            size=page*5;
        }
        Call<NetBaseEntity<ShopList>> hotCall=RetrofitService.getInstance().getHotShopByD(HomeFragment.x,HomeFragment.y,"a",1+"",size);
        hotCall.enqueue(new Callback<NetBaseEntity<ShopList>>() {
            @Override
            public void onResponse(Response<NetBaseEntity<ShopList>> response, Retrofit retrofit) {
                if (response==null){
                    return;
                }
                if (response.body().getStatus()==0){
//                    switch (event_tag){
//                        case Constants.EVENT_LOAD_MORE_DATA:
//                            Toast.makeText(AppApplication.getInstance() ,"loadMore",Toast.LENGTH_SHORT).show();
//                            shopList = response.body().getRes().getShoplist();
////                            shopList.addAll(shopListAdd);
//                            loadHomeInfo();
//                            break;
//                        case Constants.EVENT_REFRESH_DATA:
////                            Toast.makeText(AppApplication.getInstance() ,"Refresh",Toast.LENGTH_SHORT).show();
//                            break;
//                    }
                    shopList = response.body().getRes().getShoplist();
                    loadHomeInfo();
                }

            }

            private void loadHomeInfo(){
                call = RetrofitService.getInstance().getHomeInfo(x, y);
                if(call == null){
                    return;
                }
                call.enqueue(new Callback<NetBaseEntity<HomeInfoEntity>>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity<HomeInfoEntity>> response, Retrofit retrofit) {

                        NetBaseEntity<HomeInfoEntity> adListEntityNetBaseEntity = response.body();
                        adListEntityNetBaseEntity.getRes().getHotshop().clear();
                        Log.i("","6666666=="+shopList.size());
                        for(int i=0;i<shopList.size();i++){
                            HomeInfoEntity.HotshopEntity hotshop = new HomeInfoEntity.HotshopEntity();
                            hotshop.setID(shopList.get(i).getID());
                            hotshop.setName(shopList.get(i).getName());
                            hotshop.setPhone(shopList.get(i).getPhone());
                            hotshop.setLogo(shopList.get(i).getLogo());
                            hotshop.setX(shopList.get(i).getX());
                            hotshop.setY(shopList.get(i).getY());
                            hotshop.setDistance(shopList.get(i).getDistance());
                            hotshop.setTime(shopList.get(i).getTime());
                            hotshop.setMark(shopList.get(i).getMark());
                            hotshop.setSaleTotal(shopList.get(i).getSaleTotal());
                            hotshop.setSareTotal(shopList.get(i).getSareTotal());
                            hotshop.setStartingFee(shopList.get(i).getStartingFee());
//                            Log.i("","StartingFee="+shopList.get(i).getStartingFee()+"+"+hotshop.getStartingFee());
                            hotshop.setDistributionFee(shopList.get(i).getDistributionFee());
                            hotshop.setIsCollect(shopList.get(i).getIsCollect());
                            hotshop.setAddress(shopList.get(i).getAddress());
                            adListEntityNetBaseEntity.getRes().getHotshop().add(hotshop);
                        }

                        /*"ID": "1",
                        "Name": "北京旗舰店",
                        "Phone": "13811118888",
                        "Logo": "http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.png",
                        "X": "125.317786",
                        "Y": "43.829412",
                        "Distance": "0米",
                        "Time": "48",
                        "Mark": "1",
                        "SaleTotal": "7",
                        "SareTotal": "0",
                        "StartingFee": "50",
                        "DistributionFee": "5",
                        "IsCollect": "0",
                        "Address": "1"*/
//                hotshop.setID(1+"");
//                hotshop.setName("123");
//                hotshop.setPhone("1100");
//                hotshop.setLogo("http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.png");
//                hotshop.setX("125");
//                hotshop.setY("43");
//                hotshop.setDistance("666");
//                hotshop.setTime("50");
//                hotshop.setMark("5");
//                hotshop.setSaleTotal("1");
//                hotshop.setSareTotal("1");
//                hotshop.setStartingFee("10");
//                hotshop.setDistributionFee("50");
//                hotshop.setIsCollect("0");
//                hotshop.setAddress("22");
//
//                adListEntityNetBaseEntity.getRes().getHotshop().add(hotshop);

                        if(adListEntityNetBaseEntity == null){
                            ResponseBody responseBody = response.errorBody();
                            if (responseBody!=null) {
                                try {
                                    loadedListener.onException(responseBody.string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                loadedListener.onException("(responseBody = null)极有可能是json解析失败");
                            }
                        }else{
                            String data = getFromAssets("homeInfo.json"); // 测试数据
                            NetBaseEntity<HomeInfoEntity> netBaseEntity = new Gson().fromJson(data, new TypeToken<NetBaseEntity<HomeInfoEntity>>(){}.getType());

                            // 200
//                    loadedListener.onSuccess(event_tag, netBaseEntity); //参数0这里没用，主要是刷新根上拉加载的时候判断是上拉还是下拉刷新

                            loadedListener.onSuccess(event_tag, adListEntityNetBaseEntity); //参数0这里没用，主要是刷新根上拉加载的时候判断是上拉还是下拉刷新
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        loadedListener.onException(t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public String getFromAssets(String fileName){
        String Result="";
        try {
            InputStreamReader inputReader = new InputStreamReader(AppApplication.getInstance().getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";

            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result;
    }
}
