package com.yqx.mamajh.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.yqx.mamajh.Presenter.CategoryListPresenter;
import com.yqx.mamajh.Presenter.impl.CategoryListPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.ClassificationListAdapter;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.bean.CategoryBannerItemEntity;
import com.yqx.mamajh.bean.CategoryListEntity;
import com.yqx.mamajh.bean.ClassifivationInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.view.ClassificationView;
import com.yqx.mamajh.widget.cyclebanner.ADInfo;
import com.yqx.mamajh.widget.cyclebanner.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;

/**
 * Created by young on 2016/11/6.
 * 分类
 */

public class ClassificationFragment extends BaseFragment implements ClassificationView, ImageCycleView.ImageCycleViewListener {

    @BindView(R.id.class_listview)
    ListView classListview;
    @BindView(R.id.ll_class_root)
    LinearLayout rootView;


    private ImageCycleView imgs;//轮播
    private CategoryListPresenter categoryListPresenter;
    private Call<NetBaseEntity<ClassifivationInfoEntity>> call;
    private ArrayList<CategoryListEntity> listEntities;
    private ClassificationListAdapter listAdapter;

    @Override
    protected void onFirstUserVisible() {

        categoryListPresenter = new CategoryListPresenterImpl(mContext, this, call);
        initAdapter();
        addHeader();
        setAnomalyParams();
        loadData();

    }

    private void addHeader() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = layoutInflater.inflate(R.layout.fragment_class_list_head, classListview,false);
        classListview.addHeaderView(headerView);

        imgs = ButterKnife.findById(headerView, R.id.icv_imgs);
    }

    private void setAnomalyParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth, (int) (mScreenWidth * (208.0 / 483)));
        imgs.setLayoutParams(layoutParams);
    }

    private void initAdapter() {
        listEntities = new ArrayList<>();
        listAdapter = new ClassificationListAdapter(mContext, listEntities);
        classListview.setAdapter(listAdapter);
    }

    private void loadData() {

        if(NetUtils.isNetworkConnected(mContext)){
            showLoading("",true);
            categoryListPresenter.loadListData(TAG_LOG);
        }else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                    categoryListPresenter.loadListData(TAG_LOG);

                }
            });
        }
    }

    @Override
    public void showError(String msg) {
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryListPresenter.loadListData(TAG_LOG);
            }
        });
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return classListview;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_classification;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void addLoadListData(NetBaseEntity<ClassifivationInfoEntity> responseHomeListEntity) {

        List<CategoryListEntity> categoryListEntities = responseHomeListEntity.getRes().getCatelist();
        List<CategoryBannerItemEntity> categoryBannerItemEntities = responseHomeListEntity.getRes().getBannerlist();

        if(categoryBannerItemEntities != null && categoryBannerItemEntities.size() > 0){
            fillCarousel(categoryBannerItemEntities);
        }

        if(categoryListEntities != null && categoryListEntities.size() > 0){
            listEntities.addAll(categoryListEntities);
            listAdapter.notifyDataSetChanged();
        }

    }

    private void fillCarousel(List<CategoryBannerItemEntity> categoryBannerItemEntities) {
        ArrayList<ADInfo> adInfos = new ArrayList<>();
        for (CategoryBannerItemEntity itemEntity :
                categoryBannerItemEntities) {
            ADInfo adInfo = new ADInfo();
            adInfo.setId(itemEntity.getProductid());
            adInfo.setContent(itemEntity.getTitle());
            adInfo.setUrl(itemEntity.getImg());
            adInfo.setType(itemEntity.getLinktype());

            adInfos.add(adInfo);
        }
        imgs.setImageResources(adInfos, this);
    }

    @Override
    public void displayImage(String imageURL, ImageView imageView) {
        if(!CommonUtils.isEmpty(imageURL) && imageView != null){
            Glide.with(mContext).load(imageURL).crossFade().into(imageView);
        }
    }

    @Override
    public void onImageClick(ADInfo info, int postion, View imageView) {
        showToast("点击轮播"  + info.getId());
    }

    @Override
    public void onDestroy() {
        if(call != null){
            call.cancel();
        }
        super.onDestroy();
    }
}
