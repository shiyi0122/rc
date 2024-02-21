package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/6/27 16:37
 */
@Data
public class SysAppFlowPath {

    private Long id ;

    //流程名称
    private String flowPathName;

    private String createTime;

    private String updateTime;

    //景区id字符串
    private String scenicSpotIds;

//    private String appFlowPathDetailsS;

    //审批人id
    private List<SysAppFlowPathDetails> appFlowPathDetailsList;

    private List<SysAppFlowPathSpot> appFlowPathSpotList;

}
