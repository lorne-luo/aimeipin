//package com.meidi.repository.impl;
//
//import com.meidi.domain.MdProject;
//import com.meidi.repository.custom.ProjectListRepositoryCustom;
//import com.meidi.util.MdCommon;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by luanpeng on 16/3/24.
// */
//public class ProjectListRepositoryImpl implements ProjectListRepositoryCustom {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public Map<String, Object> findProjectListWithQuery(int pageNumber, int pageSize, int flag, int state, String queryStr) {
//
//        if ("0".equals(queryStr)) {
//            queryStr = "";
//        }
//
//        List<Object> paramList = new ArrayList<>();
//        String sql = "select mp.id,mp.project_code,mp.title,mp.flag,mp.discount,mp.discount_price," +
//                " mp.state,mp.create_time,mp.province_id,mp.city_id,mp.price " +
//                " from md_project mp " +
//                " left join md_project_group mpg on mpg.project_id = mp.id " +
//                " left join md_project_pack mpp on mpp.project_id = mp.id " +
//                " left join md_project_preferential mpp2 on mpp2.project_id = mp.id " +
//                " left join md_commodity mc on mc.id = mpg.commodity_id or mc.id = mpp.commodity_id or mc.id = mpp2.commodity_id " +
//                " where mp.id is not null ";
//        if (flag > -1 && flag < 4) {
//            sql += " and mp.flag = ? ";
//            paramList.add(flag);
//        } else if (flag == 4) {//韩国专区
//            sql += " and mp.city_id = ? ";
//            paramList.add(346);
//        }
//
//        if (state > -1) {
//            sql += " and mp.state = ? ";
//            paramList.add(state);
//        }
//
//        if (!MdCommon.isEmpty(queryStr)) {
//            sql += " and mp.title like ? or mp.description like ? or mc.name like ? or mc.description like ? ";
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//        }
//        sql += " group by mp.id order by mp.id desc limit ?,? ";
//        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.projectList");
//        if (paramList.size() > 0) {
//            for (int i = 0; i < paramList.size(); i++) {
//                query.setParameter(i + 1, paramList.get(i));
//            }
//        }
//        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
//        query.setParameter(paramList.size() + 2, pageSize);
//        List<MdProject> projectList = query.getResultList();
//        Map<String, Object> result = new HashMap<>();
//        result.put("projectList", projectList);
//
//        /**获取总页数**/
//        sql = " select count(0) from (" +
//                "select count(0) from md_project mp " +
//                " left join md_project_group mpg on mpg.project_id = mp.id " +
//                " left join md_project_pack mpp on mpp.project_id = mp.id " +
//                " left join md_project_preferential mpp2 on mpp2.project_id = mp.id " +
//                " left join md_commodity mc on mc.id = mpg.commodity_id or mc.id = mpp.commodity_id or mc.id = mpp2.commodity_id " +
//                " where mp.id is not null ";
//        if (flag > -1 && flag < 4) {
//            sql += " and mp.flag = ? ";
//        } else if (flag == 4) {//韩国专区
//            sql += " and mp.city_id = ? ";
//        }
//
//        if (state > -1) {
//            sql += " and mp.state = ? ";
//        }
//
//        if (!MdCommon.isEmpty(queryStr)) {
//            sql += " and mp.title like ? or mp.description like ? or mc.name like ? or mc.description like ? ";
//        }
//        sql += " group by mp.id) t ";
//        query = this.entityManager.createNativeQuery(sql);
//        if (paramList.size() > 0) {
//            for (int i = 0; i < paramList.size(); i++) {
//                query.setParameter(i + 1, paramList.get(i));
//            }
//        }
//        int totalColums = Integer.parseInt(query.getSingleResult().toString());
//        int totalPages = totalColums / pageSize;
//        if (totalColums % pageSize > 0) {
//            totalPages = totalPages + 1;
//        }
//        result.put("totalPages", totalPages);
//
//
//        return result;
//    }
//
//    @Override
//    public Map<String, Object> findProjectListWithQuery2(int pageNumber, int pageSize, int flag, int state, String queryStr) {
//        if ("0".equals(queryStr)) {
//            queryStr = "";
//        }
//
//        List<Object> paramList = new ArrayList<>();
//        String sql = "select mp.id,mp.project_code,mp.title,mp.flag,mp.discount,mp.discount_price," +
//                " mp.state,mp.create_time,mp.province_id,mp.city_id,mp.price " +
//                " from md_project mp " +
//                " left join md_project_group mpg on mpg.project_id = mp.id " +
//                " left join md_project_pack mpp on mpp.project_id = mp.id " +
//                " left join md_project_preferential mpp2 on mpp2.project_id = mp.id " +
//                " left join md_commodity mc on mc.id = mpg.commodity_id or mc.id = mpp.commodity_id or mc.id = mpp2.commodity_id " +
//                " where mp.id is not null ";
//        if (flag > -1 && flag < 4) {
//            sql += " and mp.flag = ? ";
//            paramList.add(flag);
//        } else if (flag == 4) {//韩国专区
//            sql += " and mp.city_id = ? ";
//            paramList.add(346);
//        }
//
//        if (state > -1) {
//            sql += " and mp.state = ? ";
//            paramList.add(state);
//        }
//
//        if (!MdCommon.isEmpty(queryStr)) {
//            sql += " and mp.title like ? or mp.description like ? or mc.name like ? or mc.description like ? ";
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//            paramList.add("%" + queryStr + "%");
//        }
//        sql += " group by mp.id order by mp.id desc limit ?,? ";
//        Query query = this.entityManager.createNativeQuery(sql, "searchResultMapping.projectList");
//        if (paramList.size() > 0) {
//            for (int i = 0; i < paramList.size(); i++) {
//                query.setParameter(i + 1, paramList.get(i));
//            }
//        }
//        query.setParameter(paramList.size() + 1, (pageNumber - 1) * pageSize);
//        query.setParameter(paramList.size() + 2, pageSize);
//        List<MdProject> projectList = query.getResultList();
//        Map<String, Object> result = new HashMap<>();
//        result.put("projectList", projectList);
//
//        /**获取总页数**/
//        sql = " select count(0) from (" +
//                " select count(0) from md_project mp " +
//                " left join md_project_group mpg on mpg.project_id = mp.id " +
//                " left join md_project_pack mpp on mpp.project_id = mp.id " +
//                " left join md_project_preferential mpp2 on mpp2.project_id = mp.id " +
//                " left join md_commodity mc on mc.id = mpg.commodity_id or mc.id = mpp.commodity_id or mc.id = mpp2.commodity_id " +
//                " where mp.id is not null ";
//        if (flag > -1 && flag < 4) {
//            sql += " and mp.flag = ? ";
//        } else if (flag == 4) {//韩国专区
//            sql += " and mp.city_id = ? ";
//        }
//
//        if (state > -1) {
//            sql += " and mp.state = ? ";
//        }
//
//        if (!MdCommon.isEmpty(queryStr)) {
//            sql += " and mp.title like ? or mp.description like ? or mc.name like ? or mc.description like ? ";
//        }
//        sql += " group by mp.id) t  ";
//        query = this.entityManager.createNativeQuery(sql);
//        if (paramList.size() > 0) {
//            for (int i = 0; i < paramList.size(); i++) {
//                query.setParameter(i + 1, paramList.get(i));
//            }
//        }
//        int totalColums = Integer.parseInt(query.getSingleResult().toString());
//        int totalPages = totalColums / pageSize;
//        if (totalColums % pageSize > 0) {
//            totalPages = totalPages + 1;
//        }
//        result.put("totalPages", totalPages);
//
//        return result;
//    }
//}
