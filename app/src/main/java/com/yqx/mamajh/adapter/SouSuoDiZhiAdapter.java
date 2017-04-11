package com.yqx.mamajh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.SouSuoDiZhi;

import java.util.ArrayList;

/**
 * Created by likey on 2017/3/29.
 */

public class SouSuoDiZhiAdapter extends BaseAdapter{
    private ArrayList<SouSuoDiZhi> diZhiArrayList;
    private Context context;
    private LayoutInflater inflater;

    public SouSuoDiZhiAdapter(ArrayList<SouSuoDiZhi> diZhiArrayList,Context context){
        super();
        this.diZhiArrayList=diZhiArrayList;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return diZhiArrayList.size();
    }

    @Override
    public SouSuoDiZhi getItem(int position) {
        return diZhiArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiZhiViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_search_location_result,null);
            holder=new DiZhiViewHolder();
            holder.tv_sousuodizhi_item=(TextView)convertView.findViewById(R.id.tv_sousuodizhi_item);
            convertView.setTag(holder);
        }
        holder= (DiZhiViewHolder) convertView.getTag();
        final SouSuoDiZhi diZhi=getItem(position);
        holder.tv_sousuodizhi_item.setText(diZhi.getDizhiName()+"，"+diZhi.getDizhiAddress());
        if(diZhi!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickSetDiZhi!=null){
                        onItemClickSetDiZhi.OnItemClickSetDiZhi(diZhi.getDizhiName(),diZhi.getDizhiAddress(),diZhi.getX(), diZhi.getY());
                    }
                }
            });
        }
        return convertView;
    }
    class DiZhiViewHolder{
        TextView tv_sousuodizhi_item;
    }

    public interface OnItemClickSetDiZhi{
        void OnItemClickSetDiZhi(String location,String address, String x, String y);
    }
    private OnItemClickSetDiZhi onItemClickSetDiZhi;

    public void setOnItemClickSetDiZhi(OnItemClickSetDiZhi onItemClickSetDiZhi){
        this.onItemClickSetDiZhi=onItemClickSetDiZhi;
    }
}
