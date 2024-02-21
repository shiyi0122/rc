package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysRobotCorpus {
    private Long corpusId;

    private String corpusProblem;

    private String pinYinProblem;

    private String semanticType;

    private String corpusType;

    private String genericType;

    private String corpusResUrl;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

    /**
     * 回显状态
     */
    private String genericTypeTwo;

}