package com.banshion.portal.web.index.filter;

import com.banshion.portal.util.domain.BaseFilter;

/**
 */
public class TransFilter extends BaseFilter {

    private String userName;
    private String deptName;
    private String serialNumber;
    private String idNumber;
    private String jobNumber;
    private String srcbankNumber;
    private String srcbankName;
    private String targetbankNumber;
    private String targetbankName;
    private Integer state;
    private String userId;
    private String transValue;
    private String deptId;
    private String bz;


    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSrcbankName(String srcbankName) {
        this.srcbankName = srcbankName;
    }

    public void setTargetbankName(String targetbankName) {
        this.targetbankName = targetbankName;
    }

    public void setTransValue(String transValue) {
        this.transValue = transValue;
    }

    public String getSrcbankName() {
        return srcbankName;
    }

    public String getTargetbankName() {
        return targetbankName;
    }

    public String getTransValue() {
        return transValue;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setSrcbankNumber(String srcbankNumber) {
        this.srcbankNumber = srcbankNumber;
    }

    public void setTargetbankNumber(String targetbankNumber) {
        this.targetbankNumber = targetbankNumber;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public String getSrcbankNumber() {
        return srcbankNumber;
    }

    public String getTargetbankNumber() {
        return targetbankNumber;
    }

    public Integer getState() {
        return state;
    }
}
