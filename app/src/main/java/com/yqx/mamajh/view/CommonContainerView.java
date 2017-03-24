package com.yqx.mamajh.view;


import com.yqx.mamajh.bean.BaseEntity;

import java.util.List;

/**
 * Created by young on 15/11/24.
 */
public interface CommonContainerView {

    void initializePagerViews(List<BaseEntity> categoryList);
}
