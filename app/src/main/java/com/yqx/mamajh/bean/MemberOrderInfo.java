package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/16.
 */

public class MemberOrderInfo {
    /** "status": 0,
     "mes": "成功",
     "res": {
     */
    private int status;
    private String mes;
    private MemberOrderInfoRes res;

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

    public MemberOrderInfoRes getRes() {
        return res;
    }

    public void setRes(MemberOrderInfoRes res) {
        this.res = res;
    }
    public static class MemberOrderInfoRes{
        /**"number": "23317201703110002",
         "orderID": "418",
         "ordertime": "2017-03-11 18:49:47",
         "state": "已取消",
         "statelist": [],
         "receivedname": "体验一次",
         "receivedaddress": "全",
         "receivedmobile": "123456789015",
         "receivedphone": "256385",
         "receivedpostcode": "123456",
         "paymodel": "在线支付-在线支付",
         "postprice": "0",
         "postmodel": "到店自提",
         "invoice": "不需要",
         "productprice": "0.04",
         "totalprice": "0.04",
         "payprice": "0.00",
         "notpayprice": "0.04",
         "giftpayprice": "0.00",
         "promotionprice": "0.00",
         "shopname": "北京旗舰店",
         "orderremark": "",
         "postinfo": "",
         "productcount": "0",
         "showPay": "0",
         "showCancel": "0",
         "showConfirm": "0",
         "productlist":[]*/

        private String number;
        private String orderID;
        private String ordertime;
        private String state;
        private List<MemberOrderInfoStatelist> statelist;
        private String receivedname;
        private String receivedaddress;
        private String receivedmobile;
        private String receivedphone;
        private String receivedpostcode;
        private String paymodel;
        private String postprice;
        private String postmodel;
        private String invoice;
        private String productprice;
        private String totalprice;
        private String payprice;
        private String notpayprice;
        private String giftpayprice;
        private String promotionprice;

        public String getPromotionprice() {
            return promotionprice;
        }

        public void setPromotionprice(String promotionprice) {
            this.promotionprice = promotionprice;
        }

        private String shopname;
        private String orderremark;
        private String postinfo;
        private String productcount;
        private String showPay;
        private String showCancel;
        private String showConfirm;
        private List<MemberOrderInfoProductlist> productlist;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrderID() {
            return orderID;
        }

        public void setOrderID(String orderID) {
            this.orderID = orderID;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<MemberOrderInfoStatelist> getStatelist() {
            return statelist;
        }

        public void setStatelist(List<MemberOrderInfoStatelist> statelist) {
            this.statelist = statelist;
        }

        public String getReceivedname() {
            return receivedname;
        }

        public void setReceivedname(String receivedname) {
            this.receivedname = receivedname;
        }

        public String getReceivedaddress() {
            return receivedaddress;
        }

        public void setReceivedaddress(String receivedaddress) {
            this.receivedaddress = receivedaddress;
        }

        public String getReceivedmobile() {
            return receivedmobile;
        }

        public void setReceivedmobile(String receivedmobile) {
            this.receivedmobile = receivedmobile;
        }

        public String getReceivedphone() {
            return receivedphone;
        }

        public void setReceivedphone(String receivedphone) {
            this.receivedphone = receivedphone;
        }

        public String getReceivedpostcode() {
            return receivedpostcode;
        }

        public void setReceivedpostcode(String receivedpostcode) {
            this.receivedpostcode = receivedpostcode;
        }

        public String getPaymodel() {
            return paymodel;
        }

        public void setPaymodel(String paymodel) {
            this.paymodel = paymodel;
        }

        public String getPostprice() {
            return postprice;
        }

        public void setPostprice(String postprice) {
            this.postprice = postprice;
        }

        public String getPostmodel() {
            return postmodel;
        }

        public void setPostmodel(String postmodel) {
            this.postmodel = postmodel;
        }

        public String getInvoice() {
            return invoice;
        }

        public void setInvoice(String invoice) {
            this.invoice = invoice;
        }

        public String getProductprice() {
            return productprice;
        }

        public void setProductprice(String productprice) {
            this.productprice = productprice;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getPayprice() {
            return payprice;
        }

        public void setPayprice(String payprice) {
            this.payprice = payprice;
        }

        public String getNotpayprice() {
            return notpayprice;
        }

        public void setNotpayprice(String notpayprice) {
            this.notpayprice = notpayprice;
        }

        public String getGiftpayprice() {
            return giftpayprice;
        }

        public void setGiftpayprice(String giftpayprice) {
            this.giftpayprice = giftpayprice;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getOrderremark() {
            return orderremark;
        }

        public void setOrderremark(String orderremark) {
            this.orderremark = orderremark;
        }

        public String getPostinfo() {
            return postinfo;
        }

        public void setPostinfo(String postinfo) {
            this.postinfo = postinfo;
        }

        public String getProductcount() {
            return productcount;
        }

        public void setProductcount(String productcount) {
            this.productcount = productcount;
        }

        public String getShowPay() {
            return showPay;
        }

        public void setShowPay(String showPay) {
            this.showPay = showPay;
        }

        public String getShowCancel() {
            return showCancel;
        }

        public void setShowCancel(String showCancel) {
            this.showCancel = showCancel;
        }

        public String getShowConfirm() {
            return showConfirm;
        }

        public void setShowConfirm(String showConfirm) {
            this.showConfirm = showConfirm;
        }

        public List<MemberOrderInfoProductlist> getProductlist() {
            return productlist;
        }

        public void setProductlist(List<MemberOrderInfoProductlist> productlist) {
            this.productlist = productlist;
        }

        public static class MemberOrderInfoStatelist {
            /**"statename": "新订单",
             "statetime": "2017-03-11 18:49:47"*/

            private String statename;
            private String statetime;

            public String getStatename() {
                return statename;
            }

            public void setStatename(String statename) {
                this.statename = statename;
            }

            public String getStatetime() {
                return statetime;
            }

            public void setStatetime(String statetime) {
                this.statetime = statetime;
            }
        }

        public static class MemberOrderInfoProductlist {
            /**"id": "2283",
             "code": "M0000001151",
             "name": "Hape 沙滩玩具套装 沙滩冒险小套 4件",
             "price": "0.0100",
             "count": "4",
             "totalprice": "0.04",
             "imgurl": "http://182.92.183.143:8011/webimg/img_product/product/1863.jpg"*/

            private String id;
            private String code;
            private String name;
            private String price;
            private String count;
            private String totalprice;
            private String imgurl;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getTotalprice() {
                return totalprice;
            }

            public void setTotalprice(String totalprice) {
                this.totalprice = totalprice;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }

    }
}
