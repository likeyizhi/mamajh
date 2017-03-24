package com.yqx.mamajh.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.AppApplication;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.GoodsListActivity;
import com.yqx.mamajh.bean.HotGoodsEntity;
import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.network.RetrofitService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by likey on 2017/3/14.
 */

public class MyGvAdapter extends BaseAdapter{
    private List<HotGoodsEntity.ResEntity.ProlistEntity> EShopList;
    private Context context;
    private LayoutInflater inflater;

    public MyGvAdapter (List<HotGoodsEntity.ResEntity.ProlistEntity> EShopList,Context context){
        super();
        this.EShopList=EShopList;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return EShopList.size();
    }

    @Override
    public HotGoodsEntity.ResEntity.ProlistEntity getItem(int position) {
        return EShopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return EShopList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_hot_goods,null);
            holder=new ViewHolder();
            holder.iv_img=(ImageView)convertView.findViewById(R.id.iv_img);
            holder.tv_about=(TextView) convertView.findViewById(R.id.tv_about);
            holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
            holder.tv_price_old=(TextView)convertView.findViewById(R.id.tv_price_old);
            holder.iv_cart=(ImageView)convertView.findViewById(R.id.iv_cart);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final HotGoodsEntity.ResEntity.ProlistEntity EShop=getItem(position);
        Glide.with(context).load(EShop.getImg()+"").into(holder.iv_img);
        holder.tv_about.setText(EShop.getName()+"");
        holder.tv_price.setText("￥"+EShop.getPrice()+"");
        holder.tv_price_old.setText("￥"+EShop.getOPrice()+"");
        holder.tv_price_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(EShopList.get(position)!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickSetGL!=null){
                        onItemClickSetGL.OnItemClickSetGL(EShop.getID());
                    }
                }
            });
        }
        holder.iv_cart.setTag(position);
        holder.iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<NetBaseEntity> addCartCall= RetrofitService.getInstance().addShopCart(AppApplication.TOKEN,EShop.getID());
                addCartCall.enqueue(new Callback<NetBaseEntity>() {
                    @Override
                    public void onResponse(Response<NetBaseEntity> response, Retrofit retrofit) {
                        if(response.body()==null){
                            return;
                        }
                        if (response.body().getStatus()==0){
                            Toast.makeText(context,EShop.getName()+" 添加购物车成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,EShop.getName()+" 添加失败,请重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });
//        if (EShopList.get(position)!=null){
//            holder.tv_price_old.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickSetAddCart!=null){
//                        onItemClickSetAddCart.OnItemClickSetAddCart(EShop.getID());
//                    }
//                }
//            });
//        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_img;
        TextView tv_about;
        TextView tv_price;
        TextView tv_price_old;
        ImageView iv_cart;
    }
    public interface OnItemClickSetGL{
        void OnItemClickSetGL(int proId);
    }
    private OnItemClickSetGL onItemClickSetGL;

    public void setOnItemClickSetGL(OnItemClickSetGL onItemClickSetGL){
        this.onItemClickSetGL=onItemClickSetGL;
    }

    public interface OnItemClickSetAddCart{
        void OnItemClickSetAddCart(int proId);
    }
    private OnItemClickSetAddCart onItemClickSetAddCart;

    public void setOnItemClickSetAddCart(OnItemClickSetAddCart onItemClickSetAddCart){
        this.onItemClickSetAddCart=onItemClickSetAddCart;
    }

}
