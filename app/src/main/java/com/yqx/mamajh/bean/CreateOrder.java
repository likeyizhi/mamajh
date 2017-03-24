package com.yqx.mamajh.bean;

import java.io.Serializable;

/**
 * Created by Drago on 2017/2/17 017.
 */

public class CreateOrder implements Serializable {

    /**
     * OrderNumber : 96073201702090001
     * Pay : 1
     * Price : 293.00
     * PostPay : 送货上门
     * Name : 宝贝科技
     * Phone : 13716974107
     * Address : 明珠街道南部新城南三环与幸福街交汇绿地中央广场
     */

    private String OrderNumber;
    private int    Pay;
    private String Price;
    private String PostPay;
    private String Name;
    private String Phone;
    private String Address;

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public int getPay() {
        return Pay;
    }

    public void setPay(int Pay) {
        this.Pay = Pay;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPostPay() {
        return PostPay;
    }

    public void setPostPay(String PostPay) {
        this.PostPay = PostPay;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
