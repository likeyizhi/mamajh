package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.LoactionCityAdapter;
import com.yqx.mamajh.adapter.SouSuoDiZhiAdapter;
import com.yqx.mamajh.bean.LocationCity;
import com.yqx.mamajh.bean.SouSuoDiZhi;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.network.RetrofitService;
import com.yqx.mamajh.widget.SideBar;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/4/10.
 */

public class AddAddressSearchActivity extends Activity{
    private EditText etSearchLocation;
    private TextView tvCity;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    boolean isFirstLoc = true;// 是否首次定位
    private ListView lvAddAddress;
    private LocationClient locationClient;
    private BDLocationListener locationListener=new MyLocationListener();
    private double latitude,latitudeLocation;
    private double longitude,longitudeLocation;
    private String addressLocation;
    private GeoCoder search=null;
    private String currentCity;
    private PoiSearch poiSearch;
    private PoiCitySearchOption poiCitySearchOption;
    private SouSuoDiZhiAdapter souSuoDiZhiAdapter;
    private String chooseX;
    private String chooseY;
    private String chooseLocation;
    private String chooseAddress;
    private ImageButton ibBack;
    public static AddAddressSearchActivity addAddressSearchActivity=null;
    private ImageButton ibGoLast;
    private String firstGetAddress;
    private LinearLayout llCity;
    private SideBar sidrbar;
    private ListView lvLocation;
    private TextView tvDialog;
    private LinearLayout llChangeCity;
    private ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> brands;
    private LoactionCityAdapter locationAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    tvCity.setText(""+currentCity);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_search);
        addAddressSearchActivity=this;
        if (NetUtils.isNetworkConnected(this)){
            SDKInitializer.initialize(getApplicationContext());
            poiSearch= PoiSearch.newInstance();
            poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
            initView();
            setListeners();
            loadData();
            loadLocationCity();
        }else{
            Toast.makeText(this,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }
    OnGetPoiSearchResultListener onGetPoiSearchResultListener=new OnGetPoiSearchResultListener(){

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            ArrayList<SouSuoDiZhi> diZhiArrayList=new ArrayList<SouSuoDiZhi>();
            if (poiResult.getAllPoi()!=null){
                for (int d=0;d<poiResult.getAllPoi().size();d++){
                    if (poiResult.getAllPoi().get(d).location!=null){
                        SouSuoDiZhi diZhi = new SouSuoDiZhi(poiResult.getAllPoi().get(d).location.latitude+"",
                                poiResult.getAllPoi().get(d).location.longitude+"",
                                poiResult.getAllPoi().get(d).name+"",
                                poiResult.getAllPoi().get(d).address+"");
                        diZhiArrayList.add(diZhi);
                    }
                }
                setSouSuoDiZhiAdapter(diZhiArrayList);
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1111:
                if (data!=null){
                    String firstAddress = data.getStringExtra("_location") + "";
                    String secondAddress = data.getStringExtra("_address") + "";
                    String needX = data.getStringExtra("_X");
                    String needY = data.getStringExtra("_Y");
                    chooseX=needX;
                    chooseY=needY;
                    chooseLocation=firstAddress;
                    chooseAddress=secondAddress;
                    Intent intent=new Intent();
                    intent.putExtra("_location", chooseLocation);
                    intent.putExtra("_address", chooseAddress);
                    intent.putExtra("_X", chooseX);
                    intent.putExtra("_Y", chooseY);
                    setResult(1010,intent);
                    finish();
                }
                break;
        }
    }

    private void setSouSuoDiZhiAdapter(ArrayList<SouSuoDiZhi> diZhiArrayList) {
        souSuoDiZhiAdapter=new SouSuoDiZhiAdapter(diZhiArrayList,AddAddressSearchActivity.this);
        lvAddAddress.setAdapter(souSuoDiZhiAdapter);

        souSuoDiZhiAdapter.setOnItemClickSetDiZhi(new SouSuoDiZhiAdapter.OnItemClickSetDiZhi() {
            @Override
            public void OnItemClickSetDiZhi(String location, String address, String x, String y) {
                chooseX=y;
                chooseY=x;
                chooseLocation=location;
                chooseAddress=address;
                Intent intent=new Intent();
                intent.putExtra("_location", chooseLocation);
                intent.putExtra("_address", chooseAddress);
                intent.putExtra("_X", chooseX);
                intent.putExtra("_Y", chooseY);
                setResult(1010,intent);
                finish();
            }
        });
    }
    private void loadData() {
        tvCity.setText(HomeFragment.city);
    }

    private void setListeners() {
        etSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddAddressSearchActivity.this,AddAddressNoMapActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivityForResult(intent,1111);
                overridePendingTransition(0, 0);
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibGoLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCity.setText(HomeFragment.city);
                location(latitudeLocation,longitudeLocation);
            }
        });
        llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        llCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llChangeCity.getVisibility()==View.GONE){
                    llChangeCity.setVisibility(View.VISIBLE);
                }else{
                    llChangeCity.setVisibility(View.GONE);
                }
            }
        });
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = locationAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lvLocation.setSelection(position+1);
                }
            }
        });
    }
    private void loadLocationCity() {
        Call<LocationCity> call= RetrofitService.getInstance().getLocationCity();
        call.enqueue(new Callback<LocationCity>() {
            @Override
            public void onResponse(Response<LocationCity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    List<LocationCity.LocationCityRes.LocationCityList> list = response.body().getRes().getList();
                    brands=new ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList>();
                    for (int i=0;i<list.size();i++){
                        List<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> citylist = list.get(i).getCityList();
                        for (int j=0;j<citylist.size();j++){
                            LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList brand = list.get(i).getCityList().get(j);
                            brands.add(brand);
                        }
                    }
                    setLocationAdapter(brands);
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
    private void setLocationAdapter(ArrayList<LocationCity.LocationCityRes.LocationCityList.LocationCityListCityList> brands) {
        locationAdapter=new LoactionCityAdapter(AddAddressSearchActivity.this,brands);
        lvLocation.setAdapter(locationAdapter);

        locationAdapter.setOnItemClickSetChangeCity(new LoactionCityAdapter.OnItemClickSetChangeCity() {
            @Override
            public void OnItemClickSetChangeCity(int id, String name, String x, String y) {
                tvCity.setText(name+"");
                currentCity=name;
                llChangeCity.setVisibility(View.GONE);
                location(Double.parseDouble(y), Double.parseDouble(x));
            }
        });
    }

    private void initView() {
        llChangeCity=(LinearLayout)findViewById(R.id.ll_changeCity);
        lvLocation=(ListView)findViewById(R.id.lv_location);
        tvDialog=(TextView)findViewById(R.id.tv_dialog);
        sidrbar=(SideBar)findViewById(R.id.sidrbar);
        sidrbar.setTextView(tvDialog);
        llCity=(LinearLayout)findViewById(R.id.ll_city);
        ibGoLast=(ImageButton)findViewById(R.id.ib_goLast);
        ibBack=(ImageButton)findViewById(R.id.ib_back);
        lvAddAddress=(ListView)findViewById(R.id.lv_addAddress);
        etSearchLocation=(EditText)findViewById(R.id.et_search_location);
        tvCity=(TextView)findViewById(R.id.tv_city);
        mapView=(MapView)findViewById(R.id.mapView);
        baiduMap=mapView.getMap();
        baiduMap=mapView.getMap();
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(locationListener);
        initLocation();
        locationClient.start();

        /**滑屏触发地图状态改变监听器**/
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            /**屏幕中间的经纬度**/
            @Override
            public void onMapStatusChangeFinish(final MapStatus mapStatus) {
                latitude = mapStatus.target.latitude;
                longitude = mapStatus.target.longitude;
                LatLng ptCenter = new LatLng(latitude, longitude);
                search.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
            }
        });

        search= GeoCoder.newInstance();
        /**根据经纬度得到屏幕中心点地址**/
        search.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(AddAddressSearchActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                    return;
                }
                baiduMap.clear();
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
                addressLocation = result.getAddress();
//                tvAddress.setText(result.getAddress());
//                Toast.makeText(AddAddressSearchActivity.this,addressLocation,Toast.LENGTH_SHORT).show();
//                getCity();
                ArrayList<SouSuoDiZhi> diZhiArrayList=new ArrayList<SouSuoDiZhi>();
                if (result.getPoiList()!=null){
                    for (int d=0;d<result.getPoiList().size();d++){
                        if (result.getPoiList().get(d).location!=null){
                            SouSuoDiZhi diZhi = new SouSuoDiZhi(result.getPoiList().get(d).location.latitude+"",
                                    result.getPoiList().get(d).location.longitude+"",
                                    result.getPoiList().get(d).name+"",
                                    result.getPoiList().get(d).address+"");
                            diZhiArrayList.add(diZhi);
                        }
                    }
                    setSouSuoDiZhiAdapter(diZhiArrayList);
                }
            }
        });
    }

    /**接收异步返回的定位结果**/
    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
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
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.e("BaiduLocationApiDem", sb.toString());
            showCurrentPosition(location);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
    /**定位**/
    private void showCurrentPosition(BDLocation location){
        baiduMap.setMyLocationEnabled(true);
        MyLocationData locationData=new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
//        MyLocationConfiguration.LocationMode locationMode=MyLocationConfiguration.LocationMode.NORMAL;
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//        MyLocationConfiguration config = new MyLocationConfiguration(locationMode, true, mCurrentMarker);
//        baiduMap.setMyLocationConfigeration(config);
        baiduMap.setMyLocationData(locationData);
        latitudeLocation=location.getLatitude();
        longitudeLocation=location.getLongitude();
        addressLocation=location.getAddrStr();
        currentCity=location.getCity();
        firstGetAddress=location.getPoiList().get(0).getName();
        location(latitudeLocation, longitudeLocation);
    }

    /**配置定位SDK参数**/
    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
//        int span=1000;
//        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }

    /**经纬度地址动画显示在屏幕中间**/
    private void location(double latitude,double longitude){
        LatLng ll = new LatLng(latitude, longitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(msu);
//        tvAddress.setText(addressLocation);
//        Toast.makeText(AddAddressSearchActivity.this,addressLocation,Toast.LENGTH_SHORT).show();
        getCity();
    }
    /**得到当前所在城市**/
    private void getCity(){
//        if(addressLocation!=null&&!addressLocation.equals("")){
//            int indexProvince=addressLocation.indexOf("省");
//            int indexCity=addressLocation.indexOf("市");
//            currentCity=addressLocation.substring(indexProvince + 1, indexCity)+"";
//            Message message=new Message();
//            handler.sendMessage(message);
//            message.what=1;
////            tvCity.setText("北京");
////            Toast.makeText(AddAddressSearchActivity.this,addressLocation.substring(indexProvince + 1, indexCity)+"",Toast.LENGTH_SHORT).show();
////            Toast.makeText(AddAddressSearchActivity.this,"addressLocation="+addressLocation,Toast.LENGTH_SHORT).show();
//        }
        poiCitySearchOption= new PoiCitySearchOption().city(tvCity.getText()+"").keyword(addressLocation).pageNum(1).pageCapacity(100);
        poiSearch.searchInCity(poiCitySearchOption);
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
        if(locationClient!=null){
            locationClient.stop();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
