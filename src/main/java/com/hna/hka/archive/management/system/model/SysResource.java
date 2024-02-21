package com.hna.hka.archive.management.system.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class SysResource {
    private Long resId;

    private String resCode;

    private String resPcode;

    private String resName;

    private String resIcon;

    private String resUrl;

    private Integer resSort;

    private Integer resLevels;

    private String resMenuFlag;

    private String resDescription;

    private String resStatus;

    private String resSystemType;

    private String resSystemStatus;

    private String createTime;

    private String updateTime;

    private String resPcodeName;//父节点名称

    @JsonInclude(JsonInclude.Include.NON_NULL) //忽略空值输出JSON
    private List<SysResource> child;//子菜单

}