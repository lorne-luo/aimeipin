package com.meidi.util;


import java.io.Serializable;

/**
 * Created by luanpeng on 16/3/18.
 */
public class UserSession implements Serializable{
    private String wx_openid;

    private String account;

    private Integer bu_flag;

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
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
