package com.meidi.repository;

import com.meidi.domain.GroupLaunch;
import com.meidi.repository.custom.GroupLaunchRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by luanpeng on 16/4/6.
 */
public interface GroupLaunchRepository extends CrudRepository<GroupLaunch, Integer>,GroupLaunchRepositoryCustom {

   /**
    * 获取指定用户参与的拼团
    * @param id
    * @param wxOpenid
    * @return
    */
   GroupLaunch findByIdAndGroupLaunchUserList_WxOpenid(Integer id,String wxOpenid);

   /**
    * 获取当前项目正在发起的拼团
    * @param commodityId
    * @param state
    * @param endTime
    * @return
    */
   List<GroupLaunch> findByCommodityIdAndStateAndEndTimeIsAfter(Integer commodityId,Integer state,Date endTime);

   List<GroupLaunch> findByCommodityIdAndState(Integer commodityId,Integer state);

   /**
    * 实际已结束但状态仍为拼团中的, 用于结束拼团逻辑
    */
   List<GroupLaunch> findByStateAndEndTimeIsBefore(Integer state,Date endTime);




}
