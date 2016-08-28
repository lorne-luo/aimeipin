package com.meidi.repository.impl;

import com.meidi.domain.Commodity;
import com.meidi.repository.custom.CommodityRepositoryCustom;
import com.meidi.util.MdCommon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luanpeng on 16/5/8.
 */
public class CommodityRepositoryImpl implements CommodityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Object> findCommodityWithQuery(int pageNumber, int pageSize, int flag, int state, int provinceId, String queryStr) throws Exception {
        if ("0".equals(queryStr)) {
            queryStr = "";
        }

        List<Object> paramList = new ArrayList<>();
        String sql = "select mc.id ,mc.commodity_code,mc.name,mc.flag " +
                ",mc.discount,mc.discount_price,mc.state,mc.price,mc.create_time " +
                ",mc.province_id ,dp.name as province_name, mc.city_id ,dc.name as city_name " +
                ",mc.people_number,mc.label_flag,mc.weight,mc.price_double,mc.discount_price_double " +
                " from md_commodity mc " +
                " left join dic_province dp on dp.id = mc.province_id " +
                " left join dic_city dc on dc.id = mc.city_id " +
//                " left join md_commodity_photo cp on mcp.commodity_id = mc.id " +
                " where mc.id is not null ";
        if (flag > 0 && flag < 5) {
            sql += " and mc.flag = ? ";
            paramList.add(flag);
        }else if(flag == -2){
            sql += " and mc.flag < 4 ";
        }

        if (!MdCommon.isEmpty(provinceId) && provinceId > -1) {
            sql += " and mc.province_id = ? ";
            paramList.add(provinceId);
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
            paramList.add(state);
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?)  ";
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
        }
        sql += " group by mc.id order by mc.weight asc,mc.create_time desc limit ?,? ";
        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.commodity");
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
        query.setParameter(paramList.size() + 2, pageSize);
        List resultList = query.getResultList();

        List<Commodity> commodityList = new ArrayList<>();
        if (!MdCommon.isEmpty(resultList)) {
            commodityList = MdCommon.castEntity(resultList, Commodity.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("commodityList", commodityList);

        /**获取总页数**/
        sql = " select count(0) from (" +
                "select count(0) from md_commodity mc " +
                " where mc.id is not null ";
        if (flag > 0 && flag < 5) {
            sql += " and mc.flag = ? ";
        }else if(flag == -2){
            sql += " and mc.flag < 4 ";
        }
        if (!MdCommon.isEmpty(provinceId) && provinceId > -1) {
            sql += " and mc.province_id = ? ";
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?) ";
        }
        sql += " group by mc.id) t ";
        query = this.entityManager.createNativeQuery(sql);
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        int totalColums = Integer.parseInt(query.getSingleResult().toString());
        int totalPages = totalColums / pageSize;
        if (totalColums % pageSize > 0) {
            totalPages = totalPages + 1;
        }
        result.put("totalPages", totalPages);


        return result;
    }

    @Override
    public Map<String, Object> findCommodityWithQuery2(int pageNumber, int pageSize, int flag, int state, int cityId, String queryStr) throws Exception {
        if ("0".equals(queryStr)) {
            queryStr = "";
        }

        List<Object> paramList = new ArrayList<>();
        String sql = "select mc.id ,mc.commodity_code,mc.name,mc.flag " +
                ",mc.discount,mc.discount_price,mc.state,mc.price,mc.create_time " +
                ",mc.province_id ,dp.name as province_name, mc.city_id ,dc.name as city_name " +
                ",mc.people_number,mc.label_flag,mc.weight,mc.price_double,mc.discount_price_double " +
                " from md_commodity mc " +
                " left join dic_province dp on dp.id = mc.province_id " +
                " left join dic_city dc on dc.id = mc.city_id " +
//                " left join md_commodity_photo cp on mcp.commodity_id = mc.id " +
                " where mc.id is not null ";
        if (flag > 0 && flag < 5) {
            sql += " and mc.flag = ? ";
            paramList.add(flag);
        }else if(flag == -2){
            sql += " and mc.flag < 4 ";
        }

        if (!MdCommon.isEmpty(cityId) && cityId > -1) {
            sql += " and mc.city_id = ? ";
            paramList.add(cityId);
        }else if(cityId == -1){
            //查所有
        }else if(cityId == -2){
            //查非韩国所有
            sql += " and mc.city_id != 346 ";
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
            paramList.add(state);
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?)  ";
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
        }
        sql += " group by mc.id order by mc.weight asc,mc.create_time desc limit ?,? ";
        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.commodity");
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
        query.setParameter(paramList.size() + 2, pageSize);
        List resultList = query.getResultList();

        List<Commodity> commodityList = new ArrayList<>();
        if (!MdCommon.isEmpty(resultList)) {
            commodityList = MdCommon.castEntity(resultList, Commodity.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("commodityList", commodityList);

        /**获取总页数**/
        sql = " select count(0) from (" +
                "select count(0) from md_commodity mc " +
                " where mc.id is not null ";
        if (flag > 0 && flag < 5) {
            sql += " and mc.flag = ? ";
        }else if(flag == -2){
            sql += " and mc.flag < 4 ";
        }
        if (!MdCommon.isEmpty(cityId) && cityId > -1) {
            sql += " and mc.city_id = ? ";
        }else if(cityId == -1){
            //查所有
        }else if(cityId == -2){
            //查非韩国所有
            sql += " and mc.city_id != 346 ";
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?) ";
        }
        sql += " group by mc.id) t ";
        query = this.entityManager.createNativeQuery(sql);
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        int totalColums = Integer.parseInt(query.getSingleResult().toString());
        int totalPages = totalColums / pageSize;
        if (totalColums % pageSize > 0) {
            totalPages = totalPages + 1;
        }
        result.put("totalPages", totalPages);


        return result;
    }

    @Override
    public Map<String, Object> findCommodityWithQuery3(int pageNumber, int pageSize, int categoryId, int state, int cityId, String queryStr) throws Exception {
        if ("0".equals(queryStr)) {
            queryStr = "";
        }

        List<Object> paramList = new ArrayList<>();
        String sql = "select mc.id ,mc.commodity_code,mc.name,mc.flag " +
                ",mc.discount,mc.discount_price,mc.state,mc.price,mc.create_time " +
                ",mc.province_id ,dp.name as province_name, mc.city_id ,dc.name as city_name " +
                ",mc.people_number,mc.label_flag,mc.weight,mc.price_double,mc.discount_price_double " +
                " from md_commodity mc " +
                " left join dic_province dp on dp.id = mc.province_id " +
                " left join dic_city dc on dc.id = mc.city_id " +
//                " left join md_commodity_photo cp on mcp.commodity_id = mc.id " +
                " where mc.id is not null ";
        if (categoryId > 0) {
            sql += " and mc.category_id = ? ";
            paramList.add(categoryId);
        }

        if (!MdCommon.isEmpty(cityId) && cityId > -1) {
            sql += " and mc.city_id = ? ";
            paramList.add(cityId);
        }else if(cityId == -1){
            //查所有
        }else if(cityId == -2){
            //查非韩国所有
            sql += " and mc.city_id != 346 ";
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
            paramList.add(state);
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?)  ";
            paramList.add("%" + queryStr + "%");
            paramList.add("%" + queryStr + "%");
        }
        sql += " group by mc.id order by mc.weight asc,mc.create_time desc limit ?,? ";
        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.commodity");
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
        query.setParameter(paramList.size() + 2, pageSize);
        List resultList = query.getResultList();

        List<Commodity> commodityList = new ArrayList<>();
        if (!MdCommon.isEmpty(resultList)) {
            commodityList = MdCommon.castEntity(resultList, Commodity.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("commodityList", commodityList);

        /**获取总页数**/
        sql = " select count(0) from (" +
                "select count(0) from md_commodity mc " +
                " where mc.id is not null ";
        if (categoryId > 0) {
            sql += " and mc.flag = ? ";
        }

        if (!MdCommon.isEmpty(cityId) && cityId > -1) {
            sql += " and mc.city_id = ? ";
        }else if(cityId == -1){
            //查所有
        }else if(cityId == -2){
            //查非韩国所有
            sql += " and mc.city_id != 346 ";
        }

        if (state > -1) {
            sql += " and mc.state = ? ";
        }else{
            sql += " and mc.state > -1 ";
        }

        if (!MdCommon.isEmpty(queryStr)) {
            sql += " and (mc.name like ? or mc.keyword like ?) ";
        }
        sql += " group by mc.id) t ";
        query = this.entityManager.createNativeQuery(sql);
        if (paramList.size() > 0) {
            for (int i = 0; i < paramList.size(); i++) {
                query.setParameter(i + 1, paramList.get(i));
            }
        }
        int totalColums = Integer.parseInt(query.getSingleResult().toString());
        int totalPages = totalColums / pageSize;
        if (totalColums % pageSize > 0) {
            totalPages = totalPages + 1;
        }
        result.put("totalPages", totalPages);


        return result;
    }


}
