package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by Drago on 2016/12/18 018.
 */

public class Cartlist {


    private List<Shop> cartlist;

    public List<Shop> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<Shop> cartlist) {
        this.cartlist = cartlist;
    }

    public static class Shop {
        /**
         * ShopID	Int	店铺ID
         * ShopName	String	店铺名称
         * ShopLogo	String	图片地址
         * HasGift	Int	可领取代金券数量
         * TotalPrice	String	店铺结算数量
         * TotalCount	Int	店铺结算商品数量
         * PostName	String	配送方式[忽略]
         * GiftPrice	decimal	代金券金额[忽略]
         * DeliveryPrice	decimal	配送费用[忽略]
         * GiftCount	int	代金券数量[忽略]
         * ProductList	String	商品列表数据
         */
        private int           ShopID;
        private String        ShopName;
        private String        ShopLogo;
        private int           HasGift;
        private String        TotalPrice;
        private int           TotalCount;
        private String        PostName;
        private double        GiftPrice;
        private double        DeliveryPrice;
        private int           GiftCount;
        private List<Product> ProductList;

        private boolean isCheck = false;

        public boolean getIsCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean check) {
            isCheck = check;
        }

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

        public String getPostName() {
            return PostName;
        }

        public void setPostName(String PostName) {
            this.PostName = PostName;
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

        public List<Product> getProductList() {
            return ProductList;
        }

        public void setProductList(List<Product> ProductList) {
            this.ProductList = ProductList;
        }

        public static class Product {
            /**
             * Id	Int	商品ID
             * ClassifyID	Int	分类ID
             * ShopID	Int	店铺ID
             * BrandID	Int	品牌ID
             * Code	String	商品编号
             * Name	String	商品名称
             * BarCode	String	商品条形码
             * Standard	String	商品规格
             * Img	String	商品图片
             * PromotionID	Int	促销ID
             * MarketPrice	Decimal	市场价
             * SalePrice	Decimal	销售价
             * OrderPrice	Decimal	商品在订单中价格
             * ScorePrice	Int	积分价格
             * Integral	Int	获得积分
             * Rebate	Decimal	商品返利
             * Count	Int	数量
             * IncludMailing	Bool	是否包邮
             * ActivityDescription	String	促销描述
             * SaleForm	Int	销售形势
             * PriceTotal	Decimal	价格统计[单价*数量]
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
            private int     SaleForm;
            private double  PriceTotal;

            private boolean isCheck = false;


            public boolean getIsCheck() {
                return isCheck;
            }

            public void setIsCheck(boolean check) {
                isCheck = check;
            }

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

            public int getSaleForm() {
                return SaleForm;
            }

            public void setSaleForm(int SaleForm) {
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
