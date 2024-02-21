package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/6/27 16:37
 */
@Data
public class SysAppFlowPathSpot {

    private Long id ;

    private Long flowPathId;

    private Long  scenicSpotId;

    private String scenicSpotName;

    private String createTime;

    private String updateTime;



}
