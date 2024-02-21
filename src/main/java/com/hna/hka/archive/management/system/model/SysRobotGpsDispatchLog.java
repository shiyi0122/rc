package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/30 13:17
 */
@Data
public class SysRobotGpsDispatchLog {

   private Long gpsId;

   private String robotCode;

   private Long scenicSpotId;

   private String scenicSpotName;

   private Long   exceedingTime;

   private String reason;

   private String createDate;

   private String updateDate;



}
