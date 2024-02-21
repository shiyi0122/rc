package com.hna.hka.archive.management.assetsSystem.model;

import com.hna.hka.archive.management.appSystem.model.SysRobotAccessoriesApplicationDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysRobotErrorRecords {

    //机器人故障ID
    private Long errorRecordsId;

    //景区外键ID
    private Long scenicSpotId;

    //机器人唯一编号
    private String robotCode;

    //机器人型号
    private String errorRecordsModel;

    //故障单号
    private String errorRecordsNo;

    //是否影响使用:0不影响使用  1影响使用
    private String errorRecordsAffect;

    //维修结果:0未维修；1未维修已派单；2已修好，远超协助; 3已判断问题，需要配件; 4未修好，需要售后现场解决; 5已修好，售后现场解决;6已确认修好；7已关闭;
    private String errorRecordsStatus;

    //上报来源: 1设备自检上报故障; 2管理员APP订单页上报损坏; 3管理员APP上报故障; 4用户在小程序页上报故障;
    private String errorRecordsReportSource;

    //故障类型
    private String errorRecordsType;

    //故障名称
    private String errorRecordsName;

    //故障描述
    private String errorRecordsDescription;

    //故障图片
    private String errorRecordsPic;

    //是否需要发配件: 0不需要; 1需要
    private String errorRecordsPart;

    //收件地址
    private String errorRecordsAddress;

    //配件是否已发送: 0未发送 1已发送
    private String errorRecordsSend;

    //配件是否收到: 0未收到； 1已收到
    private String errorRecordsReceive;

    //故障上报时间
    private String errorRecordsDate;

    //上报人员
    private String errorRecordsPersonnel;

    //上报电话
    private String errorRecordsTel;

    //关联订单
    private String errorRecordsOrderNo;

    //是否质保:0不质保或已过质保期;1在质保期内
    private String errorRecordsQuality;

    //审批人
    private String errorRecordsAppRover;

    //审批结果:0不同意；1同意； 2结束此流程
    private String errorRecordsApprove;

    //审批意见
    private String errorRecordsOpinion;

    //已更换配件:0未更换配件；1已更换配件
    private String errorRecordsReplace;

    //旧配件ID
    private String errorRecordsOldId;

    //新配件ID
    private String errorRecordsNewId;

    //配件来源:1新收到的配件;2园区库房配件
    private String errorRecordsSource;

    //配件价格/元
    private String errorRecordsPartPrice;

    //配件费用承担方
    private String errorRecordsPartBearer;

    //维修费用/元
    private String errorRecordsUpkeepCost;

    //维修费用承担方
    private String errorRecordsUpkeepBearer;

    //备注
    private String errorRecordsRemark;

    //是否已定损:0未定损;1定损
    private String errorRecordsLoss;

    //数据是否删除，0为删除，1为正常
    private String errorRecordsDelete;

    //创建时间
    private String createTime;

    //更新时间
    private String updateTime;

    //操作不当
    private String improperOperation;
    //配件数量
    private String errorRecordsNumber;
    //景区承担配件费用
    private String spotPartsCost;
    //景区承担维修费用
    private String spotRepairCost;
    //是否核实（0未核实，1以核实）
    private String isSettlement;
    //审核人id
    private Long sysUserId;
    //核实时间
    private String verificationTime ;
    //景区名称
    private String scenicSpotName;

    //维修ID
    private String repairId;


    //用户ID
    private String userId;

    //故障状态
    private String faultStatus;
    //流程id
    private Long flowPathId ;
    //审核位置
    private String flowPathType;

//    @ApiModelProperty("配件详情")
//    private SysRobotErrorParts[] details;
    @ApiModelProperty("配件详情新")
    private String detailsN;
    @ApiModelProperty("配件详情")
    private List<SysRobotErrorParts> details;
    @ApiModelProperty("配件详情(新)")
    private List<SysRobotAccessoriesApplicationDetail> detailList;

    @ApiModelProperty("审核详情")
    private  List<SysAppFlowPathDetails> appFlowPathDetailsList;


    @ApiModelProperty("添加库存详情")
    private String inventoryDetailList;


    //当前审核人员id
    private Long currentFlowPathId;

    //维修详情
    private String serviceRecordsDetails;
    //维修评价
    private String serviceRecordsLevel;
    //维修人员
    private String serviceRecordsPersonnel;

    private String robotModel;

    private String inventoryNumber;

    private Long goodsId;

    private String unit;

    private String goodsCode;

    //0不同意 1第一个审批人同意 2第二个审批人同意 3第三个审批人同意
    private Long agree;




}
