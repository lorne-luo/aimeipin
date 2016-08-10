package com.meidi.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by luanpeng on 16/3/24.
 */
@Entity
@Table(name = "dic_province")
public class DicProvince implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

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
}
