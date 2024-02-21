package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: SysScenicSpotBroadcastExtendVo
 * @Author: 郭凯
 * @Description: 景点导入Excel表实体
 * @Date: 2021/4/6 13:15
 * @Version: 1.0
 */
@Data
public class SysScenicSpotBroadcastExtendVo {

    @Excel(name = "景点名称")
    private String broadcastName;

    @Excel(name = "WGS84坐标")
    private String broadcastGps;

    @Excel(name = "百度坐标")
    private String broadcastGpsBaiDu;

    //0默认为无宝箱类型 1为玲龙宝箱  2为锦龙宝箱 3为祥龙宝箱
    @Excel(name = "宝箱类型")
    private String treasureType;

    //宝箱里的金币积分
    @Excel(name = "金币积分数量")
    private Long integralNum;

    //坐标半径默认值为5米
    @Excel(name = "坐标缓冲半径")
    private String scenicSpotRange;
}
