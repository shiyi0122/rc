package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysRobotSoftAssetInformationExtendVo {


    @Excel(name = "出厂日期")
    private String dateProduction;

    @Excel(name = "电池质保截止日期")
    private String batteryWarrantyDeadline;

    @Excel(name = "机器人质保截止日期")
    private String robotWarrantyDeadline;


    @Excel(name = "使用年限")
    private String serviceLife;

    @Excel(name = "出厂成本")
    private String factoryCost;

    @Excel(name = "累积折旧")
    private Double accumulate;

    @Excel(name = "净值")
    private Double netPrice;


    //机器人编号
    @Excel(name = "机器人编号")
    private String robotCode;

    @Excel(name= "归属公司名称")
    private String ascriptionCompanyName;




}