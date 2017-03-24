package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/9.
 */

public class ShopInformationEntity {
    /**
     * status : 0
     * mes : 成功
     * res : {"bigimg":"http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/b11.png","name":"喜宝孕婴 NO.15店","logo":"http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png","level":5,"icoc":true,"icos":false,"icoq":true,"collectcount":5,"iscollect":true,"procount":"476","pjtotal":"2","pjtotal2":"0","monthorder":"0","activitylist":[{"ID":16,"Name":"满xx减xx","TypeName":"满减"}],"shopimglist":[{"ID":38,"ImgSrc":"http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.jpg"}],"shopdesc":"长春喜宝孕婴连锁","shopaddress":"西环城路与西安大路交汇","shopworktime":"08:15-23:45","shopphone":"13624316203","shopauthimglist":[{"ID":3,"ImgSrc":"http://182.92.183.143:8011/webimg/img_DeliveryShop/QualificationsImg/2.jpg","Desc":""}]}
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
         * bigimg : http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/b11.png
         * name : 喜宝孕婴 NO.15店
         * logo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png
         * level : 5
         * icoc : true
         * icos : false
         * icoq : true
         * collectcount : 5
         * iscollect : true
         * procount : 476
         * pjtotal : 2
         * pjtotal2 : 0
         * monthorder : 0
         * activitylist : [{"ID":16,"Name":"满xx减xx","TypeName":"满减"}]
         * shopimglist : [{"ID":38,"ImgSrc":"http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.jpg"}]
         * shopdesc : 长春喜宝孕婴连锁
         * shopaddress : 西环城路与西安大路交汇
         * shopworktime : 08:15-23:45
         * shopphone : 13624316203
         * shopauthimglist : [{"ID":3,"ImgSrc":"http://182.92.183.143:8011/webimg/img_DeliveryShop/QualificationsImg/2.jpg","Desc":""}]
         */

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
        private String pjtotal2;
        private String monthorder;
        private String shopdesc;
        private String shopaddress;
        private String shopworktime;
        private String shopphone;
        private List<ActivitylistEntity> activitylist;
        private List<ShopimglistEntity> shopimglist;
        private List<ShopauthimglistEntity> shopauthimglist;

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

        public String getPjtotal2() {
            return pjtotal2;
        }

        public void setPjtotal2(String pjtotal2) {
            this.pjtotal2 = pjtotal2;
        }

        public String getMonthorder() {
            return monthorder;
        }

        public void setMonthorder(String monthorder) {
            this.monthorder = monthorder;
        }

        public String getShopdesc() {
            return shopdesc;
        }

        public void setShopdesc(String shopdesc) {
            this.shopdesc = shopdesc;
        }

        public String getShopaddress() {
            return shopaddress;
        }

        public void setShopaddress(String shopaddress) {
            this.shopaddress = shopaddress;
        }

        public String getShopworktime() {
            return shopworktime;
        }

        public void setShopworktime(String shopworktime) {
            this.shopworktime = shopworktime;
        }

        public String getShopphone() {
            return shopphone;
        }

        public void setShopphone(String shopphone) {
            this.shopphone = shopphone;
        }

        public List<ActivitylistEntity> getActivitylist() {
            return activitylist;
        }

        public void setActivitylist(List<ActivitylistEntity> activitylist) {
            this.activitylist = activitylist;
        }

        public List<ShopimglistEntity> getShopimglist() {
            return shopimglist;
        }

        public void setShopimglist(List<ShopimglistEntity> shopimglist) {
            this.shopimglist = shopimglist;
        }

        public List<ShopauthimglistEntity> getShopauthimglist() {
            return shopauthimglist;
        }

        public void setShopauthimglist(List<ShopauthimglistEntity> shopauthimglist) {
            this.shopauthimglist = shopauthimglist;
        }

        public static class ActivitylistEntity {
            /**
             * ID : 16
             * Name : 满xx减xx
             * TypeName : 满减
             */

            private int ID;
            private String Name;
            private String TypeName;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String TypeName) {
                this.TypeName = TypeName;
            }
        }

        public static class ShopimglistEntity {
            /**
             * ID : 38
             * ImgSrc : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.jpg
             */

            private int ID;
            private String ImgSrc;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String ImgSrc) {
                this.ImgSrc = ImgSrc;
            }
        }

        public static class ShopauthimglistEntity {
            /**
             * ID : 3
             * ImgSrc : http://182.92.183.143:8011/webimg/img_DeliveryShop/QualificationsImg/2.jpg
             * Desc :
             */

            private int ID;
            private String ImgSrc;
            private String Desc;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String ImgSrc) {
                this.ImgSrc = ImgSrc;
            }

            public String getDesc() {
                return Desc;
            }

            public void setDesc(String Desc) {
                this.Desc = Desc;
            }
        }
    }
}
