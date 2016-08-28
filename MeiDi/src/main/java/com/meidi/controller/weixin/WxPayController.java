package com.meidi.controller.weixin;

import com.meidi.domain.*;
import com.meidi.repository.*;
import com.meidi.util.DateTimeUtil;
import com.meidi.util.MdCommon;
import com.meidi.util.MdModel;
import com.meidi.util.WxTemplate;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by luanpeng on 16/4/2.
 */
@Controller
@RequestMapping("/pay")
public class WxPayController extends WxBaseController {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private GroupLaunchRepository groupLaunchRepository;
    @Resource
    private WxTicketRepository wxTicketRepository;
    @Resource
    private CommodityRepository commodityRepository;

    @Resource
    private GroupLaunchUserRepository groupLaunchUserRepository;

    /**
     * 订单支付页面
     *
     * @param request
     * @param
     * @return
     */
    @RequestMapping(value = "/orderPage/{orderId}", method = RequestMethod.GET)
    public ModelAndView orderPage(HttpServletRequest request,
                                  @PathVariable Integer orderId) {
        MdModel model = new MdModel(request);

        if (MdCommon.isEmpty(model.get("wx_openid"))) {
            return wxAuth(request);
        }
        Order order = orderRepository.findByWxOpenidAndId(MdCommon.null2String(model.get("wx_openid")), orderId);
        if (!MdCommon.isEmpty(order)) {
            model.put("order", order);
            if (order.getState() == 1) {//未支付

                //调用统一下单 订单号 微信openid ip地址 支付金额
                Map wxPaySignature = null;
                try {
                    String ip = MdCommon.getIpAddress(request);
                    //微信支付签名
                    wxPaySignature = unifiedOrder(order,model.get("wx_openid"), ip);
                    model.put("wxPaySignature", wxPaySignature);

                    //网页签名
                    Map signature = weiXinSignature(request, wxTicketRepository);
                    model.put("signature", signature);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            Commodity commodity = commodityRepository.findOne(order.getCommodityId());

            model.put("commodity", commodity);

            return new ModelAndView("weixin/order", model);
        }
        //如果此订单不属于本人 则进入商品详情页
        return new ModelAndView(new RedirectView(PATH + "/business/commodityDetailPage/" + order.getCommodityId()));
    }

    /**
     * 微信支付 回调地址
     */
    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    @Scope("prototype")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) {
//        MdModel model = new MdModel(request);

        String inputLine;
        String notityXML = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXML += inputLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("notityXML = " + notityXML);

        Map result = MdCommon.parseXML(notityXML);

        String return_code = MdCommon.null2String(result.get("return_code"));
        String return_msg = MdCommon.null2String(result.get("return_msg"));

        String responseXML = "";
        String order_code = "";
        if ("SUCCESS".equals(return_code)) {//通信成功
            String result_code = MdCommon.null2String(result.get("result_code"));
            if ("SUCCESS".equals(result_code)) {
                return_msg = "OK";

                order_code = MdCommon.null2String(result.get("out_trade_no"));
                String transaction_id = MdCommon.null2String(result.get("transaction_id"));

                Order order = orderRepository.findByOrderCode(order_code);
                if (!MdCommon.isEmpty(transaction_id)) {
                    order.setTransactionId(transaction_id);
                }
                try {
                    orderHandle(order);//处理订单
                } catch (IOException e) {
                    e.printStackTrace();
                }

                orderRepository.save(order);
            } else {
                result_code = "FAIL";
                return_msg = MdCommon.null2String(result.get("err_code_des"));
            }

            responseXML = "<xml>" +
                    "  <return_code><![CDATA[" + result_code + "]]></return_code>" +
                    "  <return_msg><![CDATA[" + return_msg + "]]></return_msg>" +
                    "</xml>";
        } else {//通信失败
            responseXML = "<xml>" +
                    "  <return_code><![CDATA[" + return_code + "]]></return_code>" +
                    "  <return_msg><![CDATA[" + return_msg + "]]></return_msg>" +
                    "</xml>";
        }
        System.out.println(responseXML);

        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            bufferedOutputStream.write(responseXML.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 支付结果
     *
     * @param request
     * @param orderCode
     * @return
     */
    @RequestMapping(value = "/payResult/{orderCode}", method = RequestMethod.GET)
    @Scope("prototype")
    public ModelAndView payResult(HttpServletRequest request,
                                  @PathVariable String orderCode) {
        MdModel model = new MdModel(request);
        Order order = orderRepository.findByOrderCode(orderCode);
        model.put("commodity", commodityRepository.findOne(order.getCommodityId()));

        String nonce_str = MdCommon.getRandomString(16);
        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", WX_APP_ID);
        packageParams.put("mch_id", MCH_ID);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", orderCode);

        String str = MdCommon.createASC2Sort(packageParams) + "&key=" + WX_API_KEY;
        System.out.println("str ===" + str);
        String sign = null;//签名
        try {
            sign = MdCommon.getMD5(str).toUpperCase();


            String url = "https://api.mch.weixin.qq.com/pay/orderquery";

            String xml = "<xml>" +
                    "<appid>" + WX_APP_ID + "</appid>" +
                    "<mch_id>" + MCH_ID + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<sign>" + sign + "</sign>" +
                    "<out_trade_no>" + orderCode + "</out_trade_no>" +
                    "</xml>";


            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
//            client.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);

            httpPost.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse response = client.execute(httpPost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

            Map result = MdCommon.parseXML(jsonStr);

            String return_code = MdCommon.null2String(result.get("return_code"));
            String return_msg = MdCommon.null2String(result.get("return_msg"));
            if (return_code.equals("SUCCESS")) {//表示通信成功
                if (!"OK".equals(return_msg)) {
                    System.out.println("err_code=" + result.get("err_code"));
                } else {
                    String trade_state = MdCommon.null2String(result.get("trade_state"));//订单状态
                    String trade_state_desc = MdCommon.null2String(result.get("trade_state_desc"));//订单状态描述
                    String transaction_id = MdCommon.null2String(result.get("transaction_id"));//微信订单号

                    if ("SUCCESS".equals(trade_state)) {//支付成功

                        if (!MdCommon.isEmpty(transaction_id) && MdCommon.isEmpty(order.getTransactionId())) {
                            order.setTransactionId(transaction_id);
                        }
                        //以下逻辑为确保如果微信回调 notifyUrl处理失败时 的情况
                        if (order.getState() == 2) {//表示在回调时以处理
                            //以处理则不做任何操作
                        } else {//回调未处理
                            orderHandle(order);
                        }

//                    refer: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
//                    交易状态码trade_state含义:
//                    SUCCESS—支付成功
//                    REFUND—转入退款
//                    NOTPAY—未支付
//                    CLOSED—已关闭
//                    REVOKED—已撤销（刷卡支付）
//                    USERPAYING--用户支付中
//                    PAYERROR--支付失败(其他原因，如银行返回失败)

                    } else if ("REFUND".equals(trade_state)) {//转入 退款
                        //此处不做处理
                    } else if ("NOTPAY".equals(trade_state)) {//未支付
                        order.setState(1);
                    } else if ("CLOSED".equals(trade_state)) {//已关闭
                        //此处不做处理
                    } else if ("PAYERROR".equals(trade_state)) {//支付失败
                        order.setState(3);
                    }
                    order = orderRepository.save(order);
                    model.put("order", order);
                    model.put("trade_state", trade_state);
                    model.put("trade_state_desc", trade_state_desc);

                    if (order.getFlag() == 1) {//拼团跳结果页面
                        GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                        if (groupLaunch.getState() == 0) {
                            return new ModelAndView("weixin/payResult", model);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //非拼团直接跳定点列表页
        return new ModelAndView(new RedirectView(PATH + "/business/myOrderPage"));
    }

    /**
     * 支付成功订单处理
     *
     * @param order
     */
    private void orderHandle(Order order) throws IOException {

        if(order.getState() >1 ){
           return;
        }
        //订单已支付
        order.setState(2);
        if (order.getFlag() == 1) {
            //以下处理拼团逻辑
            if (MdCommon.isEmpty(order.getLaunchId()) && order.getBookingFlag() == 1) {//如果是发起拼团
                GroupLaunch groupLaunch = new GroupLaunch();
                groupLaunch.setCreateTime(new Date());
                groupLaunch.setEndTime(DateTimeUtil.addDateTimeToDateTime(groupLaunch.getCreateTime(), 2));
                groupLaunch.setPeopleNumber(order.getPeopleNumber());
                groupLaunch.setCommodityId(order.getCommodityId());
                groupLaunch.setPrice(order.getPrice());
                groupLaunch.setDiscount(order.getDiscount());
                groupLaunch.setDiscountPrice(order.getDiscountPrice());
                groupLaunch.setPayAmount(order.getPayAmount());
                groupLaunch.setCommodityNumber(order.getCommodityNumber());
                groupLaunch.setDicProvince(order.getDicProvince());
                groupLaunch.setDicCity(order.getDicCity());
                groupLaunch.setCommodityName(order.getCommodityName());
                if (!MdCommon.isEmpty(order.getUnitPrice())) {
                    groupLaunch.setUnitPrice(order.getUnitPrice());//单价
                }
                if (!MdCommon.isEmpty(order.getUnit())) {
                    groupLaunch.setUnit(order.getUnit());//单位
                }

                List<GroupLaunchUser> groupLaunchList = new ArrayList<>();
                GroupLaunchUser groupLaunchUser = new GroupLaunchUser();
                groupLaunchUser.setFlag(1);//第一人 即拼团发起人
                groupLaunchUser.setWxOpenid(order.getWxOpenid());
                groupLaunchList.add(groupLaunchUser);

                groupLaunch.setGroupLaunchUserList(groupLaunchList);
                groupLaunch = groupLaunchRepository.save(groupLaunch);
                //设置订单的 拼团属性
                order.setLaunchId(groupLaunch.getId());

                //成功发起拼团
                //发消息
                WxTicket wxTicket = wxTicketRepository.findByAppid(WX_APP_ID);
                WxTemplate.groupLaunch(wxTicket.getToken(), order);

            } else if (!MdCommon.isEmpty(order.getLaunchId()) && order.getBookingFlag() == 4) {//参团
                GroupLaunch groupLaunch = groupLaunchRepository.findOne(order.getLaunchId());
                List<GroupLaunchUser> groupLaunchUserList = groupLaunch.getGroupLaunchUserList();

                GroupLaunchUser groupLaunchUser = groupLaunchUserRepository.findByLaunchIdAndWxOpenid(order.getLaunchId(),order.getWxOpenid());
                if(MdCommon.isEmpty(groupLaunchUser)){
                    groupLaunchUser = new GroupLaunchUser();
                    groupLaunchUser.setWxOpenid(order.getWxOpenid());
                    groupLaunchUser.setFlag(groupLaunchUserList.size() + 1);
                    groupLaunchUserList.add(groupLaunchUser);

                    if (groupLaunchUserList.size() == groupLaunch.getPeopleNumber()) {
                        groupLaunch.setState(1);//拼团成功 拼团结束
                        groupLaunch.setGroupLaunchUserList(groupLaunchUserList);
                        groupLaunchRepository.save(groupLaunch);

                        //暂时不发
                        WxTicket wxTicket = wxTicketRepository.findByAppid(WX_APP_ID);
                        for (GroupLaunchUser user : groupLaunchUserList) {
                            //拼团成功 给每个用户发消息
                            WxTemplate.groupLaunchOk(wxTicket.getToken(), order);
                            // TODO 拼团成功 服务号通知
                        }
                    } else {
                        //成功参团
                        WxTicket wxTicket = wxTicketRepository.findByAppid(WX_APP_ID);
                        WxTemplate.joinGroup(wxTicket.getToken(), order);
                    }
                }else{

                }
            }
        }
    }
}
