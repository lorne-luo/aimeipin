package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by luanpeng on 16/6/1.
 */
@Entity
@Table(name = "interest_commodity")
public class InterestCommodity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private List<InterestCommoditySon> interestCommoditySons;


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

    public List<InterestCommoditySon> getInterestCommoditySons() {
        return interestCommoditySons;
    }

    public void setInterestCommoditySons(List<InterestCommoditySon> interestCommoditySons) {
        this.interestCommoditySons = interestCommoditySons;
    }
}
