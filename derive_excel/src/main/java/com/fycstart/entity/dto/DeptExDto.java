package com.fycstart.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22下午 2:06
 */
public class DeptExDto extends BaseRowModel {

    @ExcelProperty(value = "id", index = 0)
    private Integer id;

    @ExcelProperty(value = "部门名称", index = 1)
    private String deptName;

    @ExcelProperty(value = "地址", index = 2)
    private String address;

    @ExcelProperty(value = "ceo", index = 3)
    private Integer ceo;

    @ExcelProperty(value = "姓名", index = 4)
    private String testName;

    @ExcelProperty(value = "时间", index = 5,format = "yyyy-MM-dd")
    private Date testDate;

    @ExcelProperty(value = "年龄", index = 6)
    private String testAge;

    @ExcelProperty(value = "测试地址", index = 7)
    private String testAddress;

    @ExcelProperty(value = "测试城市", index = 8)
    private String testCity;

    @ExcelProperty(value = "测试血型", index = 9)
    private String testBlood;

    @ExcelProperty(value = "测试宗教", index = 10)
    private String testReligion;

    @ExcelProperty(value = "测试国家", index = 11)
    private String testNation;

    @ExcelProperty(value = "测试价格", index = 12)
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
