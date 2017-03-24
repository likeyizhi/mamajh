package com.yqx.mamajh.bean;

/**
 * Created by young on 2016/12/11.
 */

public class Product {

    /**
     * ID : 225
     * Name : 完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g
     * Img : http://182.92.183.143:8011/webimg/img_product/product/225.jpg
     * OPrice : 348.00
     * Price : 348.00
     * SaleCount : 1
     */

    private String ID;
    private String Name;
    private String Img;
    private String OPrice;
    private String Price;
    private String SaleCount;

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

    public String getSaleCount() {
        return SaleCount;
    }

    public void setSaleCount(String SaleCount) {
        this.SaleCount = SaleCount;
    }
}
