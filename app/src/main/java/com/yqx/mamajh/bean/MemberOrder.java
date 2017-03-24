package com.yqx.mamajh.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 会员订单
 */

public class MemberOrder implements Serializable {


    /**
     * ID : 411
     * Number : 89368201703070003
     * OrderState : 待付款
     * FinancialState : 待付款
     * OrderStateID : 2
     * FinancialStateID : 1
     * ProductCount : 1
     * Name : 宝贝科技
     * Price : 26.00
     * PayedPrice : 0.00
     * PostPrice : 0.00
     * Time : 2017-03-07 01:24:40
     * ShopID : 23
     * ShopName : 喜宝孕婴NO.10店（八里堡店）
     * ShopLogo : http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/23.png
     * ButtonState : 1
     * payData : {"OrderNumber":"89368201703070003","Pay":"1","Price":"26.00","PostPay":"到店自提","Name":"宝贝科技","Phone":"13716974107","Address":"明珠街道南部新城南三环与幸福街交汇绿地中央广场","AccountMoney":"5509.00","PayPrice":"26.00","UseAccountPayPrice":"0.00","isSetPayPassword":"1"}
     * productlist : [{"ID":"2309","Name":"完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g","Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg","Standard":"a","OrderID":"411","OrderNumber":"89368201703070003","Count":"1","Price":"26.00","Price2":"26.00"}]
     */

    private String        ID;
    private String        Number;
    private String        OrderState;
    private String        FinancialState;
    private String        OrderStateID;
    private String        FinancialStateID;
    private String        ProductCount;
    private String        Name;
    private String        Price;
    private String        PayedPrice;
    private String        PostPrice;
    private String        Time;
    private String        ShopID;
    private String        ShopName;
    private String        ShopLogo;
    private int           ButtonState;
    private PayData       payData;
    private List<Product> productlist;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String OrderState) {
        this.OrderState = OrderState;
    }

    public String getFinancialState() {
        return FinancialState;
    }

    public void setFinancialState(String FinancialState) {
        this.FinancialState = FinancialState;
    }

    public String getOrderStateID() {
        return OrderStateID;
    }

    public void setOrderStateID(String OrderStateID) {
        this.OrderStateID = OrderStateID;
    }

    public String getFinancialStateID() {
        return FinancialStateID;
    }

    public void setFinancialStateID(String FinancialStateID) {
        this.FinancialStateID = FinancialStateID;
    }

    public String getProductCount() {
        return ProductCount;
    }

    public void setProductCount(String ProductCount) {
        this.ProductCount = ProductCount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public String getPayedPrice() {
        return PayedPrice;
    }

    public void setPayedPrice(String PayedPrice) {
        this.PayedPrice = PayedPrice;
    }

    public String getPostPrice() {
        return PostPrice;
    }

    public void setPostPrice(String PostPrice) {
        this.PostPrice = PostPrice;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getShopID() {
        return ShopID;
    }

    public void setShopID(String ShopID) {
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

    public int getButtonState() {
        return ButtonState;
    }

    public void setButtonState(int ButtonState) {
        this.ButtonState = ButtonState;
    }

    public PayData getPayData() {
        return payData;
    }

    public void setPayData(PayData payData) {
        this.payData = payData;
    }

    public List<Product> getProductlist() {
        return productlist;
    }

    public void setProductlist(List<Product> productlist) {
        this.productlist = productlist;
    }

    public static class PayData implements Serializable {
        /**
         * OrderNumber : 89368201703070003
         * Pay : 1
         * Price : 26.00
         * PostPay : 到店自提
         * Name : 宝贝科技
         * Phone : 13716974107
         * Address : 明珠街道南部新城南三环与幸福街交汇绿地中央广场
         * AccountMoney : 5509.00
         * PayPrice : 26.00
         * UseAccountPayPrice : 0.00
         * isSetPayPassword : 1
         */

        private String OrderNumber;
        private int    Pay;
        private String Price;
        private String PostPay;
        private String Name;
        private String Phone;
        private String Address;
        private String AccountMoney;
        private String PayPrice;
        private String UseAccountPayPrice;
        private String isSetPayPassword;

        public String getOrderNumber() {
            return OrderNumber;
        }

        public void setOrderNumber(String OrderNumber) {
            this.OrderNumber = OrderNumber;
        }

        public int getPay() {
            return Pay;
        }

        public void setPay(int Pay) {
            this.Pay = Pay;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getPostPay() {
            return PostPay;
        }

        public void setPostPay(String PostPay) {
            this.PostPay = PostPay;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getAccountMoney() {
            return AccountMoney;
        }

        public void setAccountMoney(String AccountMoney) {
            this.AccountMoney = AccountMoney;
        }

        public String getPayPrice() {
            return PayPrice;
        }

        public void setPayPrice(String PayPrice) {
            this.PayPrice = PayPrice;
        }

        public String getUseAccountPayPrice() {
            return UseAccountPayPrice;
        }

        public void setUseAccountPayPrice(String UseAccountPayPrice) {
            this.UseAccountPayPrice = UseAccountPayPrice;
        }

        public String getIsSetPayPassword() {
            return isSetPayPassword;
        }

        public void setIsSetPayPassword(String isSetPayPassword) {
            this.isSetPayPassword = isSetPayPassword;
        }
    }

    public static class Product implements Serializable {
        /**
         * ID : 2309
         * Name : 完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g
         * Img : http://182.92.183.143:8011/webimg/img_product/product/225.jpg
         * Standard : a
         * OrderID : 411
         * OrderNumber : 89368201703070003
         * Count : 1
         * Price : 26.00
         * Price2 : 26.00
         */

        private String ID;
        private String Name;
        private String Img;
        private String Standard;
        private String OrderID;
        private String OrderNumber;
        private String Count;
        private String Price;
        private String Price2;

        private int rating = 5;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImg() {
            return Img;
        }

        public void setImg(String Img) {
            this.Img = Img;
        }

        public String getStandard() {
            return Standard;
        }

        public void setStandard(String Standard) {
            this.Standard = Standard;
        }

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String OrderID) {
            this.OrderID = OrderID;
        }

        public String getOrderNumber() {
            return OrderNumber;
        }

        public void setOrderNumber(String OrderNumber) {
            this.OrderNumber = OrderNumber;
        }

        public String getCount() {
            return Count;
        }

        public void setCount(String Count) {
            this.Count = Count;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getPrice2() {
            return Price2;
        }

        public void setPrice2(String Price2) {
            this.Price2 = Price2;
        }
    }
}
