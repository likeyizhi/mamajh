package com.yqx.mamajh.bean;

import android.widget.ListView;

import java.util.List;

/**
 * Created by likey on 2017/4/7.
 */

public class StoreSearch {
    /**"status": 0,
     "mes": "成功",
     "res": {}*/
    private int status;
    private String mes;
    private StoreSearchRes res;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public StoreSearchRes getRes() {
        return res;
    }

    public void setRes(StoreSearchRes res) {
        this.res = res;
    }

    public static class StoreSearchRes{
        private List<StoreSearchHotSearch> hotsearch;

        public List<StoreSearchHotSearch> getHotsearch() {
            return hotsearch;
        }

        public void setHotsearch(List<StoreSearchHotSearch> hotsearch) {
            this.hotsearch = hotsearch;
        }

        public static class StoreSearchHotSearch{
            private String title;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
