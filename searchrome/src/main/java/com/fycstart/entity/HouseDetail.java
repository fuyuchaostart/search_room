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
public class HouseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 详细描述
     */
    @TableField("description")
    private String description;

    /**
     * 户型介绍
     */
    @TableField("layout_desc")
    private String layoutDesc;

    /**
     * 交通出行
     */
    @TableField("traffic")
    private String traffic;

    /**
     * 周边配套
     */
    @TableField("round_service")
    private String roundService;

    /**
     * 租赁方式
     */
    @TableField("rent_way")
    private Integer rentWay;

    /**
     * 详细地址
     */
    @TableField("address")
    private String address;

    /**
     * 附近地铁线id
     */
    @TableField("subway_line_id")
    private Integer subwayLineId;

    /**
     * 附近地铁线名称
     */
    @TableField("subway_line_name")
    private String subwayLineName;

    /**
     * 地铁站id
     */
    @TableField("subway_station_id")
    private Integer subwayStationId;

    /**
     * 地铁站名
     */
    @TableField("subway_station_name")
    private String subwayStationName;

    /**
     * 对应house的id
     */
    @TableField("house_id")
    private Integer houseId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLayoutDesc() {
        return layoutDesc;
    }

    public void setLayoutDesc(String layoutDesc) {
        this.layoutDesc = layoutDesc;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getRoundService() {
        return roundService;
    }

    public void setRoundService(String roundService) {
        this.roundService = roundService;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSubwayLineId() {
        return subwayLineId;
    }

    public void setSubwayLineId(Integer subwayLineId) {
        this.subwayLineId = subwayLineId;
    }

    public String getSubwayLineName() {
        return subwayLineName;
    }

    public void setSubwayLineName(String subwayLineName) {
        this.subwayLineName = subwayLineName;
    }

    public Integer getSubwayStationId() {
        return subwayStationId;
    }

    public void setSubwayStationId(Integer subwayStationId) {
        this.subwayStationId = subwayStationId;
    }

    public String getSubwayStationName() {
        return subwayStationName;
    }

    public void setSubwayStationName(String subwayStationName) {
        this.subwayStationName = subwayStationName;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        return "HouseDetail{" +
                "id=" + id +
                ", description=" + description +
                ", layoutDesc=" + layoutDesc +
                ", traffic=" + traffic +
                ", roundService=" + roundService +
                ", rentWay=" + rentWay +
                ", address=" + address +
                ", subwayLineId=" + subwayLineId +
                ", subwayLineName=" + subwayLineName +
                ", subwayStationId=" + subwayStationId +
                ", subwayStationName=" + subwayStationName +
                ", houseId=" + houseId +
                "}";
    }
}
