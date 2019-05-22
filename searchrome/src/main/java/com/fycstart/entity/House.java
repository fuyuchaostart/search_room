package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 房屋信息表
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * house唯一标识
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    /**
     * 价格
     */
    @TableField("price")
    private Integer price;

    /**
     * 面积
     */
    @TableField("area")
    private Integer area;

    /**
     * 卧室数量
     */
    @TableField("room")
    private Integer room;

    /**
     * 楼层
     */
    @TableField("floor")
    private Integer floor;

    /**
     * 总楼层
     */
    @TableField("total_floor")
    private Integer totalFloor;

    /**
     * 被看次数
     */
    @TableField("watch_times")
    private Integer watchTimes;

    /**
     * 建立年限
     */
    @TableField("build_year")
    private Integer buildYear;

    /**
     * 房屋状态 0-未审核 1-审核通过 2-已出租 3-逻辑删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最近数据更新时间
     */
    @TableField("last_update_time")
    private Date lastUpdateTime;

    /**
     * 城市标记缩写 如 北京bj
     */
    @TableField("city_en_name")
    private String cityEnName;

    /**
     * 地区英文简写 如昌平区 cpq
     */
    @TableField("region_en_name")
    private String regionEnName;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 房屋朝向
     */
    @TableField("direction")
    private Integer direction;

    /**
     * 距地铁距离 默认-1 附近无地铁
     */
    @TableField("distance_to_subway")
    private Integer distanceToSubway;

    /**
     * 客厅数量
     */
    @TableField("parlour")
    private Integer parlour;

    /**
     * 所在小区
     */
    @TableField("district")
    private String district;

    /**
     * 所属管理员id
     */
    @TableField("admin_id")
    private Integer adminId = 0;


    @TableField("bathroom")
    private Integer bathroom;

    /**
     * 街道
     */
    @TableField("street")
    private String street;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(Integer totalFloor) {
        this.totalFloor = totalFloor;
    }

    public Integer getWatchTimes() {
        return watchTimes;
    }

    public void setWatchTimes(Integer watchTimes) {
        this.watchTimes = watchTimes;
    }

    public Integer getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(Integer buildYear) {
        this.buildYear = buildYear;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCityEnName() {
        return cityEnName;
    }

    public void setCityEnName(String cityEnName) {
        this.cityEnName = cityEnName;
    }

    public String getRegionEnName() {
        return regionEnName;
    }

    public void setRegionEnName(String regionEnName) {
        this.regionEnName = regionEnName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getDistanceToSubway() {
        return distanceToSubway;
    }

    public void setDistanceToSubway(Integer distanceToSubway) {
        this.distanceToSubway = distanceToSubway;
    }

    public Integer getParlour() {
        return parlour;
    }

    public void setParlour(Integer parlour) {
        this.parlour = parlour;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", title=" + title +
                ", price=" + price +
                ", area=" + area +
                ", room=" + room +
                ", floor=" + floor +
                ", totalFloor=" + totalFloor +
                ", watchTimes=" + watchTimes +
                ", buildYear=" + buildYear +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", cityEnName=" + cityEnName +
                ", regionEnName=" + regionEnName +
                ", cover=" + cover +
                ", direction=" + direction +
                ", distanceToSubway=" + distanceToSubway +
                ", parlour=" + parlour +
                ", district=" + district +
                ", adminId=" + adminId +
                ", bathroom=" + bathroom +
                ", street=" + street +
                "}";
    }
}
