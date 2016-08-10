package com.meidi.job;


import com.meidi.dbaccess.DBAccess;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * Created  on 2016/3/29.
 */
public class MDJob implements Job {

    protected Date lastExeTime;
    protected String jobId;


    /**
     * 取上次统计时间点，冻结统计时间
     *
     * @param
     * @return void
     * @throws
     * @Title: getLastExeTime
     * @Description: TODO
     */
    protected void getLastExeTime() {
        Connection con = null;
        try {
            con = DBAccess.getInstance().getConnection();
            String sql = "select LASTEXECUTE from TAM011 where JOBID='" + jobId + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                this.lastExeTime = rs.getDate(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * 更新此次统计时间
     *
     * @param
     * @return void
     * @throws
     * @Title: updateThisExeTime
     * @Description: TODO
     */
    protected void updateThisExeTime() {
        Connection con = null;
        try {
            con = DBAccess.getInstance().getConnection();
            //更新统计时间
            String sql;
            sql = "update TAM011 set LASTEXECUTE=sysdate() where JOBID='" + jobId + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
