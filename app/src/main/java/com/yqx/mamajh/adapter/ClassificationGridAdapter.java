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
import com.yqx.mamajh.bean.CategoryItemEntity;

import java.util.List;

/**
 * Created by young on 2016/12/20.
 */

public class ClassificationGridAdapter extends BaseAdapter {
    Context mContext;
    List<CategoryItemEntity> mImages;

    public ClassificationGridAdapter(Context context, List<CategoryItemEntity> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CategoryItemEntity getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.class_gv_item,
                parent, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_calss_img);
        TextView textView = (TextView) rootView.findViewById(R.id.tv_class_name);
        textView.setText(getItem(position).getName());
        Glide.with(mContext).load(getItem(position).getImg()).crossFade().into(imageView);
        return rootView;
    }

}
