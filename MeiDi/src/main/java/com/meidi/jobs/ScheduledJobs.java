package com.meidi.jobs;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.meidi.domain.Commodity;
import com.meidi.domain.Order;
import com.meidi.domain.WxTicket;
import com.meidi.repository.*;
import com.meidi.util.MdCommon;

import com.meidi.util.MdConstants;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

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
     * 检查已经上架的项目状态
     * 如果项目时间结束则下架
     */
    @Scheduled(cron = "0 5 0 * * *")
    public void checkCommodityEnded() {
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
    @Scheduled(fixedDelay = 300 * 1000)
    public void checkGroupLaunch() {
        System.out.println("check Group State every 5 mins");
        System.out.println("Today is " + dateFormat.format(new Date()));
    }

    /**
     * 每小时更新一次WX ticket
     */
    @Scheduled(fixedRate = 3600 * 1000)
    public void updateWxTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                MdConstants.WX_APP_ID + "&secret=d6914a211b5175f62cc5dafb389cd074";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = client.execute(httpGet);
            String returnStr = EntityUtils.toString(httpResponse.getEntity());
            Map ret = MdCommon.json2Map(returnStr);
            //微信AccessToken
            String token = MdCommon.null2String(ret.get("access_token"));

            url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
            httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            httpResponse = client.execute(httpGet);
            returnStr = EntityUtils.toString(httpResponse.getEntity());
            ret = MdCommon.json2Map(returnStr);
            //签名票据
            String ticket = MdCommon.null2String(ret.get("ticket"));

            // update db
            WxTicket wxTicket = wxTicketRepository.findByAppid(MdConstants.WX_APP_ID);
            wxTicket.setToken(token);
            wxTicket.setTicket(ticket);
            wxTicketRepository.save(wxTicket);
        } catch (Exception e) {
            System.out.println("[ScheduledJobs] update weixin ticket failed.");
            e.printStackTrace();
        }
    }
}
