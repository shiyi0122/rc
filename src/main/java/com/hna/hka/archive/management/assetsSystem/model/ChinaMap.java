package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.model
 * @ClassName: ChinaMap
 * @Author: 郭凯
 * @Description: 景区大数据地图实体
 * @Date: 2021/5/31 17:56
 * @Version: 1.0
 */
@Data
public class ChinaMap {

    //省份ID
    private String id;

    //省份名称
    private String name;

    //订单金额
    private Integer orderAmount;

    //运营时长
    private Integer operateTime;
}
