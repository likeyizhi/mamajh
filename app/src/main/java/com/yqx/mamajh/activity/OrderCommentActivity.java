package com.yqx.mamajh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.werb.pickphotoview.PickPhotoView;
import com.werb.pickphotoview.util.PickConfig;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.AddShowProduct;
import com.yqx.mamajh.bean.MemberOrder;
import com.yqx.mamajh.bean.MemberOrderInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 订单评价
 */
public class OrderCommentActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView  tvName;
    @BindView(R.id.rating_fwtd)
    RatingBar ratingFwtd;
    @BindView(R.id.rating_cpzl)
    RatingBar ratingCpzl;
    @BindView(R.id.rating_shsd)
    RatingBar ratingShsd;
    @BindView(R.id.gv_img)
    GridView  gvImg;
    @BindView(R.id.lv_productlist)
    ListView  lvProductlist;
    @BindView(R.id.et_comment)
    EditText  etComment;

    private MemberOrder mOrder;

    private List<String> mImgs = new ArrayList<>();

    private MaterialDialog mMaterialDialog = null;
    private String orderId;
    private String[] prosfs;
    private String ordernumber;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_comment;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        setTitle("订单评价");

        orderId =(String)getIntent().getExtras().get("orderId");
        Toast.makeText(OrderCommentActivity.this,orderId+"",Toast.LENGTH_SHORT).show();
        loadData(orderId);
//        tvName.setText(mOrder.getName());
//        ProductItmeAdapter productItmeAdapter = new ProductItmeAdapter(mContext, mOrder.getProductlist());
//        lvProductlist.setAdapter(productItmeAdapter);

        mImgs.add("add");
        gvImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.equals(mImgs.get(i), "add")) {
                    new PickPhotoView.Builder(OrderCommentActivity.this)
                            .setPickPhotoSize(3)   //select max size
                            .setShowCamera(true)   //is show camera
                            .setSpanCount(3)       //SpanCount
                            .start();
                }
            }
        });
        gvImg.setAdapter(new ImgAdapter());

    }


    private void loadData(final String orderId) {
        Call<MemberOrderInfo> call= RetrofitService.getInstance().memberOrderInfo(AppApplication.TOKEN,orderId+"");
        call.enqueue(new Callback<MemberOrderInfo>() {
            @Override
            public void onResponse(Response<MemberOrderInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    MemberOrderInfo.MemberOrderInfoRes memRes = response.body().getRes();
                    ordernumber=memRes.getNumber();
                    tvName.setText(memRes.getShopname());
                    prosfs=new String[memRes.getProductlist().size()];
                    ProductItmeAdapter productItmeAdapter = new ProductItmeAdapter(mContext, memRes.getProductlist());
                    lvProductlist.setAdapter(productItmeAdapter);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }
        if (data == null) {
            return;
        }
        if (requestCode == PickConfig.PICK_PHOTO_DATA) {
            mImgs = (List<String>) data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT);
            mImgs.add("add");
            gvImg.setAdapter(new ImgAdapter());
        }
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        String productlist = "";
        for (int i=0;i<prosfs.length;i++){
            if (prosfs[i]==null){
                productlist +=(i+1)+":"+5+",";
            }else{
                productlist +=prosfs[i]+",";
            }
        }
        productlist = productlist.substring(0, productlist.length() - 1);
//        Toast.makeText(OrderCommentActivity.this,productlist,Toast.LENGTH_SHORT).show();

        int isimg = 0;
        if (mImgs.size() > 1) {
            isimg = 1;
        }
        addShowProduct((int) ratingFwtd.getRating(), (int) ratingCpzl.getRating(), (int) ratingShsd.getRating(), etComment.getText().toString().trim(), isimg, productlist);
    }

    private void addShowProduct(int fwtd, int cpzl, int shsd, String content, int isimg, String productlist) {
        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderCommentActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity<AddShowProduct>> mGetDataCallNet = RetrofitService.getInstance().addShowProduct(AppApplication.TOKEN, ordernumber, fwtd, cpzl, shsd, content, isimg, productlist);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity<AddShowProduct>>() {
                @Override
                public void onResponse(Response<NetBaseEntity<AddShowProduct>> response, Retrofit retrofit) {
                    if (response.body().getStatus() == 0) {
                        if (mImgs.size() == 2) {
                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), null, null);
                        } else if (mImgs.size() == 3) {
                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), new File(mImgs.get(1)), null);
                        } else if (mImgs.size() == 4) {
                            saveImage(response.body().getRes().getShowid(), new File(mImgs.get(0)), new File(mImgs.get(1)), new File(mImgs.get(2)));
                        } else {
                            Toast.makeText(getApplicationContext(), "晒单成功", Toast.LENGTH_SHORT).show();
                            mMaterialDialog.dismiss();
                            finish();
                        }
                    }
                    mMaterialDialog.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
    }

    private void saveImage(int showid, File file_img1, File file_img2, File file_img3) {
        RequestBody requestBody;
        MultipartBody.Part part1 = null, part2 = null, part3 = null;
        if (file_img1 != null) {
            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img1);
            part1 = MultipartBody.Part.createFormData("file_img1", file_img1.getName(), requestBody);
        }
        if (file_img2 != null) {
            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img2);
            part2 = MultipartBody.Part.createFormData("file_img2", file_img2.getName(), requestBody);
        }
        if (file_img3 != null) {
            requestBody = RequestBody.create(MediaType.parse("image/png"), file_img3);
            part3 = MultipartBody.Part.createFormData("file_img3", file_img3.getName(), requestBody);
        }

        if (NetUtils.isNetworkConnected(mContext)) {
            mMaterialDialog = new MaterialDialog.Builder(OrderCommentActivity.this)
                    .content(R.string.loading)
                    .cancelable(false)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false)
                    .show();
            Call<NetBaseEntity> mGetDataCallNet = RetrofitService.getInstance().saveImage(AppApplication.TOKEN, 2, showid, part1, part2, part3);
            mGetDataCallNet.enqueue(new Callback<NetBaseEntity>() {
                @Override
                public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                    Toast.makeText(getApplicationContext(), "晒单成功", Toast.LENGTH_SHORT).show();
                    mMaterialDialog.dismiss();
                    finish();
                }

                @Override
                public void onFailure(Throwable t) {
                    mMaterialDialog.dismiss();
                    t.printStackTrace();
                }
            });
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                }
            });
        }
    }


    private class ImgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mImgs.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final ImgViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order_comment_img, null);
                holder = new ImgViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ImgViewHolder) view.getTag();
            }
            if (TextUtils.equals(mImgs.get(i), "add")) {
                Glide.with(getApplicationContext()).load(R.mipmap.jiat).crossFade().into(holder.ivItemImg);
            } else {
                Glide.with(getApplicationContext()).load(mImgs.get(i)).crossFade().into(holder.ivItemImg);
            }
            return view;
        }


    }

    static class ImgViewHolder {
        @BindView(R.id.iv_item_img)
        ImageView ivItemImg;

        ImgViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class ProductItmeAdapter extends BaseAdapter {
        private Context context;
        private List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> entityList;

        ProductItmeAdapter(Context context, List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> entityList) {
            super();
            this.context = context;
            this.entityList = entityList;
        }

        @Override
        public int getCount() {
            return this.entityList.size();
        }

        @Override
        public MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist getItem(int i) {
            return entityList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ProductViewHolder   holder;
            if (view == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order_comment_product, null);
                holder = new ProductViewHolder(view);
                view.setTag(holder);
            }
            holder = (ProductViewHolder) view.getTag();
            MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist entity = getItem(i);
            Glide.with(getApplicationContext()).load(entity.getImgurl()).crossFade().into(holder.ivMineOrderProductItemImg);
            holder.tvItemOrderProductTitle.setText(entity.getName());
            holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                    Toast.makeText(OrderCommentActivity.this,i+":"+v,Toast.LENGTH_SHORT).show();
//                    mOrder.getProductlist().get(i).setRating((int) v);
                    prosfs[i]=(i+1)+":"+(int) v;
//                    commentInfo.getProductlist().get(i).setPropos(i);
//                    commentInfo.getProductlist().get(i).setFenShu((int)v);
//                    Toast.makeText(OrderCommentActivity.this,prosfs[i]+"",Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }


    }

    static class ProductViewHolder {
        @BindView(R.id.iv_mine_order_product_item_img)
        ImageView ivMineOrderProductItemImg;
        @BindView(R.id.tv_item_order_product_title)
        TextView  tvItemOrderProductTitle;
        @BindView(R.id.rating)
        RatingBar rating;

        ProductViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
