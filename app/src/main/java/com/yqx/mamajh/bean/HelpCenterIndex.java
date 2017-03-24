package com.yqx.mamajh.bean;

import java.util.List;

/**
 * 常见问题首页
 */

public class HelpCenterIndex {
    /**
     * id	Int	分类ID
     * title	String	分类标题
     * icon	String	分类ico
     * list	String	问题标题列表
     * id	Int	问题ID
     * title	String	问题标题
     * icon	String	问题ico
     */

    private int                  id;
    private String               title;
    private String               icon;
    private int                  imgRes;
    private List<HelpCenterList> list;

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<HelpCenterList> getList() {
        return list;
    }

    public void setList(List<HelpCenterList> list) {
        this.list = list;
    }

    public static class HelpCenterList {
        /**
         * id : 42
         * title : 登录与验证
         * icon : http://182.92.183.143:8011/webimg/img_article/
         */

        private int    id;
        private String title;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
