package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/9.
 */

public class ShopCategoryEntity {

    /**
     * status : 0
     * mes : 成功
     * res : {"catelist":[{"ID":5,"Name":"奶粉","ChildList":[{"ID":6,"Name":"婴儿奶粉","Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg"}]}]}
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
        private List<CatelistEntity> catelist;

        public List<CatelistEntity> getCatelist() {
            return catelist;
        }

        public void setCatelist(List<CatelistEntity> catelist) {
            this.catelist = catelist;
        }

        public static class CatelistEntity {
            /**
             * ID : 5
             * Name : 奶粉
             * ChildList : [{"ID":6,"Name":"婴儿奶粉","Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg"}]
             */

            private int ID;
            private String Name;
            private List<ChildListEntity> ChildList;

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

            public List<ChildListEntity> getChildList() {
                return ChildList;
            }

            public void setChildList(List<ChildListEntity> ChildList) {
                this.ChildList = ChildList;
            }

            public static class ChildListEntity {
                /**
                 * ID : 6
                 * Name : 婴儿奶粉
                 * Img : http://182.92.183.143:8011/webimg/img_product/product/225.jpg
                 */

                private int ID;
                private String Name;
                private String Img;

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

                public String getImg() {
                    return Img;
                }

                public void setImg(String Img) {
                    this.Img = Img;
                }
            }
        }
    }
}
