package com.meidi.util;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by luanpeng on 16/4/23.
 */
public class WxAbout implements MdConstants {

    /**
     * 微信退款接口
     *
     * @param order
     * @return
     * @throws Exception
     */
    public static Map orderRefund(Map order) throws Exception {
        String appid = WX_APP_ID;
        String mch_id = MCH_ID;//商户号
        String nonce_str = Common.getRandomString(16);
        String transaction_id = order.get("transaction_id").toString();//微信订单号
        String out_trade_no = order.get("order_code").toString();//商户内部订单号
        String out_refund_no = order.get("refund_code").toString();//商户退款单号
        Integer total_fee = Integer.parseInt(order.get("pay_amount").toString());//总金额
        Integer refund_fee = total_fee;//退款金额
        String op_user_id = MCH_ID;//操作员帐号, 默认为商户号
        String key = WX_API_KEY;//商户API密钥 只参与签名 不需要上传

        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        if (!Common.isEmpty(transaction_id)) {
            packageParams.put("transaction_id", transaction_id);
        } else {
            packageParams.put("out_trade_no", out_trade_no);
        }
        packageParams.put("out_refund_no", out_refund_no);
        packageParams.put("total_fee", total_fee.toString());
        packageParams.put("refund_fee", refund_fee.toString());
        packageParams.put("op_user_id", op_user_id);
        //按照ascII排序
        String str = Common.createASC2Sort(packageParams) + "&key=" + key;

        String sign = Common.getMD5(str).toUpperCase();//签名

        String xml = "<xml>" +
                "<appid>" + appid + "</appid>" +
                "<mch_id>" + mch_id + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>";
        if (!Common.isEmpty(transaction_id)) {
            xml += "<transaction_id>" + transaction_id + "</transaction_id>";
        } else {
            xml += "<out_trade_no>" + out_trade_no + "</out_trade_no>";
        }
        xml += "<out_refund_no>" + out_refund_no + "</out_refund_no>" +
                "<total_fee>" + total_fee + "</total_fee>" +
                "<refund_fee>" + refund_fee + "</refund_fee>" +
                "<op_user_id>" + op_user_id + "</op_user_id>" +
                "</xml>";

        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        CloseableHttpClient httpClient = Common.clientSSL();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(xml, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

        //解析XML
        Map result = Common.parseXML(jsonStr);

        return result;
    }
}
