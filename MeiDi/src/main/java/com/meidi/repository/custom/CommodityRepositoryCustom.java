package com.meidi.repository.custom;

import java.util.Map;

/**
 * Created by luanpeng on 16/5/8.
 */
public interface CommodityRepositoryCustom {
    /**
     * 后台用
     * @param pageNumber
     * @param pageSize
     * @param flag -1查所有 －2 查非咨询的所有
     * @param state
     * @param provinceId
     * @param queryStr
     * @return
     * @throws Exception
     */
    Map<String, Object> findCommodityWithQuery(int pageNumber, int pageSize, int flag,int state, int provinceId, int categoryId, String queryStr) throws Exception;

    /**
     * 前台微信用
     * @param pageNumber
     * @param pageSize
     * @param flag -1查所有 －2 查非咨询的所有
     * @param state
     * @param cityId -1查所有  －2 查非韩国所有
     * @param queryStr
     * @return
     * @throws Exception
     */
    Map<String, Object> findCommodityWithQuery2(int pageNumber, int pageSize, int flag,int state, int cityId, String queryStr) throws Exception;
}
