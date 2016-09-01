package com.meidi.repository;

import com.meidi.domain.Commodity;
import com.meidi.repository.custom.CommodityRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/5/7.
 */
public interface CommodityRepository extends PagingAndSortingRepository<Commodity, Integer>,CommodityRepositoryCustom {


    /**
     * 同城市下的一上线的不同项目
     *
     * @param cityId
     * @param state
     * @param projectId
     * @param pageable
     * @return
     */
    List<Commodity> findByDicCity_IdAndStateAndIdIsNot(int cityId, int state, int projectId, Pageable pageable);

    List<Commodity> findByDicCity_IdAndCategory_IdAndStateAndIdIsNot(int cityId,int categoryId, int state, int projectId, Pageable pageable);

    List<Commodity> findByStateAndEndDateBefore(int state,String datetime);

}
