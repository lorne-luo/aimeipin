package com.meidi.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by luanpeng on 16/3/24.
 */
@Entity
@Table(name = "md_commodity")
@SqlResultSetMapping(
        name = "searchResultMapping.commodity",
//        entities = {
//                @EntityResult(
//                        entityClass = CommodityPhoto.class
//                )
//        },
        columns = {
                @ColumnResult(name = "id"),
                @ColumnResult(name = "commodity_code"),
                @ColumnResult(name = "flag"),
                @ColumnResult(name = "name"),
                @ColumnResult(name = "price"),
                @ColumnResult(name = "discount"),
                @ColumnResult(name = "discount_price"),
                @ColumnResult(name = "state"),
                @ColumnResult(name = "create_time"),
                @ColumnResult(name = "province_id"),
                @ColumnResult(name = "province_name"),
                @ColumnResult(name = "city_id"),
                @ColumnResult(name = "city_name"),
                @ColumnResult(name = "category_id"),
                @ColumnResult(name = "category_name"),
                @ColumnResult(name = "people_number"),
                @ColumnResult(name = "label_flag"),
                @ColumnResult(name = "weight"),
                @ColumnResult(name = "price_double"),
                @ColumnResult(name = "discount_price_double")
        }
)
public class Commodity implements Serializable {

    public Commodity() {

    }

    public Commodity(Integer id, String commodityCode, Integer flag, String name, Integer price, Float discount, Integer discountPrice,
                     Integer state, Timestamp createTime, Integer provinceId, String provinceName, Integer cityId, String cityName,
                     Integer categoryId, String categoryName,
                     Integer peopleNumber,Integer labelFlag,Integer weight,Double priceDouble,Double discountPriceDouble) {
        super();
        this.id = id;
        this.commodityCode = commodityCode;
        this.flag = flag;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.discountPrice = discountPrice;
        this.state = state;
        this.createTime = createTime;
        DicProvince dicProvince = new DicProvince();
        dicProvince.setId(provinceId);
        dicProvince.setName(provinceName);
        DicCity dicCity = new DicCity();
        dicCity.setId(cityId);
        dicCity.setName(cityName);
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        this.dicProvince = dicProvince;
        this.dicCity = dicCity;
        this.category = category;
        this.peopleNumber = peopleNumber;
        this.labelFlag = labelFlag;
        this.weight = weight;
        this.priceDouble = priceDouble;
        this.discountPriceDouble = discountPriceDouble;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 项目类型: 1 拼团, 2 福袋, 3 特惠, 4 咨询
     */
    private Integer flag = 1;

    private String name;

    private String keyword;

    /**
     * 原价
     */
    private Integer price;

    @Column(name = "price_double")
    private Double priceDouble = 0.0;

    private String unit;

    @Column(name = "discount_unit")
    private String discountUnit;

    /**
     * 折扣
     */
    private Float discount;

    /**
     * 折后价
     */
    @Column(name = "discount_price")
    private Integer discountPrice;

    @Column(name = "discount_price_double")
    private Double discountPriceDouble = 0.0;

    /**
     * 定金
     */
    private Integer deposit = 0;

    @Column(name = "deposit_double")
    private Double depositDouble = 0.0;

    @Column(name = "commodity_number")
    private Integer commodityNumber = 0;

    /**
     * 实际销量
     */
    private Integer sold = 0;

    /**
     * 自定义销量
     */
    @Column(name = "custom_sold")
    private Integer customSold = 0;

    /**
     * 状态: -1 已删除, 0 未上架, 1 已上架
     */
    private Integer state = 0;

    private String tags;

    @Column(name = "people_number")
    private Integer peopleNumber = 0;

    /**
     * 一人购买项目
     */
    @Column(name = "alone_price")
    private Integer alonePrice = 0;

    @Column(name = "alone_price_double")
    private Double alonePriceDouble = 0.0;

//    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private String startDate;

//    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private String endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "description")
    private String description;

    /**
     * 备注,支付说明
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 分享摘要
     */
    @Column(name = "sharing_summary")
    private String sharingSummary;

    @Column(name = "label_flag")
    private Integer labelFlag = 0;

    @Column(name = "case_url")
    private String caseUrl;

    private Integer weight = 2;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private DicProvince dicProvince;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private DicCity dicCity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "commodity_id", referencedColumnName = "id")
    private List<CommodityPhoto> commodityPhotoList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(Integer commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Integer getCustomSold() {
        return customSold;
    }

    public void setCustomSold(Integer customSold) {
        this.customSold = customSold;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getAlonePrice() {
        return alonePrice;
    }

    public void setAlonePrice(Integer alonePrice) {
        this.alonePrice = alonePrice;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<CommodityPhoto> getCommodityPhotoList() {
        return commodityPhotoList;
    }

    public void setCommodityPhotoList(List<CommodityPhoto> commodityPhotoList) {
        this.commodityPhotoList = commodityPhotoList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemarks() {return remarks;}

    public void setRemarks(String remarks) {this.remarks = remarks;}

    public String getSharingSummary() {return sharingSummary;}

    public void setSharingSummary(String sharingSummary) {this.sharingSummary = sharingSummary;}

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLabelFlag() {
        return labelFlag;
    }

    public void setLabelFlag(Integer labelFlag) {
        this.labelFlag = labelFlag;
    }

    public String getCaseUrl() {
        return caseUrl;
    }

    public void setCaseUrl(String caseUrl) {
        this.caseUrl = caseUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Double getDiscountPriceDouble() {
        return discountPriceDouble;
    }

    public void setDiscountPriceDouble(Double discountPriceDouble) {
        this.discountPriceDouble = discountPriceDouble;
    }

    public Double getDepositDouble() {
        return depositDouble;
    }

    public void setDepositDouble(Double depositDouble) {
        this.depositDouble = depositDouble;
    }

    public Double getAlonePriceDouble() {
        return alonePriceDouble;
    }

    public void setAlonePriceDouble(Double alonePriceDouble) {
        this.alonePriceDouble = alonePriceDouble;
    }

    public Double getPriceDouble() {
        return priceDouble;
    }

    public void setPriceDouble(Double priceDouble) {
        this.priceDouble = priceDouble;
    }

    public Category getCategory() {return category;}

    public void setCategory(Category category) {this.category = category;}


}
