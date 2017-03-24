package com.yqx.mamajh.bean;

import java.util.List;

/**
 * Created by young on 2016/12/20.
 */

public class ClassifivationInfoEntity {
    private List<CategoryBannerItemEntity> bannerlist;
    private List<CategoryListEntity> catelist;

    public List<CategoryBannerItemEntity> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<CategoryBannerItemEntity> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public List<CategoryListEntity> getCatelist() {
        return catelist;
    }

    public void setCatelist(List<CategoryListEntity> catelist) {
        this.catelist = catelist;
    }
}
