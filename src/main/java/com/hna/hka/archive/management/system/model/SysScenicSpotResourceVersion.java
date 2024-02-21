package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotResourceVersion {
    private Long resId;

    private Long scenicSpotId;

    private String resVersion;

    private String resType;

    private String createDate;

    private String updateDate;

}