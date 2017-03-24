package com.yqx.mamajh.bean;

import java.util.ArrayList;

/**
 * Created by young on 2016/12/23.
 */

public class SearchHistoryListEntity {

    private ArrayList<SearchItemEntity> historysearch;
    private ArrayList<SearchItemEntity> hotsearch;

    public ArrayList<SearchItemEntity> getHistorysearch() {
        return historysearch;
    }

    public void setHistorysearch(ArrayList<SearchItemEntity> historysearch) {
        this.historysearch = historysearch;
    }

    public ArrayList<SearchItemEntity> getHotsearch() {
        return hotsearch;
    }

    public void setHotsearch(ArrayList<SearchItemEntity> hotsearch) {
        this.hotsearch = hotsearch;
    }
}
