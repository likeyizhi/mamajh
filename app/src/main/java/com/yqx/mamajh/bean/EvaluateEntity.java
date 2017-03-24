package com.yqx.mamajh.bean;

/**
 * Created by young on 2017/3/9.
 */

public class EvaluateEntity {
    /**
     * status : 0
     * mes : 成功
     * res : {"id":"21","bigimg":"http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/b11.png","name":"喜宝孕婴 NO.15店","logo":"http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png","level":5,"icoc":false,"icos":false,"icoq":true,"collectcount":5,"iscollect":true,"procount":"476","pjtotal":"2","pjscore":"10.0","highscore":"16.2","servicescore":5,"productscore":5,"deliverscore":5,"pjgood":"0","pjdisappointe":"0","pjpic":"1","praisedegree":"0"}
     */

    private int status;
    private String mes;
    private ResEntity res;

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

    public ResEntity getRes() {
        return res;
    }

    public void setRes(ResEntity res) {
        this.res = res;
    }

    public static class ResEntity {
        /**
         * id : 21
         * bigimg : http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/b11.png
         * name : 喜宝孕婴 NO.15店
         * logo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png
         * level : 5
         * icoc : false
         * icos : false
         * icoq : true
         * collectcount : 5
         * iscollect : true
         * procount : 476
         * pjtotal : 2
         * pjscore : 10.0
         * highscore : 16.2
         * servicescore : 5
         * productscore : 5
         * deliverscore : 5
         * pjgood : 0
         * pjdisappointe : 0
         * pjpic : 1
         * praisedegree : 0
         */

        private String id;
        private String bigimg;
        private String name;
        private String logo;
        private int level;
        private boolean icoc;
        private boolean icos;
        private boolean icoq;
        private int collectcount;
        private boolean iscollect;
        private String procount;
        private String pjtotal;
        private String pjscore;
        private String highscore;
        private int servicescore;
        private int productscore;
        private int deliverscore;
        private String pjgood;
        private String pjdisappointe;
        private String pjpic;
        private String praisedegree;

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

        public int getCollectcount() {
            return collectcount;
        }

        public void setCollectcount(int collectcount) {
            this.collectcount = collectcount;
        }

        public boolean isIscollect() {
            return iscollect;
        }

        public void setIscollect(boolean iscollect) {
            this.iscollect = iscollect;
        }

        public String getProcount() {
            return procount;
        }

        public void setProcount(String procount) {
            this.procount = procount;
        }

        public String getPjtotal() {
            return pjtotal;
        }

        public void setPjtotal(String pjtotal) {
            this.pjtotal = pjtotal;
        }

        public String getPjscore() {
            return pjscore;
        }

        public void setPjscore(String pjscore) {
            this.pjscore = pjscore;
        }

        public String getHighscore() {
            return highscore;
        }

        public void setHighscore(String highscore) {
            this.highscore = highscore;
        }

        public int getServicescore() {
            return servicescore;
        }

        public void setServicescore(int servicescore) {
            this.servicescore = servicescore;
        }

        public int getProductscore() {
            return productscore;
        }

        public void setProductscore(int productscore) {
            this.productscore = productscore;
        }

        public int getDeliverscore() {
            return deliverscore;
        }

        public void setDeliverscore(int deliverscore) {
            this.deliverscore = deliverscore;
        }

        public String getPjgood() {
            return pjgood;
        }

        public void setPjgood(String pjgood) {
            this.pjgood = pjgood;
        }

        public String getPjdisappointe() {
            return pjdisappointe;
        }

        public void setPjdisappointe(String pjdisappointe) {
            this.pjdisappointe = pjdisappointe;
        }

        public String getPjpic() {
            return pjpic;
        }

        public void setPjpic(String pjpic) {
            this.pjpic = pjpic;
        }

        public String getPraisedegree() {
            return praisedegree;
        }

        public void setPraisedegree(String praisedegree) {
            this.praisedegree = praisedegree;
        }
    }
}
