package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 预约看房信息表
 * </p>
 *
 * @author fycstart
 * @since 2019-04-29
 */
public class HouseSubscribe implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 房源id
     */
    @TableField("house_id")
    private Integer houseId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户描述
     */
    @TableField("user_desc")
    private String userDesc;

    /**
     * 预约状态 1-加入待看清单 2-已预约看房时间 3-看房完成
     */
    @TableField("status")
    private Integer status;

    /**
     * 数据创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;

    /**
     * 预约时间
     */
    @TableField("order_time")
    private LocalDateTime orderTime;

    /**
     * 联系电话
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 房源发布者id
     */
    @TableField("admin_id")
    private Integer adminId;


    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "HouseSubscribe{" +
                "id=" + id +
                ", houseId=" + houseId +
                ", userId=" + userId +
                ", userDesc=" + userDesc +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", orderTime=" + orderTime +
                ", telephone=" + telephone +
                ", adminId=" + adminId +
                "}";
    }
}
