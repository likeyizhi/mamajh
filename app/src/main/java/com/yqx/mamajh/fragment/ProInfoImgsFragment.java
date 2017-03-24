package com.yqx.mamajh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yqx.mamajh.R;

/**
 * Created by likey on 2017/3/12.
 */

public class ProInfoImgsFragment extends Fragment{
    private String img;
    private View view;
    private ImageView image;

    public static ProInfoImgsFragment newInstance(String imgSrc) {
        ProInfoImgsFragment newImgFragment = new ProInfoImgsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imgSrc", imgSrc);
        newImgFragment.setArguments(bundle);
        return newImgFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            img = args.getString("imgSrc");
        }
    }

    //    public ProInfoImgsFragment(String imgSrc) {
//        img=imgSrc;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_proinfo_imgs,null);
        setImage();
        return view;
    }

    private void setImage() {
        image=(ImageView)view.findViewById(R.id.iv_proinfo_imgs);
        Glide.with(getActivity()).load(img).crossFade().into(image);
    }
}
