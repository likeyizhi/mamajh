package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2016/12/19.
 */

public class CategoryListEntity {
    private String ID;
    private String Name;
    private List<CategoryItemEntity> ChildList;

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

    public List<CategoryItemEntity> getChildList() {
        return ChildList;
    }

    public void setChildList(List<CategoryItemEntity> childList) {
        ChildList = childList;
    }
}
