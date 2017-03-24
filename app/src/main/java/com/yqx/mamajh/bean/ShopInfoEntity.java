package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2016/12/26.
 */

public class ShopInfoEntity {

    /**
     * id : 21
     * bigimg : http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/b11.png
     * name : 喜宝孕婴 NO.15店
     * phone:0437-88888888
     * logo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png
     * level : 5
     * collectcount : 5
     * iscollect : true
     * icoc : false
     * icos : false
     * icoq : true
     * procount : 476
     * pjcount : 2
     * showcoupon : true
     * couponlist : [{"ID":4,"Price":1,"Condition":1,"ReceiveDate":"2016-12-11"}]
     */

    private String id;
    private String bigimg;
    private String name;
    private String phone;
    private String logo;
    private int level;
    private String collectcount;
    private boolean iscollect;
    private boolean icoc;
    private boolean icos;
    private boolean icoq;
    private String procount;
    private String pjcount;
    private boolean showcoupon;
    private List<CouponEntity> couponlist;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean iscollect() {
        return iscollect;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigimg() {
        return bigimg;
    }

    public void setBigimg(String bigimg) {
        this.bigimg = bigimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCollectcount() {
        return collectcount;
    }

    public void setCollectcount(String collectcount) {
        this.collectcount = collectcount;
    }

    public boolean isIscollect() {
        return iscollect;
    }

    public void setIscollect(boolean iscollect) {
        this.iscollect = iscollect;
    }

    public boolean isIcoc() {
        return icoc;
    }

    public void setIcoc(boolean icoc) {
        this.icoc = icoc;
    }

    public boolean isIcos() {
        return icos;
    }

    public void setIcos(boolean icos) {
        this.icos = icos;
    }

    public boolean isIcoq() {
        return icoq;
    }

    public void setIcoq(boolean icoq) {
        this.icoq = icoq;
    }

    public String getProcount() {
        return procount;
    }

    public void setProcount(String procount) {
        this.procount = procount;
    }

    public String getPjcount() {
        return pjcount;
    }

    public void setPjcount(String pjcount) {
        this.pjcount = pjcount;
    }

    public boolean isShowcoupon() {
        return showcoupon;
    }

    public void setShowcoupon(boolean showcoupon) {
        this.showcoupon = showcoupon;
    }

    public List<CouponEntity> getCouponlist() {
        return couponlist;
    }

    public void setCouponlist(List<CouponEntity> couponlist) {
        this.couponlist = couponlist;
    }

}
