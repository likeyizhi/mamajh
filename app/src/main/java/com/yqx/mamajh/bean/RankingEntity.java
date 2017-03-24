package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/8.
 */

public class RankingEntity {

    /**
     * status : 0
     * mes : 成功
     * res : [{"SalesRanking":[{"ID":"1087","Title":"【滴滴出行】优惠券","ImgSrc":"http://182.92.183.143:8011/webimg/img_product/product/1087.jpg","SellPrice":"1","SaleCount":"1"}]}]
     */

    private int status;
    private String mes;
    private List<ResEntity> res;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<ResEntity> getRes() {
        return res;
    }

    public void setRes(List<ResEntity> res) {
        this.res = res;
    }

    public static class ResEntity {
        private List<SalesRankingEntity> SalesRanking;

        public List<SalesRankingEntity> getSalesRanking() {
            return SalesRanking;
        }

        public void setSalesRanking(List<SalesRankingEntity> SalesRanking) {
            this.SalesRanking = SalesRanking;
        }

        public static class SalesRankingEntity {
            /**
             * ID : 1087
             * Title : 【滴滴出行】优惠券
             * ImgSrc : http://182.92.183.143:8011/webimg/img_product/product/1087.jpg
             * SellPrice : 1
             * SaleCount : 1
             */

            private String ID;
            private String Title;
            private String ImgSrc;
            private String SellPrice;
            private String SaleCount;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String ImgSrc) {
                this.ImgSrc = ImgSrc;
            }

            public String getSellPrice() {
                return SellPrice;
            }

            public void setSellPrice(String SellPrice) {
                this.SellPrice = SellPrice;
            }

            public String getSaleCount() {
                return SaleCount;
            }

            public void setSaleCount(String SaleCount) {
                this.SaleCount = SaleCount;
            }
        }
    }
}
