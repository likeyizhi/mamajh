package com.yqx.mamajh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.utils.ViewHolder;

import java.util.List;

/**
 * Created by young on 15/11/22.
 */
public class TitleGroupAdapter extends BaseAdapter {

    private List<HomeInfoEntity.BlowshopFourAdEntity> navigationList;
    private Context context;

    public TitleGroupAdapter(Context context, List<HomeInfoEntity.BlowshopFourAdEntity> list){
        this.context = context;
        this.navigationList = list;
    }

    @Override
    public int getCount() {
        return navigationList == null ? 0 : navigationList.size();
    }

    @Override
    public Object getItem(int position) {
        return navigationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.title_group_item, null);
        }

        ImageView icon = ViewHolder.get(convertView, R.id.iv_icon);
        TextView title = ViewHolder.get(convertView, R.id.tv_title);

        HomeInfoEntity.BlowshopFourAdEntity navigationEntity = navigationList.get(position);
//        icon.setImageResource(navigationEntity.getIconResId());
        Glide.with(context).load(navigationEntity.getIcon()).crossFade().into(icon);
        title.setText(navigationEntity.getTitle());

        return convertView;
    }
}
