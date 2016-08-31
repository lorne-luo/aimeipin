package com.meidi.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.meidi.domain.Commodity;
import com.meidi.domain.Order;
import com.meidi.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class ScheduledJobs {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private CommodityRepository commodityRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private WxTicketRepository wxTicketRepository;

    /**
     * 每天00:05检查上架商品到期
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void checkProjectState() {
        String today = dateFormat.format(new Date());
        List<Commodity> endedCommodity = commodityRepository.findByStateAndEndDateBefore(1, today);

        for (Commodity commodity : endedCommodity) {
            // find all unpaid order (state=1) and close it
            List<Order> orders = orderRepository.findByStateAndCommodityId(1,commodity.getId());
            for(Order order : orders){
                order.setState(8);
                orderRepository.save(order);
            }
            // mark commodity as down
            commodity.markAsDown();
            commodityRepository.save(commodity);
        }
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
