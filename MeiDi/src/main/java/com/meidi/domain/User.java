package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by luanpeng on 16/3/10.
 */
@Entity
@Table(name = "md_user")
@SqlResultSetMapping(
        name = "searchResultMapping.User",
        entities = {
                @EntityResult(
                        entityClass = User.class
                )
        }
)
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wx_openid")
    private String wxOpenid;

    private String nickname;

    private String headimgurl;

    private String name;

    private String mobile;

    @Column(name = "wx_number")
    private String wxNumber;

    private Integer gender;

    private String channels;

    private String interests;

    private String wheres;

    @Column(name = "is_subscribe")
    private Boolean isSubscribe = false;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private DicCity dicCity;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();

    private Integer integral = 0;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getWheres() {
        return wheres;
    }

    public void setWheres(String wheres) {
        this.wheres = wheres;
    }

    public DicCity getDicCity() {
        return dicCity;
    }

    public void setDicCity(DicCity dicCity) {
        this.dicCity = dicCity;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Boolean getIsSubscribe() { return isSubscribe; }

    public void setIsSubscribe(Boolean isSubscribe) { this.isSubscribe = isSubscribe; }

}
