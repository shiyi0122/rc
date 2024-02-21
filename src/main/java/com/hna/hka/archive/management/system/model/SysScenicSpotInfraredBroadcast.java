package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotInfraredBroadcast {
    private Long infraredId;

    private Long scenicSpotId;

    private String infraredTitle;

    private String infraredContent;

    private String infraredEnableType;

    private String type;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}