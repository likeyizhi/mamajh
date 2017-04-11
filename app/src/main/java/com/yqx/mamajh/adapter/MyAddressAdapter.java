package com.yqx.mamajh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.DeliveryAddress;

import java.util.List;

/**
 * Created by likey on 2017/3/30.
 */

public class MyAddressAdapter extends BaseAdapter{
    private Context context;
    private List<DeliveryAddress> myAddressList;
    private LayoutInflater inflater;
    public MyAddressAdapter (Context context,List<DeliveryAddress> myAddressList){
        super();
        this.context=context;
        this.myAddressList=myAddressList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return myAddressList.size();
    }

    @Override
    public DeliveryAddress getItem(int position) {
        return myAddressList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return myAddressList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAddressViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_search_location_myaddress,null);
            holder=new MyAddressViewHolder();
            holder.tv_address=(TextView)convertView.findViewById(R.id.tv_address);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
            convertView.setTag(holder);
        }
        holder= (MyAddressViewHolder) convertView.getTag();
        final DeliveryAddress myAddress=getItem(position);
        holder.tv_address.setText(myAddress.getArea()+myAddress.getAddress()+"");
        holder.tv_name.setText(myAddress.getName()+"");
        holder.tv_phone.setText(myAddress.getPhone()+"");
        if (myAddress!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickSetMyAddress!=null){
                        onItemClickSetMyAddress.OnItemClickSetMyAddress(myAddress.getArea(),myAddress.getAddress(),myAddress.getX(),myAddress.getY());
                    }
                }
            });
        }
        return convertView;
    }
    class MyAddressViewHolder{
        TextView tv_address;
        TextView  tv_name;
        TextView  tv_phone;
    }
    public interface OnItemClickSetMyAddress{
        void OnItemClickSetMyAddress(String area,String chooseAddress,String x,String y);
    }
    private OnItemClickSetMyAddress onItemClickSetMyAddress;

    public void setOnItemClickSetMyAddress(OnItemClickSetMyAddress onItemClickSetMyAddress){
        this.onItemClickSetMyAddress=onItemClickSetMyAddress;
    }

}
