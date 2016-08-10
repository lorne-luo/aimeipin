package com.meidi.repository.impl;

import com.meidi.domain.Order;
import com.meidi.repository.custom.OrderRepositoryCustom;
import com.meidi.util.MdCommon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/4/21.
 */
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findOrderWithQuery(int pageNumber, int pageSize, int flag, int state, String queryStr) {
        List<Object> list = new ArrayList<>();
        String sql = "select mo.* " +
                " from md_order mo " +
                " where mo.id is not null ";
        if (!MdCommon.isEmpty(flag) && flag > 0) {
            sql += " and mo.flag = ? ";
            list.add(flag);
        }
        if (!MdCommon.isEmpty(state) && state > 0) {
            if(state < 6){
                sql += " and mo.state = ? ";
            }else{
                sql += " and mo.state >= ? ";
            }

            list.add(state);
        }
        if(!MdCommon.isEmpty(queryStr)){
            sql += " and (mo.username like ? or mo.mobile like ? or mo.commodity_name like ?) ";
            list.add("%" + queryStr + "%");
            list.add("%" + queryStr + "%");
            list.add("%" + queryStr + "%");
        }
        sql += " order by mo.id desc limit ?,? ";
        Query query = entityManager.createNativeQuery(sql, "searchResultMapping.order");
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                query.setParameter(i + 1, list.get(i));
            }
        }

        query.setParameter(list.size() + 1, (pageNumber - 1) * pageSize);
        query.setParameter(list.size() + 2, pageSize);

        List<Order> orderList = query.getResultList();
        Map<String,Object> result = new HashMap<>();
        result.put("orderList", orderList);


        sql = "select count(0) as num " +
                " from md_order mo " +
                " where mo.id is not null ";
        if (!MdCommon.isEmpty(flag) && flag > 0) {
            sql += " and mo.flag = ? ";
        }
        if (!MdCommon.isEmpty(state) && state > 0) {
            if(state < 6){
                sql += " and mo.state = ? ";
            }else{
                sql += " and mo.state >= ? ";
            }
        }
        if(!MdCommon.isEmpty(queryStr)){
            sql += " and (mo.username like ? or mo.mobile like ? or mo.commodity_name like ?) ";
        }
        query = entityManager.createNativeQuery(sql);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                query.setParameter(i + 1, list.get(i));
            }
        }
        int totalNumber = Integer.parseInt(query.getSingleResult().toString());
        int totalPages = totalNumber / pageSize;
        if (totalNumber % pageSize > 0) {
            totalPages = totalPages + 1;
        }
        result.put("totalPages", totalPages);

        return result;
    }

}