package com.banshion.portal.web.index.domain;

public class TTransInfo {
    private String serialNumber;

    private String transValue;

    private String userId;

    private String srcbankNumber;

    private String srcbankName;

    private String targetbankName;

    private String targetbankNumber;

    private int state;

    private String bz;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getTransValue() {
        return transValue;
    }

    public void setTransValue(String transValue) {
        this.transValue = transValue == null ? null : transValue.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSrcbankNumber() {
        return srcbankNumber;
    }

    public void setSrcbankNumber(String srcbankNumber) {
        this.srcbankNumber = srcbankNumber == null ? null : srcbankNumber.trim();
    }

    public String getSrcbankName() {
        return srcbankName;
    }

    public void setSrcbankName(String srcbankName) {
        this.srcbankName = srcbankName == null ? null : srcbankName.trim();
    }

    public String getTargetbankName() {
        return targetbankName;
    }

    public void setTargetbankName(String targetbankName) {
        this.targetbankName = targetbankName == null ? null : targetbankName.trim();
    }

    public String getTargetbankNumber() {
        return targetbankNumber;
    }

    public void setTargetbankNumber(String targetbankNumber) {
        this.targetbankNumber = targetbankNumber == null ? null : targetbankNumber.trim();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }
}