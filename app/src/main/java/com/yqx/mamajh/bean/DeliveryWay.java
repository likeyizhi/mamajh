package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by Drago on 2017/2/17 017.
 */

public class DeliveryWay {


    private List<DeliverylistBean> deliverylist;

    public List<DeliverylistBean> getDeliverylist() {
        return deliverylist;
    }

    public void setDeliverylist(List<DeliverylistBean> deliverylist) {
        this.deliverylist = deliverylist;
    }

    public static class DeliverylistBean {
        /**
         * Title : 到店自提
         * Desc : 提货地址：西环城路与西安大路交汇
         * Price : 无
         * TID : 1
         */

        private String Title;
        private String Desc;
        private String Price;
        private int    TID;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public int getTID() {
            return TID;
        }

        public void setTID(int TID) {
            this.TID = TID;
        }
    }
}
