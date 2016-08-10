package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by luanpeng on 16/4/6.
 */
@Entity
@Table(name = "md_group_launch_user")
public class GroupLaunchUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer flag = 0;

    @Column(name = "launch_id")
    private Integer launchId;

    @Column(name = "wx_openid")
    private String wxOpenid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Integer launchId) {
        this.launchId = launchId;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
