package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.StoreSearch;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/28.
 */

public class StoreSearchActivity extends Activity{
    private ImageButton ib_back;
    private EditText et_search;
    private TextView tv_search;
    private TagGroup tag_store_search;
    private String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_search);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
        initView();
        loadData();
        setListeners();
    }

    private void setListeners() {
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoreSearchActivity.this,GoodsListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("title",et_search.getText()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tag_store_search.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                et_search.setText(tag);
                Intent intent=new Intent(StoreSearchActivity.this,GoodsListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("title",et_search.getText()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        Call<StoreSearch> call= RetrofitService.getInstance().getShopSearchList(id);
        call.enqueue(new Callback<StoreSearch>() {
            @Override
            public void onResponse(Response<StoreSearch> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    List<StoreSearch.StoreSearchRes.StoreSearchHotSearch> hotSearchList = response.body().getRes().getHotsearch();
                    ArrayList<String> hots=new ArrayList<String>();
                    for (int i=0;i<hotSearchList.size();i++){
                        hots.add(hotSearchList.get(i).getTitle()+"");
                    }
                    tag_store_search.setTags(hots);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initView() {
        ib_back=(ImageButton)findViewById(R.id.ib_back);
        et_search=(EditText)findViewById(R.id.et_search);
        tv_search=(TextView)findViewById(R.id.tv_search);
        tag_store_search=(TagGroup)findViewById(R.id.tag_store_search);
    }
}
