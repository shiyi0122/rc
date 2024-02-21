package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotProblemExtend {
    private Long extendId;

    private Long corpusId;

    private Long scenicSpotId;

    private String extendCorpusProblem;

    private String extendCorpusPinyin;

    private String extendType;

    private String createDate;

    private String updateDate;

}