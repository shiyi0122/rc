package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotPriceModificationLog {
    private Long priceModificationId;

    @Excel(name = "修改人名称",orderNum = "0",width = 20)
    private String priceModificationUserName;

    @Excel(name = "景区名称",orderNum = "1",width = 30)
    private String priceModificationSpotName;

    @Excel(name = "押金",orderNum = "2",width = 20)
    private String scenicSpotDeposit;

    @Excel(name = "周末单价",orderNum = "3",width = 20)
    private String scenicSpotWeekendPrice;

    @Excel(name = "工作日单价",orderNum = "4",width = 20)
    private String scenicSpotNormalPrice;

    @Excel(name = "周末起租价格",orderNum = "5",width = 20)
    private String scenicSpotWeekendRentPrice;

    @Excel(name = "工作日起租价格",orderNum = "6",width = 20)
    private String scenicSpotNormalRentPrice;

    private String type;

    @Excel(name = "起租时间",orderNum = "7",width = 20)
    private String scenicSpotRentTime;

    @Excel(name = "调度费",orderNum = "8",width = 20)
    private String scenicSpotBeyondPrice;
    
    @Excel(name = "工作日计费时间",orderNum = "9",width = 20)
    private String scenicSpotNormalTime;
    
    @Excel(name = "周末计费时间",orderNum = "10",width = 20)
    private String scenicSpotWeekendTime;

    @Excel(name = "随机播报时间",orderNum = "11",width = 20)
    private String randomBroadcastTime;

    @Excel(name = "锁定次数",orderNum = "12",width = 20)
    private String scenicSpotFrequency;

    @Excel(name = "电子围栏锁定时间",orderNum = "13",width = 20)
    private String scenicSpotFenceTime;

    @Excel(name = "禁区锁定时间",orderNum = "14",width = 20)
    private String scenicSpotForbiddenZoneTime;

    @Excel(name = "修改时间",orderNum = "15",width = 20)
    private String createDate;
    
    private String scenicSpotContact;

    private String scenicSpotPhone;

    private String scenicSpotProvince;

    private String testStartTime;

    private String trialOperationsTime;

    private String formalOperationTime;

    private String stopOperationTime;

}