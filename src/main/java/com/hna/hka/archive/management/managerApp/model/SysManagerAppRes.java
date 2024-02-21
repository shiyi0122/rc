package com.hna.hka.archive.management.managerApp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class SysManagerAppRes {
    private Long resId;

    private String resCode;

    private String resPcode;

    private String resName;

    private Integer resSort;

    private String resMenuFlag;

    private String resDescription;

    private String createTime;

    private String updateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL) //忽略空值输出JSON
    private List<SysManagerAppRes> child;//子菜单
}