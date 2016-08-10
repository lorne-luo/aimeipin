package com.meidi.repository.custom;

import java.util.Map;

/**
 * Created by luanpeng on 16/4/9.
 */
public interface GroupLaunchRepositoryCustom {

    /**
     * 获取当前项目正在被发起的拼团
     * 拼团的状态 必须是正在发起的  拼团的结束时间还没到的
     * @param commodityId
     * @return
     */
    Map<String,Object> findLaunchByCommodityIdAndEndTimeWithQuery(Integer commodityId);


}
