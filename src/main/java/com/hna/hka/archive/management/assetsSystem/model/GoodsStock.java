package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ExampleProperty;
import lombok.Data;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-20 15:43
 **/
@Data
@ApiModel("库存管理")
public class GoodsStock {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("配件id")
    private Long managementId;

    @ApiModelProperty("仓库id")
    private Long spotId;

    @ApiModelProperty("配件编码")
    @Excel(name = "配件编码" , width = 15 , orderNum = "2")
    private String code;

    @ApiModelProperty("配件名称")
    @Excel(name = "配件名称" , width = 15 , orderNum = "3")
    private String name;

    @ApiModelProperty("配件类型")
    private String model;

    @ApiModelProperty("单位")
    @Excel(name = "单位" , width = 15 , orderNum = "4")
    private String unit;

    @ApiModelProperty("仓库名称")
    @Excel(name = "仓库名称" , width = 15 , orderNum = "1")
    private String spotName;

    @ApiModelProperty("数量")
    @Excel(name = "数量" , width = 15 , orderNum = "5")
    private Long amount;

    @ApiModelProperty("阈值")
    @Excel(name = "阈值" , width = 15 , orderNum = "6")
    private Long threshold;

    @ApiModelProperty("备注")
    @Excel(name = "备注" , width = 15 , orderNum = "7")
    private String notes;

    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间" , width = 15 , orderNum = "8")
    private String updateTime;

    @ApiModelProperty("景区名称")
    private String  scenicSpotName;
}
