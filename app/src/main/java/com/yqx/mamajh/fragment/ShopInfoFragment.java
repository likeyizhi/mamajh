package com.yqx.mamajh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.squareup.okhttp.ResponseBody;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.activity.ShowBigImageActivity;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.ShopInformationEntity;
import com.yqx.mamajh.network.RetrofitService;

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

public class ShopInfoFragment extends BaseFragment {

    @BindView(R.id.ll_root)
    RelativeLayout root;
    @BindView(R.id.ll_eva)
    LinearLayout evaRoot;
    @BindView(R.id.tv_eva_num)
    TextView num;
    @BindView(R.id.ll_sell)
    LinearLayout sellRoot;
    @BindView(R.id.tv_sell_num)
    TextView sellNum;
    @BindView(R.id.lv_activity)
    ListView activitys;
    @BindView(R.id.gv_imgs)
    GridView imgs;
    @BindView(R.id.tv_address)
    TextView address;
    @BindView(R.id.tv_time)
    TextView time;
    @BindView(R.id.tv_tell)
    TextView tell;
    @BindView(R.id.gv_shopauthi)
    GridView shopauthi;

    private String id;

    private ListViewDataAdapter<ShopInformationEntity.ResEntity.ActivitylistEntity> activityAdapter;
    private ListViewDataAdapter<ShopInformationEntity.ResEntity.ShopimglistEntity> shopingsAdapter;
    private ListViewDataAdapter<ShopInformationEntity.ResEntity.ShopauthimglistEntity> shopauthImgsAdapter;

    public void scrollHeader(int scrollY) {
        root.setTranslationY(-scrollY);
    }

    @Override
    protected void onFirstUserVisible() {
//        mScrollView = ButterKnife.findById(root, R.id.scrollview);
//        root.setMinimumHeight(mScreenHeight);
//        setScrollViewOnScrollListener();

        Bundle bundle = getArguments();
        if(bundle != null ){
            id = bundle.getString(ShopActivity.IDBUNDLE);
        }

        activityAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ShopInformationEntity.ResEntity.ActivitylistEntity>() {
            @Override
            public ViewHolderBase<ShopInformationEntity.ResEntity.ActivitylistEntity> createViewHolder(int position) {
                return new ViewHolderBase<ShopInformationEntity.ResEntity.ActivitylistEntity>() {

                    TextView activity;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_activity, null);

                        activity = (TextView) convertView.findViewById(R.id.tv_actvity);


                        return convertView;
                    }

                    @Override
                    public void showData(int position, ShopInformationEntity.ResEntity.ActivitylistEntity itemData) {

                        if(itemData != null){
                            activity.setText(itemData.getName() + "(" + itemData.getTypeName() + ")");
                        }

                    }
                };
            }
        });

        shopingsAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ShopInformationEntity.ResEntity.ShopimglistEntity>() {
            @Override
            public ViewHolderBase<ShopInformationEntity.ResEntity.ShopimglistEntity> createViewHolder(int position) {
                return new ViewHolderBase<ShopInformationEntity.ResEntity.ShopimglistEntity>() {

                    ImageView imageView;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {

                        View convertView = layoutInflater.inflate(R.layout.item_img, null);
                        imageView = (ImageView) convertView.findViewById(R.id.iv_img);

                        return convertView;
                    }

                    @Override
                    public void showData(int position, final ShopInformationEntity.ResEntity.ShopimglistEntity itemData) {
                        if(itemData != null){
                            Glide.with(mContext).load(itemData.getImgSrc()).into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 图片点击
//                                    Snackbar.make(imageView,"点击实景"+itemData.getID(),Snackbar.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(), ShowBigImageActivity.class);
                                    intent.putExtra("_imgUrl",itemData.getImgSrc());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                };
            }
        });

        shopauthImgsAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<ShopInformationEntity.ResEntity.ShopauthimglistEntity>() {
            @Override
            public ViewHolderBase<ShopInformationEntity.ResEntity.ShopauthimglistEntity> createViewHolder(int position) {
                return new ViewHolderBase<ShopInformationEntity.ResEntity.ShopauthimglistEntity>() {

                    ImageView imageView;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.item_img, null);
                        imageView = (ImageView) convertView.findViewById(R.id.iv_img);



                        return convertView;
                    }

                    @Override
                    public void showData(int position, final ShopInformationEntity.ResEntity.ShopauthimglistEntity itemData) {
                        if (itemData != null){
                            Glide.with(mContext).load(itemData.getImgSrc()).into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //TODO 图片点击 商家资质
//                                Snackbar.make(imageView,"点击",Snackbar.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(), ShowBigImageActivity.class);
                                    intent.putExtra("_imgUrl",itemData.getImgSrc());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                };
            }
        });

        activitys.setAdapter(activityAdapter);
        imgs.setAdapter(shopingsAdapter);
        shopauthi.setAdapter(shopauthImgsAdapter);

        loadData();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return root;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    private void loadData() {
        showLoading("", true);
        Call<ShopInformationEntity> call = RetrofitService.getInstance().getShopInformation(id);
        call.enqueue(new Callback<ShopInformationEntity>() {
            @Override
            public void onResponse(Response<ShopInformationEntity> response, Retrofit retrofit) {
                ShopInformationEntity shopInformationEntity = response.body();

                hideLoading();
                if(shopInformationEntity == null){
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
                    if(shopInformationEntity.getStatus() != 0){
                        showError(shopInformationEntity.getMes());
                    }else{
                        List<ShopInformationEntity.ResEntity.ActivitylistEntity> activitylistEntityist = shopInformationEntity.getRes().getActivitylist();
                        List<ShopInformationEntity.ResEntity.ShopimglistEntity> shopimglistEntities = shopInformationEntity.getRes().getShopimglist();
                        List<ShopInformationEntity.ResEntity.ShopauthimglistEntity> shopauthimglistEntities = shopInformationEntity.getRes().getShopauthimglist();

                        activityAdapter.getDataList().addAll(activitylistEntityist);
                        shopingsAdapter.getDataList().addAll(shopimglistEntities);
                        shopauthImgsAdapter.getDataList().addAll(shopauthimglistEntities);

                        activityAdapter.notifyDataSetChanged();
                        shopingsAdapter.notifyDataSetChanged();
                        shopauthImgsAdapter.notifyDataSetChanged();

                        num.setText(shopInformationEntity.getRes().getPjtotal2());
                        sellNum.setText(shopInformationEntity.getRes().getMonthorder());
                        address.setText("地址："+shopInformationEntity.getRes().getShopaddress());
                        time.setText("营业时间"+shopInformationEntity.getRes().getShopworktime());
                        tell.setText("商家电话"+shopInformationEntity.getRes().getShopphone());

                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();
                showToast("服务器错误，请稍后再试");
                showError(t.getMessage());
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_shop_info;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
}
