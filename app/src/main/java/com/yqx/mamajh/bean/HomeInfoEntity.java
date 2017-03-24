package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2016/12/11.
 */

public class HomeInfoEntity {

    private List<CarouseladEntity> carouselad;
    private List<WordadEntity> wordad;
    private List<HotshopEntity> hotshop;
    private List<BlowshopFourAdEntity> blowshop_four_ad;
    private List<BlowshopOneAdEntity> blowshop_one_ad;

    public List<CarouseladEntity> getCarouselad() {return carouselad;}

    public void setCarouselad(List<CarouseladEntity> carouselad) {
        this.carouselad = carouselad;
    }

    public List<WordadEntity> getWordad() {
        return wordad;
    }

    public void setWordad(List<WordadEntity> wordad) {
        this.wordad = wordad;
    }

    public List<HotshopEntity> getHotshop() {
        return hotshop;
    }

    public void setHotshop(List<HotshopEntity> hotshop) {
        this.hotshop = hotshop;
    }

    public List<BlowshopFourAdEntity> getBlowshop_four_ad() {
        return blowshop_four_ad;
    }

    public void setBlowshop_four_ad(List<BlowshopFourAdEntity> blowshop_four_ad) {
        this.blowshop_four_ad = blowshop_four_ad;
    }

    public List<BlowshopOneAdEntity> getBlowshop_one_ad() {
        return blowshop_one_ad;
    }

    public void setBlowshop_one_ad(List<BlowshopOneAdEntity> blowshop_one_ad) {
        this.blowshop_one_ad = blowshop_one_ad;
    }

    public static class CarouseladEntity {
        /**
         * title : 2015-12-01
         * img : http://182.92.183.143:8011/webimg/img_AD/201661611613422.jpg
         * linktype : 1
         * productid : 1
         */

        private String title;
        private String img;
        private String linktype;
        private String productid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLinktype() {
            return linktype;
        }

        public void setLinktype(String linktype) {
            this.linktype = linktype;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }
    }

    public static class WordadEntity {
        /**
         * title : 大促
         * desc : 小葵花妈妈课堂开课了！
         * img : http://182.92.183.143:8011/webimg/img_AD/
         * linktype : 1
         * productid : 1
         */

        private String title;
        private String desc;
        private String img;
        private String linktype;
        private String productid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLinktype() {
            return linktype;
        }

        public void setLinktype(String linktype) {
            this.linktype = linktype;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }
    }

    public static class HotshopEntity {
        /**
         * ID : 1
         * Name : 北京旗舰店
         * Phone : 13811118888
         * Logo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/1.png
         * X : 116.529112
         * Y : 39.931321
         * Distance : 752米
         * Time : 48
         * Mark : 0
         * SaleTotal : 200
         * SareTotal : 0
         * IsCollect : 0
         * Address : 1
         */

        private String ID;
        private String Name;
        private String Phone;
        private String Logo;
        private String X;
        private String Y;
        private String Distance;
        private String Time;
        private String Mark;
        private String SaleTotal;
        private String SareTotal;
        private String StartingFee;
        private String DistributionFee;
        private String IsCollect;
        private String Address;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getLogo() {
            return Logo;
        }

        public void setLogo(String logo) {
            Logo = logo;
        }

        public String getX() {
            return X;
        }

        public void setX(String x) {
            X = x;
        }

        public String getY() {
            return Y;
        }

        public void setY(String y) {
            Y = y;
        }

        public String getDistance() {
            return Distance;
        }

        public void setDistance(String distance) {
            Distance = distance;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String time) {
            Time = time;
        }

        public String getMark() {
            return Mark;
        }

        public void setMark(String mark) {
            Mark = mark;
        }

        public String getSaleTotal() {
            return SaleTotal;
        }

        public void setSaleTotal(String saleTotal) {
            SaleTotal = saleTotal;
        }

        public String getSareTotal() {
            return SareTotal;
        }

        public void setSareTotal(String sareTotal) {
            SareTotal = sareTotal;
        }

        public String getStartingFee() {
            return StartingFee;
        }

        public void setStartingFee(String startingFee) {
            StartingFee = startingFee;
        }

        public String getDistributionFee() {
            return DistributionFee;
        }

        public void setDistributionFee(String distributionFee) {
            DistributionFee = distributionFee;
        }

        public String getIsCollect() {
            return IsCollect;
        }

        public void setIsCollect(String isCollect) {
            IsCollect = isCollect;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }
    }

    public static class BlowshopFourAdEntity {
        /**
         * title : 2015-12-01
         * img : http://182.92.183.143:8011/webimg/img_AD/201661611613422.jpg
         * linktype : 1
         * productid : 1
         */

        private String title;
        private String img;
        private String linktype;
        private String productid;

        private int icon;

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLinktype() {
            return linktype;
        }

        public void setLinktype(String linktype) {
            this.linktype = linktype;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }
    }

    public static class BlowshopOneAdEntity {
        /**
         * title : 1
         * img : http://182.92.183.143:8011/webimg/img_AD/2016616132246226.jpg
         * linktype : 1
         * productid : 1
         */

        private String title;
        private String img;
        private String linktype;
        private String productid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLinktype() {
            return linktype;
        }

        public void setLinktype(String linktype) {
            this.linktype = linktype;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }
    }
}
