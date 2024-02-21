package com.hna.hka.archive.management.assetsSystem.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import lombok.Data;

import java.util.List;

@Data
public class SysRobotAccessoriesApplication {

    private Long accessoriesApplicationId;

    private Long scenicSpotId;

    private Long accessoriesId;
    @Excel(name = "配件名称",orderNum = "1",width = 20)
    private String accessoriesName;

    private String accessoriesType;

    private String accessoriesImg;

    @Excel(name = "配件价格/元",orderNum = "2",width = 20)
    private String accessoryPrice;

    @Excel(name = "配件型号",orderNum = "3",width = 20)
    private String accessoryModel;

    @Excel(name = "配件数量",orderNum = "4",width = 20)
    private String accessoryNumber;

    @Excel(name = "申请人",orderNum = "5",width = 20)
    private String applicant;

    private String applicantPhone;

    private String processingProgress;

    @Excel(name = "申请原因",orderNum = "8",width = 20)
    private String applicationReason;

    @Excel(name = "审批记录",orderNum = "9",width = 20)
    private String approvalRecord;

    private String accessoriesReceivedType;

    private String receivingAddress;

    @Excel(name = "备注",orderNum = "11",width = 20)
    private String remarks;

    @Excel(name = "申请时间",orderNum = "7",width = 20)
    private String createDate;

    private String updateDate;

    private String longinTokenId;

    @Excel(name = "申请景区",orderNum = "0",width = 20)
    private String scenicSpotName;

    @Excel(name = "处理进度",orderNum = "6",width = 20)
    private String processingProgressName;

    @Excel(name = "配件是否已收到",orderNum = "10",width = 20)
    private String accessoriesReceivedTypeName;

    //景区承担配件费
    private String spotPartsCost ;

    //是否核实0未核实，1已核实
    private String  isSettlement;

    //核实时间
    private String verificationTime;

    //核实人
    private Long  sysUserId;

    //发货库房
    private Long warehouseId;
    //发货说明
    private String shippingInstructions;
    //快递单号
    private String courierNumber;
    //快递费
    private String expressFee;
    //签收图片
    private String signInPicture;

    private String approvalProgress;

    //配件编码
    private String accessoriesCode;

    //仓库发货地址
    private String type;

    //配件详情列表
    private   String detailListN ;
    private List<SysRobotAccessoriesApplicationDetail> detailList;
}
