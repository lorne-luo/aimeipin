package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by luanpeng on 16/3/30.
 */
@Entity
@Table(name = "md_user_favorite")
public class UserFavorite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wx_openid")
    private String wxOpenid;

    @Column(name = "commodity_id")
    private Integer commodityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}
