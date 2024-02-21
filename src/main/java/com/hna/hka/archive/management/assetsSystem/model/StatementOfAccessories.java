package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/4/20 14:59
 */
@Data
public class StatementOfAccessories {

    private Long   errorRecordsId;
    //类型
    @Excel(name = "类型" ,replace = {"备件申请_1" , "故障上报_2"},orderNum = "1")
    private String  type;
    //景区id
    private Long scenicSpotId;
    //景区名称
    @Excel(name = "景区名称" , width = 20,orderNum = "2")
    private String scenicSpotName ;
    //申请人
    private Long applyUserId;
    //申请人名称
    @Excel(name = "申请人名称" , width = 20,orderNum = "3")
    private String userName;
    //申请时间
    @Excel(name = "申请时间" , width = 20,orderNum = "4")
    private String applyTime;
    //确认时间
    @Excel(name = "确认时间" , width = 20,orderNum = "5")
    private String serviceRecordsServiceDate;
    //配件名称
    @Excel(name = "配件名称" , width = 20,orderNum = "6")
    private String accessoryName;
    //配件数量
    @Excel(name = "配件数量" , width = 20,orderNum = "7")
    private String errorRecordsNumber;
    //配件价格
    @Excel(name = "配件价格" , width = 20,orderNum = "8")
    private String errorRecordsPartPrice;
   // 是否运营不当造成
   @Excel(name = "是否运营不当造成" ,replace = {"不是_0" , "是_1"},orderNum = "9")
   private String improperOperation;
    //配件是否收到
    @Excel(name = "配件是否收到" ,replace = {"未收到_0" , "收到_1"},orderNum = "10")
    private String errorRecordsReceive;
    //是否更换配件
    @Excel(name = "是否更换配件" ,replace = {"不需要_0" , "需要_1"},orderNum = "11")
    private String errorRecordsReplace;
    //维修费用
    @Excel(name = "维修费用" , width = 20,orderNum = "12")
    private String errorRecordsUpkeepCost;
    //维修结果 1未维修已派单；2已修好，远超协助;3已判断问题，需要配件;4未修好，需要售后现场解决;5已修好，售后现场解决;6已确认修好；7已关闭;
    @Excel(name = "维修结果" ,replace = {"未维修已派单_1" , "已修好，远超协助_2","已判断问题，需要配件_3","未修好，需要售后现场解决_4","已修好，售后现场解决_5","已确认修好_6","已关闭_7"},orderNum = "13")
    private String serviceRecordsResult;
    //景区承担配件费用
    @Excel(name = "景区承担配件费用" , width = 20,orderNum = "14")
    private String spotPartsCost;
    //景区承担维修费用
    @Excel(name = "景区承担维修费用" , width = 20,orderNum = "15")
    private String spotRepairCost;

    //总配件价格
    private Double accessoryPrice;

    @Excel(name = "是否结算" ,replace = {"未结算_0" , "已结算_1"}, width = 20,orderNum = "16")
    private String isSettlement;






}

