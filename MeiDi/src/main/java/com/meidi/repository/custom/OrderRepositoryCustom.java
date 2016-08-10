package com.meidi.repository.custom;

import java.util.Map;

/**
 * Created by luanpeng on 16/4/21.
 */
public interface OrderRepositoryCustom {

    Map<String, Object> findOrderWithQuery(int pageNumber,int pageSize,int flag, int state, String queryStr);

}
