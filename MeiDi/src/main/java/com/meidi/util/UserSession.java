package com.meidi.util;


import java.io.Serializable;

/**
 * Created by luanpeng on 16/3/18.
 */
public class UserSession implements Serializable{
    private String wx_openid; //商城公众号openid (同一用户在不同公众号openid不一致)

    private String booking_wx_openid = null; //下单公众号openid

    private String account; // 商城后台用户名

    private Integer bu_flag;  //商城后台 1 超级管理员, 0 普通管理员

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getBooking_wx_openid() {
        // 若下单公众号openid为null则返回商城公众号openid
        if (booking_wx_openid == null)
            return wx_openid;
        else
            return booking_wx_openid;
    }

    public void setBooking_wx_openid(String booking_wx_openid) {
        this.booking_wx_openid = booking_wx_openid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBu_flag() {
        return bu_flag;
    }

    public void setBu_flag(Integer bu_flag) {
        this.bu_flag = bu_flag;
    }
}
