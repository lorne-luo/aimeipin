package com.meidi.repository;

import com.meidi.domain.Others;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/4/23.
 */
public interface OthersRepository extends CrudRepository<Others, Integer> {

    List<Others> findByFlag(Integer flag);
}
