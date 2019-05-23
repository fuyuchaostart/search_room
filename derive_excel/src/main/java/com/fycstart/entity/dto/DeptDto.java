package com.fycstart.entity;

/**
 * @author fyc
 * @description: TODO
 * @date 2019/5/22下午 2:06
 */
public class DeptVo {

    private Integer id;

    private String deptName;

    private String address;

    private Integer ceo;


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
}
