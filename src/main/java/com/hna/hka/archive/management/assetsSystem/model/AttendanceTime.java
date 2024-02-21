package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ClassName：AttendanceTime
 * @Author: gouteng
 * @Date: 2022-11-22 11:22
 * @Description: 必须描述类做什么事情, 实现什么功能
 */
@Data
public class AttendanceTime {
//    ID
    private Long id;
//    开始时间
    private String startDate;
//    结束时间
    private String endDate;
//    状态
    private String status;
//    创建人
    private String creatorName;
//    创建时间
    private String createDate;
//    更新时间
    private String updateDate;
//    开始年月日
    private String startSpecificDate;
//    结束年月日
    private String endSpecificDate;
}
