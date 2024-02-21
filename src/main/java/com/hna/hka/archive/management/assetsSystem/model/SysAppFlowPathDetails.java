package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/6/27 16:40
 */
@Data
public class SysAppFlowPathDetails {

    private Long id ;

    //审批流程id
    private Long flowPathId;

    //审批人id
    private String sysAppUserId ;

    //排序
    private String sort;
   // 类型
    private String type;

    private String  createTime;

    private String updateTime;

    private String userName;

    //审批结果
    private String  approvalResults;
    //备注
    private String  reason;

    private List<SysAppFlowPathDetails> appFlowPathDetailsList;


}
