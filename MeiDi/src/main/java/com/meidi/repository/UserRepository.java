package com.meidi.repository;

import com.meidi.domain.User;
import com.meidi.repository.custom.UserRepositoryCustom;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by luanpeng on 16/3/10.
 */
public interface UserRepository extends CrudRepository<User, Integer>,UserRepositoryCustom {

    User findByWxOpenid(String wxOpenid);
}
