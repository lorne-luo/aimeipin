package com.meidi.repository;

import com.meidi.domain.UserFavorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;



/**
 * Created by luanpeng on 16/3/30.
 */
public interface UserFavoriteRepository extends CrudRepository<UserFavorite, Integer>,PagingAndSortingRepository<UserFavorite,Integer> {

   Page<UserFavorite> findByWxOpenid(String wxOpenid,Pageable pageable);

   UserFavorite findByWxOpenidAndCommodityId(String wxOpenid, Integer id);
}
