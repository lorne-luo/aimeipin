package com.meidi.job;


import com.meidi.dbaccess.DAOUtil;
import com.meidi.util.Common;
import com.meidi.util.MdConstants;
import com.meidi.util.WxAbout;
import com.meidi.util.WxTemplate;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.persistence.Column;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on 2016/3/29.
 */
public class CheckGroupLaunchStateJob extends MDJob implements MdConstants {

    /**
     * 检查已经发起的拼团状态
     * 如果拼团时间结束则改变状态
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String sql = " select mgl.id from md_group_launch mgl " +
                " where mgl.state = 0 and  mgl.end_time <= sysdate()";

        try {
            DAOUtil daoUtil = new DAOUtil(sql);
            List<Map> list = daoUtil.executeQuery();
            for (Map map : list) {

                String id = Common.null2String(map.get("id"));


                //获取此状态下的已支付的订单
                //获取 退款所需字段
                sql = "select mo.id,mo.order_code,mo.refund_code,mo.transaction_id,mo.transaction_id," +
                        "mo.commodity_name,mo.pay_amount,mo.wx_openid " +
                        " from md_order mo where mo.launch_id = ? and mo.state = 2 ";
                daoUtil = new DAOUtil(sql);
                daoUtil.bind(1, id);
                List<Map> orderList = daoUtil.executeQuery();
                for (Map order : orderList) {
                    //执行退款
                    //TODO
//                    int ret = 0;
//                    String refund_id = null;
                    order.put("refund_code", Common.getRandomString(16));
//                    Map result = WxAbout.orderRefund(order);
//                    String return_code = Common.null2String(result.get("return_code"));
//                    String return_msg = Common.null2String(result.get("return_msg"));
//                    if (return_code.equals("SUCCESS")) {//表示通信成功
//                        String result_code = Common.null2String(result.get("result_code"));
//                        if ("SUCCESS".equals(result_code)) {
//                            refund_id = Common.null2String(result.get("refund_id"));//微信退款单号
//                        } else {
//                            ret = -2;//退款失败
//                            System.out.println(result.get("err_code"));
//                        }
//                    } else {
//                        ret = -2;//退款失败
//                        System.out.println(return_msg);
//                    }
//                    if(ret == 0){
                    //先更新拼团状态
                    sql = " update md_group_launch set state = 2 where id = ?";
                    daoUtil = new DAOUtil(sql);
                    daoUtil.bind(1, id);
                    daoUtil.executeUpdate();

                    //修改商品数量
//                    sql = "update md_commodity set sold =sole - ?,commodity_number=commodity_number + ?";


                    //退款成功 更改订单状态  请求退款
                    sql = "update md_order set state = 5,launch_id = null,refund_code = ? where id = ?";
                    daoUtil = new DAOUtil(sql);
//                        daoUtil.bind(1, refund_id);
                    daoUtil.bind(1, order.get("refund_code"));
                    daoUtil.bind(2, order.get("id"));
                    daoUtil.executeUpdate();

                    //发消息
                    sql = "select token from wx_ticket where appid = ?";
                    daoUtil = new DAOUtil(sql);
                    daoUtil.bind(1, WX_APP_ID);
                    Map token = daoUtil.executeQueryOne();
                    WxTemplate.groupClose(token.get("token").toString(), order);
//                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CheckGroupLaunchStateJob job = new CheckGroupLaunchStateJob();
        try {
            job.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
