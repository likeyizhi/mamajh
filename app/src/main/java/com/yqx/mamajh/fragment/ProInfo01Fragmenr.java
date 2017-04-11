package com.yqx.mamajh.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.MineEditAddressActivity;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.activity.ProfitActivity;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.activity.StoreActivity;
import com.yqx.mamajh.adapter.OtherAdapter;
import com.yqx.mamajh.adapter.ViewPagerAdapter;
import com.yqx.mamajh.bean.ProInfo;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import android.view.ViewGroup.LayoutParams;

import static com.yqx.mamajh.R.id.swipe;
import static com.yqx.mamajh.R.id.vp_proinfo;

/**
 * Created by likey on 2017/3/11.
 */

public class ProInfo01Fragmenr extends android.support.v4.app.Fragment {
    private View view;
    private ViewPager vp_proImg;

    private ImageView[] tips;

    private ImageView imageView;

    private int[] imgIdArray ;
    private TextView tv_proName;
    private TextView tv_shopPrice;
    private TextView tv_realPrice;
    private TextView tv_buyNumber;
    private ImageView iv_shop_icon;
    private TextView tv_shopName;
    private RatingBar ratingBar;
    private TextView tv_guanzhu;
    private TextView tv_quanbu;
    private TextView tv_huodong;
    private TextView tv_lianximaijia;
    private TextView tv_jinrudianpu;
    private TextView tv_proinf_zhida;
    private TextView tv_proinfo_ziqu;
    private TextView tv_proinfo_fn;
    private TextView tv_proinfo_bd;
    private TextView tv_proinfo_cuxiao;
    private TextView tv_proinfo_pingjia;
    private TextView tv_proinfo_haopinglv;
    private WebView wbv_proinfoxiangqing;
    private ListView lv_proinfo_others;
    private ArrayList<Fragment> fragments;
    private PagerAdapter thisAdapter;
    private LinearLayout tip;
    private OtherAdapter otherAdapter;
    private LinearLayout ll_cuXiao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_proinfo_01,null);
        initView();
        int id = ((ProductInfoActivity) getActivity()).getId();
        loadData(id);

        return view;
    }

    private void setAdapter(List<ProInfo.ProInfoRes.ProInfoMoreproimg> moreproimg) {
        fragments=new ArrayList<Fragment>();
        for (int i=0;i<moreproimg.size();i++){
            fragments.add(ProInfoImgsFragment.newInstance(moreproimg.get(i).getImgSrc()));
        }
        thisAdapter=new PagerAdapter(getChildFragmentManager(), fragments);
        vp_proImg.setAdapter(thisAdapter);

        tips=new ImageView[moreproimg.size()];
        for (int j=0;j<tips.length;j++){
            ImageView otip=new ImageView(getActivity());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            params.setMargins(5,0,5,0);
            otip.setLayoutParams(params);
            tips[j]=otip;
            if(j==0){
                tips[j].setBackgroundResource(R.mipmap.indicator_focused);
            }else{
                tips[j].setBackgroundResource(R.mipmap.indicator);
            }
            tip.addView(otip); //把点点添加到容器中
        }
        vp_proImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImageBackground(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    /**
     * 设置选中的tip的背景
     * @param selectItems
     */
    private void setImageBackground(int selectItems){
        for(int i=0; i<tips.length; i++){
            if(i == selectItems){
                tips[i].setBackgroundResource(R.mipmap.indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.mipmap.indicator);
            }
        }
    }

    /**
     * 适配器
     *
     */
    private final class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list ;
        public PagerAdapter(FragmentManager fm, List<Fragment> list){
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            if(list == null) return 0;
            return list.size();
        }
    }

    private void loadData(final int id) {
//        Toast.makeText(getActivity(),""+id,Toast.LENGTH_SHORT).show();
        final Call<ProInfo> call= RetrofitService.getInstance().getProInfo(id,AppApplication.TOKEN);
        call.enqueue(new Callback<ProInfo>() {
            public ProInfo.ProInfoRes proInfos;

            @Override
            public void onResponse(Response<ProInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    proInfos=response.body().getRes();
//                    Toast.makeText(getActivity(),""+response.body().getRes().getName(),Toast.LENGTH_SHORT).show();
//                    vp_proImg
                    tv_proName.setText(proInfos.getProname()+"");
                    tv_shopPrice.setText(proInfos.getSaleprice()+"");
                    tv_realPrice.setText("￥"+proInfos.getMarketprice()+"");
                    tv_realPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_buyNumber.setText(proInfos.getSalecount()+"人已购买");
                    Glide.with(getActivity()).load(proInfos.getLogo()).into(iv_shop_icon);
                    tv_shopName.setText(proInfos.getName()+"");
                    ratingBar.setRating(proInfos.getLevel());
                    tv_guanzhu.setText(proInfos.getCollectcount()+"");
                    tv_quanbu.setText(proInfos.getProcount()+"");
                    tv_huodong.setText(proInfos.getActivitycount()+"");
                    tv_lianximaijia.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(getActivity(),proInfos.getPhone()+"",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+proInfos.getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    tv_jinrudianpu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(),StoreActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(ShopActivity.IDBUNDLE, proInfos.getShopid()+"");
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    int delListSize = proInfos.getDeliverylist().size();
                    switch (delListSize){
                        case 0:
                            tv_proinf_zhida.setVisibility(View.INVISIBLE);
                            tv_proinfo_ziqu.setVisibility(View.INVISIBLE);
                            tv_proinfo_fn.setVisibility(View.INVISIBLE);
                            tv_proinfo_bd.setVisibility(View.INVISIBLE);
                            break;
                        case 1:
                            tv_proinf_zhida.setVisibility(View.VISIBLE);
                            tv_proinf_zhida.setText(proInfos.getDeliverylist().get(0).getTitle()+"");
                            tv_proinfo_ziqu.setVisibility(View.INVISIBLE);
                            tv_proinfo_fn.setVisibility(View.INVISIBLE);
                            tv_proinfo_bd.setVisibility(View.INVISIBLE);
                            break;
                        case 2:
                            tv_proinf_zhida.setVisibility(View.VISIBLE);
                            tv_proinf_zhida.setText(proInfos.getDeliverylist().get(0).getTitle()+"");
                            tv_proinfo_ziqu.setVisibility(View.VISIBLE);
                            tv_proinfo_ziqu.setText(proInfos.getDeliverylist().get(1).getTitle()+"");
                            tv_proinfo_fn.setVisibility(View.INVISIBLE);
                            tv_proinfo_bd.setVisibility(View.INVISIBLE);
                            break;
                        case 3:
                            tv_proinf_zhida.setVisibility(View.VISIBLE);
                            tv_proinf_zhida.setText(proInfos.getDeliverylist().get(0).getTitle()+"");
                            tv_proinfo_ziqu.setVisibility(View.VISIBLE);
                            tv_proinfo_ziqu.setText(proInfos.getDeliverylist().get(1).getTitle()+"");
                            tv_proinfo_fn.setVisibility(View.VISIBLE);
                            tv_proinfo_fn.setText(proInfos.getDeliverylist().get(2).getTitle()+"");
                            tv_proinfo_bd.setVisibility(View.INVISIBLE);
                            break;
                        case 4:
                            tv_proinf_zhida.setVisibility(View.VISIBLE);
                            tv_proinf_zhida.setText(proInfos.getDeliverylist().get(0).getTitle()+"");
                            tv_proinfo_ziqu.setVisibility(View.VISIBLE);
                            tv_proinfo_ziqu.setText(proInfos.getDeliverylist().get(1).getTitle()+"");
                            tv_proinfo_fn.setVisibility(View.VISIBLE);
                            tv_proinfo_fn.setText(proInfos.getDeliverylist().get(2).getTitle()+"");
                            tv_proinfo_bd.setVisibility(View.VISIBLE);
                            tv_proinfo_bd.setText(proInfos.getDeliverylist().get(3).getTitle()+"");
                            break;
                    }
//                    if (proInfos.getDeliverylist().get(0).getState()==1){
//                        tv_proinf_zhida.setTextColor(Color.rgb(255,77,77));//#FF4D4D
//                    }else{
//                        tv_proinf_zhida.setTextColor(Color.rgb(142,142,142));//#8E8E8E
//                    }
//
//                    if (proInfos.getDeliverylist().get(1).getState()==1){
//                        tv_proinfo_ziqu.setTextColor(Color.rgb(255,77,77));//#FF4D4D
//                    }else{
//                        tv_proinfo_ziqu.setTextColor(Color.rgb(142,142,142));//#8E8E8E
//                    }
//
//                    if (proInfos.getDeliverylist().get(2).getState()==1){
//                        tv_proinfo_fn.setTextColor(Color.rgb(255,77,77));//#FF4D4D
//                    }else{
//                        tv_proinfo_fn.setTextColor(Color.rgb(142,142,142));//#8E8E8E
//                    }
//
//                    if (proInfos.getDeliverylist().get(3).getState()==1){
//                        tv_proinfo_bd.setTextColor(Color.rgb(255,77,77));//#FF4D4D
//                    }else{
//                        tv_proinfo_bd.setTextColor(Color.rgb(142,142,142));//#8E8E8E
//                    }

                    tv_proinfo_pingjia.setText("评价/晒单("+proInfos.getPjcount()+")");
                    tv_proinfo_haopinglv.setText(proInfos.getGoodratio()+"%");
                    if (proInfos.getActivitylist().size()!=0){
//                        Toast.makeText(getActivity(),proInfos.getActivitylist().get(0).getName()+"",Toast.LENGTH_SHORT).show();
                        tv_proinfo_cuxiao.setText(proInfos.getActivitylist().get(0).getName()+" . . .");
                    }else {
                        ll_cuXiao.setVisibility(View.GONE);
                    }
                    ll_cuXiao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String[] items=new String[proInfos.getActivitylist().size()];
                            for (int i=0;i<proInfos.getActivitylist().size();i++){
                                items[i]=proInfos.getActivitylist().get(i).getName();
                            }
                            showCuxiaoDialog(items);
                        }
                    });
                    setOtherAdapter(proInfos.getOtherlist());
                    setWebView(proInfos.getDetailsurl());
                    setAdapter(proInfos.getMoreproimg());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void showCuxiaoDialog(String[] items) {
        new AlertDialog.Builder(getActivity())
                .setTitle("促销活动")
                .setItems(items, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void setOtherAdapter(List<ProInfo.ProInfoRes.ProInfoOtherlist> otherlist) {
        otherAdapter=new OtherAdapter(getActivity(),otherlist);
        lv_proinfo_others.setAdapter(otherAdapter);
    }

    private void setWebView(final String url){
        wbv_proinfoxiangqing.post(new Runnable() {
            @SuppressLint("NewApi")
            public void run() {
                WebSettings settings = wbv_proinfoxiangqing.getSettings();
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);

                wbv_proinfoxiangqing.setWebChromeClient(new WebChromeClient());
                wbv_proinfoxiangqing.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        view.getSettings().setLoadsImagesAutomatically(false);
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.getSettings().setLoadsImagesAutomatically(true);
                    }
                });
                wbv_proinfoxiangqing.getSettings().setJavaScriptEnabled(true);
                wbv_proinfoxiangqing.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                wbv_proinfoxiangqing.loadUrl(url);
//				webView.loadDataWithBaseURL("fake://not/needed", url, "text/html", "utf-8", "");
            }
        });
    }

    private void initView() {
        vp_proImg=(ViewPager)view.findViewById(R.id.vp_proImg);
        tv_proName=(TextView)view.findViewById(R.id.tv_proName);
        tv_shopPrice=(TextView)view.findViewById(R.id.tv_shopPrice);
        tv_realPrice=(TextView)view.findViewById(R.id.tv_realPrice);
        tv_buyNumber=(TextView)view.findViewById(R.id.tv_buyNumber);
        iv_shop_icon=(ImageView)view.findViewById(R.id.iv_shop_icon);
        tv_shopName=(TextView)view.findViewById(R.id.tv_shopName);
        ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        tv_guanzhu=(TextView)view.findViewById(R.id.tv_guanzhu);
        tv_quanbu=(TextView)view.findViewById(R.id.tv_quanbu);
        tv_huodong=(TextView)view.findViewById(R.id.tv_huodong);
        tv_lianximaijia=(TextView)view.findViewById(R.id.tv_lianximaijia);
        tv_jinrudianpu=(TextView)view.findViewById(R.id.tv_jinrudianpu);
        tv_proinf_zhida=(TextView)view.findViewById(R.id.tv_proinf_zhida);
        tv_proinfo_ziqu=(TextView)view.findViewById(R.id.tv_proinfo_ziqu);
        tv_proinfo_fn=(TextView)view.findViewById(R.id.tv_proinfo_fn);
        tv_proinfo_bd=(TextView)view.findViewById(R.id.tv_proinfo_bd);
        ll_cuXiao=(LinearLayout)view.findViewById(R.id.ll_cuXiao);
        tv_proinfo_cuxiao=(TextView)view.findViewById(R.id.tv_proinfo_cuxiao);
        tv_proinfo_pingjia=(TextView)view.findViewById(R.id.tv_proinfo_pingjia);
        tv_proinfo_haopinglv=(TextView)view.findViewById(R.id.tv_proinfo_haopinglv);
        wbv_proinfoxiangqing=(WebView)view.findViewById(R.id.wbv_proinfoxiangqing);

        lv_proinfo_others=(ListView)view.findViewById(R.id.lv_proinfo_others);

        tip=(LinearLayout)view.findViewById(R.id.tip);
    }



}
