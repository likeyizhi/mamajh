package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/10.
 */

public class SearchResultBean {
     /*"status": 0,
     "mes": "成功",
     "res": []*/

    private int status;
    private String mes;
    private List<SearchResultRes> res;

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

    public List<SearchResultRes> getRes() {
        return res;
    }

    public void setRes(List<SearchResultRes> res) {
        this.res = res;
    }

    public static class SearchResultRes {

        /*"ID": "26",
     "Name": "爱润宝孕婴",
     "Phone": "0431-81096397",
     "Logo": "http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/26.png",
     "X": "125.372098",
     "Y": "43.844671",
     "Distance": "4.27km",
     "Time": "0",
     "Price": "0.0000",
     "Mark": "10",
     "SaleTotal": "0",
     "SareTotal": "0",
     "PromotionList": [],
     "ProductList": []
     "Address": "西环城路与西安大路交汇"*/

        private String ID;
        private String Name;
        private String Phone;
        private String Logo;
        private String X;
        private String Y;
        private String Distance;
        private String Time;
        private String Price;
        private String Mark;
        private String SaleTotal;
        private String SareTotal;
        private List<SearchResultPromotionList> PromotionList;
        private List<SearchResultProductList> ProductList;
        private String Address;

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String logo) {
            Logo = logo;
        }

        public String getX() {
            return X;
        }

        public void setX(String x) {
            X = x;
        }

        public String getY() {
            return Y;
        }

        public void setY(String y) {
            Y = y;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String distance) {
            Distance = distance;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getMark() {
            return Mark;
        }

        public void setMark(String mark) {
            Mark = mark;
        }

        public String getSaleTotal() {
            return SaleTotal;
        }

        public void setSaleTotal(String saleTotal) {
            SaleTotal = saleTotal;
        }

        public String getSareTotal() {
            return SareTotal;
        }

        public void setSareTotal(String sareTotal) {
            SareTotal = sareTotal;
        }

        public List<SearchResultPromotionList> getPromotionList() {
            return PromotionList;
        }

        public void setPromotionList(List<SearchResultPromotionList> promotionList) {
            PromotionList = promotionList;
        }

        public List<SearchResultProductList> getProductList() {
            return ProductList;
        }

        public void setProductList(List<SearchResultProductList> productList) {
            ProductList = productList;
        }

        public static class SearchResultPromotionList {

        }

        public static class SearchResultProductList {
        /*"ProductID": "298",
          "ProductName": "妙兜mumtop超薄兜兜裤婴幼儿拉拉裤 XXL 48片 15Kg以上",
          "Price": "178.00",
          "MarkPrice": "178.00",
          "SelaCount": "1.00"*/

            private String ProductID;
            private String ProductName;
            private String Price;
            private String MarkPrice;
            private String SelaCount;

            public String getProductID() {
                return ProductID;
            }

            public void setProductID(String productID) {
                ProductID = productID;
            }

            public String getProductName() {
                return ProductName;
            }

            public void setProductName(String productName) {
                ProductName = productName;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String price) {
                Price = price;
            }

            public String getMarkPrice() {
                return MarkPrice;
            }

            public void setMarkPrice(String markPrice) {
                MarkPrice = markPrice;
            }

            public String getSelaCount() {
                return SelaCount;
            }

            public void setSelaCount(String selaCount) {
                SelaCount = selaCount;
            }
        }
    }
}