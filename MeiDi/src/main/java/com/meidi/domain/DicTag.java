package com.meidi.domain;

import org.springframework.cglib.core.TinyBitSet;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by luanpeng on 16/3/30.
 */
@Entity
@Table(name = "dic_tag")
public class DicTag implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer flag;

    private String name;

    private Integer level = 1;

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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
