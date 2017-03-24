package com.yqx.mamajh.view;

import com.yqx.mamajh.bean.NetBaseEntity;
import com.yqx.mamajh.bean.SearchHistoryListEntity;

/**
 * Created by young on 2016/12/23.
 */

public interface SearchHistoryView extends BaseView {
    void refreshListData(NetBaseEntity<SearchHistoryListEntity> responseHomeListEntity);
}
