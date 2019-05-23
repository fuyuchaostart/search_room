package com.fycstart.entity.dto;

import java.time.LocalDateTime;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22下午 2:06
 */
public class DeptDto {

    private Integer id;

    private String deptName;

    private String address;

    private Integer ceo;


    private String testName;

    private LocalDateTime testDate;

    private String testAge;

    private String testAddress;

    private String testCity;

    private String testBlood;

    private String testReligion;

    private String testNation;

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

    public LocalDateTime getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDateTime testDate) {
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
