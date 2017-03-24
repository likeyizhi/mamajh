package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by Drago on 2017/2/16 016.
 */

public class OrderInfo {

    /**
     * DeliveryID : 49
     * DeliveryName : 宝贝科技
     * DeliveryAddress : 明珠街道南部新城南三环与幸福街交汇绿地中央广场
     * DeliveryPhone : 13716974107
     * shoplist : [{"ShopID":21,"ShopName":"喜宝孕婴 NO.15店","ShopLogo":"http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png","HasGift":0,"TotalPrice":"288.00","TotalCount":1,"ProductList":[{"ID":288,"ClassifyID":1,"ShopID":21,"BrandID":0,"Code":"S0002100025","Name":"合生元（BIOSTIME）金装较大婴儿配方奶粉2段（6-12个月）900g","BarCode":"3760170400013","Standard":"","Img":"http://182.92.183.143:8011/webimg/img_product/product/288.jpg","PromotionID":0,"MarketPrice":288,"SalePrice":288,"OrderPrice":288,"ScorePrice":0,"Integral":0,"Rebate":0,"Count":1,"IncludMailing":false,"ActivityDescription":"","SaleForm":"1","PriceTotal":288}],"PostID":0,"PostName":"到店自提","GiftID":0,"GiftPrice":0,"DeliveryPrice":0,"GiftCount":0}]
     * BuyItem : 1
     * ProductCount : 1
     * Freight : 0.0
     * PriceGiftPriceTotal : 0.0
     * OrderTotalPrice : 288.0
     * OrderPayPrice : 288.0
     * TotalPayScore : 0
     * MemberTotalScore : 0.00
     * CanPayTotalScore : 0
     * PayTotalScore : 0
     * ScoreProductCount : 0
     * TotalScore : 5.00
     */

    private int            DeliveryID;
    private String         DeliveryName;
    private String         DeliveryAddress;
    private String         DeliveryPhone;
    private int            BuyItem;
    private int            ProductCount;
    private double         Freight;
    private double         PriceGiftPriceTotal;
    private double         OrderTotalPrice;
    private double         OrderPayPrice;
    private int            TotalPayScore;
    private String         MemberTotalScore;
    private int            CanPayTotalScore;
    private int            PayTotalScore;
    private int            ScoreProductCount;
    private String         TotalScore;
    private List<Shoplist> shoplist;

    public int getDeliveryID() {
        return DeliveryID;
    }

    public void setDeliveryID(int DeliveryID) {
        this.DeliveryID = DeliveryID;
    }

    public String getDeliveryName() {
        return DeliveryName;
    }

    public void setDeliveryName(String DeliveryName) {
        this.DeliveryName = DeliveryName;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String DeliveryAddress) {
        this.DeliveryAddress = DeliveryAddress;
    }

    public String getDeliveryPhone() {
        return DeliveryPhone;
    }

    public void setDeliveryPhone(String DeliveryPhone) {
        this.DeliveryPhone = DeliveryPhone;
    }

    public int getBuyItem() {
        return BuyItem;
    }

    public void setBuyItem(int BuyItem) {
        this.BuyItem = BuyItem;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public double getFreight() {
        return Freight;
    }

    public void setFreight(double Freight) {
        this.Freight = Freight;
    }

    public double getPriceGiftPriceTotal() {
        return PriceGiftPriceTotal;
    }

    public void setPriceGiftPriceTotal(double PriceGiftPriceTotal) {
        this.PriceGiftPriceTotal = PriceGiftPriceTotal;
    }

    public double getOrderTotalPrice() {
        return OrderTotalPrice;
    }

    public void setOrderTotalPrice(double OrderTotalPrice) {
        this.OrderTotalPrice = OrderTotalPrice;
    }

    public double getOrderPayPrice() {
        return OrderPayPrice;
    }

    public void setOrderPayPrice(double OrderPayPrice) {
        this.OrderPayPrice = OrderPayPrice;
    }

    public int getTotalPayScore() {
        return TotalPayScore;
    }

    public void setTotalPayScore(int TotalPayScore) {
        this.TotalPayScore = TotalPayScore;
    }

    public String getMemberTotalScore() {
        return MemberTotalScore;
    }

    public void setMemberTotalScore(String MemberTotalScore) {
        this.MemberTotalScore = MemberTotalScore;
    }

    public int getCanPayTotalScore() {
        return CanPayTotalScore;
    }

    public void setCanPayTotalScore(int CanPayTotalScore) {
        this.CanPayTotalScore = CanPayTotalScore;
    }

    public int getPayTotalScore() {
        return PayTotalScore;
    }

    public void setPayTotalScore(int PayTotalScore) {
        this.PayTotalScore = PayTotalScore;
    }

    public int getScoreProductCount() {
        return ScoreProductCount;
    }

    public void setScoreProductCount(int ScoreProductCount) {
        this.ScoreProductCount = ScoreProductCount;
    }

    public String getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(String TotalScore) {
        this.TotalScore = TotalScore;
    }

    public List<Shoplist> getShoplist() {
        return shoplist;
    }

    public void setShoplist(List<Shoplist> shoplist) {
        this.shoplist = shoplist;
    }

    public static class Shoplist {
        /**
         * ShopID : 21
         * ShopName : 喜宝孕婴 NO.15店
         * ShopLogo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/21.png
         * HasGift : 0
         * TotalPrice : 288.00
         * TotalCount : 1
         * ProductList : [{"ID":288,"ClassifyID":1,"ShopID":21,"BrandID":0,"Code":"S0002100025","Name":"合生元（BIOSTIME）金装较大婴儿配方奶粉2段（6-12个月）900g","BarCode":"3760170400013","Standard":"","Img":"http://182.92.183.143:8011/webimg/img_product/product/288.jpg","PromotionID":0,"MarketPrice":288,"SalePrice":288,"OrderPrice":288,"ScorePrice":0,"Integral":0,"Rebate":0,"Count":1,"IncludMailing":false,"ActivityDescription":"","SaleForm":"1","PriceTotal":288}]
         * PostID : 0
         * PostName : 到店自提
         * GiftID : 0.0
         * GiftPrice : 0.0
         * DeliveryPrice : 0.0
         * GiftCount : 0
         */

        private int                   ShopID;
        private String                ShopName;
        private String                ShopLogo;
        private int                   HasGift;
        private String                TotalPrice;
        private int                   TotalCount;
        private int                   PostID;
        private String                PostName;
        private int                   GiftID;
        private double                GiftPrice;
        private double                DeliveryPrice;
        private int                   GiftCount;
        private List<ProductListBean> ProductList;

        public int getShopID() {
            return ShopID;
        }

        public void setShopID(int ShopID) {
            this.ShopID = ShopID;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String ShopName) {
            this.ShopName = ShopName;
        }

        public String getShopLogo() {
            return ShopLogo;
        }

        public void setShopLogo(String ShopLogo) {
            this.ShopLogo = ShopLogo;
        }

        public int getHasGift() {
            return HasGift;
        }

        public void setHasGift(int HasGift) {
            this.HasGift = HasGift;
        }

        public String getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(String TotalPrice) {
            this.TotalPrice = TotalPrice;
        }

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }

        public int getPostID() {
            return PostID;
        }

        public void setPostID(int PostID) {
            this.PostID = PostID;
        }

        public String getPostName() {
            return PostName;
        }

        public void setPostName(String PostName) {
            this.PostName = PostName;
        }

        public int getGiftID() {
            return GiftID;
        }

        public void setGiftID(int GiftID) {
            this.GiftID = GiftID;
        }

        public double getGiftPrice() {
            return GiftPrice;
        }

        public void setGiftPrice(double GiftPrice) {
            this.GiftPrice = GiftPrice;
        }

        public double getDeliveryPrice() {
            return DeliveryPrice;
        }

        public void setDeliveryPrice(double DeliveryPrice) {
            this.DeliveryPrice = DeliveryPrice;
        }

        public int getGiftCount() {
            return GiftCount;
        }

        public void setGiftCount(int GiftCount) {
            this.GiftCount = GiftCount;
        }

        public List<ProductListBean> getProductList() {
            return ProductList;
        }

        public void setProductList(List<ProductListBean> ProductList) {
            this.ProductList = ProductList;
        }

        public static class ProductListBean {
            /**
             * ID : 288
             * ClassifyID : 1
             * ShopID : 21
             * BrandID : 0
             * Code : S0002100025
             * Name : 合生元（BIOSTIME）金装较大婴儿配方奶粉2段（6-12个月）900g
             * BarCode : 3760170400013
             * Standard :
             * Img : http://182.92.183.143:8011/webimg/img_product/product/288.jpg
             * PromotionID : 0
             * MarketPrice : 288.0
             * SalePrice : 288.0
             * OrderPrice : 288.0
             * ScorePrice : 0
             * Integral : 0
             * Rebate : 0.0
             * Count : 1
             * IncludMailing : false
             * ActivityDescription :
             * SaleForm : 1
             * PriceTotal : 288.0
             */

            private int     ID;
            private int     ClassifyID;
            private int     ShopID;
            private int     BrandID;
            private String  Code;
            private String  Name;
            private String  BarCode;
            private String  Standard;
            private String  Img;
            private int     PromotionID;
            private double  MarketPrice;
            private double  SalePrice;
            private double  OrderPrice;
            private int     ScorePrice;
            private int     Integral;
            private double  Rebate;
            private int     Count;
            private boolean IncludMailing;
            private String  ActivityDescription;
            private String  SaleForm;
            private double  PriceTotal;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public int getClassifyID() {
                return ClassifyID;
            }

            public void setClassifyID(int ClassifyID) {
                this.ClassifyID = ClassifyID;
            }

            public int getShopID() {
                return ShopID;
            }

            public void setShopID(int ShopID) {
                this.ShopID = ShopID;
            }

            public int getBrandID() {
                return BrandID;
            }

            public void setBrandID(int BrandID) {
                this.BrandID = BrandID;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getBarCode() {
                return BarCode;
            }

            public void setBarCode(String BarCode) {
                this.BarCode = BarCode;
            }

            public String getStandard() {
                return Standard;
            }

            public void setStandard(String Standard) {
                this.Standard = Standard;
            }

            public String getImg() {
                return Img;
            }

            public void setImg(String Img) {
                this.Img = Img;
            }

            public int getPromotionID() {
                return PromotionID;
            }

            public void setPromotionID(int PromotionID) {
                this.PromotionID = PromotionID;
            }

            public double getMarketPrice() {
                return MarketPrice;
            }

            public void setMarketPrice(double MarketPrice) {
                this.MarketPrice = MarketPrice;
            }

            public double getSalePrice() {
                return SalePrice;
            }

            public void setSalePrice(double SalePrice) {
                this.SalePrice = SalePrice;
            }

            public double getOrderPrice() {
                return OrderPrice;
            }

            public void setOrderPrice(double OrderPrice) {
                this.OrderPrice = OrderPrice;
            }

            public int getScorePrice() {
                return ScorePrice;
            }

            public void setScorePrice(int ScorePrice) {
                this.ScorePrice = ScorePrice;
            }

            public int getIntegral() {
                return Integral;
            }

            public void setIntegral(int Integral) {
                this.Integral = Integral;
            }

            public double getRebate() {
                return Rebate;
            }

            public void setRebate(double Rebate) {
                this.Rebate = Rebate;
            }

            public int getCount() {
                return Count;
            }

            public void setCount(int Count) {
                this.Count = Count;
            }

            public boolean isIncludMailing() {
                return IncludMailing;
            }

            public void setIncludMailing(boolean IncludMailing) {
                this.IncludMailing = IncludMailing;
            }

            public String getActivityDescription() {
                return ActivityDescription;
            }

            public void setActivityDescription(String ActivityDescription) {
                this.ActivityDescription = ActivityDescription;
            }

            public String getSaleForm() {
                return SaleForm;
            }

            public void setSaleForm(String SaleForm) {
                this.SaleForm = SaleForm;
            }

            public double getPriceTotal() {
                return PriceTotal;
            }

            public void setPriceTotal(double PriceTotal) {
                this.PriceTotal = PriceTotal;
            }
        }
    }
}
