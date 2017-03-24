package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2017/3/9.
 */

public class EvaluatesEntity {
    /**
     * status : 0
     * mes : 成功
     * res : {"commentlist":[{"ID":10,"Title":"","Content":"哈哈好啊","UserName":"hgnetwork","UserLogo":"http://182.92.183.143:8011/UpFile/Face/F-1-20160902211601055.png","Time":"2016-09-04 16:27","OrderTime":"2016-09-04 16:27","ListImg":[{"ID":2,"Desc":"","Img":"http://182.92.183.143:8011/UpFile/Share/1-20160904163827820.jpg"}],"ListProduct":[{"ID":225,"Title":"完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g","Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg"}]}]}
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
        private List<CommentlistEntity> commentlist;

        public List<CommentlistEntity> getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(List<CommentlistEntity> commentlist) {
            this.commentlist = commentlist;
        }

        public static class CommentlistEntity {
            /**
             * ID : 10
             * Title :
             * Content : 哈哈好啊
             * UserName : hgnetwork
             * UserLogo : http://182.92.183.143:8011/UpFile/Face/F-1-20160902211601055.png
             * Time : 2016-09-04 16:27
             * OrderTime : 2016-09-04 16:27
             * ListImg : [{"ID":2,"Desc":"","Img":"http://182.92.183.143:8011/UpFile/Share/1-20160904163827820.jpg"}]
             * ListProduct : [{"ID":225,"Title":"完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g","Img":"http://182.92.183.143:8011/webimg/img_product/product/225.jpg"}]
             */

            private int ID;
            private String Title;
            private String Content;
            private String UserName;
            private String UserLogo;
            private String Time;
            private String OrderTime;
            private List<ListImgEntity> ListImg;
            private List<ListProductEntity> ListProduct;

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getUserName() {
                return UserName;
            }

            public void setUserName(String UserName) {
                this.UserName = UserName;
            }

            public String getUserLogo() {
                return UserLogo;
            }

            public void setUserLogo(String UserLogo) {
                this.UserLogo = UserLogo;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String Time) {
                this.Time = Time;
            }

            public String getOrderTime() {
                return OrderTime;
            }

            public void setOrderTime(String OrderTime) {
                this.OrderTime = OrderTime;
            }

            public List<ListImgEntity> getListImg() {
                return ListImg;
            }

            public void setListImg(List<ListImgEntity> ListImg) {
                this.ListImg = ListImg;
            }

            public List<ListProductEntity> getListProduct() {
                return ListProduct;
            }

            public void setListProduct(List<ListProductEntity> ListProduct) {
                this.ListProduct = ListProduct;
            }

            public static class ListImgEntity {
                /**
                 * ID : 2
                 * Desc :
                 * Img : http://182.92.183.143:8011/UpFile/Share/1-20160904163827820.jpg
                 */

                private int ID;
                private String Desc;
                private String Img;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public String getDesc() {
                    return Desc;
                }

                public void setDesc(String Desc) {
                    this.Desc = Desc;
                }

                public String getImg() {
                    return Img;
                }

                public void setImg(String Img) {
                    this.Img = Img;
                }
            }

            public static class ListProductEntity {
                /**
                 * ID : 225
                 * Title : 完达山优越金童金装婴儿配方羊奶粉1段（0-6个月适用）900g
                 * Img : http://182.92.183.143:8011/webimg/img_product/product/225.jpg
                 */

                private int ID;
                private String Title;
                private String Img;

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public String getTitle() {
                    return Title;
                }

                public void setTitle(String Title) {
                    this.Title = Title;
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
