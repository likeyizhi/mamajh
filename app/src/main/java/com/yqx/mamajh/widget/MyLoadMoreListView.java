package com.yqx.mamajh.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by young on 2017/3/12.
 */

public class MyLoadMoreListView extends LoadMoreListView {
    public MyLoadMoreListView(Context context) {
        super(context);
    }

    public MyLoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
