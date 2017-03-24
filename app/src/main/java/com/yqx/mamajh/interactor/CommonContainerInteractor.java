package com.yqx.mamajh.interactor;

import android.content.Context;

import com.yqx.mamajh.bean.BaseEntity;

import java.util.List;


/**
 * Created by young on 15/11/24.
 */
public interface CommonContainerInteractor {

    List<BaseEntity> getCommonCategoryList(Context context);
}
