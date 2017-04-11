package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.SouSuoDiZhiAdapter;
import com.yqx.mamajh.bean.SouSuoDiZhi;
import com.yqx.mamajh.fragment.HomeFragment;

import java.util.ArrayList;

/**
 * Created by likey on 2017/4/10.
 */

public class AddAddressNoMapActivity extends Activity{
    private TextView tvCity;
    private ImageButton ibBack;
    private EditText etSearchLocation;
    private String currentCity;
    private PoiSearch poiSearch;
    private SouSuoDiZhiAdapter souSuoDiZhiAdapter;
    private PoiCitySearchOption poiCitySearchOption;

    private ListView lvNoMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_nomap);
        initView();
        if (NetUtils.isNetworkConnected(this)){
            poiSearch= PoiSearch.newInstance();
            poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
            setListeners();
        }
    }

    private void setListeners() {
        etSearchLocation.addTextChangedListener(watcher);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private TextWatcher watcher = new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().trim().equals("")){
                poiCitySearchOption= new PoiCitySearchOption().city(tvCity.getText()+"").keyword(etSearchLocation.getText()+"");
                poiSearch.searchInCity(poiCitySearchOption);
            }
        }
    };
    OnGetPoiSearchResultListener onGetPoiSearchResultListener=new OnGetPoiSearchResultListener(){

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            ArrayList<SouSuoDiZhi> diZhiArrayList=new ArrayList<SouSuoDiZhi>();
            if (poiResult.getAllPoi()!=null){
                for (int d=0;d<poiResult.getAllPoi().size();d++){
                    if (poiResult.getAllPoi().get(d).location!=null){
                        SouSuoDiZhi diZhi = new SouSuoDiZhi(poiResult.getAllPoi().get(d).location.latitude+"",
                                poiResult.getAllPoi().get(d).location.longitude+"",
                                poiResult.getAllPoi().get(d).name+"",
                                poiResult.getAllPoi().get(d).address+"");
                        diZhiArrayList.add(diZhi);
                    }
                }
            }
            setSouSuoDiZhiAdapter(diZhiArrayList);
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };
    private void setSouSuoDiZhiAdapter(ArrayList<SouSuoDiZhi> diZhiArrayList) {
        souSuoDiZhiAdapter=new SouSuoDiZhiAdapter(diZhiArrayList,AddAddressNoMapActivity.this);
        lvNoMap.setAdapter(souSuoDiZhiAdapter);

        souSuoDiZhiAdapter.setOnItemClickSetDiZhi(new SouSuoDiZhiAdapter.OnItemClickSetDiZhi() {
            @Override
            public void OnItemClickSetDiZhi(String location,String address, String x, String y) {
                Intent intent=new Intent();
                intent.putExtra("_location", location);
                intent.putExtra("_address", address);
                intent.putExtra("_X", y);
                intent.putExtra("_Y", x);
                setResult(1111,intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void initView() {
        tvCity=(TextView)findViewById(R.id.tv_city);
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        etSearchLocation=(EditText)findViewById(R.id.et_search_location);
        lvNoMap=(ListView)findViewById(R.id.lv_noMap);
        Intent intent=getIntent();
        currentCity=intent.getStringExtra("currentCity")+"";
        tvCity.setText(currentCity);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
