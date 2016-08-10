package com.meidi.repository.custom;

import java.util.Map;

/**
 * Created by luanpeng on 16/5/13.
 */
public interface UserRepositoryCustom {

    Map findUserWithQuery(int pageNumber, int pageSize, int cityId ,String queryStr);
}
