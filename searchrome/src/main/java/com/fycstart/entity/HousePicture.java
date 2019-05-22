package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 房屋图片信息
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public class HousePicture implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属房屋id
     */
    @TableField("house_id")
    private Integer houseId;

    /**
     * 图片路径
     */
    @TableField("cdn_prefix")
    private String cdnPrefix;

    /**
     * 宽
     */
    @TableField("width")
    private Integer width;

    /**
     * 高
     */
    @TableField("height")
    private Integer height;

    /**
     * 所属房屋位置
     */
    @TableField("location")
    private String location;

    /**
     * 文件名
     */
    @TableField("path")
    private String path;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getCdnPrefix() {
        return cdnPrefix;
    }

    public void setCdnPrefix(String cdnPrefix) {
        this.cdnPrefix = cdnPrefix;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "HousePicture{" +
                "id=" + id +
                ", houseId=" + houseId +
                ", cdnPrefix=" + cdnPrefix +
                ", width=" + width +
                ", height=" + height +
                ", location=" + location +
                ", path=" + path +
                "}";
    }
}
