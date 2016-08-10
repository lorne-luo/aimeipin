package com.meidi.repository;

import com.meidi.domain.UserIntegral;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/4/23.
 */
public interface UserIntegralRepository extends PagingAndSortingRepository<UserIntegral, Integer> {
    List<UserIntegral> findByWxOpenid(String wxOpnied, Pageable pageable);
}
