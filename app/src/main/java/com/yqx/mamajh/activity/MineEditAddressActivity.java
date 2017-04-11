package com.yqx.mamajh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.SouSuoDiZhiAdapter;
import com.yqx.mamajh.base.BaseActivity;
import com.yqx.mamajh.bean.DeliveryAddress;
import com.yqx.mamajh.bean.DeliveryInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SouSuoDiZhi;
import com.yqx.mamajh.dbcity.City;
import com.yqx.mamajh.dbcity.CitySelectActivity;
import com.yqx.mamajh.fragment.HomeFragment;
import com.yqx.mamajh.network.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MineEditAddressActivity extends BaseActivity {

    @BindView(R.id.et_address_name)
    EditText        etAddressName;
    @BindView(R.id.et_address_phone)
    EditText        etAddressPhone;
    @BindView(R.id.et_address_phone2)
    EditText        etAddressPhone2;
    @BindView(R.id.et_address_detail)
    EditText        etAddressDetail;
    @BindView(R.id.et_address_zipcode)
    EditText        etAddressZipcode;
    @BindView(R.id.btn_address_submit)
    Button btnAddressSubmit;
    @BindView(R.id.tv_province)
    TextView tvProvince;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_goForAddress)
    TextView tvGoForAddress;
    @BindView(R.id.tv_nextAddress)
    EditText tvNextAddress;
    @BindView(R.id.lv_editAddress)
    ListView lvEditAddress;

    public final static String KEY_ENTITY = "entity";


    private DeliveryAddress mAddress = null;

    private MaterialDialog mMaterialDialog = null;
    private String provinceName;
    private int provinceId=0;
    private String cityName;
    private int cityId=0;
    private String areaName;
    private int areaId=0;
    private int id;
    private String title;
    private String buttonText;
    private PoiSearch poiSearch;
    private SouSuoDiZhiAdapter souSuoDiZhiAdapter;
    private PoiCitySearchOption poiCitySearchOption;
    private String needX;
    private String needY;
    private String firstAddress;
    private String secondAddress;

    @Override
    protected void getBundleExtras(Bundle extras) {
        id=extras.getInt("id",0);
        Toast.makeText(MineEditAddressActivity.this,id+"",Toast.LENGTH_SHORT).show();
        title=extras.getString("title");
        buttonText=extras.getString("buttonText");
        if (NetUtils.isNetworkConnected(this)){
            poiSearch= PoiSearch.newInstance();
            poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine_edit_address;
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
        setTitle(""+title);
        btnAddressSubmit.setText(buttonText+"");
//        etAddressDetail.addTextChangedListener(watcher);
//        showToast(""+id);
        Call<DeliveryInfo> initCall=RetrofitService.getInstance().getDeliveryInfo(AppApplication.TOKEN,id);
        initCall.enqueue(new Callback<DeliveryInfo>() {
            @Override
            public void onResponse(Response<DeliveryInfo> response, Retrofit retrofit) {
                if (response.body()==null){
//                    showToast("return");
                    return;
                }
                if (response.body().getStatus()==0){
                    DeliveryInfo.DeliveryInfoRes res = response.body().getRes();
//                    showToast(""+res.getName());
                    etAddressName.setText(res.getName());
                    etAddressPhone.setText(res.getPhone());
                    etAddressPhone2.setText(res.getPhone2());
                    provinceId=Integer.parseInt(res.getProvinceid());
                    cityId=Integer.parseInt(res.getCityid());
                    areaId=Integer.parseInt(res.getAreaid());
                    initAddress(res.getProvinceid(), 0, tvProvince);
                    initAddress(res.getCityid(), provinceId, tvCity);
                    initAddress(res.getAreaid(), cityId, tvArea);
//                tvAddressDistrict.setText(mAddress.getArea());
                    if (res.getAddress().contains("|")){
                        String[] threeAddress=(res.getAddress()+"").split("\\|");
//                        for (int i=0;i<threeAddress.length;i++){
//                        }
//                        Toast.makeText(MineEditAddressActivity.this,threeAddress[2]+"",Toast.LENGTH_SHORT).show();
                        if (threeAddress.length==3){
                            tvGoForAddress.setText(threeAddress[0]+"");
                            tvNextAddress.setText(threeAddress[1]+"");
                            etAddressDetail.setText(threeAddress[2]+"");
                        }else{
                            showToast("地址错误");
                        }
                    }
                    etAddressZipcode.setText(res.getPostcode());
                    needX=res.getX()+"";
                    needY=res.getY()+"";

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });





//        if (getIntent().getExtras() != null) {
//            mAddress = (DeliveryAddress) getIntent().getExtras().getSerializable(KEY_ENTITY);
//            if (mAddress != null) {
//                etAddressName.setText(mAddress.getName());
//                etAddressPhone.setText(mAddress.getPhone());
//                etAddressPhone2.setText(mAddress.getPhone2());
////                tvAddressDistrict.setText(mAddress.getArea());
//                showToast(""+mAddress.getArea());
//                etAddressDetail.setText(mAddress.getAddress());
//                etAddressZipcode.setText(mAddress.getPostcode());
//            }
//        }

    }
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().trim().equals("")){
                if ("市".equals(tvCity.getText()+"")){
                    Toast.makeText(MineEditAddressActivity.this,"请先选择省市区",Toast.LENGTH_SHORT).show();
                }else{
                    poiCitySearchOption= new PoiCitySearchOption().city(tvCity.getText()+"").keyword(etAddressDetail.getText()+"").pageCapacity(100);
                    poiSearch.searchInCity(poiCitySearchOption);
                    btnAddressSubmit.setVisibility(View.GONE);
                }
            }
        }
    };

    OnGetPoiSearchResultListener onGetPoiSearchResultListener=new OnGetPoiSearchResultListener(){
        @Override
        public void onGetPoiResult(PoiResult poiResult){
            ArrayList<SouSuoDiZhi> diZhiArrayList=new ArrayList<SouSuoDiZhi>();
            if (poiResult.getAllPoi()!=null){
                for (int d=0;d<poiResult.getAllPoi().size();d++) {
                    if (poiResult.getAllPoi().get(d).location != null) {
                        SouSuoDiZhi diZhi = new SouSuoDiZhi(poiResult.getAllPoi().get(d).location.latitude + "",
                                poiResult.getAllPoi().get(d).location.longitude + "",
                                poiResult.getAllPoi().get(d).name + "",
                                poiResult.getAllPoi().get(d).address + "");
                        diZhiArrayList.add(diZhi);
                    }
                    setSouSuoDiZhiAdapter(diZhiArrayList);
                }
            }else{
                Toast.makeText(MineEditAddressActivity.this,"地址无效，请尝试更改",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };

    private void initAddress(final String initAddressId, int findID, final TextView tv) {
        Call<City> call=RetrofitService.getInstance().getAreaDictionary(findID);
        call.enqueue(new Callback<City>() {
            public String returnName;
            @Override
            public void onResponse(Response<City> response, Retrofit retrofit) {
                if (response.body()==null){
                    return ;
                }
                if (response.body().getStatus()==0){
                    for (int i=0;i<response.body().getRes().size();i++){
                        if ((response.body().getRes().get(i).getId()+"").equals(initAddressId)){
                            tv.setText(response.body().getRes().get(i).getName()+"");
                            switch (tv.getId()){
                                case R.id.tv_province:
                                    provinceName=response.body().getRes().get(i).getName()+"";
                                    break;
                                case R.id.tv_city:
                                    cityName=response.body().getRes().get(i).getName()+"";
                                    break;
                                case R.id.tv_area:
                                    areaName=response.body().getRes().get(i).getName()+"";
                                    break;
                            }
                        }
                    }
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
        switch (requestCode){
            case 1010:
                if (data!=null) {
//                Toast.makeText(MineEditAddressActivity.this,""+data.getStringExtra("_address"),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MineEditAddressActivity.this,""+data.getStringExtra("_location"),Toast.LENGTH_SHORT).show();
                    firstAddress = data.getStringExtra("_location") + "";
                    secondAddress = data.getStringExtra("_address") + "";
                    tvGoForAddress.setText(firstAddress + "");
                    tvNextAddress.setText(secondAddress + "");
                    needX = data.getStringExtra("_X");
                    needY = data.getStringExtra("_Y");
//                Toast.makeText(MineEditAddressActivity.this,""+data.getStringExtra("_X"),Toast.LENGTH_SHORT).show();
//                Toast.makeText(MineEditAddressActivity.this,""+data.getStringExtra("_Y"),Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @OnClick({R.id.btn_address_submit, R.id.tv_province, R.id.tv_city,R.id.tv_area,R.id.tv_goForAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goForAddress:
                Intent intent=new Intent(MineEditAddressActivity.this,AddAddressSearchActivity.class);
                startActivityForResult(intent,1010);
                break;
            case R.id.tv_province:
                setAddress(0,"省份");
                break;
            case R.id.tv_city:
                if (provinceId!=0){
                    setAddress(provinceId,"城市");
                }else{
                    showToast("请先选择省份");
                }
                break;
            case R.id.tv_area:
                if (cityId!=0){
                    setAddress(cityId,"地区");
                }else{
                    showToast("请先选择城市");
                }
                break;
            case R.id.btn_address_submit:
                String name = etAddressName.getText().toString().trim();
                String phone = etAddressPhone.getText().toString().trim();
                String phone2 = etAddressPhone2.getText().toString().trim();
                String district = tvProvince.getText()+" "+tvCity.getText()+" "+tvArea.getText();
                String addFirst= tvGoForAddress.getText()+"";
                String addSecond=tvNextAddress.getText()+"";
                String detail = etAddressDetail.getText().toString().trim();
                String zipcode = etAddressZipcode.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("姓名不能为空");
                    break;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号码不能为空");
                    break;
                }
                if (phone.length()!=11){
                    showToast("手机号码格式不正确");
                    break;
                }
//                if (TextUtils.isEmpty(district)) {
//                    showToast("所在区域不能为空");
//                    break;
//                }
//                if (TextUtils.isEmpty(provinceName)) {
//                    showToast("所在省份不能为空");
//                    break;
//                }
//                if (TextUtils.isEmpty(cityName)) {
//                    showToast("所在城市不能为空");
//                    break;
//                }
//                if (TextUtils.isEmpty(areaName)) {
//                    showToast("所在区/县不能为空");
//                    break;
//                }
                if (TextUtils.isEmpty(detail)) {
                    showToast("详细地址不能为空");
                    break;
                }
                if (TextUtils.isEmpty(addFirst)) {
                    showToast("地址栏1不能为空");
                    break;
                }
                if (TextUtils.isEmpty(addSecond)) {
                    showToast("地址栏2不能为空");
                    break;
                }
//                if (TextUtils.isEmpty(zipcode)) {
//                    showToast("邮政编码不能为空");
//                    break;
//                }
                if (mAddress == null) {
                    mAddress = new DeliveryAddress();
                }
                mAddress.setName(name);
                mAddress.setPhone(phone);
                mAddress.setPhone2(phone2);
                mAddress.setArea(/*areaId+*/"0");
                mAddress.setAddress(addFirst+"|"+addSecond+"|"+detail);
                mAddress.setPostcode(zipcode);
                submitAddress();
                break;
        }
    }

    private void setAddress(int addressId, final String address) {
        Call<City> provinceCall=RetrofitService.getInstance().getAreaDictionary(addressId);
        provinceCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Response<City> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    List<City.CityRes> provinceList = response.body().getRes();
                    String[] items=new String[provinceList.size()];
                    for(int i=0;i<provinceList.size();i++){
                        items[i]=provinceList.get(i).getName();
                    }
                    int[] ids=new int[provinceList.size()];
                    for(int j=0;j<provinceList.size();j++){
                        ids[j]=provinceList.get(j).getId();
                    }
                    showCityDialog(items,ids,address+"");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void showCityDialog(final String[] items, final int[] ids, final String address) {
        new AlertDialog.Builder(MineEditAddressActivity.this)
                .setTitle("请选择"+address)
                .setItems(items, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (address){
                            case "省份":
                                tvProvince.setText(items[which]);
                                provinceName=items[which];
                                provinceId=ids[which];
//                                if (id!=0){
                                    tvCity.setText("市");
                                    cityName="";
                                    cityId=0;
                                    tvArea.setText("区/县");
                                    areaId=0;
                                    areaName="";
//                                }
                                dialog.dismiss();
                                break;
                            case "城市":
                                tvCity.setText(items[which]);
                                cityName=items[which];
                                cityId=ids[which];
//                                if (id!=0){
                                    tvArea.setText("区/县");
                                    areaId=0;
                                    areaName="";
//                                }
                                dialog.dismiss();
                                break;
                            case "地区":
                                tvArea.setText(items[which]);
                                areaName=items[which];
                                areaId=ids[which];
//                                showToast(""+areaName+areaId);
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * updateid	Int	是	1	配送地址ID
     * Name	String	是	李华	收货人
     * address	String	是	北京三里屯k吧	收货地址
     * Phone	String	是	138776222	移动电话
     * Phone2	String	否	400-820820	固定电话
     * PostCode	String	否	3322000	邮编
     * areaID	Int	是	1	地区ID
     */
    private void submitAddress() {
        mMaterialDialog = new MaterialDialog.Builder(MineEditAddressActivity.this)
                .content(R.string.loading)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
        Map<String, String> params = new HashMap<>();
        params.put("updateid", id + "");
        params.put("name", mAddress.getName());
        params.put("address", mAddress.getAddress());
        params.put("phone", mAddress.getPhone());
        params.put("PostCode", mAddress.getPostcode());
        params.put("areaid", mAddress.getArea());
        params.put("postCode", mAddress.getPostcode());
        params.put("x", needX);
        params.put("y", needY);
        if (!TextUtils.isEmpty(mAddress.getPhone2())) {
            params.put("phone2", mAddress.getPhone2());
        }
        Call<NetBaseEntity> call = RetrofitService.getInstance().saveDeliveryInfo(AppApplication.TOKEN, params);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body().getStatus() == 0) {
                    showToast(response.body().getMes());
                    finish();
                } else {
                    showToast(response.body().getMes());
                }
                mMaterialDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                mMaterialDialog.dismiss();
            }
        });
    }
    private void setSouSuoDiZhiAdapter(ArrayList<SouSuoDiZhi> diZhiArrayList) {
        souSuoDiZhiAdapter=new SouSuoDiZhiAdapter(diZhiArrayList,MineEditAddressActivity.this);
        lvEditAddress.setAdapter(souSuoDiZhiAdapter);

        souSuoDiZhiAdapter.setOnItemClickSetDiZhi(new SouSuoDiZhiAdapter.OnItemClickSetDiZhi() {
            @Override
            public void OnItemClickSetDiZhi(String location, String address, String x, String y) {
//                Toast.makeText(SearchLocationActivity.this,""+location+","+x+","+y,Toast.LENGTH_SHORT).show();
//                HomeFragment.x=y;
//                HomeFragment.y=x;
                needX=y.trim();
                needY=x.trim();
                etAddressDetail.setText(location+"");
                lvEditAddress.setVisibility(View.GONE);
                btnAddressSubmit.setVisibility(View.VISIBLE);
            }
        });
    }

}
