package com.yqx.mamajh.dbcity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yqx.mamajh.R;

import java.util.List;

/**
 * Created by likey on 2017/3/16.
 */

public class CityAdapter extends BaseAdapter{
    private List<City.CityRes> cities;
    private Context context;
    private LayoutInflater inflater;
    
    public CityAdapter (List<City.CityRes> cities, Context context){
        super();
        this.cities=cities;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
    }
    

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public City.CityRes getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cities.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_city_select,null);
            holder=new ViewHolder();
            holder.tvCity=(TextView)convertView.findViewById(R.id.tv_city);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        final City.CityRes city=getItem(position);
        holder.tvCity.setText(city.getName());
        if (city!=null){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickSetCity!=null){
                        onItemClickSetCity.OnItemClickSetCity(city.getId(), city.getName());
                    }
                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        TextView tvCity;
    }
    public interface OnItemClickSetCity{
        void OnItemClickSetCity(int id, String name);
    }
    private OnItemClickSetCity onItemClickSetCity;

    public void setOnItemClickSetCity(OnItemClickSetCity onItemClickSetCity){
        this.onItemClickSetCity=onItemClickSetCity;
    }
}
