package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotPadNew {
    private Long padId;

    private String padUrl;

    private String padNumber;

    private String padDescription;

    private String createDate;

    private String updateDate;

    //更新范围 1，全国，2景区，3待定
    private String updateScope;
    //更新类型 1强制 2 提示更新 3 不提示更新
    private String updateType;
    //包类型 1完整包 2资源包
    private String packageType;
    //景区id
    private String scenicSpotIds;
    //关联版本包id
    private String completeIds;

    private String startTime;

    private String endTime;

    private String padOssUrl;

    private String scenicSpotId;


    private String scenicSpotName;



}