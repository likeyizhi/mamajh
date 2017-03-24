package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/9.
 */

public class HotGoodsEntity {
    /**
     * status : 0
     * mes : 成功
     * res : {"prolist":[{"ID":225,"Name":"完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g","Spec":"","OPrice":348,"Price":348,"Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg"}]}
     */

    private int status;
    private String mes;
    private ResEntity res;

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

    public ResEntity getRes() {
        return res;
    }

    public void setRes(ResEntity res) {
        this.res = res;
    }

    public static class ResEntity {
        private List<ProlistEntity> prolist;

        public List<ProlistEntity> getProlist() {
            return prolist;
        }

        public void setProlist(List<ProlistEntity> prolist) {
            this.prolist = prolist;
        }

        public static class ProlistEntity {
            /**
             * ID : 225
             * Name : 完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g
             * Spec :
             * OPrice : 348.0
             * Price : 348.0
             * Img : http://182.92.183.143:8011/webimg/img_product/product/225.jpg
             */

            private int ID;
            private String Name;
            private String Spec;
            private double OPrice;
            private double Price;
            private String Img;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getSpec() {
                return Spec;
            }

            public void setSpec(String Spec) {
                this.Spec = Spec;
            }

            public double getOPrice() {
                return OPrice;
            }

            public void setOPrice(double OPrice) {
                this.OPrice = OPrice;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }
        }
    }
}
