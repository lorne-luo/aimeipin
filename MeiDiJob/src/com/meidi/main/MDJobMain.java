package com.meidi.main;

import com.meidi.job.CheckGroupLaunchStateJob;
import com.meidi.job.CheckProjectStateJob;
import com.meidi.job.UpdateWxTicketJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Created  on 2016/3/29.
 */
public class MDJobMain {
    private Scheduler scheduler = null;

    public static void main(String[] args) {

        MDJobMain yjyJobMain = new MDJobMain();
        try {
            yjyJobMain.start();

            Thread.sleep(5000);

            System.out.println("MD Job batch started ...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void start() throws SchedulerException, ParseException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        scheduler = factory.getScheduler();

        /**
         * 每小时更新一次WX ticket
         */
        JobDetail updateWxTicketJob = new JobDetail("updateWxTicketJob", "updateWxTicketJob", UpdateWxTicketJob.class);
        CronTrigger updateWxTicketTrigger = new CronTrigger("updateWxTicketTrigger", "updateWxTicketTrigger", "0 0 * * * ?");
        scheduler.scheduleJob(updateWxTicketJob, updateWxTicketTrigger);

        /**
         * 每5分钟检查一次拼团状态
         */
        JobDetail checkGroupLaunchStateJob = new JobDetail("checkGroupLaunchStateJob", "checkGroupLaunchStateJob", CheckGroupLaunchStateJob.class);
        CronTrigger checkGroupLaunchStateTrigger = new CronTrigger("checkGroupLaunchStateTrigger", "checkGroupLaunchStateTrigger", "0 0/5 * * * ?");
        scheduler.scheduleJob(checkGroupLaunchStateJob, checkGroupLaunchStateTrigger);

        /**
         * 每天00:05检查上架商品到期
         */
        JobDetail checkProjectStateJob = new JobDetail("checkProjectStateJob", "checkProjectStateJob", CheckProjectStateJob.class);
        CronTrigger checkProjectStateTrigger = new CronTrigger("checkProjectStateTrigger", "checkProjectStateTrigger", "0 5 0 * * ?");
        scheduler.scheduleJob(checkProjectStateJob, checkProjectStateTrigger);

//        JobDetail testJob = new JobDetail("testJob", "testJob", TestJob.class);
//        CronTrigger testTrigger = new CronTrigger("testTrigger", "testTrigger", "0 * * * * ?");
//        scheduler.scheduleJob(testJob, testTrigger);

        scheduler.start();

    }


    public void stop() throws Exception {
        if (scheduler != null && scheduler.isStarted()) {
            scheduler.shutdown();
        }
    }
}
