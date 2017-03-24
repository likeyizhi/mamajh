package com.yqx.mamajh.bean;

/**
 * 会员收支明细记录
 */

public class AccountBalance {
    /**
     * Time : 2016-10-09
     * Price : -10
     * PriceIn : --
     * PriceOut : 10
     * Des : 提现申请
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
