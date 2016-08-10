package com.meidi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by luanpeng on 16/4/24.
 */
public class WxTemplate implements MdConstants {



    /**
     * 拼团失败
     * @param token
     * @param order
     * @throws IOException
     */
//    public static void groupClose(String token, Map order) throws IOException {
//        String jsonStr = "{\"touser\": \"" + order.get("wx_openid") + "\", \"template_id\": \"" + WX_GROUP_FAIL + "\", " +
//                "\"url\": \"" + "" + "\",\"topcolor\":\"#32b16c\"," +
//                "\"data\":{\"first\": {\"value\":\"拼团失败提醒！\",\"color\":\"#0A0A0A\"}," +
//                "\"keyword1\": {\"value\":\"" + order.get("pay_amount") + "\",\"color\":\"#888\"}," +
//                "\"keyword2\": {\"value\":\"" + order.get("project_title") + "\",\"color\":\"#888\"}," +
//                "\"keyword3\": {\"value\":\"" + "" + "\",\"color\":\"#888\"}," +
//                "\"keyword4\": {\"value\":\"" + order.get("order_code") + "\",\"color\":\"#888\"}," +
//                "\"remark\": {\"value\":\"抱歉您有一个拼团因超时失败！\",\"color\":\"#888\"}" +
//                "}}";
//
//        sendMsg(token, jsonStr);
//    }

    /**
     * 拼团失败
     * @param token
     * @param order
     * @throws IOException
     */
    public static void groupClose(String token, Map order) throws IOException {
        String jsonStr = "{\"touser\": \"" + order.get("wx_openid") + "\", \"template_id\": \"" + WX_GROUP_FAIL + "\", " +
                "\"url\": \"" + "" + "\",\"topcolor\":\"#32b16c\"," +
                "\"data\":{\"first\": {\"value\":\"很遗憾地通知您，因超时未达规定人数，开团失败！\",\"color\":\"#0A0A0A\"}," +
                "\"keyword1\": {\"value\":\"" + Integer.parseInt(Common.null2String(order.get("pay_amount"))) /100 + "\",\"color\":\"#888\"}," +
                "\"keyword2\": {\"value\":\"" + order.get("commodity_name") + "\",\"color\":\"#888\"}," +
                "\"keyword3\": {\"value\":\"" + "" + "\",\"color\":\"#888\"}," +
                "\"keyword4\": {\"value\":\"" + order.get("order_code") + "\",\"color\":\"#888\"}," +
                "\"remark\": {\"value\":\"您有一个拼团因超时失败，请到聚会美商城“我的订单”中查看，重新发起拼团！\",\"color\":\"#888\"}" +
                "}}";

        sendMsg(token, jsonStr);
    }

    public static void sendMsg(String token, String jsonStr) throws IOException{
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);


        httpPost.setEntity(new StringEntity(jsonStr, "utf-8"));
        HttpResponse response = client.execute(httpPost);
        String returnStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(returnStr);
    }
}
