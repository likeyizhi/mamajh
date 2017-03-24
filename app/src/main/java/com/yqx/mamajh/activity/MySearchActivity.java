package com.yqx.mamajh.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.yqx.mamajh.R;

/**
 * Created by likey on 2017/3/10.
 */

public class MySearchActivity extends Activity{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_mysearch);

    }
}
