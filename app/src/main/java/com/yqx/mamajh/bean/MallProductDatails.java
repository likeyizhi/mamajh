package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by DELL on 2017/3/17.
 */

public class MallProductDatails {
    private int status;
   private String mes;
    private List<MallProductDatailsRes> res;

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

    public List<MallProductDatailsRes> getRes() {
        return res;
    }

    public void setRes(List<MallProductDatailsRes> res) {
        this.res = res;
    }

    public static class MallProductDatailsRes {
        private String ID;
        private String Title;
        private String ImgSrc;
        private String Oprice;
        private String MarkPrice;
        private String SaleCount;
        private String Price;
        private String Dis;

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

        public String getImgSrc() {
            return ImgSrc;
        }

        public void setImgSrc(String imgSrc) {
            ImgSrc = imgSrc;
        }

        public String getOprice() {
            return Oprice;
        }

        public void setOprice(String oprice) {
            Oprice = oprice;
        }

        public String getMarkPrice() {
            return MarkPrice;
        }

        public void setMarkPrice(String markPrice) {
            MarkPrice = markPrice;
        }

        public String getSaleCount() {
            return SaleCount;
        }

        public void setSaleCount(String saleCount) {
            SaleCount = saleCount;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getDis() {
            return Dis;
        }

        public void setDis(String dis) {
            Dis = dis;
        }
    }
}
