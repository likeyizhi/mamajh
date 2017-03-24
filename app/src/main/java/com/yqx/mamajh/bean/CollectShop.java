package com.yqx.mamajh.bean;

/**
 * Created by Drago on 2016/12/23 023.
 */

public class CollectShop {


    /**
     * title	String	店铺标题
     * img	String	店铺图片
     * id	Int	店铺ID
     * star	String	店铺红心
     * score		分数
     * BuyCount	Int	购买次数
     * BuyPrice	String	购买金额
     */

    private String title;
    private String img;
    private int    id;
    private int    star;
    private String score;
    private String attentionquanty;
    private String BuyCount;
    private String BuyPrice;

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

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAttentionquanty() {
        return attentionquanty;
    }

    public void setAttentionquanty(String attentionquanty) {
        this.attentionquanty = attentionquanty;
    }

    public String getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(String BuyCount) {
        this.BuyCount = BuyCount;
    }

    public String getBuyPrice() {
        return BuyPrice;
    }

    public void setBuyPrice(String BuyPrice) {
        this.BuyPrice = BuyPrice;
    }
}
