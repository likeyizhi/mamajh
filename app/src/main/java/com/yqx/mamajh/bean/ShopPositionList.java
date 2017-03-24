package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2016/12/11.
 */

public class ShopPositionList {

    private int status;
    private String mes;
    private List<ResShoplist> res;

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

    public List<ResShoplist> getRes() {
        return res;
    }

    public void setRes(List<ResShoplist> res) {
        this.res = res;
    }

    public static class ResShoplist {
        /**
         * ID : 1
         * Name : 北京旗舰店
         * Phone : 13811118888
         * Logo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.png
         * X : 116.529112
         * Y : 39.931321
         * Distance : 752米
         * Time : 48
         * Mark : 0
         * SaleTotal : 200
         * SareTotal : 0
         * StartingFee: 50
         * DistributionFee: 5
         * IsCollect : 0
         * Address : 1
         */

        private String ID;
        private String Name;
        private String Phone;
        private String Logo;
        private String X;
        private String Y;
        private String Distance;
        private String Time;
        private String Mark;
        private String SaleTotal;
        private String SareTotal;
        private String StartingFee;
        private String DistributionFee;
        private String IsCollect;
        private String Address;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String Logo) {
            this.Logo = Logo;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }

        public String getY() {
            return Y;
        }

        public void setY(String Y) {this.Y = Y;}

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String Distance) {
            this.Distance = Distance;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getMark() {
            return Mark;
        }

        public void setMark(String Mark) {
            this.Mark = Mark;
        }

        public String getSaleTotal() {
            return SaleTotal;
        }

        public void setSaleTotal(String SaleTotal) {
            this.SaleTotal = SaleTotal;
        }

        public String getSareTotal() {
            return SareTotal;
        }

        public void setSareTotal(String SareTotal) {
            this.SareTotal = SareTotal;
        }

        public String getStartingFee() {
            return StartingFee;
        }

        public void setStartingFee(String SareTotal) {
            this.StartingFee = StartingFee;
        }

        public String getDistributionFee() {
            return DistributionFee;
        }

        public void setDistributionFee(String DistributionFee) {this.DistributionFee = DistributionFee;}

        public String getIsCollect() {
            return IsCollect;
        }

        public void setIsCollect(String IsCollect) {
            this.IsCollect = IsCollect;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }
    }

}
