package com.yqx.mamajh.view;


import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;

import java.util.ArrayList;

/**
 * Created by young on 16/1/8.
 */
public interface SpecialOfferView extends BaseView{
    void refreshListData(ArrayList<SpecialChannelGoodsEntity> responseProductListEntity);

    void addMoreListData(ArrayList<SpecialChannelGoodsEntity> responseProductListEntity);

    void navigateToNewsDetail(int position, SpecialChannelGoodsEntity entity);
}
