package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/3/1 15:12
 */

@Data
public class SysRobotPeripheral {

    //外设id
    @ApiModelProperty("外设id")
//    @Excel(name = "外设id" ,width = 15,orderNum = "6")
    private  Long  robotPeripheralId;

    @ApiModelProperty("景区id")
    private  Long  scenicSpotId;
    @Excel(name = "外设名称" , width = 15 , orderNum = "1")
    @ApiModelProperty("外设名称")
    private  String peripheralName;
    @Excel(name = "外设单价/元" , width = 15 , orderNum = "2")
    @ApiModelProperty("外设单价/元")
    private  String peripheralPrice;
    @Excel(name = "外设数量" , width = 15 , orderNum = "2")
    @ApiModelProperty("外设数量")
    private String  peripheralQuantity;
    @Excel(name = "外设单位" , width = 15 , orderNum = "3")
    @ApiModelProperty("外设单位")
    private String  peripheralCompany;
    @Excel(name = "备注" , width = 15 , orderNum = "4")
    @ApiModelProperty("备注")
    private String  remarks;
    @Excel(name = "创建时间" , width = 15 , orderNum = "5")
    @ApiModelProperty("创建时间")
    private String createDate;

    @ApiModelProperty("修改时间")
    private String updateDate;
    @Excel(name = "景区名称" , width = 15 , orderNum = "0")
    @ApiModelProperty("景区名称")
    private String scenicSpotName;


}
