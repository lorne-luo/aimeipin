package com.meidi.job;


import com.meidi.dbaccess.DAOUtil;
import com.meidi.util.Common;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;
import java.util.Map;

/**
 * Created on 2016/3/29.
 */
public class CheckProjectStateJob extends MDJob {

    /**
     * 每天凌晨00:05
     * 检查已经上架的项目状态
     * 如果项目时间结束则下架
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String sql = " select id from md_commodity where state = 1 and  end_date <= date(sysdate())";

        try {
            DAOUtil daoUtil = new DAOUtil(sql);
            List<Map> list = daoUtil.executeQuery();
            for(Map map : list){
                sql = "update md_commodity set state = 0 where id = ?";
                daoUtil = new DAOUtil(sql);
                daoUtil.bind(1, map.get("id"));
                daoUtil.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CheckProjectStateJob job = new CheckProjectStateJob();
        try {
            job.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
