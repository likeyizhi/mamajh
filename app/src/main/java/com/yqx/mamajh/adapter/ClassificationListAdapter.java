package com.yqx.mamajh.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.obsessive.library.utils.ToastUtils;
import com.yqx.mamajh.R;
import com.yqx.mamajh.activity.GoodsListActivity;
import com.yqx.mamajh.bean.CategoryListEntity;
import com.yqx.mamajh.utils.GridViewUtils;
import com.yqx.mamajh.widget.WrapHeightGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 2016/12/19.
 */

public class ClassificationListAdapter extends BaseAdapter {
    List<CategoryListEntity> mImages = new ArrayList<CategoryListEntity>();
    LayoutInflater mInflater;
    Context mContext;

    public ClassificationListAdapter(Context context, List<CategoryListEntity> items) {
        mContext = context;
        mImages = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public CategoryListEntity getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.class_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mGridView = (WrapHeightGridView) convertView.findViewById(R.id.my_gridview);
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.my_tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CategoryListEntity item = getItem(position);
        // 设置GridView的Adapter
        viewHolder.mGridView.setAdapter(new ClassificationGridAdapter(mContext, item.getChildList()));
        // 计算GridView宽度, 设置默认为numColumns为3.
//        GridViewUtils.updateGridViewLayoutParams(viewHolder.mGridView, 3);
        viewHolder.mTextView.setText(item.getName());

        viewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                ToastUtils.showShortToastSafe(item.getChildList().get(position).getName());
                Intent intent = new Intent();
                intent.putExtra("id", mImages.get(position).getID());
                intent.setClass(mContext, GoodsListActivity.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    /**
     * @author mrsimple
     */
    static class ViewHolder {
        WrapHeightGridView mGridView;
        TextView mTextView;
    }
}
