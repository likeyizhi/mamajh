package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by Drago on 2017/2/17 017.
 */

public class Coupon {


    private List<GiftlistBean> giftlist;

    public List<GiftlistBean> getGiftlist() {
        return giftlist;
    }

    public void setGiftlist(List<GiftlistBean> giftlist) {
        this.giftlist = giftlist;
    }

    public static class GiftlistBean {
        /**
         * ID : 5
         * Begin : 2016-07-08
         * End : 2016-12-11
         * Number : 00000004000001456
         * Desc : 可在 喜宝孕婴 NO.15店 中使用
         * Img : http://m.mamajh.com/themes/default/images/quan.png
         * Price : 1.0000
         */

        private int ID;
        private String Begin;
        private String End;
        private String Number;
        private String Desc;
        private String Img;
        private String Price;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBegin() {
            return Begin;
        }

        public void setBegin(String Begin) {
            this.Begin = Begin;
        }

        public String getEnd() {
            return End;
        }

        public void setEnd(String End) {
            this.End = End;
        }

        public String getNumber() {
            return Number;
        }

        public void setNumber(String Number) {
            this.Number = Number;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
        }

        public String getImg() {
            return Img;
        }

        public void setImg(String Img) {
            this.Img = Img;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }
    }
}
