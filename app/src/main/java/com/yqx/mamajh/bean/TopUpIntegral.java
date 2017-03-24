package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by Drago on 2016/12/27 027.
 */

public class TopUpIntegral {

    private List<Pricelist> pricelist;

    public List<Pricelist> getPricelist() {
        return pricelist;
    }

    public void setPricelist(List<Pricelist> pricelist) {
        this.pricelist = pricelist;
    }

    public static class Pricelist {
        /**
         * price : 50
         */

        private int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
