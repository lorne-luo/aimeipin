package com.meidi.repository;

import com.meidi.domain.CommodityPhoto;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/5/9.
 */
public interface CommodityPhotoRepository extends PagingAndSortingRepository<CommodityPhoto, Integer> {
    List<CommodityPhoto> findByCommodityId(Integer id);


}
