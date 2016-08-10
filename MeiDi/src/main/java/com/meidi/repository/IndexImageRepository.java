package com.meidi.repository;

import com.meidi.domain.IndexImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by luanpeng on 16/4/22.
 */
public interface IndexImageRepository extends PagingAndSortingRepository<IndexImage, Integer> {


}
