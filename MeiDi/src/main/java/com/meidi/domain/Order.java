package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by luanpeng on 16/3/31.
 */
@Entity
@Table(name = "md_order")
@SqlResultSetMapping(name = "searchResultMapping.order",
        entities = {@EntityResult(entityClass = Order.class)}
)
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_code")
    private String orderCode;

    @Column(name = "refund_code")
    private String refundCode;

    @Column(name = "refund_id")
    private String refundId;

    @Column(name = "wx_openid")
    private String wxOpenid;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "ask_id")
    private Integer askId;

    @Column(name = "commodity_name")
    private String commodityName;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id",referencedColumnName = "id")
    private DicProvince dicProvince;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private DicCity dicCity;

    /**
     *发起的拼团Id 只有完成支付 成功发起拼团之后才有
     */
    @Column(name = "launch_id")
    private Integer launchId;

    /**
     * 拼团订单不为0
     */
    @Column(name = "people_number")
    private Integer peopleNumber = 0;

    /**
     * 预订类型 1 发起拼团预订 2 拼团一人预订  3普通预订 4参团预订
     *         1 拼团专享支付（发起拼团）  2 拼团一人支付  3普通(特惠 福袋)支付  4参团支付
     */
    @Column(name = "booking_flag")
    private Integer bookingFlag = 3 ;

    /**
     * 预定数量
     */
    @Column(name = "commodity_number")
    private Integer commodityNumber;

    /**
     * 订单总额
     */
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

    /**
     * 订单折扣
     */
    private Float discount;

    /**
     * 订单折后总额
     */
    @Column(name = "discount_price")
    private Integer discountPrice;

    /**
     *实付金额
     */
    @Column(name = "payAmount")
    private Integer payAmount;

    private String username;

    private String mobile;

    /**
     * 支付状态
     * 1 未支付                       | 未支付
     * 2 已支付                       | 已支付
     * 3 支付失败                     | 支付失败
     * 4 订单取消 等待退款             | 已完成
     * 5 已退款 订单关闭 取消中 等待审核 | 取消中
     * 6 订单取消 订单关闭（未支付）     | 已取消(已退款)
     * 7 订单关闭（拼团超时 已退款）     | 已取消(不退款)
     * 8 订单关闭（已在医院做完项目）    | 已取消(未付款)
     * 9 已取消(已退款)                | 已取消(未付款)
     */
    private Integer state = 1;

    /**
     * 微信订单号
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 项目类型: 1 拼团, 2 福袋, 3 特惠, 4 咨询
     * 与commondity.flag一致
     */
    private Integer flag = 0;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    private String remarks;

    /**
     * 微信号
     */
    private String weixin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "booking_time")
    private Date bookingTime;

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


    public Integer getLaunchId() {
        return launchId;
    }

    public void setLaunchId(Integer launchId) {
        this.launchId = launchId;
    }


    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRefundCode() {
        return refundCode;
    }

    public void setRefundCode(String refundCode) {
        this.refundCode = refundCode;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }


    public Integer getAskId() {
        return askId;
    }

    public void setAskId(Integer askId) {
        this.askId = askId;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBookingFlag() {
        return bookingFlag;
    }

    public void setBookingFlag(Integer bookingFlag) {
        this.bookingFlag = bookingFlag;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
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
