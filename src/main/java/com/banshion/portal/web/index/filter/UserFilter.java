package com.banshion.portal.web.index.filter;

import com.banshion.portal.util.domain.BaseFilter;

/**
 */
public class UserFilter extends BaseFilter { // 继承过来一些jqgrid查询传递的条件

    private String name ;
    private String jobNumber;
    private String idNumber;
    private String loginName;
    private String sex;
    private String deptId;
    private String id;
    private String bankNumber;

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getSex() {
        return sex;
    }

    public String getDeptId() {
        return deptId;
    }
}
