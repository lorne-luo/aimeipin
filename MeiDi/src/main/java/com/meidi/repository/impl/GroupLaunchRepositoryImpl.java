package com.meidi.repository.impl;

import com.meidi.domain.GroupLaunch;
import com.meidi.repository.custom.GroupLaunchRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/4/9.
 */
public class GroupLaunchRepositoryImpl implements GroupLaunchRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findLaunchByCommodityIdAndEndTimeWithQuery(Integer commodityId) {


        String sql = " select mgl.id,mgl.people_number,mgl.end_time " +
                " from md_group_launch mgl " +
                " where mgl.commodity_id = ? and mgl.state = 0 and mglu.flag = 1 and mgl.end_time > now() ";
        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.launch");
        query.setParameter(1, commodityId);
        List<Object[]> groupLaunchList = query.getResultList();
        List newList = new ArrayList<>();
        for(Object[] object : groupLaunchList ){
            GroupLaunch groupLaunch = (GroupLaunch) object[0];
            Map map = new HashMap<>();
            map.put("groupLaunch", groupLaunch);
            map.put("nickname", object[1]);
            map.put("headimgurl", object[2]);

            newList.add(map);
        }




        Map<String, Object> map = new HashMap<>();
        map.put("resultList", newList);


        return map;
    }
}
