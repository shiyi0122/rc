package com.hna.hka.archive.management.appSystem.model;

public class AppRobotSoftAssetInformation {

    private Long softAssetInformationId;

    private Long robotId;

    private String dateProduction;

    private String batteryWarrantyDeadline;

    private String robotWarrantyDeadline;

    private String createDate;

    private String updateDate;

    private String cost;

    private String serviceLife;

    private String factoryCost;

    private Double accumulate;

    private Double netPrice;

    private String robotCode;

    public Long getSoftAssetInformationId() {
        return softAssetInformationId;
    }

    public void setSoftAssetInformationId(Long softAssetInformationId) {
        this.softAssetInformationId = softAssetInformationId;
    }

    public Long getRobotId() {
        return robotId;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public String getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(String dateProduction) {
        this.dateProduction = dateProduction;
    }

    public String getBatteryWarrantyDeadline() {
        return batteryWarrantyDeadline;
    }

    public void setBatteryWarrantyDeadline(String batteryWarrantyDeadline) {
        this.batteryWarrantyDeadline = batteryWarrantyDeadline;
    }

    public String getRobotWarrantyDeadline() {
        return robotWarrantyDeadline;
    }

    public void setRobotWarrantyDeadline(String robotWarrantyDeadline) {
        this.robotWarrantyDeadline = robotWarrantyDeadline;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String getFactoryCost() {
        return factoryCost;
    }

    public void setFactoryCost(String factoryCost) {
        this.factoryCost = factoryCost;
    }

    public Double getAccumulate() {
        return accumulate;
    }

    public void setAccumulate(Double accumulate) {
        this.accumulate = accumulate;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    public String getRobotCode() {
        return robotCode;
    }

    public void setRobotCode(String robotCode) {
        this.robotCode = robotCode;
    }
}