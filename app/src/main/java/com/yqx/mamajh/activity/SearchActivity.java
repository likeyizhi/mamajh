package com.yqx.mamajh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.widgets.ClearEditText;
import com.yqx.mamajh.Presenter.SearchHistoryPresenter;
import com.yqx.mamajh.Presenter.impl.SearchHistoryPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SearchHistoryListEntity;
import com.yqx.mamajh.bean.SearchItemEntity;
import com.yqx.mamajh.bean.SearchResultBean;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.view.SearchHistoryView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import me.gujun.android.taggroup.TagGroup;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/12/21.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener, TagGroup.OnTagClickListener, SearchHistoryView {
    @BindView(R.id.ib_leftbtn)
    ImageButton ibLeftbtn;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.tag_group_hot)
    TagGroup tagGroupHot;
    @BindView(R.id.tag_group_history)
    TagGroup tagGroupHistory;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.sl_root)
    ScrollView root;


    private Call<NetBaseEntity<SearchHistoryListEntity>> call;
    private SearchHistoryPresenter searchHistoryPresenter;
    public static final String SEARCH_INPUT = "search_input";
    private String inputTitle;

    @Override
    protected void getBundleExtras(Bundle extras) {
        if(extras != null){
            inputTitle = extras.getString(SearchActivity.SEARCH_INPUT);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_search;
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
        mToolbar.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(inputTitle)){
            etSearch.setText(inputTitle);
        }
        setClickListener();
        loadData();
    }

    private void loadData() {

        searchHistoryPresenter = new SearchHistoryPresenterImpl(mContext,this, call);

        if(NetUtils.isNetworkConnected(mContext)){
            if (null != tagGroupHot) {
                tagGroupHot.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        searchHistoryPresenter.loadListData(TAG_LOG);
                    }
                }, Constants.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        }else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                    searchHistoryPresenter.loadListData(TAG_LOG);
                }
            });
        }
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHistoryPresenter.loadListData(TAG_LOG);
            }
        });
    }

    private void setClickListener() {
        ibLeftbtn.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tagGroupHot.setOnTagClickListener(this);
        tagGroupHistory.setOnTagClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_leftbtn:
                finish();
                break;
            case R.id.tv_search:
                String k=etSearch.getText()+"";
                Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                intent.putExtra("_searchKey", k);
                startActivity(intent);
                break;
            case R.id.tv_clear:
//                showToast("");
                break;

        }
    }

    @Override
    public void onTagClick(String tag) {
        Intent intent = new Intent();
        intent.setClass(mContext, SearchActivity.class);
        intent.putExtra(SearchActivity.SEARCH_INPUT, tag);
        finish();
        startActivity(intent);
        // activity开启无动画
        overridePendingTransition(0, 0);

    }

    @Override
    public void refreshListData(NetBaseEntity<SearchHistoryListEntity> responseHomeListEntity) {
        SearchHistoryListEntity searchHistoryListEntity = responseHomeListEntity.getRes();
        if(searchHistoryListEntity != null){
            ArrayList<SearchItemEntity> searchHotList = searchHistoryListEntity.getHotsearch();
            ArrayList<SearchItemEntity> searchHistoryList = searchHistoryListEntity.getHistorysearch();

            ArrayList<String> hotSearchList = new ArrayList<>();
            ArrayList<String> historySearchList = new ArrayList<>();

            if(searchHistoryList != null && searchHistoryList.size() > 0){
                for (SearchItemEntity item :
                        searchHistoryList) {
                    historySearchList.add(item.getTitle());
                }
            }

            if(searchHotList != null && searchHotList.size() > 0){
                for (SearchItemEntity item :
                        searchHotList) {
                    hotSearchList.add(item.getTitle());
                }
            }

            tagGroupHot.setTags(hotSearchList);
            tagGroupHistory.setTags(historySearchList);
        }
    }
}
