package com.yqx.mamajh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.OrderInfoActivity;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.bean.MemberOrderInfo;

import java.util.List;

/**
 * Created by likey on 2017/3/18.
 */

public class OrderInfoAdapter extends BaseAdapter{
    private List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> productlist;
    private Context context;
    private LayoutInflater inflater;

    public OrderInfoAdapter (List<MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist> productlist,Context context){
        super();
        this.productlist=productlist;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist getItem(int position) {
        return productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Integer.parseInt(productlist.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_order_info,null);
            holder=new ViewHolder();
            holder.iv_proImg=(ImageView)convertView.findViewById(R.id.iv_proImg);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_oneMoney=(TextView)convertView.findViewById(R.id.tv_oneMoney);
            holder.tv_productNumber=(TextView)convertView.findViewById(R.id.tv_productNumber);
            holder.tv_salePrice=(TextView)convertView.findViewById(R.id.tv_salePrice);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final MemberOrderInfo.MemberOrderInfoRes.MemberOrderInfoProductlist pro=getItem(position);
        Glide.with(context).load(pro.getImgurl()).into(holder.iv_proImg);
        holder.tv_name.setText(pro.getName()+"");
        holder.tv_oneMoney.setText("￥"+pro.getPrice()+"");
        holder.tv_productNumber.setText("选择数量："+pro.getCount()+"");
        holder.tv_salePrice.setText("合计：￥"+pro.getTotalprice());
        if(pro!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, ProductInfoActivity.class);
                    intent.putExtra("_id",Integer.parseInt(pro.getId()) );
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_proImg;
        TextView tv_name;
        TextView tv_oneMoney;
        TextView tv_productNumber;
        TextView tv_salePrice;
    }
}
