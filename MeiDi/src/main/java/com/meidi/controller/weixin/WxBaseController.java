package com.meidi.controller.weixin;

import com.meidi.domain.Commodity;
import com.meidi.domain.Order;
import com.meidi.domain.WxTicket;
import com.meidi.repository.CommodityRepository;
import com.meidi.repository.WxTicketRepository;
import com.meidi.util.MdCommon;
import com.meidi.util.MdConstants;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by luanpeng on 16/3/31.
 */
public class WxBaseController implements MdConstants {

    @Resource
    private CommodityRepository commodityRepository;

    /**
     * 商城跳授权, snsapi_userinfo方式, 需要用户点击确认授权
     *
     * @param request
     * @return
     */
    public ModelAndView wxAuth(HttpServletRequest request) {

        String url = MdCommon.getUrl(request);
        if (url == null || url == "") url = HOME;
        HttpSession session = request.getSession();
        session.setAttribute(USER_URL, url);
        return new ModelAndView(new RedirectView("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WX_APP_ID + "&"
                + "redirect_uri=" + HOME + "/wxAuth/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"));
    }

    /**
     * 打卡公众号授权采用双重授权登陆,
     * 1.先snsapi_base静默授权商城公号获取商城公号openid并据此生成只含openid的空user,并保存商城openid至session
     * 2.再snsapi_userinfo用户确认授权打卡公号, 拿到昵称头像等信息更新上一步生成的user
     *
     * @param request
     * @return
     */
    public ModelAndView dkAuth(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {

        String url = MdCommon.getUrl(request);
        if (url == null || url.equals("")){
            // url为空转去第一个打卡项目
            List<Commodity> commodityList = commodityRepository.findByFlagAndState(5,1);
            if(commodityList != null && commodityList.size() > 0){
                url = PATH + "/business/bookingDaka/"+commodityList.get(0).getId();
            }else{
                throw new NoSuchRequestHandlingMethodException("bookingDaka", WxBaseController.class);
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute(USER_URL, url);
        return new ModelAndView(new RedirectView("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WX_APP_ID + "&"
                + "redirect_uri=" + HOME + "/wxAuth/dkAuth&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect"));
    }

    /**
     * 使用SHA-1生成
     * 微信js-sdk签名
     *
     * @return
     * @throws
     * @throws
     */
    public Map weiXinSignature(HttpServletRequest request, WxTicketRepository wxTicketRepository) throws Exception {
        String noncestr = MdCommon.getRandomString(16);//随机数
        String jsapi_ticket = "";
        String timestamp = String.valueOf((new Date().getTime() / 1000));//时间戳


        String url = MdCommon.getUrl(request);


        WxTicket wxTicket = wxTicketRepository.findByAppid(WX_APP_ID);
        jsapi_ticket = wxTicket.getTicket();

        String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;


        String signature = MdCommon.getSHA1(str);
        Map map = new HashMap();
        map.put("noncestr", noncestr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        map.put("appid", WX_APP_ID);
        return map;
    }

    /**
     * 调用 微信统一下单接口 生成预支付交易会话标识prepay_id  并签名
     *
     * @param order
     * @param wxOpenid
     * @param ip
     * @return
     * @throws Exception
     */
    public Map unifiedOrder(Order order, String wxOpenid, String ip) throws Exception {
        String appid = WX_APP_ID;
        String mch_id = MCH_ID;//商户号
        String nonce_str = MdCommon.getRandomString(16);//随机数
        String body = order.getCommodityName();//商品描述
        String out_trade_no = order.getOrderCode();//商户内部订单号
        Integer total_fee = order.getPayAmount();//总金额
        String spbill_create_ip = ip;//终端IP
        String notify_url = PATH + "/pay/notifyUrl";//通知地址
        String trade_type = "JSAPI";//交易类型
        String openid = wxOpenid;//用户标识
        String key = WX_API_KEY;//商户API密钥 只参与签名 不需要上传


        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", out_trade_no);
        packageParams.put("total_fee", total_fee.toString());
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", openid);
        //按照ascII排序
        String str = MdCommon.createASC2Sort(packageParams) + "&key=" + key;
        System.out.println("str ===" + str);
        String sign = MdCommon.getMD5(str).toUpperCase();//签名
        System.out.println("sing===" + sign);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        String xml = "<xml>" +
                "<appid>" + appid + "</appid>" +
                "<mch_id>" + mch_id + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>" +
                "<body><![CDATA[" + body + "]]></body>" +
                "<out_trade_no>" + out_trade_no + "</out_trade_no>" +
                "<total_fee>" + total_fee + "</total_fee>" +
                "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" +
                "<notify_url>" + notify_url + "</notify_url>" +
                "<trade_type>" + trade_type + "</trade_type>" +
                "<openid>" + openid + "</openid>" +
                "</xml>";

        String prepay_id = "";//预支付交易会话标识

        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
//        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

        httpPost.setEntity(new StringEntity(xml, "UTF-8"));
        HttpResponse response = client.execute(httpPost);
        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println("jsonStr===" + jsonStr);
        //解析XML
        Map result = MdCommon.parseXML(jsonStr);


        String return_code = MdCommon.null2String(result.get("return_code"));
        String return_msg = MdCommon.null2String(result.get("return_msg"));
        System.out.println("return_code==" + return_code);
        System.out.println("return_msg==" + return_msg);
        if (return_code.equals("SUCCESS")) {//表示通信成功
            if (!"OK".equals(return_msg)) {
                System.out.println("err_code=" + result.get("err_code"));
            } else {//交易成功
                prepay_id = MdCommon.null2String(result.get("prepay_id"));
                System.out.println("prepay_id=" + prepay_id);
            }
        }


        SortedMap<String, String> finalPackage = new TreeMap<>();

        //生成页面接口 签名
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String packageStr = "prepay_id=" + prepay_id;
        finalPackage.put("appId", appid);
        finalPackage.put("timeStamp", timeStamp);
        finalPackage.put("nonceStr", nonce_str);
        finalPackage.put("package", packageStr);
        finalPackage.put("signType", "MD5");

        String str2 = MdCommon.createASC2Sort(finalPackage) + "&key=" + key;
        System.out.println("str2==" + str2);

        String sign2 = MdCommon.getMD5(str2).toUpperCase();

        Map wxPay = new HashMap<>();
        wxPay.put("timestamp", timeStamp);
        wxPay.put("nonceStr", nonce_str);
        wxPay.put("package", packageStr);
        wxPay.put("signType", "MD5");
        wxPay.put("paySign", sign2);


        return wxPay;
    }

    /**
     * 微信退款接口
     *
     * @param order
     * @return
     * @throws Exception
     */
    public Map orderRefund(Order order) throws Exception {
        String appid = WX_APP_ID;
        String mch_id = MCH_ID;//商户号
        String nonce_str = MdCommon.getRandomString(16);
        String transaction_id = order.getTransactionId();//微信订单号
        String out_trade_no = order.getOrderCode();//商户内部订单号
        String out_refund_no = order.getRefundCode();//商户退款单号
        Integer total_fee = order.getPayAmount();//总金额
        Integer refund_fee = order.getPayAmount();//退款金额
//        Integer total_fee = 1;//总金额
//        Integer refund_fee = 1;//退款金额
        String op_user_id = MCH_ID;//操作员帐号, 默认为商户号
        String key = WX_API_KEY;//商户API密钥 只参与签名 不需要上传

        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        if (!MdCommon.isEmpty(transaction_id)) {
            packageParams.put("transaction_id", transaction_id);
        } else {
            packageParams.put("out_trade_no", out_trade_no);
        }
        packageParams.put("out_refund_no", out_refund_no);
        packageParams.put("total_fee", total_fee.toString());
        packageParams.put("refund_fee", refund_fee.toString());
        packageParams.put("op_user_id", op_user_id);
        //按照ascII排序
        String str = MdCommon.createASC2Sort(packageParams) + "&key=" + key;

        String sign = MdCommon.getMD5(str).toUpperCase();//签名

        String xml = "<xml>" +
                "<appid>" + appid + "</appid>" +
                "<mch_id>" + mch_id + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>";
        if (!MdCommon.isEmpty(transaction_id)) {
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
        CloseableHttpClient httpClient = MdCommon.clientSSL();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(xml, "UTF-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

        //解析XML
        Map result = MdCommon.parseXML(jsonStr);

        return result;
    }
}
