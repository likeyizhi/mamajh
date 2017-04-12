package com.yqx.mamajh.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.widgets.BrowserLayout;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by young on 2016/11/6.
 * 发现
 */

public class DiscoveryFragment extends BaseFragment {
    @BindView(R.id.common_web_browser_layout)
    BrowserLayout commonWebBrowserLayout;

    @Override
    protected void onFirstUserVisible() {

        commonWebBrowserLayout.hideBrowserController();
//        commonWebBrowserLayout.loadUrl("http://m.mamajh.com/Discover.aspx");
        commonWebBrowserLayout.loadUrl("http://m.mamajh.com/Discover2.aspx");
//        commonWebBrowserLayout.loadUrl("http://m.mamajh.com/App/Discover.aspx");

//        commonWebBrowserLayout.getWebView().setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                Intent intent = new Intent(mContext, BaseWebActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, url);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

}
