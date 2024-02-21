package com.hna.hka.archive.management.appSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRobotSoftAssetInformation {
    private Long softAssetInformationId;

    private Long robotId;
    @ApiModelProperty("出厂日期")
    @Excel(name = "出厂日期",width = 10 ,orderNum = "5")
    private String dateProduction;
    @ApiModelProperty("电池质保截止日期")
    @Excel(name = "电池质保截止日期",width = 10 ,orderNum = "6")
    private String batteryWarrantyDeadline;
    @ApiModelProperty("机器人质保截止日期")
    @Excel(name = "机器人质保截止日期",width = 10 ,orderNum = "7")
    private String robotWarrantyDeadline;
    @ApiModelProperty("创建时间")
    private String createDate;
    @ApiModelProperty("更新时间")
    @Excel(name = "更新时间",width = 10 ,orderNum = "8")
    private String updateDate;
//    @ApiModelProperty("成本")
//    @Excel(name = "成本",width = 10 ,orderNum = "9")
//    private String cost;
    @ApiModelProperty("使用年限")
    @Excel(name = "使用年限",width = 10 ,orderNum = "10")
    private String serviceLife;
    @ApiModelProperty("出厂成本")
    @Excel(name = "出厂成本",width = 10 ,orderNum = "11")
    private String factoryCost;
    @ApiModelProperty("累积折旧")
    @Excel(name = "累积折旧",width = 10 ,orderNum = "12")
    private Double accumulate;
    @ApiModelProperty("净值")
    @Excel(name = "净值",width = 10 ,orderNum = "13")
    private Double netPrice;


    //机器人编号
    @ApiModelProperty("机器人编号")
    @Excel(name = "机器人编号",width = 10 ,orderNum = "0")
    private String robotCode;

    //机器人型号
    @ApiModelProperty("机器人型号")
    @Excel(name = "机器人型号",width = 10 ,orderNum = "2")
    @JSONField(serialzeFeatures= SerializerFeature.WriteMapNullValue)
    private String robotModel;

    //景区ID
    @ApiModelProperty("景区id")
    private String scenicSpotId;

    //景区名称
    @ApiModelProperty("景区名称")
    @Excel(name = "景区名称",width = 10 ,orderNum = "3")
    private String scenicSpotName;

    //机器人版本号
    @ApiModelProperty("机器人版本号")
    @Excel(name = "机器人版本号",width = 10 ,orderNum = "1")
    private String clientVersion;

    //生产批次
    @ApiModelProperty("生产批次")
    @Excel(name = "生产批次",width = 10 ,orderNum = "4")
    private String robotBatchNumber;

    //生产批次
    @ApiModelProperty("故障状态")
    private String robotFaultState;

    //设备状态
    @ApiModelProperty("设备状态")
    @Excel(name = "设备状态",width = 10,orderNum = "14")
    private String  equipmentStatus ;

    //归属公司id
    @ApiModelProperty("归属公司id")
    private String ascriptionCompanyId;
    @Excel(name = "归属公司名称",width = 10,orderNum = "15")
    @ApiModelProperty("归属公司名称")
    private String ascriptionCompanyName;

    @ApiModelProperty("当天运行时间")
    private Integer dayTime;
    @ApiModelProperty("当月运行时间")
    private Integer monthTime;
    @ApiModelProperty("当年运行时间")
    private Integer yearTime;
    //运行时长
    @ApiModelProperty("总运行时长")
    private Integer runTime;


}