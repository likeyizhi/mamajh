package com.yqx.mamajh.interactor.impl;

import android.content.Context;

import com.yqx.mamajh.R;
import com.yqx.mamajh.bean.BaseEntity;
import com.yqx.mamajh.interactor.CommonContainerInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by young on 15/11/24.
 */
public class SpecialtyListInteractorImpl implements CommonContainerInteractor {
    @Override
    public List<BaseEntity> getCommonCategoryList(Context context) {
        List<BaseEntity> resultData = new ArrayList<>();
        String[] specialtyCategoryArray = context.getResources().getStringArray(R.array.specialtycategory_list);
        resultData.add(new BaseEntity(specialtyCategoryArray[0], specialtyCategoryArray[0]));
        resultData.add(new BaseEntity(specialtyCategoryArray[1], specialtyCategoryArray[1]));

        return resultData;
    }
}
