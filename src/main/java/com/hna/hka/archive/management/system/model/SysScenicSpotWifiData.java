package com.hna.hka.archive.management.system.model;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/19 11:15
 * WIFI探针数据统计
 */
@Data
public class SysScenicSpotWifiData {

   private String  wifiId;

   private String mac;

   private String rssi;

   private String rssiOne;

   private String   rssiTwo;

   private String rssiThree;

   private String tmc;

   private String router;

   private String ranges;

   private String createDate;

   private String updateDate;




}
