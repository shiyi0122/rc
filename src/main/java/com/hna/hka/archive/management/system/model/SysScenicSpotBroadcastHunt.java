package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotBroadcastHunt {

        private Long broadcastId;

        //景区ID
        private Long scenicSpotId;

        @Excel(name = "WGS84坐标",width = 20,orderNum = "2")
        private String broadcastGps;
        @Excel(name = "百度坐标" , width = 20,orderNum = "3")
        private String broadcastGpsBaiDu;

        //坐标半径默认值为5米
        private String scenicSpotRange;

        //景点名称
        private String broadcastName;

        //景区名称拼音格式
        private String pinYinName;

        //介绍类型 默认值1为景点介绍类型
        private String introductionTypes;

        //导航类型 默认值2为导航类型
        private String navigationType;

        //'播报优先级设置
        private String broadcastPriority;

        //0 景点类型 1停靠点类型 （排序用）
        private String sortType;

        //寻宝开关  0关闭 1开启 默认0
        private String switchs;

        private String createDate;

        private String updateDate;
        /**
         * 景区名称
         */
        @Excel(name = "景区名称",width = 20,orderNum = "0")
        private String scenicSpotName;

        //0默认为无宝箱类型 1为玲龙宝箱  2为锦龙宝箱 3为祥龙宝箱
        private String treasureType;

        //宝箱里的金币积分
        private Long integralNum;

        private Integer pageSize;
        private Integer pageNum;
}
