package com.yqx.mamajh.bean;

/**
 * Created by likey on 2017/4/7.
 */

public class ShopCartCount {
    /**"status": 0,
     "mes": "成功",
     "res": {}*/
    private int status;
    private String mes;
    private ShopCartCountRes res;

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

    public ShopCartCountRes getRes() {
        return res;
    }

    public void setRes(ShopCartCountRes res) {
        this.res = res;
    }

    public static class ShopCartCountRes{
        /**"Count": "2"*/
        private String Count;

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            Count = count;
        }
    }
}
