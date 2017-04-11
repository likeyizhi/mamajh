package com.yqx.mamajh.bean;

import java.io.Serializable;

/**
 * Created by Drago on 2016/12/12 012.
 */

public class DeliveryAddress implements Serializable {


    private boolean isCheck = false;

    /**
     * moren	String	1=是
     * id	Int	配送地址ID
     * name	String	收货人
     * address	String	配送地址
     * phone	String	接收电话
     * phone2	String	固定电话
     * postcode	String	邮编
     * area	String	详细地址
     */

    private String moren;
    private int    id;
    private String name;
    private String address;
    private String phone;
    private String phone2;
    private String postcode;
    private String area;
    private String x;
    private String y;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean check) {
        isCheck = check;
    }

    public String getMoren() {
        return moren;
    }

    public void setMoren(String moren) {
        this.moren = moren;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
