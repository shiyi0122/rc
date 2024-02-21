package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/8 13:52
 */
@Data
public class SysRobotPadNewSpot {

    private Long id;

    private Long padId;

    private Long scenicSpotId;

    private String createDate;

    private String updateDate;

    private String type;


}
