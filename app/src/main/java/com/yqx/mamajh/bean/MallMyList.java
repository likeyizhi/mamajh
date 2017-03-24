package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/21.
 */

public class MallMyList {
    /**"status": 0,
     "mes": "成功",
     "InteCount": 10000.00,
     "res":[]*/

    private int status;
    private String mes;
    private float InteCount;
    private List<MallMyListRes> res;

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

    public float getInteCount() {
        return InteCount;
    }

    public void setInteCount(float inteCount) {
        InteCount = inteCount;
    }

    public List<MallMyListRes> getRes() {
        return res;
    }

    public void setRes(List<MallMyListRes> res) {
        this.res = res;
    }

    public static class MallMyListRes{
        private List<MallMyListMyList> IntegralMallMyList;

        public List<MallMyListMyList> getIntegralMallMyList() {
            return IntegralMallMyList;
        }

        public void setIntegralMallMyList(List<MallMyListMyList> integralMallMyList) {
            IntegralMallMyList = integralMallMyList;
        }

        public static class MallMyListMyList{
            /**"ID": "1286",
             "Title": "拉链式创意金属耳机",
             "SellPrice": "100",
             "MarketPrice": "7.50",
             "address": "",
             "Time": "181天前",
             "ImgSrc": "http://182.92.183.143:8011/webimg/img_product/product/1286.jpg"*/

            private String ID;
            private String Title;
            private String SellPrice;
            private String MarketPrice;
            private String address;
            private String Time;
            private String ImgSrc;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String title) {
                Title = title;
            }

            public String getSellPrice() {
                return SellPrice;
            }

            public void setSellPrice(String sellPrice) {
                SellPrice = sellPrice;
            }

            public String getMarketPrice() {
                return MarketPrice;
            }

            public void setMarketPrice(String marketPrice) {
                MarketPrice = marketPrice;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String time) {
                Time = time;
            }

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String imgSrc) {
                ImgSrc = imgSrc;
            }
        }
    }
}
