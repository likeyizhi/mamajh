package com.yqx.mamajh.dbcity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.yqx.mamajh.R;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/16.
 */

public class CitySelectActivity extends Activity{
    private ListView lv_city;
    private int cityId;
    private CityAdapter cAdapter;
    private ArrayList<String> cityNames;
    private ArrayList<String> cityIds;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        cityId=0;
        loadData();
        initView();
    }

    private void loadData() {
        Call<City> call= RetrofitService.getInstance().getAreaDictionary(cityId);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Response<City> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    setAdapter(response.body().getRes());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setAdapter(List<City.CityRes> cities) {
        cAdapter=new CityAdapter(cities, CitySelectActivity.this);
        lv_city.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
        setListeners();
    }


    private void setListeners() {
        cAdapter.setOnItemClickSetCity(new CityAdapter.OnItemClickSetCity() {
            @Override
            public void OnItemClickSetCity(int id, String name) {
                Toast.makeText(CitySelectActivity.this,""+id,Toast.LENGTH_SHORT).show();
                cityNames.add(name+"");
                cityIds.add(id+"");
                cityId=id;
                if (cityIds.size()!=3){
                    loadData();
                }else{
                    Toast.makeText(CitySelectActivity.this,"city="+cityNames+"id="+cityIds,Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void initView() {
        lv_city=(ListView)findViewById(R.id.lv_city);
    }
}
