package com.yqx.mamajh.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.bean.ProInfo;
import com.yqx.mamajh.network.RetrofitService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/11.
 */

public class ProInfo02Fragmenr extends android.support.v4.app.Fragment {
    private View view;
    private WebView wbv_proinfo01;
    private String url;
    private int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_proinfo_02,null);
        id = ((ProductInfoActivity) getActivity()).getId();
        initView();
        getData();
        return view;
    }

    private void getData() {
        Call<ProInfo> call= RetrofitService.getInstance().getProInfo(id, AppApplication.TOKEN);
        call.enqueue(new Callback<ProInfo>() {
            @Override
            public void onResponse(Response<ProInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    setWebView(response.body().getRes().getDetailsurl());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void initView() {
        wbv_proinfo01=(WebView)view.findViewById(R.id.wbv_proinfo01);
    }

    private void setWebView(final String url) {
        wbv_proinfo01.post(new Runnable() {
            public void run() {
                WebSettings settings = wbv_proinfo01.getSettings();
                settings.setUseWideViewPort(true);
                settings.setLoadWithOverviewMode(true);

                wbv_proinfo01.setWebChromeClient(new WebChromeClient());
                wbv_proinfo01.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        view.getSettings().setLoadsImagesAutomatically(false);
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        view.getSettings().setLoadsImagesAutomatically(true);
                    }
                });
                wbv_proinfo01.getSettings().setJavaScriptEnabled(true);
                wbv_proinfo01.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                wbv_proinfo01.loadUrl(url);
//				webView.loadDataWithBaseURL("fake://not/needed", url, "text/html", "utf-8", "");
            }
        });
    }
}
