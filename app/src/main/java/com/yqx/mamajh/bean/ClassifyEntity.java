package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/8.
 */

public class ClassifyEntity {
    /**
     * status : 0
     * mes : 成功
     * res : [{"CataList":[{"ID":"9","Name":"其它","ImgSrc":"http://182.92.183.143:8011/webimg/img_product/classify/"}]}]
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
        private List<CataListEntity> CataList;

        public List<CataListEntity> getCataList() {
            return CataList;
        }

        public void setCataList(List<CataListEntity> CataList) {
            this.CataList = CataList;
        }

        public static class CataListEntity {
            /**
             * ID : 9
             * Name : 其它
             * ImgSrc : http://182.92.183.143:8011/webimg/img_product/classify/
             */

            private String ID;
            private String Name;
            private String ImgSrc;

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

            public String getImgSrc() {
                return ImgSrc;
            }

            public void setImgSrc(String ImgSrc) {
                this.ImgSrc = ImgSrc;
            }
        }
    }
}
