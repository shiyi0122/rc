package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: SysScenicSpotBillingRules
 * @Author: 郭凯
 * @Description: 景区计费规则Excel表下载
 * @Date: 2020/12/7 13:17
 * @Version: 1.0
 */

@Data
public class SysScenicSpotBillingRules {

    @Excel(name = "景区名称",orderNum = "0" ,width = 20)
    private String scenicSpotName;

    @Excel(name = "调度费",orderNum = "9" ,width = 10)
    private String scenicSpotBeyondPrice;

    @Excel(name = "周六日单价",orderNum = "2" ,width = 10)
    private String scenicSpotWeekendPrice;

    @Excel(name = "工作日单价",orderNum = "3" ,width = 10)
    private String scenicSpotNormalPrice;

    @Excel(name = "周六日起租价格",orderNum = "4" ,width = 10)
    private String scenicSpotWeekendRentPrice;

    @Excel(name = "工作日起租价格",orderNum = "5" ,width = 10)
    private String scenicSpotNormalRentPrice;

    @Excel(name = "周六日计费时间",orderNum = "6" ,width = 10)
    private String scenicSpotWeekendTime;

    @Excel(name = "周六日计费时间",orderNum = "7" ,width = 10)
    private String scenicSpotNormalTime;

    @Excel(name = "起租时间",orderNum = "8" ,width = 10)
    private String scenicSpotRentTime;

    @Excel(name = "押金",orderNum = "1" ,width = 10)
    private String scenicSpotDeposit;

    @Excel(name = "添加时间",orderNum = "11" ,width = 20)
    private String createDate;

    @Excel(name = "随机播报时间",orderNum = "10" ,width = 20)
    private String randomBroadcastTime;


}
