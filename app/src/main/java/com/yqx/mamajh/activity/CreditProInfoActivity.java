package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.MallProductDatails;
import com.yqx.mamajh.network.RetrofitService;


import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CreditProInfoActivity extends Activity {
    private String id;
    private TextView tvTitle;
    private ImageView ivImgSrc;
    private TextView tvOprice;
    private TextView tvMarkPrice;
    private TextView tvSaleCount;
    private TextView tvPrice;
    private ImageButton ibBack;
    private WebView tvDis;
    private List<MallProductDatails.MallProductDatailsRes> mMallProductDatails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_pro_info);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        setView();
        loadData();
    }

    private void setView() {
        tvTitle = (TextView) findViewById(R.id.tv_shangPinMing);
        ivImgSrc = (ImageView) findViewById(R.id.iv_photo);
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        tvMarkPrice = (TextView) findViewById(R.id.tv_jiaGe);
        tvSaleCount = (TextView) findViewById(R.id.tv_yiDui);
        tvPrice = (TextView) findViewById(R.id.tv_jinBi);
        tvDis = (WebView) findViewById(R.id.tv_xiangQing);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void loadData() {
        Call<MallProductDatails> call = RetrofitService.getInstance().getMallProductDatails(id);
        call.enqueue(new Callback<MallProductDatails>() {
            @Override
            public void onResponse(Response<MallProductDatails> response, Retrofit retrofit) {


                if (response.body().getStatus() == 0) {
                    mMallProductDatails = response.body().getRes();
                    if (!mMallProductDatails.isEmpty()) {
                        tvTitle.setText(mMallProductDatails.get(0).getTitle() + "");
                        tvMarkPrice.setText("￥" + mMallProductDatails.get(0).getMarkPrice());
                        tvMarkPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        tvPrice.setText(mMallProductDatails.get(0).getPrice() + "金币");
                        tvSaleCount.setText(mMallProductDatails.get(0).getSaleCount() + "件已兑换");

                        Glide.with(CreditProInfoActivity.this).load(mMallProductDatails.get(0).getImgSrc() + "").into(ivImgSrc);



                        setWebView(StringEscapeUtils.unescapeHtml4(mMallProductDatails.get(0).getDis()));
//                        tvDis.getSettings().setJavaScriptEnabled(true);
//                        tvDis.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//                        String data = mMallProductDatails.get(0).getDis() + "";
//
//                        tvDis.loadDataWithBaseURL("", data, "text/html", "utf-8", "");
                    }
                } else {
                    Toast.makeText(CreditProInfoActivity.this, response.body().getMes() + "", Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    private void setWebView(final String dis) {
        tvDis.post(new Runnable() {
            public void run() {
                WebSettings settings = tvDis.getSettings();
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);

                tvDis.setWebChromeClient(new WebChromeClient());
                tvDis.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        view.getSettings().setLoadsImagesAutomatically(false);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.getSettings().setLoadsImagesAutomatically(true);
                    }
                });
                tvDis.getSettings().setJavaScriptEnabled(true);
                tvDis.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                tvDis.loadDataWithBaseURL("fake://not/needed", dis, "text/html", "utf-8", "");
            }
        });
    }


}
