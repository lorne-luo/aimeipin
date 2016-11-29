package com.meidi.domain;

import com.meidi.util.MdCommon;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;
import java.util.Map;

/**
 * Created by luanpeng on 16/4/2.
 */
@Entity
@Table(name = "wx_ticket")
public class WxTicket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String appid;

    private String appsecret;

    private String token;

    private String ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public void updateTokenTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                this.getAppid() + "&secret=" + this.getAppsecret();

        String returnStr = "";
        try {
            HttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = client.execute(httpGet);
            returnStr = EntityUtils.toString(httpResponse.getEntity());
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

            this.setToken(token);
            this.setTicket(ticket);
        } catch (Exception e) {
            System.out.println("[WxTicket] update weixin token and ticket failed.");
            System.out.println(returnStr);
            e.printStackTrace();
        }
    }
}
