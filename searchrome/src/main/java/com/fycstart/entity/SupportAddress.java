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
public class SupportAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上一级行政单位名
     */
    @TableField("belong_to")
    private String belongTo;

    /**
     * 行政单位英文名缩写
     */
    @TableField("en_name")
    private String enName;

    /**
     * 行政单位中文名
     */
    @TableField("cn_name")
    private String cnName;

    /**
     * 行政级别 市-city 地区-region
     */
    @TableField("level")
    private String level;

    /**
     * 百度地图经度
     */
    @TableField("baidu_map_lng")
    private Double baiduMapLng;

    /**
     * 百度地图纬度
     */
    @TableField("baidu_map_lat")
    private Double baiduMapLat;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Double getBaiduMapLng() {
        return baiduMapLng;
    }

    public void setBaiduMapLng(Double baiduMapLng) {
        this.baiduMapLng = baiduMapLng;
    }

    public Double getBaiduMapLat() {
        return baiduMapLat;
    }

    public void setBaiduMapLat(Double baiduMapLat) {
        this.baiduMapLat = baiduMapLat;
    }

    @Override
    public String toString() {
        return "SupportAddress{" +
                "id=" + id +
                ", belongTo=" + belongTo +
                ", enName=" + enName +
                ", cnName=" + cnName +
                ", level=" + level +
                ", baiduMapLng=" + baiduMapLng +
                ", baiduMapLat=" + baiduMapLat +
                "}";
    }


    /**
     * 行政级别定义
     */
    public enum Level {
        CITY("city"),
        REGION("region");

        private String value;

        Level(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Level of(String value) {
            for (Level level : Level.values()) {
                if (level.getValue().equals(value)) {
                    return level;
                }
            }

            throw new IllegalArgumentException();
        }
    }
}
