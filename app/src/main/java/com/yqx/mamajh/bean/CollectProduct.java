package com.yqx.mamajh.bean;

/**
 * 我的收藏 商品
 */

public class CollectProduct {

    /**
     * title : 德运高钙全脂成人牛奶粉1000g
     * img : http://182.92.183.143:8011/webimg/img_product/product/9.jpg
     * id : 9
     * price : 169.00
     */

    private String title;
    private String img;
    private int    id;
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
