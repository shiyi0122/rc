package com.hna.hka.archive.management.assetsSystem.model;

import lombok.Data;

@Data
public class SysRobotErrorParts {

    //id
    private Long errorRepairManagementId;
    // 故障表id
    private Long    errorRecordsId;
    //配件id
    private Long partsManagementId;
    //创建时间
    private String  createDate;
    //修改时间
    private String  updateDate ;
    //数量
    private Double quantity;
//金额
    private Double amount;

    private String inventoryNumber;

    private String  accessoryName;

    private String  accessoryModel;

    private String  accessoriesCode;

    private String  accessoryPriceOut;

    private String unit;

}
