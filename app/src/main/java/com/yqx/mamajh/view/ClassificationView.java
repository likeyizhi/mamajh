package com.yqx.mamajh.view;

import com.yqx.mamajh.bean.ClassifivationInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;

/**
 * Created by young on 2016/12/19.
 */

public interface ClassificationView extends BaseView {
    void addLoadListData(NetBaseEntity<ClassifivationInfoEntity> responseHomeListEntity);
}
