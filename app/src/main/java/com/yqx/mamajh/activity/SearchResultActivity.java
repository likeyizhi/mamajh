package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.SearchResultAdapter;
import com.yqx.mamajh.bean.SearchResultBean;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/10.
 */

public class SearchResultActivity extends Activity{
    private String k;
    private ImageButton ibBack;
    private ListView lvSResult;
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Intent intent=getIntent();
        k=intent.getStringExtra("_searchKey");
//        Toast.makeText(this,""+k,Toast.LENGTH_SHORT).show();
        initView();
        loadData();
        setListeners();
    }

    private void setListeners() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        Call<SearchResultBean> call= RetrofitService.getInstance().getSearchResult(AppApplication.TOKEN, k, HomeFragment.x, HomeFragment.y);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Response<SearchResultBean> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
//                    Toast.makeText(SearchResultActivity.this,"1"+response.body().getRes().get(0).getLogo(),Toast.LENGTH_SHORT).show();
                    setAdapter(response.body().getRes());
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void setAdapter(List<SearchResultBean.SearchResultRes> shoplist) {
        adapter=new SearchResultAdapter(SearchResultActivity.this,shoplist);
        lvSResult.setAdapter(adapter);

        adapter.setOnItemClickSetIntoShop(new SearchResultAdapter.OnItemClickSetIntoShop() {
            @Override
            public void OnItemClickSetIntoShop(String id) {
                Intent intent=new Intent(SearchResultActivity.this,StoreActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ShopActivity.IDBUNDLE, id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        lvSResult=(ListView)findViewById(R.id.lv_searchResult);
    }
}
