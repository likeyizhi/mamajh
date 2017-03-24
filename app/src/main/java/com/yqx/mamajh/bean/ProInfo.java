package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by likey on 2017/3/11.
 */

public class ProInfo {
    /*  "status": 0,
        "mes": "成功",
        "res"*/
    private int status;
    private String mes;
    private ProInfoRes res;

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

    public ProInfoRes getRes() {
        return res;
    }

    public void setRes(ProInfoRes res) {
        this.res = res;
    }

    public static class ProInfoRes {
/*      "moreproimg": [],
        "proname": "宝德 初生儿宽口玻璃奶瓶晶钻玻璃材质配防胀气硅胶奶嘴 蓝色 120ml",
        "prodesc": "",
        "procontent": "<p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bf0b5e57-8d98-4aab-a945-99f679d44afa.jpg\" style=\"float:none;\" title=\"0001.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bd472b86-af0d-4acc-849d-99080d419d4c.jpg\" style=\"float:none;\" title=\"0002.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-158167c49e-449d-4753-aab1-6e5ea23983d4.jpg\" style=\"float:none;\" title=\"0003.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bef4e5d3-dc36-4ecc-aad5-085cea773f1c.jpg\" style=\"float:none;\" title=\"0004.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-16a19014da-19ab-4b78-a9c6-69f75cecc6b9.jpg\" style=\"float:none;\" title=\"0005.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-16b8bb635f-6f53-40b0-b92c-f358e3fa7e80.jpg\" style=\"float:none;\" title=\"0006.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-169d3e6d9a-f872-4a38-bee6-784f68b87f73.jpg\" style=\"float:none;\" title=\"0007.jpg\" /></p><p><br /></p>",
        "saleprice": "44.00",
        "marketprice": "55.00",
        "salecount": 100,
        "isothershow": true,
        "otherlist": [],
        "iscollect": false,
        "shopid": 23,
        "bigimg": "http://182.92.183.143:8011/webimg/img_DeliveryShop/ShopIxImg/default.jpg",
        "name": "喜宝孕婴NO.10店（八里堡店）",
        "phone": "13214489405",
        "logo": "http://182.92.183.143:8011/webimg/img_DeliveryShop/DeliveryShop/23.png",
        "level": 5,
        "icoc": false,
        "icos": false,
        "icoq": false,
        "collectcount": 1,
        "procount": "0",
        "activitycount": "0",
        "activitylist": [],
        "deliverylist": [],
        "pjcount": 0,
        "goodratio": "100",
        "paramlist": [],
        "package": "<p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bf0b5e57-8d98-4aab-a945-99f679d44afa.jpg\" style=\"float:none;\" title=\"0001.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bd472b86-af0d-4acc-849d-99080d419d4c.jpg\" style=\"float:none;\" title=\"0002.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-158167c49e-449d-4753-aab1-6e5ea23983d4.jpg\" style=\"float:none;\" title=\"0003.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-15bef4e5d3-dc36-4ecc-aad5-085cea773f1c.jpg\" style=\"float:none;\" title=\"0004.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-16a19014da-19ab-4b78-a9c6-69f75cecc6b9.jpg\" style=\"float:none;\" title=\"0005.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-16b8bb635f-6f53-40b0-b92c-f358e3fa7e80.jpg\" style=\"float:none;\" title=\"0006.jpg\" /></p><p><img src=\"http://182.92.183.143:8011/webimg/editor/2016-08-03-169d3e6d9a-f872-4a38-bee6-784f68b87f73.jpg\" style=\"float:none;\" title=\"0007.jpg\" /></p><p><br /></p>",
        "detailsurl": "http://wx.bjqydl.com/ShopProductDetails.aspx?id=2399"*/

        private List<ProInfoMoreproimg> moreproimg;
        private String proname;
        private String prodesc;
        private String procontent;
        private String saleprice;
        private String marketprice;
        private int salecount;
        private boolean isothershow;
        private List<ProInfoOtherlist> otherlist;
        private boolean iscollect;
        private int shopid;
        private String bigimg;
        private String name;
        private String phone;
        private String logo;
        private int level;
        private boolean icoc;
        private boolean icos;
        private boolean icoq;
        private int collectcount;
        private String procount;
        private String activitycount;
        private List<ProInfoActivitylist> activitylist;
        private List<ProInfoDeliverylist> deliverylist;
        private int pjcount;
        private String goodratio;
        private List<ProInfoParamlist> paramlist;
        //        private String package;
        private String detailsurl;

        public List<ProInfoMoreproimg> getMoreproimg() {
            return moreproimg;
        }

        public void setMoreproimg(List<ProInfoMoreproimg> moreproimg) {
            this.moreproimg = moreproimg;
        }

        public String getProname() {
            return proname;
        }

        public void setProname(String proname) {
            this.proname = proname;
        }

        public String getProdesc() {
            return prodesc;
        }

        public void setProdesc(String prodesc) {
            this.prodesc = prodesc;
        }

        public String getProcontent() {
            return procontent;
        }

        public void setProcontent(String procontent) {
            this.procontent = procontent;
        }

        public String getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(String saleprice) {
            this.saleprice = saleprice;
        }

        public String getMarketprice() {
            return marketprice;
        }

        public void setMarketprice(String marketprice) {
            this.marketprice = marketprice;
        }

        public int getSalecount() {
            return salecount;
        }

        public void setSalecount(int salecount) {
            this.salecount = salecount;
        }

        public boolean isothershow() {
            return isothershow;
        }

        public void setIsothershow(boolean isothershow) {
            this.isothershow = isothershow;
        }

        public List<ProInfoOtherlist> getOtherlist() {
            return otherlist;
        }

        public void setOtherlist(List<ProInfoOtherlist> otherlist) {
            this.otherlist = otherlist;
        }

        public boolean iscollect() {
            return iscollect;
        }

        public void setIscollect(boolean iscollect) {
            this.iscollect = iscollect;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getProcount() {
            return procount;
        }

        public void setProcount(String procount) {
            this.procount = procount;
        }

        public String getActivitycount() {
            return activitycount;
        }

        public void setActivitycount(String activitycount) {
            this.activitycount = activitycount;
        }

        public List<ProInfoActivitylist> getActivitylist() {
            return activitylist;
        }

        public void setActivitylist(List<ProInfoActivitylist> activitylist) {
            this.activitylist = activitylist;
        }

        public List<ProInfoDeliverylist> getDeliverylist() {
            return deliverylist;
        }

        public void setDeliverylist(List<ProInfoDeliverylist> deliverylist) {
            this.deliverylist = deliverylist;
        }

        public int getPjcount() {
            return pjcount;
        }

        public void setPjcount(int pjcount) {
            this.pjcount = pjcount;
        }

        public String getGoodratio() {
            return goodratio;
        }

        public void setGoodratio(String goodratio) {
            this.goodratio = goodratio;
        }

        public List<ProInfoParamlist> getParamlist() {
            return paramlist;
        }

        public void setParamlist(List<ProInfoParamlist> paramlist) {
            this.paramlist = paramlist;
        }

        public String getDetailsurl() {
            return detailsurl;
        }

        public void setDetailsurl(String detailsurl) {
            this.detailsurl = detailsurl;
        }

        public static class ProInfoMoreproimg {
            /*"ID": 8083,
            "ImgSrc": "ht*/
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

            public void setImgSrc(String imgSrc) {
                ImgSrc = imgSrc;
            }
        }
        public static class ProInfoOtherlist {
            /*"ID": 2400,
            "Title": "宝德 初生儿宽口玻璃奶瓶晶钻玻璃材质配防胀气硅胶奶嘴 绿色 120ml"*/
            private int ID;
            private String Title;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String title) {
                Title = title;
            }
        }
        public static class ProInfoActivitylist {
            /**"ID":16,
             "Name":"满xx减xx",
             "TypeName":"满减"*/
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

            public void setName(String name) {
                Name = name;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String typeName) {
                TypeName = typeName;
            }
        }
        public static class ProInfoDeliverylist {
            /*"Title": "门店直达",
            "State": 0*/
            private String Title;
            private int State;

            public String getTitle() {
                return Title;
            }

            public void setTitle(String title) {
                Title = title;
            }

            public int getState() {
                return State;
            }

            public void setState(int state) {
                State = state;
            }
        }
        public static class ProInfoParamlist {
            /*"Name": "商品名称",
            "Content": "宝德 初生儿宽口玻璃奶瓶晶钻玻璃材质配防胀气硅胶奶嘴 蓝色 120ml"*/
            private String Name;
            private String Content;

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String content) {
                Content = content;
            }
        }
    }
}
