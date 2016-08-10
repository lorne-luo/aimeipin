package com.meidi.repository.impl;

import com.meidi.domain.User;
import com.meidi.repository.custom.UserRepositoryCustom;
import com.meidi.util.MdCommon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/5/13.
 */
public class UserRepositoryImpl implements UserRepositoryCustom {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map findUserWithQuery(int pageNumber, int pageSize, int cityId, String queryStr) {
        if ("0".equals(queryStr)) {
            queryStr = "";
        }
        List paramList = new ArrayList<>();
        String sql = " select * " +
                " from md_user mu " +
                " where mu.id is not null ";
        if (!MdCommon.isEmpty(cityId) && cityId > 0) {
            sql += " and mu.city_id = ? ";
            paramList.add(cityId);
        }
        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and ( mu.name like ? or mu.mobile like ? or mu.nickname like ? or mu.wx_number like ? " +
                    " or mu.channels like ? or mu.interests like ? or mu.wheres like ? ) ";
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
        }
        sql += " order by mu.id desc limit ?,?";
        Query query = entityManager.createNativeQuery(sql, "searchResultMapping.User");
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
        query.setParameter(paramList.size() + 2, pageSize);
        List<User> userList = query.getResultList();
        Map result = new HashMap<>();
        result.put("userList", userList);

        sql = " select count(0) as number " +
                " from md_user mu " +
                " where mu.id is not null ";
        if (!MdCommon.isEmpty(cityId) && cityId > 0) {
            sql += " and mu.city_id = ? ";
        }
        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and ( mu.name like ? or mu.mobile like ? or mu.nickname like ? or mu.wx_number like ? " +
                    " or mu.channels like ? or mu.interests like ? or mu.wheres like ? ) ";
        }
        query = entityManager.createNativeQuery(sql);
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
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
