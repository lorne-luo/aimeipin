package com.meidi.job;


import com.meidi.dbaccess.DAOUtil;
import com.meidi.util.Common;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created on 2016/3/29.
 */
public class UpdateWxTicketJob extends MDJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf3d017eaf0b53463&secret=d6914a211b5175f62cc5dafb389cd074";
        try {
            HttpClient client = new HttpClient();
            GetMethod getMethod = new GetMethod(url);
            client.executeMethod(getMethod);
            String returnStr = getMethod.getResponseBodyAsString();
            Map ret = Common.json2Map(returnStr);
            //微信AccessToken
            String token = Common.null2String(ret.get("access_token"));

            url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
            getMethod = new GetMethod(url);
            client.executeMethod(getMethod);
            returnStr = getMethod.getResponseBodyAsString();
            ret = Common.json2Map(returnStr);
            //签名票据
            String ticket = Common.null2String(ret.get("ticket"));

//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String sql = "update wx_ticket set token=?,ticket=? where appid='wxf3d017eaf0b53463' ";
            DAOUtil dao = new DAOUtil(sql);
            dao.bind(1, token);
            dao.bind(2, ticket);
            dao.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UpdateWxTicketJob job = new UpdateWxTicketJob();
        try {
            job.execute(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
