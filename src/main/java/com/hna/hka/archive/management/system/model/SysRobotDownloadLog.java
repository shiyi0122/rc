package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/2/25 9:35
 */

@Data
public class SysRobotDownloadLog {

    private Long logId;

    private String robotCode;

    private Long  scenicSpotId;

    private String  downloadUrl;

    private String  createDate;

    private String  updateDate;

    private String  size;

    private String  remarks;

    private String  scenicSpotName  ;

}

