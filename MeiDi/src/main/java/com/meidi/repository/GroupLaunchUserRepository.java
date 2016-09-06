package com.meidi.repository;

import com.meidi.domain.GroupLaunchUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/4/7.
 */
public interface GroupLaunchUserRepository extends CrudRepository<GroupLaunchUser, Integer> {

    /**
     * 获取指定用户的拼团
     * @param id
     * @param wxOpenid
     * @return
     */
    GroupLaunchUser findByLaunchIdAndWxOpenid(Integer id, String wxOpenid);

    /**
     * 获取指定拼团的 参团用户
     * @param id
     * @return
     */
    List<GroupLaunchUser> findByLaunchId(Integer id);

    List<GroupLaunchUser> findByLaunchIdOrderByFlagAsc(Integer id);
}
