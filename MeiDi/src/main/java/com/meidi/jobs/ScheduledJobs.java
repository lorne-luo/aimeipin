package com.meidi.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobs {

    /**
     * 每天00:05检查上架商品到期
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void checkProjectState() {
        System.out.println("daily check Project State");
    }

    /**
     * 每5分钟检查一次拼团状态
     */
    @Scheduled(fixedRate = 300 * 1000)
    public void checkGroupLaunch() {
        System.out.println("check Group State every 5 mins");
    }

    /**
     * 每小时更新一次WX ticket
     */
    @Scheduled(fixedRate = 3600 * 1000)
    public void updateWxTicket() {
        System.out.println("update Wx Ticket hourly");
    }
}
