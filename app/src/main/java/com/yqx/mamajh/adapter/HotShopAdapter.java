package com.yqx.mamajh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.ShopList;

import java.util.List;


/**
 * Created by likey on 2017/3/9.
 */

public class HotShopAdapter extends BaseAdapter{
    private Context context;
    private List<ShopList.ShoplistBean> shopList;
    private LayoutInflater inflater;
    public HotShopAdapter(Context context, List<ShopList.ShoplistBean> shopList){
        super();
        this.context=context;
        this.shopList=shopList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public ShopList.ShoplistBean getItem(int position) {
        return shopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(shopList.get(position).getID());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_home_list,null);
            holder=new ViewHolder();
            holder.iv_img_item=(ImageView)convertView.findViewById(R.id.iv_img_item);
            holder.tv_item_title=(TextView)convertView.findViewById(R.id.tv_item_title);
            holder.tv_sales=(TextView)convertView.findViewById(R.id.tv_sales);
            holder.ratingBar=(RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.tv_money=(TextView)convertView.findViewById(R.id.tv_money);
            holder.tv_distance=(TextView)convertView.findViewById(R.id.tv_distance);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final ShopList.ShoplistBean shop=getItem(position);
        Glide.with(context).load(shop.getLogo()).crossFade().into(holder.iv_img_item);
        holder.tv_item_title.setText(shop.getName());
        holder.tv_sales.setText("月售"+shop.getSaleTotal()+"单");
        holder.ratingBar.setRating(Float.parseFloat(shop.getMark()));
        holder.tv_distance.setText(shop.getDistance()+"");
        holder.tv_money.setText("配送费"+shop.getDistributionFee()+"元/满"+shop.getStartingFee()+"元免配送费");
        if(shopList.get(position)!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickSetHS!=null){
                        onItemClickSetHS.OnItemClickSetHS(shop.getID());
                    }
                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_img_item;
        TextView tv_item_title;
        TextView tv_sales;
        RatingBar ratingBar;
        TextView tv_money;
        TextView tv_distance;
    }
    public interface OnItemClickSetHS{
        void OnItemClickSetHS(String id);
    }
    private OnItemClickSetHS onItemClickSetHS;

    public void setOnItemClickSetHS(OnItemClickSetHS onItemClickSetHS){
        this.onItemClickSetHS=onItemClickSetHS;
    }

}
