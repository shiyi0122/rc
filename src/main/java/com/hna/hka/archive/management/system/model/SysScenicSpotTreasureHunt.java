package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class SysScenicSpotTreasureHunt {

        private Long treasureId;

        private Long scenicSpotId;

        private String treasureName;

        private String treasureType;

        private String couponAmount;

        private String inventory;

        private String endValidity;

        private String startValidity;

        private String picUrl;

        private String prizeWeight;

        private String createDate;

        private String updateDate;

        private String prizeSize;
        /**
         * 景区名称
         */
        @Excel(name = "景区名称",width = 20,orderNum = "0")
        private String scenicSpotName;

        private Integer type;
}
