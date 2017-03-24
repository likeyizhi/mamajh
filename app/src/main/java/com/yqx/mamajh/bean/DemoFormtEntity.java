package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/7.
 */

public class DemoFormtEntity {

    /**
     * status : 0
     * mes : 成功
     * res : [{"AdList":[{"Title":"积分商城1","Desc":"积分商城1","ImgSrc":"http://182.92.183.143:8011/webimg/img_AD/2016617112326950.jpg","Link":"http://"}]},{"NewbieAreas":[{"ID":"1108","Name":"【修正】素妍人参洁肤粉","Img":"http://182.92.183.143:8011/webimg/img_product/product/1108.jpg","OPrice":"19.80","Price":"19.80","IntegralPrice":"10"}]},{"HeatExchangeArea":[{"ID":"1285","Name":"入耳式耳机","Img":"http://182.92.183.143:8011/webimg/img_product/product/1285.jpg","OPrice":"3.80","Price":"3.80","IntegralPrice":"50"}]},{"TimeLimit":[{"ID":"1087","Name":"【滴滴出行】优惠券","Img":"http://182.92.183.143:8011/webimg/img_product/time/36f20895-44ea-4c8a-91a1-6238d5e9a0f7.jpg","OPrice":"0.00","Price":"0.00","IntegralPrice":"1","Specifications":"","SaleTotal":"1","EndTime":"2016-09-30 00:00:00"}]}]
     */

    private int status;
    private String mes;
    private List<ResEntity> res;

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

    public List<ResEntity> getRes() {
        return res;
    }

    public void setRes(List<ResEntity> res) {
        this.res = res;
    }

    public static class ResEntity {
        private List<AdListEntity> AdList;
        private List<NewbieAreasEntity> NewbieAreas;
        private List<HeatExchangeAreaEntity> HeatExchangeArea;
        private List<TimeLimitEntity> TimeLimit;

        public List<AdListEntity> getAdList() {
            return AdList;
        }

        public void setAdList(List<AdListEntity> AdList) {
            this.AdList = AdList;
        }

        public List<NewbieAreasEntity> getNewbieAreas() {
            return NewbieAreas;
        }

        public void setNewbieAreas(List<NewbieAreasEntity> NewbieAreas) {
            this.NewbieAreas = NewbieAreas;
        }

        public List<HeatExchangeAreaEntity> getHeatExchangeArea() {
            return HeatExchangeArea;
        }

        public void setHeatExchangeArea(List<HeatExchangeAreaEntity> HeatExchangeArea) {
            this.HeatExchangeArea = HeatExchangeArea;
        }

        public List<TimeLimitEntity> getTimeLimit() {
            return TimeLimit;
        }

        public void setTimeLimit(List<TimeLimitEntity> TimeLimit) {
            this.TimeLimit = TimeLimit;
        }

        public static class AdListEntity {
            /**
             * Title : 积分商城1
             * Desc : 积分商城1
             * ImgSrc : http://182.92.183.143:8011/webimg/img_AD/2016617112326950.jpg
             * Link : http://
             */

            private String Title;
            private String Desc;
            private String ImgSrc;
            private String Link;

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

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String ImgSrc) {
                this.ImgSrc = ImgSrc;
            }

            public String getLink() {
                return Link;
            }

            public void setLink(String Link) {
                this.Link = Link;
            }
        }

        public static class NewbieAreasEntity {
            /**
             * ID : 1108
             * Name : 【修正】素妍人参洁肤粉
             * Img : http://182.92.183.143:8011/webimg/img_product/product/1108.jpg
             * OPrice : 19.80
             * Price : 19.80
             * IntegralPrice : 10
             */

            private String ID;
            private String Name;
            private String Img;
            private String OPrice;
            private String Price;
            private String IntegralPrice;

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

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }

            public String getOPrice() {
                return OPrice;
            }

            public void setOPrice(String OPrice) {
                this.OPrice = OPrice;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getIntegralPrice() {
                return IntegralPrice;
            }

            public void setIntegralPrice(String IntegralPrice) {
                this.IntegralPrice = IntegralPrice;
            }
        }

        public static class HeatExchangeAreaEntity {
            /**
             * ID : 1285
             * Name : 入耳式耳机
             * Img : http://182.92.183.143:8011/webimg/img_product/product/1285.jpg
             * OPrice : 3.80
             * Price : 3.80
             * IntegralPrice : 50
             */

            private String ID;
            private String Name;
            private String Img;
            private String OPrice;
            private String Price;
            private String IntegralPrice;

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

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }

            public String getOPrice() {
                return OPrice;
            }

            public void setOPrice(String OPrice) {
                this.OPrice = OPrice;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getIntegralPrice() {
                return IntegralPrice;
            }

            public void setIntegralPrice(String IntegralPrice) {
                this.IntegralPrice = IntegralPrice;
            }
        }

        public static class TimeLimitEntity {
            /**
             * ID : 1087
             * Name : 【滴滴出行】优惠券
             * Img : http://182.92.183.143:8011/webimg/img_product/time/36f20895-44ea-4c8a-91a1-6238d5e9a0f7.jpg
             * OPrice : 0.00
             * Price : 0.00
             * IntegralPrice : 1
             * Specifications :
             * SaleTotal : 1
             * EndTime : 2016-09-30 00:00:00
             */

            private String ID;
            private String Name;
            private String Img;
            private String OPrice;
            private String Price;
            private String IntegralPrice;
            private String Specifications;
            private String SaleTotal;
            private String EndTime;

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

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }

            public String getOPrice() {
                return OPrice;
            }

            public void setOPrice(String OPrice) {
                this.OPrice = OPrice;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public String getIntegralPrice() {
                return IntegralPrice;
            }

            public void setIntegralPrice(String IntegralPrice) {
                this.IntegralPrice = IntegralPrice;
            }

            public String getSpecifications() {
                return Specifications;
            }

            public void setSpecifications(String Specifications) {
                this.Specifications = Specifications;
            }

            public String getSaleTotal() {
                return SaleTotal;
            }

            public void setSaleTotal(String SaleTotal) {
                this.SaleTotal = SaleTotal;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }
        }
    }
}
