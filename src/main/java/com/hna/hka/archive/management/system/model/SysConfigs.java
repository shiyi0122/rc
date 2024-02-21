package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysConfigs {
    private Long configsId;

    private String title;

    private String configsType;

    private String createTime;

    private String updateTime;

    private String configsValues;

}