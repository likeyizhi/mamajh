package com.yqx.mamajh.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;

/**
 * Created by likey on 2017/3/27.
 */

public class ShowBigImageActivity extends Activity{
    private ImageView iv_big_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_image);
        Intent intent=getIntent();
        String imgUrl=intent.getStringExtra("_imgUrl");
        iv_big_image=(ImageView)findViewById(R.id.iv_big_image);
        Glide.with(this).load(imgUrl).error(R.mipmap.mmjhicon512).into(iv_big_image);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }
}
