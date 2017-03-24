package com.yqx.mamajh.bean;

/**
 * 会员积分明细
 */

public class AccountIntegral {

    /**
     * Time : 2016-12-01
     * Price : +50.00
     * PriceIn : 50
     * PriceOut : --
     * Des : 每日分享得积分
     */

    private String Time;
    private String Price;
    private String PriceIn;
    private String PriceOut;
    private String Des;

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPriceIn() {
        return PriceIn;
    }

    public void setPriceIn(String PriceIn) {
        this.PriceIn = PriceIn;
    }

    public String getPriceOut() {
        return PriceOut;
    }

    public void setPriceOut(String PriceOut) {
        this.PriceOut = PriceOut;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String Des) {
        this.Des = Des;
    }
}
