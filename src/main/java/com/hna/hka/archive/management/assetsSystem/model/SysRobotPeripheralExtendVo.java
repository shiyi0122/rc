package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/1 15:12
 */

@Data
public class SysRobotPeripheralExtendVo {

    //外设id

//    @Excel(name = "外设id")
    private Long robotPeripheralId ;
    @Excel(name = "外设名称")
    private  String peripheralName;
    @Excel(name = "外设单价/元")
    private  String peripheralPrice;
    @Excel(name = "外设数量")
    private String  peripheralQuantity;
    @Excel(name = "外设单位")
    private String  peripheralCompany;
    @Excel(name = "备注")
    private String  remarks;
    @Excel(name = "创建时间")
    private String createDate;
    @Excel(name = "景区名称")
    private String scenicSpotName;


}
