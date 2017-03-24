package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/14.
 */

public class EShopProductListBean {
    /*    "status": 0,
        "mes": "成功",
        "res": {}*/
    private int status;
    private String mes;
    private EShopProductListRes res;

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

    public EShopProductListRes getRes() {
        return res;
    }

    public void setRes(EShopProductListRes res) {
        this.res = res;
    }
    public static class EShopProductListRes{
        /*"prolist": []*/
        private List<EShopProductListprolist> prolist;

        public List<EShopProductListprolist> getProlist() {
            return prolist;
        }

        public void setProlist(List<EShopProductListprolist> prolist) {
            this.prolist = prolist;
        }

        public static class EShopProductListprolist{
            /*"ID": 286,
            "Name": "合生元呵护幼儿3段配方奶粉 （12-36个月）900g",
            "Spec": "",
            "OPrice": 218.0,
            "Price": 218.0,
            "Img": "http://182.92.183.143:8011/webimg/img_product/product/286.jpg"*/
            private int ID;
            private String Name;
            private String Spec;
            private float OPrice;
            private float Price;
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

            public void setName(String name) {
                Name = name;
            }

            public String getSpec() {
                return Spec;
            }

            public void setSpec(String spec) {
                Spec = spec;
            }

            public float getOPrice() {
                return OPrice;
            }

            public void setOPrice(float OPrice) {
                this.OPrice = OPrice;
            }

            public float getPrice() {
                return Price;
            }

            public void setPrice(float price) {
                Price = price;
            }

            public String getImg() {
                return Img;
            }

            public void setImg(String img) {
                Img = img;
            }
        }

    }

}
