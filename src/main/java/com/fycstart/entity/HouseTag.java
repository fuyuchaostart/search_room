package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 房屋标签映射关系表
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public class HouseTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房屋id
     */
    @TableField("house_id")
    private Integer houseId;

    /**
     * 标签id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;


    public HouseTag() {
    }

    public HouseTag(Integer houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

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

    @Override
    public String toString() {
        return "HouseTag{" +
                "houseId=" + houseId +
                ", id=" + id +
                ", name=" + name +
                "}";
    }
}
