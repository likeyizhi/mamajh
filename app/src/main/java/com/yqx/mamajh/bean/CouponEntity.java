package com.yqx.mamajh.bean;

/**
 * Created by young on 2016/12/26.
 */

public class CouponEntity {
    private String ID;
    private String Price;
    private String Condition;
    private String ReceiveDate;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getReceiveDate() {
        return ReceiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        ReceiveDate = receiveDate;
    }
}
