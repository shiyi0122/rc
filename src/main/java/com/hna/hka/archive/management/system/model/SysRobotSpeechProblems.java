package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysRobotSpeechProblems {
    private Long speechProblemsId;

    @Excel(name = "语音问题名称",width = 20,orderNum = "1")
    private String speechProblems;

    @Excel(name = "语音问题拼音",width = 20,orderNum = "2")
    private String speechProblemsPinyin;

    private Long scenicSpotId;

    @Excel(name = "景区名称",width = 30,orderNum = "0")
    private String scenicSpotName;

    private String handleState;

    private String problemStatus;

    @Excel(name = "出现次数" ,width = 20,orderNum = "5")
    private String problemTimes;

    private String voiceType;

    @Excel(name = "添加时间" ,width = 20,orderNum = "5")
    private String createDate;

    private String updateDate;
    
    @Excel(name = "处理状态",width = 20,orderNum = "3")
    private String handleStateName;

    @Excel(name = "问题状态",width = 20,orderNum = "4")
    private String problemStatusName;
}