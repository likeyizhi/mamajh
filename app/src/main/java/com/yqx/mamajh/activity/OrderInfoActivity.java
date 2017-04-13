package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.obsessive.library.netstatus.NetUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.adapter.OrderInfoAdapter;
import com.yqx.mamajh.bean.CreateOrder;
import com.yqx.mamajh.bean.MemberOrder;
import com.yqx.mamajh.bean.MemberOrderInfo;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.yqx.mamajh.fragment.OngoingFragment.type;

/**
 * Created by likey on 2017/3/16.
 */

public class OrderInfoActivity extends Activity{
    private TextView tvOrderNumber;
    private TextView tvOrderTime;
    private TextView tvOrderPerson;
    private TextView tvOrderAddress;
    private ListView lvOrderList;
    private TextView tvOrderPeiSong;
    private TextView tvPayWay;
    private TextView tvOtherInfo;
    private TextView tvOrderRemark;
    private LinearLayout llThreeTV;
    private TextView tvProNumber;
    private TextView tvOrderPrice;
    private TextView tvDeliveryPrice;
    private TextView tvUnPayMoney;
    private LinearLayout llUnPay;
    private Button tvUnPayCancle;
    private Button btnToPay;
    private LinearLayout llUnDelivery;
    private Button btnUnDelCancle;
    private LinearLayout llSureGet;
    private Button btnSureGet;
    private LinearLayout llToComment;
    private Button btnToComment;
    private TextView tvAlreadyCancle;
    private TextView tvOrderState;
    private String orderId;
    private OrderInfoAdapter proAdapter;
    private TextView tvStoreName;
    private TextView tvGiftPayPrice;
    private TextView tvPromotionPrice;
    private ImageButton ib_back;
    private String shopId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_order_info);
        Intent intent=getIntent();
        orderId=intent.getStringExtra("_orderId");
        shopId=intent.getStringExtra("_shopId");
//        Toast.makeText(OrderInfoActivity.this,""+orderId,Toast.LENGTH_SHORT).show();
        initView();
        loadData();
    }
    //默认返回true
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }
    /**
     * use SytemBarTintManager
     *
     * @param tintDrawable
     */
    protected void setSystemBarTintDrawable(Drawable tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
            if (tintDrawable != null) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintDrawable(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }

    }
    private void loadData() {
        Call<MemberOrderInfo> call= RetrofitService.getInstance().memberOrderInfo(AppApplication.TOKEN,orderId+"");
        call.enqueue(new Callback<MemberOrderInfo>() {
            @Override
            public void onResponse(Response<MemberOrderInfo> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    MemberOrderInfo.MemberOrderInfoRes memRes = response.body().getRes();
                    setData(memRes);
                    setListeners(memRes);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void setListeners(final MemberOrderInfo.MemberOrderInfoRes memRes) {
        //待付款--订单详情--取消订单
        tvUnPayCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel(memRes.getOrderID()+"");
            }
        });
        //待付款--订单详情--去支付
        btnToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder order = new CreateOrder();
                order.setOrderNumber(memRes.getNumber());
                order.setAddress(memRes.getReceivedaddress()+"");
                order.setName(memRes.getReceivedname()+"");
                order.setPay((int)Float.parseFloat(memRes.getPayprice()+""));
                order.setPhone(memRes.getReceivedmobile()+"");
                order.setPostPay(memRes.getPostmodel()+"");
                order.setPrice(memRes.getTotalprice()+"");
                Intent intent=new Intent(OrderInfoActivity.this,CreateOrderSuccessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        //待发货--订单详情--取消订单
        btnUnDelCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCancel(memRes.getOrderID()+"");
            }
        });
        //待收货--订单详情--确定收货
        btnSureGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderSign(memRes.getOrderID()+"");
            }
        });
        //待评价--订单详情--去评价
        btnToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderInfoActivity.this,OrderCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("orderId", memRes.getNumber());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData(MemberOrderInfo.MemberOrderInfoRes memRes) {
        tvOrderNumber.setText("订单号码："+memRes.getNumber());
        tvOrderTime.setText("下单时间："+memRes.getOrdertime());
        tvOrderState.setText(""+memRes.getState());
        tvOrderPerson.setText(""+memRes.getReceivedname());
        tvOrderAddress.setText(""+memRes.getReceivedaddress());
        tvOrderPeiSong.setText(""+memRes.getPostmodel());
        tvPayWay.setText(""+memRes.getPaymodel());
        tvOtherInfo.setText(""+memRes.getPostinfo());
        tvOrderRemark.setText(""+memRes.getOrderremark());
        tvStoreName.setText(""+memRes.getShopname()+"");
        tvStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderInfoActivity.this,StoreActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString(ShopActivity.IDBUNDLE,shopId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tvProNumber.setText("共"+memRes.getProductlist().size()+"件商品，");
        tvOrderPrice.setText("共"+memRes.getTotalprice()+"元");
        tvDeliveryPrice.setText("￥"+memRes.getPostprice());
        tvUnPayMoney.setText("￥"+memRes.getNotpayprice());
        tvGiftPayPrice.setText(memRes.getGiftpayprice()+"");
        tvPromotionPrice.setText(memRes.getPromotionprice()+"");
        switch (memRes.getState()){
            case "待付款":
                llUnPay.setVisibility(View.VISIBLE);
                llUnDelivery.setVisibility(View.GONE);
                llSureGet.setVisibility(View.GONE);
                llToComment.setVisibility(View.GONE);
                tvAlreadyCancle.setVisibility(View.GONE);
                break;
            case "待发货":
                llUnPay.setVisibility(View.GONE);
                llUnDelivery.setVisibility(View.VISIBLE);
                llSureGet.setVisibility(View.GONE);
                llToComment.setVisibility(View.GONE);
                tvAlreadyCancle.setVisibility(View.GONE);
                break;
            case "待收货":
                llUnPay.setVisibility(View.GONE);
                llUnDelivery.setVisibility(View.GONE);
                llSureGet.setVisibility(View.VISIBLE);
                llToComment.setVisibility(View.GONE);
                tvAlreadyCancle.setVisibility(View.GONE);
                break;
            case "已完成":
                llUnPay.setVisibility(View.GONE);
                llUnDelivery.setVisibility(View.GONE);
                llSureGet.setVisibility(View.GONE);
                llToComment.setVisibility(View.VISIBLE);
                tvAlreadyCancle.setVisibility(View.GONE);
                break;
            case "已取消":
                llUnPay.setVisibility(View.GONE);
                llUnDelivery.setVisibility(View.GONE);
                llSureGet.setVisibility(View.GONE);
                llToComment.setVisibility(View.GONE);
                tvAlreadyCancle.setVisibility(View.VISIBLE);
                break;
        }
        setAdapter(memRes.getProductlist());
    }

    private void setAdapter(List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> productlist) {
        proAdapter=new OrderInfoAdapter(productlist,OrderInfoActivity.this);
        lvOrderList.setAdapter(proAdapter);
    }

    private void orderSign(String orderId) {
        Call<NetBaseEntity> call=RetrofitService.getInstance().orderSign(AppApplication.TOKEN,orderId);
        call.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body()==null){
                    return;
                }
                if (response.body().getStatus()==0){
                    Toast.makeText(OrderInfoActivity.this,"确认成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(OrderInfoActivity.this,"确认失败:"+response.body().getMes(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(OrderInfoActivity.this,t+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消订单
     *
     * @param orderid
     */
    private void orderCancel(String orderid) {
//        Toast.makeText(OrderInfoActivity.this,""+orderid,Toast.LENGTH_SHORT).show();
        Call<NetBaseEntity> mGetDataCallNet = RetrofitService.getInstance().orderCancel(AppApplication.TOKEN, orderid);
        mGetDataCallNet.enqueue(new Callback<NetBaseEntity>() {
            @Override
            public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(OrderInfoActivity.this,"取消成功",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(OrderInfoActivity.this,"取消失败:"+response.body().getMes(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


    private void initView() {
        tvOrderNumber=(TextView)findViewById(R.id.tv_dingDanHaoMa);
        tvOrderTime=(TextView)findViewById(R.id.tv_xiaDanShiJian);
        tvOrderState=(TextView)findViewById(R.id.tv_zhuangTai);
        tvOrderPerson=(TextView)findViewById(R.id.tV_shouHuoRen);
        tvOrderAddress=(TextView)findViewById(R.id.tv_diZhi);
        lvOrderList=(ListView)findViewById(R.id.lv_shangPinLieBiao);
        tvOrderPeiSong=(TextView)findViewById(R.id.tv_peiSongFangShi);
        tvPayWay=(TextView)findViewById(R.id.tv_fuKuanFangShi);
        tvOtherInfo=(TextView)findViewById(R.id.tv_qiTaXinXi);
        tvOrderRemark=(TextView)findViewById(R.id.tv_beiZhu);
        tvStoreName=(TextView)findViewById(R.id.tv_store_name);
        //下边三行数据
        llThreeTV=(LinearLayout)findViewById(R.id.ll_three);
        tvProNumber=(TextView)findViewById(R.id.tv_proNum);
        tvOrderPrice=(TextView)findViewById(R.id.tv_orderPrice);
        tvDeliveryPrice=(TextView)findViewById(R.id.tv_deliveryPrice);
        tvUnPayMoney=(TextView)findViewById(R.id.tv_unPayMoney);
        tvGiftPayPrice=(TextView)findViewById(R.id.tv_giftpayprice);
        tvPromotionPrice=(TextView)findViewById(R.id.tv_promotionprice);
        //待付款布局
        llUnPay=(LinearLayout)findViewById(R.id.ll_unPay);
        tvUnPayCancle=(Button)findViewById(R.id.btn_unPayCancle);
        btnToPay=(Button)findViewById(R.id.btn_unPayTpPay);

        //待发货布局
        llUnDelivery=(LinearLayout)findViewById(R.id.ll_unDelivery);
        btnUnDelCancle=(Button)findViewById(R.id.btn_unDelCancle);

        //待收货布局
        llSureGet=(LinearLayout)findViewById(R.id.ll_sureGet);
        btnSureGet=(Button)findViewById(R.id.btn_sureGet);

        //待评价布局
        llToComment=(LinearLayout)findViewById(R.id.ll_toComment);
        btnToComment=(Button)findViewById(R.id.btn_ToConnemt);

        //已取消
        tvAlreadyCancle=(TextView)findViewById(R.id.tv_alreadyCancle);

        ib_back=(ImageButton)findViewById(R.id.ib_back);
    }
}
