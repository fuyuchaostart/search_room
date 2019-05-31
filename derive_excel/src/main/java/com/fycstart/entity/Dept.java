package com.fycstart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author fycstart
 * @since 2019-05-21
 */
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("dept_name")
    private String deptName;

    @TableField("address")
    private String address;

    @TableField("ceo")
    private Integer ceo;

    @TableField("test_name")
    private String testName;

    @TableField("test_date")
    private Date testDate;

    @TableField("test_age")
    private String testAge;

    @TableField("test_address")
    private String testAddress;

    @TableField("test_city")
    private String testCity;

    @TableField("test_blood")
    private String testBlood;

    @TableField("test_religion")
    private String testReligion;

    @TableField("test_nation")
    private String testNation;

    @TableField("test_money")
    private String testMoney;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCeo() {
        return ceo;
    }

    public void setCeo(Integer ceo) {
        this.ceo = ceo;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestAge() {
        return testAge;
    }

    public void setTestAge(String testAge) {
        this.testAge = testAge;
    }

    public String getTestAddress() {
        return testAddress;
    }

    public void setTestAddress(String testAddress) {
        this.testAddress = testAddress;
    }

    public String getTestCity() {
        return testCity;
    }

    public void setTestCity(String testCity) {
        this.testCity = testCity;
    }

    public String getTestBlood() {
        return testBlood;
    }

    public void setTestBlood(String testBlood) {
        this.testBlood = testBlood;
    }

    public String getTestReligion() {
        return testReligion;
    }

    public void setTestReligion(String testReligion) {
        this.testReligion = testReligion;
    }

    public String getTestNation() {
        return testNation;
    }

    public void setTestNation(String testNation) {
        this.testNation = testNation;
    }

    public String getTestMoney() {
        return testMoney;
    }

    public void setTestMoney(String testMoney) {
        this.testMoney = testMoney;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", deptName=" + deptName +
                ", address=" + address +
                ", ceo=" + ceo +
                ", testName=" + testName +
                ", testDate=" + testDate +
                ", testAge=" + testAge +
                ", testAddress=" + testAddress +
                ", testCity=" + testCity +
                ", testBlood=" + testBlood +
                ", testReligion=" + testReligion +
                ", testNation=" + testNation +
                ", testMoney=" + testMoney +
                "}";
    }
}
