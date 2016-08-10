package com.meidi.repository;

import com.meidi.domain.BackendUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by luanpeng on 16/6/15.
 */
public interface BackendUserRepository extends PagingAndSortingRepository<BackendUser, Integer> {

    BackendUser findByAccountAndPassword(String account,String password);

    BackendUser findByAccount(String account);



}
