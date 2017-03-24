package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.TitleGroupAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.DemoFormtEntity;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.cyclebanner.ADInfo;
import com.yqx.mamajh.widget.cyclebanner.ImageCycleView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2017/3/7.
 */

public class CreditActivity extends BaseActivity implements AdapterView.OnItemClickListener, ImageCycleView.ImageCycleViewListener {

    @BindView(R.id.icv_imgs)
    ImageCycleView cycleView;
    @BindView(R.id.gv_group)
    GridView group;
    @BindView(R.id.gv_new)
    GridView newGroup;
    @BindView(R.id.gv_hot)
    GridView hotGroup;
    @BindView(R.id.gv_time)
    GridView timeGroup;
    @BindView(R.id.ll_root)
    LinearLayout root;


    private List<HomeInfoEntity.BlowshopFourAdEntity> navigationList;
    private TitleGroupAdapter groupAdapter;

    private ListViewDataAdapter<DemoFormtEntity.ResEntity.NewbieAreasEntity> newGoodsAdapter;
    private ListViewDataAdapter<DemoFormtEntity.ResEntity.HeatExchangeAreaEntity> hotGoodsAdapter;
    private ListViewDataAdapter<DemoFormtEntity.ResEntity.TimeLimitEntity> limitGoodsAdapter;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_credit;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return root;
    }

    @Override
    protected void initViewsAndEvents() {

        setTitle("积分兑换");

        setAnomalyParams();

        navigationList = new ArrayList<>();
        fillData();
        groupAdapter = new TitleGroupAdapter(mContext, navigationList);
        group.setAdapter(groupAdapter);
        group.setOnItemClickListener(this);

        newGoodsAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<DemoFormtEntity.ResEntity.NewbieAreasEntity>() {
            @Override
            public ViewHolderBase<DemoFormtEntity.ResEntity.NewbieAreasEntity> createViewHolder(int position) {
                return new ViewHolderBase<DemoFormtEntity.ResEntity.NewbieAreasEntity>() {

                    ImageView img;
                    TextView about;
                    TextView money;
                    TextView status;
                    LinearLayout ll;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_new, null);
                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_about);
                        money = (TextView) convertView.findViewById(R.id.tv_money);
                        status = (TextView) convertView.findViewById(R.id.tv_status);
                        ll=(LinearLayout)convertView.findViewById(R.id.ll_ll);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, final DemoFormtEntity.ResEntity.NewbieAreasEntity itemData) {

                        Glide.with(mContext).load(itemData.getImg()).into(img);
                        about.setText(itemData.getName());
                        money.setText(itemData.getIntegralPrice());
                        status.setText(itemData.getOPrice());
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(CreditActivity.this,CreditProInfoActivity.class);
                                intent.putExtra("id",itemData.getID());
                                startActivity(intent);

                            }
                        });
                    }

                };
            }
        });

        hotGoodsAdapter = new ListViewDataAdapter<DemoFormtEntity.ResEntity.HeatExchangeAreaEntity>(new ViewHolderCreator<DemoFormtEntity.ResEntity.HeatExchangeAreaEntity>() {
            @Override
            public ViewHolderBase<DemoFormtEntity.ResEntity.HeatExchangeAreaEntity> createViewHolder(int position) {
                return new ViewHolderBase<DemoFormtEntity.ResEntity.HeatExchangeAreaEntity>() {
                    ImageView img;
                    TextView about;
                    TextView money;
                    TextView status;
                    LinearLayout ll;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_new, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_about);
                        money = (TextView) convertView.findViewById(R.id.tv_money);
                        status = (TextView) convertView.findViewById(R.id.tv_status);
                        ll=(LinearLayout)convertView.findViewById(R.id.ll_ll);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, final DemoFormtEntity.ResEntity.HeatExchangeAreaEntity itemData) {

                        Glide.with(mContext).load(itemData.getImg()).into(img);
                        about.setText(itemData.getName());
                        money.setText(itemData.getIntegralPrice());
                        status.setText(itemData.getOPrice());
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent =new Intent(CreditActivity.this,CreditProInfoActivity.class);
                                intent.putExtra("id",itemData.getID());
                                startActivity(intent);

                            }
                        });

                    }
                };
            }
        });

        limitGoodsAdapter = new ListViewDataAdapter<DemoFormtEntity.ResEntity.TimeLimitEntity>(new ViewHolderCreator<DemoFormtEntity.ResEntity.TimeLimitEntity>() {
            @Override
            public ViewHolderBase<DemoFormtEntity.ResEntity.TimeLimitEntity> createViewHolder(int position) {
                return new ViewHolderBase<DemoFormtEntity.ResEntity.TimeLimitEntity>() {
                    ImageView img;
                    TextView about;
                    TextView money;
                    TextView status;
                    LinearLayout ll;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_new, null);

                        img = (ImageView) convertView.findViewById(R.id.iv_img);
                        about = (TextView) convertView.findViewById(R.id.tv_about);
                        money = (TextView) convertView.findViewById(R.id.tv_money);
                        status = (TextView) convertView.findViewById(R.id.tv_status);
                        ll=(LinearLayout)convertView.findViewById(R.id.ll_ll);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, final DemoFormtEntity.ResEntity.TimeLimitEntity itemData) {
                        Glide.with(mContext).load(itemData.getImg()).into(img);
                        about.setText(itemData.getName());
                        money.setText(itemData.getIntegralPrice());
                        status.setText(itemData.getSpecifications());
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent =new Intent(CreditActivity.this,CreditProInfoActivity.class);
                                intent.putExtra("id",itemData.getID());
                                startActivity(intent);

                            }
                        });

                    }
                };
            }
        });

        newGroup.setAdapter(newGoodsAdapter);
        hotGroup.setAdapter(hotGoodsAdapter);
        timeGroup.setAdapter(limitGoodsAdapter);

        loadData();

    }

    private void setAnomalyParams() {
//        bannerHeight = (int) (mScreenWidth * (208.0 / 483));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth, (int) (mScreenWidth * (208.0 / 483)));
        cycleView.setLayoutParams(layoutParams);
    }

    private void loadData() {

        showLoading("", true);
        Call<DemoFormtEntity> call = RetrofitService.getInstance().getCreditList();
        call.enqueue(new Callback<DemoFormtEntity>() {
            @Override
            public void onResponse(Response<DemoFormtEntity> response, Retrofit retrofit) {
                DemoFormtEntity netBaseEntity = response.body();
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
                    List<DemoFormtEntity.ResEntity> listEntity = netBaseEntity.getRes();

                    fillCarousel(listEntity.get(0).getAdList());

                    newGoodsAdapter.getDataList().addAll(listEntity.get(1).getNewbieAreas());
                    hotGoodsAdapter.getDataList().addAll(listEntity.get(2).getHeatExchangeArea());
                    limitGoodsAdapter.getDataList().addAll(listEntity.get(3).getTimeLimit());

                    newGoodsAdapter.notifyDataSetChanged();
                    hotGoodsAdapter.notifyDataSetChanged();
                    limitGoodsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();

            }
        });

    }

    private void fillCarousel(List<DemoFormtEntity.ResEntity.AdListEntity> adList) {
        ArrayList<ADInfo> adInfos = new ArrayList<>();
        if(adList != null && adList.size() > 0){
            for (DemoFormtEntity.ResEntity.AdListEntity carouseladentity :
                    adList) {

                ADInfo adInfo = new ADInfo();
//                adInfo.setId(carouseladentity.getProductid());
                adInfo.setContent(carouseladentity.getTitle());
                adInfo.setUrl(carouseladentity.getImgSrc());
//                adInfo.setType(carouseladentity.getLinktype());

                adInfos.add(adInfo);
            }
        }
        cycleView.setImageResources(adInfos, this);
    }

    private void fillData() {

        HomeInfoEntity.BlowshopFourAdEntity entitySale = new HomeInfoEntity.BlowshopFourAdEntity();
        entitySale.setTitle("热门分类");
        entitySale.setIcon(R.mipmap.jfdh_rmfl);

        HomeInfoEntity.BlowshopFourAdEntity entityDictionary = new HomeInfoEntity.BlowshopFourAdEntity();
        entityDictionary.setTitle("销量排行");
        entityDictionary.setIcon(R.mipmap.jfdh_xlph);

        HomeInfoEntity.BlowshopFourAdEntity entityHot = new HomeInfoEntity.BlowshopFourAdEntity();
        entityHot.setTitle("我可兑换");
        entityHot.setIcon(R.mipmap.jfdh_wkdh);

        HomeInfoEntity.BlowshopFourAdEntity entityIntegration = new HomeInfoEntity.BlowshopFourAdEntity();
        entityIntegration.setTitle("江湖币赚取");
        entityIntegration.setIcon(R.mipmap.jfdh_jhbhq);

        navigationList.add(entitySale);
        navigationList.add(entityDictionary);
        navigationList.add(entityHot);
        navigationList.add(entityIntegration);

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

        switch (position){
            case 0:
                //热门分类
                readyGo(ClassifyActivity.class);
                break;
            case 1:
                //销量排行
                readyGo(RankingActivity.class);
                break;
            case 2:
                //我可兑换
//                readyGo(MyListActivity.class);
                Intent intent=new Intent(CreditActivity.this,MyListActivity.class);
                intent.putExtra("title","我的兑换");
                startActivity(intent);
                break;
            case 3:
                //江湖笔赚取
                if(TextUtils.isEmpty(AppApplication.TOKEN)){
                    readyGo(LoginActivity.class);
                }else{
                    readyGo(ProfitActivity.class);
                }
                break;
        }

    }

    @Override
    public void displayImage(String imageURL, ImageView imageView) {
        if(!CommonUtils.isEmpty(imageURL)){
            Glide.with(mContext).load(imageURL).crossFade().into(imageView);
        }
    }

    @Override
    public void onImageClick(ADInfo info, int postion, View imageView) {

    }
}
