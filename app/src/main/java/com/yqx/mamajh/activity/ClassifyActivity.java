package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.base.BaseAppCompatActivity;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.ClassifyEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/8.
 */

public class ClassifyActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.gv_group)
    GridView gridView;

    ListViewDataAdapter<ClassifyEntity.ResEntity.CataListEntity> listEntityListViewDataAdapter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_classify;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return gridView;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("积分商城分类");
        listEntityListViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ClassifyEntity.ResEntity.CataListEntity>() {
            @Override
            public ViewHolderBase<ClassifyEntity.ResEntity.CataListEntity> createViewHolder(int position) {
                return new ViewHolderBase<ClassifyEntity.ResEntity.CataListEntity>() {

                    ImageView img;
                    TextView about;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_classify, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_name);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, ClassifyEntity.ResEntity.CataListEntity itemData) {

                        Glide.with(mContext).load(itemData.getImgSrc()).into(img);
                        about.setText(itemData.getName());

                    }
                };
            }
        });

        gridView.setAdapter(listEntityListViewDataAdapter);
        gridView.setOnItemClickListener(this);
        loadData();

    }

    private void loadData() {
        showLoading("", true);
        Call<ClassifyEntity> call = RetrofitService.getInstance().getClassify();
        call.enqueue(new Callback<ClassifyEntity>() {
            @Override
            public void onResponse(Response<ClassifyEntity> response, Retrofit retrofit) {
                ClassifyEntity netBaseEntity = response.body();
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
                    List<ClassifyEntity.ResEntity> listEntity = netBaseEntity.getRes();

                    listEntityListViewDataAdapter.getDataList().addAll(listEntity.get(0).getCataList());
                    listEntityListViewDataAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Throwable t) {

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ClassifyEntity.ResEntity.CataListEntity cataListEntity = listEntityListViewDataAdapter.getItem(position);
        if(cataListEntity != null){
//            Bundle bundle = new Bundle();
//            bundle.putString("id", cataListEntity.getID());
//            bundle.putString("title", cataListEntity.getName());
//            readyGo(MyListActivity.class, bundle);
            Intent intent=new Intent(ClassifyActivity.this,MyListActivity.class);
            intent.putExtra("id", Integer.parseInt(cataListEntity.getID()));
//            Toast.makeText(ClassifyActivity.this,cataListEntity.getID(),Toast.LENGTH_SHORT).show();
            intent.putExtra("title", cataListEntity.getName());
            startActivity(intent);
        }
    }
}
