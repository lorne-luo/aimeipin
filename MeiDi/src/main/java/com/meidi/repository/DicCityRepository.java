package com.meidi.repository;

import com.meidi.domain.DicCity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/3/28.
 */
public interface DicCityRepository extends CrudRepository<DicCity,Integer> {

    List<DicCity> findByParentId(Integer id);
}
