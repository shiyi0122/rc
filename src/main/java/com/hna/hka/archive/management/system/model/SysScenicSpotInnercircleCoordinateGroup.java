package com.hna.hka.archive.management.system.model;

import lombok.Data;

@Data
public class SysScenicSpotInnercircleCoordinateGroup {
    private Long coordinateInnercircleId;

    private Long coordinateId;

    private String coordinateInnercircleName;

    private String coordinateInnercircleContent;

    private String bufferRadius;

    private String innercircleType;

    private String createDate;

    private String updateDate;

    /**
     * 景区名称
     */
    private String scenicSpotName;

}