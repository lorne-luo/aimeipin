package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by luanpeng on 16/3/26.
 */
@Entity
@Table(name = "md_commodity_photo")
public class CommodityPhoto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "commodity_id")
    private Integer commodityId;

    @Column(name = "image_name")
    private String imageName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
