package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotTreasureHuntRule {

        private Long ruleId;

        private Long scenicSpotId;

        private String ruleContent;

        private String ruleUrl;

        private String ruleType;

        private String createDate;

        private String updateDate;
        /**
         * 景区名称
         */
        @Excel(name = "景区名称",width = 20,orderNum = "0")
        private String scenicSpotName;

        //寻宝开机播报规则内容
        private String ruleContents;
}
