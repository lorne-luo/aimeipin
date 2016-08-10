package com.meidi.repository;

import com.meidi.domain.DicTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/3/30.
 */
public interface DicTagRepository extends CrudRepository<DicTag, Integer> {

    List<DicTag> findByFlag(Integer flag);
}
