package com.yqx.mamajh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.ProductInfoActivity;
import com.yqx.mamajh.bean.ProInfo;

import java.util.List;

/**
 * Created by likey on 2017/3/17.
 */

public class OtherAdapter extends BaseAdapter{
    private List<ProInfo.ProInfoRes.ProInfoOtherlist> otherlist;
    private Context context;
    private LayoutInflater inflater;

    public OtherAdapter (Context context, List<ProInfo.ProInfoRes.ProInfoOtherlist> otherlist){
        super();
        this.context=context;
        this.otherlist=otherlist;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return otherlist.size();
    }

    @Override
    public ProInfo.ProInfoRes.ProInfoOtherlist getItem(int position) {
        return otherlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return otherlist.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_other_pro,null);
            holder=new ViewHolder();
            holder.tv_other_pro=(TextView)convertView.findViewById(R.id.tv_other_pro);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final ProInfo.ProInfoRes.ProInfoOtherlist otherPro=getItem(position);
        holder.tv_other_pro.setText(otherPro.getTitle()+"");
        holder.tv_other_pro.setTag(position);
        holder.tv_other_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductInfoActivity.class);
                intent.putExtra("_id",otherPro.getID());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_other_pro;
    }
}
