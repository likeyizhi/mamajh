package com.yqx.mamajh.view;

import com.yqx.mamajh.bean.HomeInfoEntity;
import com.yqx.mamajh.bean.NetBaseEntity;

/**
 * Created by young on 2016/12/12.
 */

public interface HomeListView extends BaseView {

    void refreshListData(NetBaseEntity<HomeInfoEntity> responseHomeListEntity);

    void addMoreListData(NetBaseEntity<HomeInfoEntity> responseHomeListEntity);

}
