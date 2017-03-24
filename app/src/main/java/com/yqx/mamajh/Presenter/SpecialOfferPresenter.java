package com.yqx.mamajh.Presenter;


import com.yqx.mamajh.bean.SpecialChannelGoodsEntity;

/**
 * Created by young on 16/1/8.
 */
public interface SpecialOfferPresenter {

    void loadListData(int state, int event_tag, int page, boolean isSwipeRefresh);

    void onItemClickListener(int position, SpecialChannelGoodsEntity itemData);
}
