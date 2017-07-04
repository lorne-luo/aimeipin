package com.meidi.repository;

import com.meidi.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/3/28.
 */
public interface CategoryRepository extends CrudRepository<Category,Integer> {

}
