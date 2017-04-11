package com.yqx.mamajh.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.bumptech.glide.Glide;
import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.Presenter.HomeListPresenter;
import com.yqx.mamajh.Presenter.impl.HomeListPresenterImpl;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.CreditActivity;
import com.yqx.mamajh.activity.HotShopActivity;
import com.yqx.mamajh.activity.MySearchActivity;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.activity.SearchActivity;
import com.yqx.mamajh.activity.SearchLocationActivity;
import com.yqx.mamajh.activity.ShopActivity;
import com.yqx.mamajh.activity.SpecialtyTodayActivity;
import com.yqx.mamajh.activity.StoreActivity;
import com.yqx.mamajh.activity.StoreNewActivity;
import com.yqx.mamajh.adapter.TitleGroupAdapter;
import com.yqx.mamajh.base.BaseFragment;
import com.yqx.mamajh.base.BaseWebActivity;
import com.yqx.mamajh.base.Constants;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.ShopList;
import com.yqx.mamajh.network.RetrofitInterface;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.services.LocationService;
import com.yqx.mamajh.view.HomeListView;
import com.yqx.mamajh.widget.LoadMoreListView;
import com.yqx.mamajh.widget.cyclebanner.ADInfo;
import com.yqx.mamajh.widget.cyclebanner.ImageCycleView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by young on 2016/11/6.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, HomeListView, ImageCycleView.ImageCycleViewListener, AbsListView.OnScrollListener, AdapterView.OnItemClickListener, View.OnClickListener {


    private static final int SERVICE_STOP = 0x01;
    @BindView(R.id.fragment_list_list_view)
    LoadMoreListView mListView;
    @BindView(R.id.fragment_list_swipe_layout)
    XSwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_rl)
    FrameLayout homeRl;

    @BindView(R.id.ll_location)
    LinearLayout locationSearchRootView; //地址跟搜索的root布局
    @BindView(R.id.rl_location)
    RelativeLayout locationRootView; //地址root布局
    @BindView(R.id.tv_location)
    TextView locationView; //地址显示布局
    @BindView(R.id.ll_search_small)
    LinearLayout searchSmallView; //地址跟搜索的搜索布局
    @BindView(R.id.fl_title_search_bg)
    FrameLayout searchBgView; //搜索的背景布局
    @BindView(R.id.ll_search)
    LinearLayout searchView; //搜索布局

    private com.yqx.mamajh.location.LocationService locationService;

    private int headerHeight = 0;
    private int bannerHeight;
    private ImageCycleView imgs;//轮播
    private GridView group;//八个可点项
    private TextView informationView;//提示信息


    private List<HomeInfoEntity.BlowshopFourAdEntity> navigationList;

    private int mCurrentPage;
    private ListViewDataAdapter<HomeInfoEntity.HotshopEntity> shopAdapter;
    private HomeListPresenter mHomeListPresenter;
    public static String y/* = "39.928538"*/;
    public static String x/* = "116.521065"*/;
    public static String location;
    public String firstLocation;
    private Call<NetBaseEntity<HomeInfoEntity>> call;

    private LocationService mLocationService;
    private boolean isBound;

    private String province;
    public static String city;
    private String street;
    private TitleGroupAdapter groupAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HomeFragment.SERVICE_STOP:
//                    if(isBound){
//                        isBound = false;
//                        mContext.unbindService(conn);
//                        mLocationService.stopSelf();
                    locationService.unregisterListener(mListener); //注销掉监听
                    locationService.stop(); //停止定位服务
                    location = (String) msg.obj;
                    firstLocation=location;
                    locationView.setText(location);
                    hideLoading();
                    initData();
//                    Toast.makeText(getActivity(),""+x+","+y,Toast.LENGTH_SHORT).show();
//                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onFirstUserVisible() {

        mCurrentPage = 1;
        initLocation();
        initSwipeColor();
        setClickListener();

        initHead();
        setAnomalyParams();

        initProductAdapter();

        mListView.setOnScrollListener(this);
        mHomeListPresenter = new HomeListPresenterImpl(mContext, this, call);
        initData();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
//                showToast(""+shopAdapter.getItem((int) id).getID());
                bundle.putString(ShopActivity.IDBUNDLE, shopAdapter.getItem((int) id).getID());
                readyGo(StoreActivity.class, bundle);
            }
        });
    }

    private void setClickListener() {
        searchView.setOnClickListener(this);
        searchSmallView.setOnClickListener(this);
        locationRootView.setOnClickListener(this);
    }

    private void initData() {
        if(NetUtils.isNetworkConnected(mContext)){
            if (null != mSwipeRefreshLayout) {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showLoading("加载中...",true);
                        mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA,
                                mCurrentPage,x, y , false);
                    }
                }, Constants.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        }else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //错误后的点击屏幕的处理
                    mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA,
                            mCurrentPage, x, y, false);
                }
            });
        }
    }

    private void initLocation() {
        showLoading(mContext.getString(R.string.common_loading_message), true);
//        isBound = mContext.bindService(new Intent(mContext, LocationService.class), conn, Context.BIND_AUTO_CREATE);

        // -----------location config ------------
        locationService = ((AppApplication) (getActivity().getApplication())).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
//        int type = getIntent().getIntExtra("from", 0);
//        if (type == 0) {
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
//            locationService.setLocationOption(locationService.getOption());
//        }
        locationService.start();// 定位SDK
    }

    @Override
    public void showError(String msg) {
        mCurrentPage = 1;
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA,
                        mCurrentPage, x, y, false);
            }
        });
    }

    @Override
    protected void onUserVisible() {
//        Toast.makeText(getActivity(),"vis0",Toast.LENGTH_SHORT).show();
        if (firstLocation!=location){
            locationView.setText(location);
            mCurrentPage = 1;
            mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentPage,x, y, true);
            firstLocation=location;
//            Toast.makeText(getActivity(),""+location+","+x+","+y,Toast.LENGTH_SHORT).show();
        }

    }

    private void initSwipeColor() {
        //配置刷新颜色
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initHead() {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = layoutInflater.inflate(R.layout.fragment_home_list_head, mListView,false);
        mListView.addHeaderView(headerView);

        imgs = ButterKnife.findById(headerView, R.id.icv_imgs);
        group = ButterKnife.findById(headerView, R.id.gv_group);
        informationView = ButterKnife.findById(headerView, R.id.tv_inoftmation);

        navigationList = new ArrayList<>();
        fillData();
        groupAdapter = new TitleGroupAdapter(mContext, navigationList);
        group.setAdapter(groupAdapter);
        group.setOnItemClickListener(this);

    }

    private void fillData() {
        HomeInfoEntity.BlowshopFourAdEntity entitySale = new HomeInfoEntity.BlowshopFourAdEntity();
        entitySale.setTitle("门店特卖");
        entitySale.setIcon(R.mipmap.sale);

        HomeInfoEntity.BlowshopFourAdEntity entityDictionary = new HomeInfoEntity.BlowshopFourAdEntity();
        entityDictionary.setTitle("育儿宝典");
        entityDictionary.setIcon(R.mipmap.canon);

        HomeInfoEntity.BlowshopFourAdEntity entityHot = new HomeInfoEntity.BlowshopFourAdEntity();
        entityHot.setTitle("热销店铺");
        entityHot.setIcon(R.mipmap.shop);

        HomeInfoEntity.BlowshopFourAdEntity entityIntegration = new HomeInfoEntity.BlowshopFourAdEntity();
        entityIntegration.setTitle("积分兑换");
        entityIntegration.setIcon(R.mipmap.integral);

        navigationList.add(entitySale);
        navigationList.add(entityDictionary);
        navigationList.add(entityHot);
        navigationList.add(entityIntegration);

    }

    private void initProductAdapter() {
        shopAdapter = new ListViewDataAdapter<HomeInfoEntity.HotshopEntity>(new ViewHolderCreator<HomeInfoEntity.HotshopEntity>() {
            @Override
            public ViewHolderBase createViewHolder(int position) {
                return new ViewHolderBase<HomeInfoEntity.HotshopEntity>() {

                    ImageView img;
                    TextView title;
                    TextView money;
                    RatingBar ratingBar;
                    TextView sales;
                    TextView distance;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {

                        View convertView = layoutInflater.inflate(R.layout.item_home_list, null);

                        img = ButterKnife.findById(convertView, R.id.iv_img_item);
                        title = ButterKnife.findById(convertView, R.id.tv_item_title);
                        ratingBar = ButterKnife.findById(convertView, R.id.ratingBar);
                        sales = ButterKnife.findById(convertView, R.id.tv_sales);
                        money = ButterKnife.findById(convertView, R.id.tv_money);
                        distance = ButterKnife.findById(convertView, R.id.tv_distance);

                        return convertView;
                    }

                    @Override
                    public void showData(final int position, final HomeInfoEntity.HotshopEntity itemData) {
                        if(itemData != null){
                            Glide.with(mContext).load(itemData.getLogo()).crossFade().into(img);
                            title.setText(itemData.getName());
                            ratingBar.setRating(Float.parseFloat(itemData.getMark()));
                            sales.setText("月售"+itemData.getSaleTotal()+"单");
                            money.setText("配送费"+itemData.getDistributionFee()+"元/满"+itemData.getStartingFee()+"元免配送费");
                            distance.setText(itemData.getDistance());
                        }

                    }
                };
            }
        });
        mListView.setAdapter(shopAdapter);

    }

    private void setAnomalyParams() {
        bannerHeight = (int) (mScreenWidth * (208.0 / 483));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mScreenWidth, (int) (mScreenWidth * (208.0 / 483)));
        imgs.setLayoutParams(layoutParams);
    }

    @Override
    protected void onUserInvisible() {



    }

    @Override
    protected View getLoadingTargetView() {
        return homeRl;
    }

    @Override
    protected void initViewsAndEvents() {


    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void onRefresh() {

        mCurrentPage = 1;
        mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentPage,x, y, true);
//        Toast.makeText(getActivity(),""+x+","+y,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void refreshListData(NetBaseEntity<HomeInfoEntity> responseHomeListEntity) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        HomeInfoEntity homeInfoEntity = responseHomeListEntity.getRes();
        if(homeInfoEntity != null){

            fillCarousel(homeInfoEntity.getCarouselad());//填充轮播图

            shopAdapter.getDataList().clear();
            shopAdapter.getDataList().addAll(homeInfoEntity.getHotshop());
            shopAdapter.notifyDataSetChanged();

//            if(homeInfoEntity.getBlowshop_four_ad() != null && homeInfoEntity.getBlowshop_four_ad().size() > 0){
//                navigationList.clear();
//                navigationList.addAll(homeInfoEntity.getBlowshop_four_ad());
//                groupAdapter.notifyDataSetChanged();
//            }

        }

//        if (null != mListView) {
//            if (responseHomeListEntity.getHeadlines().size() >= Constants.PAGE_SIZE) {
//                mListView.setCanLoadMore(true);
//            } else {
//                mListView.setCanLoadMore(false);
//            }
//        }

    }

    private void fillCarousel(List<HomeInfoEntity.CarouseladEntity> carouselad) {
        ArrayList<ADInfo> adInfos = new ArrayList<>();
        if(carouselad != null && carouselad.size() > 0){
//            for (HomeInfoEntity.CarouseladEntity carouseladentity :
//                    carouselad) {
            for(int i=0;i<carouselad.size();i++){
                ADInfo adInfo = new ADInfo();
                adInfo.setId(carouselad.get(i).getProductid());
                adInfo.setContent(carouselad.get(i).getTitle());
                adInfo.setUrl(carouselad.get(i).getImg());
                adInfo.setType(carouselad.get(i).getLinktype());

                adInfos.add(adInfo);
            }
        }
        imgs.setImageResources(adInfos, this);
    }

    @Override
    public void addMoreListData(NetBaseEntity<HomeInfoEntity> responseHomeListEntity) {

    }

    @Override
    public void displayImage(String imageURL, ImageView imageView) {
        if(!CommonUtils.isEmpty(imageURL)){
            Glide.with(mContext).load(imageURL).crossFade().into(imageView);
        }
    }
    /*
    * 轮播图片点击事件
    * */
    @Override
    public void onImageClick(ADInfo info, int postion, View imageView) {
//        showToast("点击轮播"  + info.getId());
//        Intent intent=new Intent(getActivity(),ProductInfoActivity.class);
//        intent.putExtra("_id",225);
//        startActivity(intent);
    }

    @Override
    public void onDestroy() {
//        try {
//            if(isBound)
//                mContext.unbindService(conn);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onDestroy();
    }

    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLocationService = ((LocationService.LocationBinder) service).getService();
            mLocationService.setOnGetLocationListener(new LocationService.OnGetLocationListener() {
                @Override
                public void getLocation(final String lastLatitude, final String lastLongitude, final String latitude, final String longitude, final String country, final String locality, final String street) {
                    if (!"loading...".equals(street)){

                        y = lastLatitude;
                        x = lastLongitude;

                        Message message = new Message();
                        message.what = HomeFragment.SERVICE_STOP;
                        message.obj = street;
                        handler.sendMessage(message);
                    }
                    Message message = new Message();
                    message.what = HomeFragment.SERVICE_STOP;
                    message.obj = street;
                    handler.sendMessage(message);
                }
            });
        }
    };
    private boolean isBottom;
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

        switch (i) {
            case SCROLL_STATE_FLING:
                //Log.i("info", "SCROLL_STATE_FLING");
                break;
            case SCROLL_STATE_IDLE:
                if (isBottom) {
                    mCurrentPage ++;
//                    showToast(mCurrentPage+"");
                    mHomeListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentPage,x, y, true);

                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem+visibleItemCount == totalItemCount){
            //Log.i("info", "到底了....");
            isBottom = true;
        }else{
            isBottom = false;
        }

        View sview = mListView.getChildAt(0);
        if(null != sview) {
            int top = -(sview.getTop());
            headerHeight = sview.getHeight()-270;
            Log.d("top+headerHeight",top +"..."+ headerHeight);
            if (top > 0 && top <= headerHeight && firstVisibleItem==0) {
                changeState(View.GONE, View.VISIBLE);
                float scale = (float) top / headerHeight;
                float alpha = (255 * scale);
                searchBgView.getBackground().setAlpha((int)alpha);
                searchView.getBackground().setAlpha((int)alpha);
            }else if(top == 0){
                changeState(View.VISIBLE, View.GONE);
                searchBgView.getBackground().setAlpha(0);
                searchView.getBackground().setAlpha(0);
            }
            else if(firstVisibleItem > 0){
                changeState(View.GONE, View.VISIBLE);
                searchBgView.getBackground().setAlpha(255);
                searchView.getBackground().setAlpha(255);
            }
        }
    }

    private void changeState(int locationState, int searchState) {
        locationSearchRootView.setVisibility(locationState);
        searchBgView.setVisibility(searchState);
    }

    //轮播下边四个点击
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                readyGo(SpecialtyTodayActivity.class);
                break;
            case 1:
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "育儿宝典");
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, "http://m.mamajh.com/Knowledge.aspx");
                bundle.putBoolean(BaseWebActivity.BUNDLE_KEY_SHOW_BOTTOM_BAR, false);
                readyGo(BaseWebActivity.class, bundle);
                break;
            case 2:
                readyGo(HotShopActivity.class);
                break;
            case 3:
                readyGo(CreditActivity.class);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_search:
                readyGo(SearchActivity.class);
                break;
            case R.id.ll_search_small:
                readyGo(SearchActivity.class);
                break;
            case R.id.rl_location:
                Intent intent=new Intent(getActivity(), SearchLocationActivity.class);
                intent.putExtra("city",city);
                firstLocation=location;
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                /*StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                *//**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 *//*
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
//                logMsg(sb.toString());*/

                BigDecimal bigDecimalLatitude = new BigDecimal(location.getLatitude());
                BigDecimal bigDecimalLongitude = new BigDecimal(location.getLongitude());

                y = bigDecimalLatitude.toString();
                x = bigDecimalLongitude.toString();

                province=location.getProvince();
                city=location.getCity();
                street=location.getStreet();

                Message message = new Message();
                message.what = HomeFragment.SERVICE_STOP;
                message.obj = location.getProvince()+location.getCity()+location.getStreet();
                handler.sendMessage(message);

                hideLoading();
            }
        }

        public void onConnectHotSpotMessage(String s, int i){
        }
    };

}
