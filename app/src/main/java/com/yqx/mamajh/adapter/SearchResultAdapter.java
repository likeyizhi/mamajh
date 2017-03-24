package com.yqx.mamajh.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.bean.SearchResultBean;

import org.xutils.x;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by likey on 2017/3/10.
 */

public class SearchResultAdapter extends BaseAdapter{
    private Context context;
    private List<SearchResultBean.SearchResultRes> shoplist;
    private LayoutInflater inflater;
    ViewHolder holder=null;

    public SearchResultAdapter (Context context,List<SearchResultBean.SearchResultRes> shoplist){
        this.shoplist=shoplist;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return shoplist.size();
    }

    @Override
    public SearchResultBean.SearchResultRes getItem(int position) {
        return shoplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(shoplist.get(position).getID());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_search_result,null);
            holder=new ViewHolder();
            holder.iv_searchShop=(ImageView)convertView.findViewById(R.id.iv_searchShop);
            holder.tv_searchShopName=(TextView)convertView.findViewById(R.id.tv_searchShopName);
            holder.rbar_sr=(RatingBar)convertView.findViewById(R.id.rbar_sr);
            holder.tv_saleTotal=(TextView)convertView.findViewById(R.id.tv_saleTotal);
            holder.tv_sareTotal=(TextView)convertView.findViewById(R.id.tv_sareTotal);
            holder.tv_distance=(TextView)convertView.findViewById(R.id.tv_distance);
            holder.ll_thisShop=(LinearLayout) convertView.findViewById(R.id.ll_thisShop);

            holder.ll_shopGoods1=(LinearLayout)convertView.findViewById(R.id.ll_shopGoods1);
            holder.ll_shopGoods2=(LinearLayout)convertView.findViewById(R.id.ll_shopGoods2);
            holder.ll_shopGoods3=(LinearLayout)convertView.findViewById(R.id.ll_shopGoods3);
            holder.ll_shopGoods4=(LinearLayout)convertView.findViewById(R.id.ll_shopGoods4);
            holder.ll_shopGoods5=(LinearLayout)convertView.findViewById(R.id.ll_shopGoods5);
            holder.tv_productName1=(TextView)convertView.findViewById(R.id.tv_productName1);
            holder.tv_productName2=(TextView)convertView.findViewById(R.id.tv_productName2);
            holder.tv_productName3=(TextView)convertView.findViewById(R.id.tv_productName3);
            holder.tv_productName4=(TextView)convertView.findViewById(R.id.tv_productName4);
            holder.tv_productName5=(TextView)convertView.findViewById(R.id.tv_productName5);
            holder.tv_productName5=(TextView)convertView.findViewById(R.id.tv_productName5);
            holder.tv_selaCount1=(TextView)convertView.findViewById(R.id.tv_selaCount1);
            holder.tv_selaCount2=(TextView)convertView.findViewById(R.id.tv_selaCount2);
            holder.tv_selaCount3=(TextView)convertView.findViewById(R.id.tv_selaCount3);
            holder.tv_selaCount4=(TextView)convertView.findViewById(R.id.tv_selaCount4);
            holder.tv_selaCount5=(TextView)convertView.findViewById(R.id.tv_selaCount5);
            holder.tv_markPrice1=(TextView)convertView.findViewById(R.id.tv_markPrice1);
            holder.tv_markPrice2=(TextView)convertView.findViewById(R.id.tv_markPrice2);
            holder.tv_markPrice3=(TextView)convertView.findViewById(R.id.tv_markPrice3);
            holder.tv_markPrice4=(TextView)convertView.findViewById(R.id.tv_markPrice4);
            holder.tv_markPrice5=(TextView)convertView.findViewById(R.id.tv_markPrice5);

            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final SearchResultBean.SearchResultRes shop=getItem(position);
        x.image().bind(holder.iv_searchShop,shop.getLogo());
        holder.tv_searchShopName.setText(shop.getName());
        holder.rbar_sr.setRating(Float.parseFloat(shop.getMark()));
        holder.tv_saleTotal.setText("月售"+shop.getSaleTotal()+"单");
        holder.tv_sareTotal.setText("评价数"+shop.getSareTotal());
        holder.tv_distance.setText(shop.getDistance());
        holder.ll_thisShop.setTag(position);
        if(shoplist.get(position)!=null){
            holder.ll_thisShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickSetIntoShop!=null){
                        onItemClickSetIntoShop.OnItemClickSetIntoShop(shop.getID());
                    }
                }
            });
        }

        int shopProLS=shop.getProductList().size();
        if (shopProLS>5){
            shopProLS=5;
        }
        switch (shopProLS){
            case 0:
                break;
            case 1:
                holder.ll_shopGoods1.setVisibility(View.VISIBLE);
                holder.ll_shopGoods1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(0).getProductID()));
                        context.startActivity(intent);
                    }
                });

                holder.tv_productName1.setText(shop.getProductList().get(0).getProductName());
                holder.tv_selaCount1.setText("月销"+shop.getProductList().get(0).getSelaCount()+"件");
                holder.tv_markPrice1.setText("￥"+shop.getProductList().get(0).getMarkPrice());

                break;
            case 2:
                holder.ll_shopGoods1.setVisibility(View.VISIBLE);
                holder.ll_shopGoods1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(0).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName1.setText(shop.getProductList().get(0).getProductName());
                holder.tv_selaCount1.setText("月销"+shop.getProductList().get(0).getSelaCount()+"件");
                holder.tv_markPrice1.setText("￥"+shop.getProductList().get(0).getMarkPrice());

                holder.ll_shopGoods2.setVisibility(View.VISIBLE);
                holder.ll_shopGoods2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(1).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName2.setText(shop.getProductList().get(1).getProductName());
                holder.tv_selaCount2.setText("月销"+shop.getProductList().get(1).getSelaCount()+"件");
                holder.tv_markPrice2.setText("￥"+shop.getProductList().get(1).getMarkPrice());
                break;
            case 3:
                holder.ll_shopGoods1.setVisibility(View.VISIBLE);
                holder.ll_shopGoods1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(0).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName1.setText(shop.getProductList().get(0).getProductName());
                holder.tv_selaCount1.setText("月销"+shop.getProductList().get(0).getSelaCount()+"件");
                holder.tv_markPrice1.setText("￥"+shop.getProductList().get(0).getMarkPrice());

                holder.ll_shopGoods2.setVisibility(View.VISIBLE);
                holder.ll_shopGoods2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(1).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName2.setText(shop.getProductList().get(1).getProductName());
                holder.tv_selaCount2.setText("月销"+shop.getProductList().get(1).getSelaCount()+"件");
                holder.tv_markPrice2.setText("￥"+shop.getProductList().get(1).getMarkPrice());

                holder.ll_shopGoods3.setVisibility(View.VISIBLE);
                holder.ll_shopGoods3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(2).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName3.setText(shop.getProductList().get(2).getProductName());
                holder.tv_selaCount3.setText("月销"+shop.getProductList().get(2).getSelaCount()+"件");
                holder.tv_markPrice3.setText("￥"+shop.getProductList().get(2).getMarkPrice());
                break;
            case 4:
                holder.ll_shopGoods1.setVisibility(View.VISIBLE);
                holder.ll_shopGoods1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(0).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName1.setText(shop.getProductList().get(0).getProductName());
                holder.tv_selaCount1.setText("月销"+shop.getProductList().get(0).getSelaCount()+"件");
                holder.tv_markPrice1.setText("￥"+shop.getProductList().get(0).getMarkPrice());

                holder.ll_shopGoods2.setVisibility(View.VISIBLE);
                holder.ll_shopGoods2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(1).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName2.setText(shop.getProductList().get(1).getProductName());
                holder.tv_selaCount2.setText("月销"+shop.getProductList().get(1).getSelaCount()+"件");
                holder.tv_markPrice2.setText("￥"+shop.getProductList().get(1).getMarkPrice());

                holder.ll_shopGoods3.setVisibility(View.VISIBLE);
                holder.ll_shopGoods3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(2).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName3.setText(shop.getProductList().get(2).getProductName());
                holder.tv_selaCount3.setText("月销"+shop.getProductList().get(2).getSelaCount()+"件");
                holder.tv_markPrice3.setText("￥"+shop.getProductList().get(2).getMarkPrice());

                holder.ll_shopGoods4.setVisibility(View.VISIBLE);
                holder.ll_shopGoods4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(3).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName4.setText(shop.getProductList().get(3).getProductName());
                holder.tv_selaCount4.setText("月销"+shop.getProductList().get(3).getSelaCount()+"件");
                holder.tv_markPrice4.setText("￥"+shop.getProductList().get(3).getMarkPrice());
                break;
            case 5:
                holder.ll_shopGoods1.setVisibility(View.VISIBLE);
                holder.ll_shopGoods1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(0).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName1.setText(shop.getProductList().get(0).getProductName());
                holder.tv_selaCount1.setText("月销"+shop.getProductList().get(0).getSelaCount()+"件");
                holder.tv_markPrice1.setText("￥"+shop.getProductList().get(0).getMarkPrice());

                holder.ll_shopGoods2.setVisibility(View.VISIBLE);
                holder.ll_shopGoods2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(1).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName2.setText(shop.getProductList().get(1).getProductName());
                holder.tv_selaCount2.setText("月销"+shop.getProductList().get(1).getSelaCount()+"件");
                holder.tv_markPrice2.setText("￥"+shop.getProductList().get(1).getMarkPrice());

                holder.ll_shopGoods3.setVisibility(View.VISIBLE);
                holder.ll_shopGoods3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(2).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName3.setText(shop.getProductList().get(2).getProductName());
                holder.tv_selaCount3.setText("月销"+shop.getProductList().get(2).getSelaCount()+"件");
                holder.tv_markPrice3.setText("￥"+shop.getProductList().get(2).getMarkPrice());

                holder.ll_shopGoods4.setVisibility(View.VISIBLE);
                holder.ll_shopGoods4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(3).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName4.setText(shop.getProductList().get(3).getProductName());
                holder.tv_selaCount4.setText("月销"+shop.getProductList().get(3).getSelaCount()+"件");
                holder.tv_markPrice4.setText("￥"+shop.getProductList().get(3).getMarkPrice());

                holder.ll_shopGoods5.setVisibility(View.VISIBLE);
                holder.ll_shopGoods5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context,ProductInfoActivity.class);
                        intent.putExtra("_id",Integer.parseInt(shop.getProductList().get(4).getProductID()));
                        context.startActivity(intent);
                    }
                });
                holder.tv_productName5.setText(shop.getProductList().get(4).getProductName());
                holder.tv_selaCount5.setText("月销"+shop.getProductList().get(4).getSelaCount()+"件");
                holder.tv_markPrice5.setText("￥"+shop.getProductList().get(4).getMarkPrice());
                break;

        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_searchShop;
        TextView tv_searchShopName;
        RatingBar rbar_sr;
        TextView tv_saleTotal;
        TextView tv_sareTotal;
        TextView tv_distance;
        LinearLayout ll_thisShop;

        LinearLayout ll_shopGoods1;
        LinearLayout ll_shopGoods2;
        LinearLayout ll_shopGoods3;
        LinearLayout ll_shopGoods4;
        LinearLayout ll_shopGoods5;
        TextView tv_productName1;
        TextView tv_productName2;
        TextView tv_productName3;
        TextView tv_productName4;
        TextView tv_productName5;
        TextView tv_selaCount1;
        TextView tv_selaCount2;
        TextView tv_selaCount3;
        TextView tv_selaCount4;
        TextView tv_selaCount5;
        TextView tv_markPrice1;
        TextView tv_markPrice2;
        TextView tv_markPrice3;
        TextView tv_markPrice4;
        TextView tv_markPrice5;
    }
    public interface OnItemClickSetIntoShop{
        void OnItemClickSetIntoShop(String id);
    }
    private OnItemClickSetIntoShop onItemClickSetIntoShop;

    public void setOnItemClickSetIntoShop(OnItemClickSetIntoShop onItemClickSetIntoShop){
        this.onItemClickSetIntoShop=onItemClickSetIntoShop;
    }

}
