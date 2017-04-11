package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.MyAddressAdapter;
import com.yqx.mamajh.adapter.SouSuoDiZhiAdapter;
import com.yqx.mamajh.adapter.LoactionCityAdapter;
import com.yqx.mamajh.bean.DeliveryAddress;
import com.yqx.mamajh.bean.LocationCity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SouSuoDiZhi;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.utils.CharacterParser;
import com.yqx.mamajh.utils.PinyinComparator;
import com.yqx.mamajh.widget.SideBar;
import com.yqx.mamajh.widget.SideBar.OnTouchingLetterChangedListener;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.baidu.mapapi.search.core.SearchResult.*;

/**
 * Created by likey on 2017/3/28.
 */

public class SearchLocationActivity extends Activity{
    private PoiSearch poiSearch;
    private PoiCitySearchOption poiCitySearchOption;
    private ImageButton ib_back;
    private TextView tv_city;
    private LinearLayout ll_city;
    private EditText et_search_location;
    private ImageButton ib_search_location;
    private LinearLayout ll_sousuodizhi;
    private ListView lv_sousuodizhi;
    private SouSuoDiZhiAdapter souSuoDiZhiAdapter;
    private String city;
    private MaterialDialog mMaterialDialog = null;
    private TextView tv_current_address;
    private ListView lv_myAddress;
    private MyAddressAdapter myAddressAdapter;
    private int whichLV;
    private String checkedAddress;
    private LinearLayout ll_changeCity;
    private SideBar sideBar;
    private TextView dialog;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private LoactionCityAdapter adapter;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    private LoactionCityAdapter locationAdapter;
    private ListView lv_location;
    private ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> brands;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        Intent intent=getIntent();
        city=intent.getStringExtra("city");
        initView();
        if (NetUtils.isNetworkConnected(this)){
            poiSearch=PoiSearch.newInstance();
            poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
            setListeners();
            loadMyAddress();
        }
    }

    private void loadMyAddress() {
        if (AppApplication.TOKEN!=null){
            mMaterialDialog = new MaterialDialog.Builder(SearchLocationActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<List<DeliveryAddress>>> call = RetrofitService.getInstance().deliveryInfoList(AppApplication.TOKEN, 1);
            call.enqueue(new Callback<NetBaseEntity<List<DeliveryAddress>>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<List<DeliveryAddress>>> response, Retrofit retrofit) {
                    if (response.body()==null){
                        return;
                    }
                    if (response.body().getStatus()==0) {
                        List<DeliveryAddress> myAddressList = response.body().getRes();
                        setMyAddressAdapter(myAddressList);
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
    }

    private void setMyAddressAdapter(List<DeliveryAddress> myAddressList) {
        myAddressAdapter=new MyAddressAdapter(SearchLocationActivity.this,myAddressList);
        lv_myAddress.setAdapter(myAddressAdapter);

        myAddressAdapter.setOnItemClickSetMyAddress(new MyAddressAdapter.OnItemClickSetMyAddress() {
            @Override
            public void OnItemClickSetMyAddress(String area,String chooseAddress,String x,String y) {
//                String[] areas=area.split(".");
//                String mycity=areas[1];
//                Toast.makeText(SearchLocationActivity.this,"mycity",Toast.LENGTH_SHORT).show();
                checkedAddress=chooseAddress;
//                whichLV=1002;
//                poiCitySearchOption= new PoiCitySearchOption().city(city).keyword(chooseAddress+"").pageNum(1).pageCapacity(10);
//                poiSearch.searchInCity(poiCitySearchOption);
                if (x==null||y==null){
                    Toast.makeText(SearchLocationActivity.this,"请重新修改此地址",Toast.LENGTH_SHORT).show();
                }else{
                    HomeFragment.y=y;
                    HomeFragment.x=x;
                    HomeFragment.location=chooseAddress;
                    finish();
                }
            }
        });
    }

    private void setListeners() {
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(ll_city,"接口获取城市列表",Snackbar.LENGTH_SHORT).show();
            }
        });
        ib_search_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(et_search_location.getText()+"").trim().equals("")){
                    whichLV=1001;
                    poiCitySearchOption= new PoiCitySearchOption().city(tv_city.getText()+"").keyword(et_search_location.getText()+"");
                    poiSearch.searchInCity(poiCitySearchOption);

//                    mMaterialDialog = new MaterialDialog.Builder(SearchLocationActivity.this)
//                            .content(R.string.loading)
//                            .cancelable(false)
//                            .progress(true, 0)
//                            .progressIndeterminateStyle(false)
//                            .show();
                }else{
                    Toast.makeText(SearchLocationActivity.this,"请输入搜索内容",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_changeCity.getVisibility()==View.VISIBLE){
                    ll_changeCity.setVisibility(View.GONE);
                }else{
                    ll_changeCity.setVisibility(View.VISIBLE);
                    ll_sousuodizhi.setVisibility(View.GONE);
                    loadLocationCity();
                }

            }
        });

        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = locationAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lv_location.setSelection(position+1);
                }
            }
        });
        et_search_location.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
//            Toast.makeText(SearchLocationActivity.this,""+s,Toast.LENGTH_SHORT).show();
            if (!s.toString().trim().equals("")){
//                Toast.makeText(SearchLocationActivity.this,s.toString().trim().charAt(0)+"",Toast.LENGTH_SHORT).show();
//                if (s.toString().trim().charAt(0)+"".equals())
                whichLV=1001;
                poiCitySearchOption= new PoiCitySearchOption().city(tv_city.getText()+"").keyword(et_search_location.getText()+"");
                poiSearch.searchInCity(poiCitySearchOption);
            }else{
                ll_sousuodizhi.setVisibility(View.GONE);
            }
        }
    };

    private void loadLocationCity() {
        mMaterialDialog = new MaterialDialog.Builder(SearchLocationActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Call<LocationCity> call=RetrofitService.getInstance().getLocationCity();
        call.enqueue(new Callback<LocationCity>() {
            @Override
            public void onResponse(Response<LocationCity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    List<LocationCity.LocationCityRes.LocationCityList> list = response.body().getRes().getList();
                    brands=new ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList>();
//                    Toast.makeText(SearchLocationActivity.this,""+list.get(0).getCityList().get(0).getName(),Toast.LENGTH_SHORT).show();
                    for (int i=0;i<list.size();i++){
                        List<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> citylist = list.get(i).getCityList();
                        for (int j=0;j<citylist.size();j++){
                            LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList brand = list.get(i).getCityList().get(j);
//                            Toast.makeText(SearchLocationActivity.this,""+list.get(i).getCityList().get(j).getName(),Toast.LENGTH_SHORT).show();
                            brands.add(brand);
                        }
                    }
                    setLocationAdapter(brands);
                    mMaterialDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mMaterialDialog.dismiss();
            }
        });
    }

    private void setLocationAdapter(ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> brands) {
        locationAdapter=new LoactionCityAdapter(SearchLocationActivity.this,brands);
        lv_location.setAdapter(locationAdapter);

        locationAdapter.setOnItemClickSetChangeCity(new LoactionCityAdapter.OnItemClickSetChangeCity() {
            @Override
            public void OnItemClickSetChangeCity(int id, String name, String x, String y) {
                tv_city.setText(name+"");
                ll_changeCity.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        ib_back=(ImageButton)findViewById(R.id.ib_back);
        tv_city=(TextView)findViewById(R.id.tv_city);
        tv_city.setText(city);
        ll_city=(LinearLayout)findViewById(R.id.ll_city);
        et_search_location=(EditText)findViewById(R.id.et_search_location);
        ib_search_location=(ImageButton)findViewById(R.id.ib_search_location);
        ll_sousuodizhi=(LinearLayout)findViewById(R.id.ll_sousuodizhi);
        lv_sousuodizhi=(ListView)findViewById(R.id.lv_sousuodizhi);
        tv_current_address=(TextView)findViewById(R.id.tv_current_address);
        tv_current_address.setText(""+HomeFragment.location);
        lv_myAddress=(ListView)findViewById(R.id.lv_myAddress);
        ll_changeCity=(LinearLayout)findViewById(R.id.ll_changeCity);
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);
        lv_location=(ListView)findViewById(R.id.lv_location);
    }

    OnGetPoiSearchResultListener onGetPoiSearchResultListener=new OnGetPoiSearchResultListener(){
        @Override
        public void onGetPoiResult(PoiResult poiResult){
            ArrayList<SouSuoDiZhi> diZhiArrayList=new ArrayList<SouSuoDiZhi>();
            if (poiResult.getAllPoi()!=null){
                switch (whichLV){
                    case 1001:
                        for (int d=0;d<poiResult.getAllPoi().size();d++){
                            if (poiResult.getAllPoi().get(d).location!=null){
                                SouSuoDiZhi diZhi = new SouSuoDiZhi(poiResult.getAllPoi().get(d).location.latitude+"",
                                        poiResult.getAllPoi().get(d).location.longitude+"",
                                        poiResult.getAllPoi().get(d).name+"",
                                        poiResult.getAllPoi().get(d).address+"");
                                diZhiArrayList.add(diZhi);
                            }
                        }
                        setSouSuoDiZhiAdapter(diZhiArrayList);
                        if ("".equals(et_search_location.getText()+"")){
                            ll_sousuodizhi.setVisibility(View.GONE);
                        }else{
                            ll_sousuodizhi.setVisibility(View.VISIBLE);
                            ll_changeCity.setVisibility(View.GONE);
                        }
                        break;
                    case 1002:
                        HomeFragment.y=poiResult.getAllPoi().get(0).location.latitude+"";
                        HomeFragment.x=poiResult.getAllPoi().get(0).location.longitude+"";
                        HomeFragment.location=checkedAddress;
                        finish();
                        break;
                }
            }else{
                Toast.makeText(SearchLocationActivity.this,"地址无效，请尝试更改",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void setSouSuoDiZhiAdapter(ArrayList<SouSuoDiZhi> diZhiArrayList) {
        souSuoDiZhiAdapter=new SouSuoDiZhiAdapter(diZhiArrayList,SearchLocationActivity.this);
        lv_sousuodizhi.setAdapter(souSuoDiZhiAdapter);

        souSuoDiZhiAdapter.setOnItemClickSetDiZhi(new SouSuoDiZhiAdapter.OnItemClickSetDiZhi() {
            @Override
            public void OnItemClickSetDiZhi(String location,String address, String x, String y) {
//                Toast.makeText(SearchLocationActivity.this,""+location+","+x+","+y,Toast.LENGTH_SHORT).show();
                HomeFragment.x=y;
                HomeFragment.y=x;
                HomeFragment.location=location;
                finish();
            }
        });
    }


}
