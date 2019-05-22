package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public class Subway implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 线路名
     */
    @TableField("name")
    private String name;

    /**
     * 所属城市英文名缩写
     */
    @TableField("city_en_name")
    private String cityEnName;


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

    public String getCityEnName() {
        return cityEnName;
    }

    public void setCityEnName(String cityEnName) {
        this.cityEnName = cityEnName;
    }

    @Override
    public String toString() {
        return "Subway{" +
                "id=" + id +
                ", name=" + name +
                ", cityEnName=" + cityEnName +
                "}";
    }
}
