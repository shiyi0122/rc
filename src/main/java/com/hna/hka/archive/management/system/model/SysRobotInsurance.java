package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotInsurance {

    private Long insuranceId;
//    @Excel(name = "机器人id")
    private Long robotId;

    @Excel(name = "保险单号" ,orderNum = "3" , width = 10)
    private String insuranceNumber;

    @Excel(name = "出险电话")
    private String emergencyCall;

//    @Excel(name = "参险种类",replace = {"三者保险_1"})
    private String insuranceType;

//    @Excel(name = "参险单位",replace = {"北京九星智元科技有限公司_1" , "常州九星人工智能科技有限公司_2" , "平遥九星科技有限公司_3" , "上海游伴科技有限责任公司_4"})
    private String insuranceCompany;
    
    private String insureTime;

//    @Excel(name = "投保开始日期",exportFormat="yyyy-MM-dd",importFormat = "yyyy-MM-dd")
    private String insureStartTime;

    @Excel(name = "投保结束日期" ,orderNum = "4" , width = 10)
    private String insureEndTime;

    private String insureUrl;

    private String accidentProcess;

    private String createDate;

    private String updateDate;
    
    /**
     * 机器人编号
     */
    @Excel(name = "机器人ID",orderNum = "2" , width = 10)
    private String robotCode;
    
    /**
     * 景区ID
     */
    private String scenicSpotId;
    
    /**
     * 景区名称
     */
    @Excel(name = "景区名称" ,orderNum = "1" , width = 10)
    private String scenicSpotName;
    
    /**
     * 剩余时间
     */
    @Excel(name = "剩余时间", orderNum = "5" , width = 10)
    private String remainingTime;
    
    private String insureState;
    
    @Excel(name = "保险公司",replace = {"平安保险_1" , "中国人保财险_2" , "中国人寿保险_3"},orderNum = "6" , width = 10)
    private String insuranceUnit;

    @Excel(name = "出险流程关联id",orderNum = "7" , width = 10)
    private Long configsId;



}