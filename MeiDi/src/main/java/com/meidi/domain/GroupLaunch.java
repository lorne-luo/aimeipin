package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by luanpeng on 16/4/6.
 */
@Entity
@Table(name = "md_group_launch")
@SqlResultSetMapping(name = "searchResultMapping.launch",
//        entities = {@EntityResult(entityClass = GroupLaunch.class)},
        columns = {
                @ColumnResult(name = "id"),
                @ColumnResult(name = "people_number"),
                @ColumnResult(name = "end_time")
        }
)
public class GroupLaunch implements Serializable {

    public GroupLaunch(){

    }

    public GroupLaunch(Integer id,Integer peopleNumber,Date endTime){
        super();
        this.id = id;
        this.peopleNumber = peopleNumber;
        this.endTime = endTime;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private DicProvince dicProvince;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private DicCity dicCity;

    @Column(name = "commodity_name")
    private String commodityName;

    @Column(name = "commodity_number")
    private Integer commodityNumber;

    @Column(name = "people_number")
    private Integer peopleNumber;

    private Integer price;

    /**
     * 单价
     */
    @Column(name = "unit_price")
    private Integer unitPrice;

    /**
     * 单位
     */
    private String unit;

    private Float discount;

    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "pay_amount")
    private Integer payAmount;

    /**
     * 状态 0 拼团中, 1 拼团成功 拼团结束, 3 拼团失败
     */
    private Integer state = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "launch_id", referencedColumnName = "id")
    private List<GroupLaunchUser> groupLaunchUserList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<GroupLaunchUser> getGroupLaunchUserList() {
        return groupLaunchUserList;
    }

    public void setGroupLaunchUserList(List<GroupLaunchUser> groupLaunchUserList) {
        this.groupLaunchUserList = groupLaunchUserList;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public DicProvince getDicProvince() {
        return dicProvince;
    }

    public void setDicProvince(DicProvince dicProvince) {
        this.dicProvince = dicProvince;
    }

    public DicCity getDicCity() {
        return dicCity;
    }

    public void setDicCity(DicCity dicCity) {
        this.dicCity = dicCity;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(Integer commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
