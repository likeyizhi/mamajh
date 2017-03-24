package com.yqx.mamajh.Presenter;

/**
 * Created by young on 2016/12/11.
 */

public interface HomeListPresenter {
    void loadListData(String requestTag, int event_tag, int page, String x, String y, boolean isSwipeRefresh);
}
