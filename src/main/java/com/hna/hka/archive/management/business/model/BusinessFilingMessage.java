package com.hna.hka.archive.management.business.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/21 15:52
 */
@Data
public class BusinessFilingMessage {
    @Excel(name = "id" , orderNum = "1" ,width = 20)
    private Long id;
    //报备人id
    private Long  filingId;
    //报备人名称
    @Excel(name = "报备人姓名" , orderNum = "1" ,width = 20)
    private String filingName;
    //报备人手机号
    @Excel(name = "报备人手机号" , orderNum = "2" ,width = 20)
    private String  filingPhone;
    //报备区域
    @Excel(name = "报备区域" , orderNum = "3" ,width = 20)
    private String  filingRegion;
    //报备周期
    @Excel(name = "报备周期" , orderNum = "4" ,width = 20)
    private String  filingCycle;
    //报备状态
    @Excel(name = "报备状态" ,replace = {"未提交_0" , "已提交_1"},orderNum = "5" ,width = 20)
    private String  filingState;
    //报备景区名称
    @Excel(name = "报备景区名称" , orderNum = "6" ,width = 20)
    private String  filingSpotName;
    //报备景区联系人
    @Excel(name = "报备景区联系人" , orderNum = "7" ,width = 20)
    private String  filingSpotContacts;
    //报备景区联系人手机号
    @Excel(name = "报备景区联系人手机号" , orderNum = "8" ,width = 20)
    private String  filingSpotContactsPhone;
    //报备日期
    @Excel(name = "报备日期" , orderNum = "9" ,width = 20)
    private String  filingTime;

    //职位
    @Excel(name = "景区联系人职位" , orderNum = "9" ,width = 20)
    private String  filingSpotContactsPosition;

    //机器人数量
    @Excel(name = "预计投放机器人数量" , orderNum = "9" ,width = 20)
    private String  filingRobotCount;

    //景区归属地
    @Excel(name = "景区归属地" , orderNum = "10" ,width = 20)
    private String  filingSpotCity;
    //星级
    @Excel(name = "景区类型"  , replace = {"市民公园_0" , "A_1","AA_2","AAA_3","AAAA_4","AAAAA_5"}, orderNum = "11" ,width = 20)
    private String  filingSpotStarLevel;
    //报备景区面积
    @Excel(name = "报备景区面积" , orderNum = "12" ,width = 20)
    private String   filingSpotMeasure;
    //年度人流量
    @Excel(name = "景区年度有效客流(万人)" , orderNum = "12" ,width = 20)
    private String  filingSpotVisitorsFlowrate;
    //备注
    @Excel(name = "备注" , orderNum = "13" ,width = 20)
    private String  remarks;
    //审核人id
    private String  approvalId;
    //审核人名称
    private String  approvalName;
    //报备失效期
    @Excel(name = "报备失效期" , orderNum = "13" ,width = 20)
    private String  signingTime;
    //审核结果（2通过3驳回4待审核5已失效6已签约）
    private String  findingsOfAudit;
    //审核日期
    private String  auditDate;
    //原因
    private String  reson;
    //创建时间
    private String  createTime;
    //修改时间
    private String  updateTime;
    //是否签约（6未签约，7 已签约）
    private String  isSigning;
    //签约日期
    private String  signingTimeDetermine;
    //签约失效日期
    @Excel(name = "签约失效期" , orderNum = "14" ,width = 20)
    private String  signingTimeDetermineInvalid;

    /**
     * 主管报备人姓名
     */
    @Excel(name = "主管报备人姓名" , orderNum = "15" ,width = 20)
    private String executiveDirectorName ;

    /**
     * 主管报备人手机号
     */
    @Excel(name = "主管报备人手机号" , orderNum = "16" ,width = 20)
    private String executiveDirectorPhone;

    /**
     * 路况
     */
    @Excel(name = "路况" , replace = {"无_0" , "封闭式仅行人_1","开放式人车混行_2"}, orderNum = "17" ,width = 20)
    private String traffic;

    /**
     * 是否失效
     */
    @Excel(name = "是否失效" , replace = {"未失效_0" , "已失效_1"} ,orderNum = "18" ,width = 20)
    private String state;

    /**
     * 路况1，封闭似仅行人2,,开发式人车混行
     */
//    @Excel(name = "路况", replace = {"封闭式仅行人_1" , "开发式人车混行_2"},orderNum = "19" ,width = 20)
    private String roadConditions;

    /**
     * 路面1沥青路2混凝土路3石板路4碎石路5透水路
     */
    @Excel(name = "路面" ,replace = {"沥青路_1" , "混凝土路_2","石板路_3","碎石路_4","透水路_5","沥青路,混凝土路_1,2","沥青路,石板路_1,3","沥青路,碎石路_1,4","沥青路,透水路_1,5","混凝土路,石板路_2,3","混凝土路,碎石路_2,4","混凝土路,透水路_2,5","石板路,碎石路_3,4","石板路,透水路_3,5","碎石路,透水路_4,5","沥青路,混凝土路,石板路_1,2,3","沥青路,混凝土路,碎石路_1,2,4","沥青路,混凝土路,透水路_1,2,5","沥青路,石板路,碎石路_1,3,4","沥青路,石板路,透水路_1,3,5","沥青路,碎石路,透水路_1,4,5","混凝土路,石板路,碎石路_2,3,4","混凝土路,石板路,透水路_2,3,5","石板路,碎石路,透水路_3,4,5","沥青路,混凝土路,石板路,碎石路_1,2,3,4","沥青路,混凝土路,石板路,透水路_1,2,3,5","沥青路,混凝土路,碎石路,透水路_1,2,4,5","沥青路,石板路,碎石路,透水路_1,3,4,5","混凝土路,石板路,碎石路,透水路_2,3,4,5"}  ,orderNum = "19" ,width = 20)
    private String  pavement;

    /**
     * 路宽1,2米以下，2，2-3米，3，3-4米，4，4-5米，5，5米以上
     */
    @Excel(name = "路宽",replace = {"2米以下_1" , "2-3米_2","3-4米_3","4-5米_4","5米以上_5"}, orderNum = "20" ,width = 20)
    private String roadWidth;

    /**
     * 机器人可运行线路（公里）
     */
    @Excel(name = "机器人可运行线路（公里）" , orderNum = "21" ,width = 20)
    private String robotLinesInOperation;

    /**
     * 景区交通工具0无，1脚踏车2小型电动车3游览摆渡车
     */
    @Excel(name = "景区交通工具" ,replace = {"脚踏车_1" , "小型电动车_2","游览摆渡车_3","脚踏车,小型电动车_1,2","脚踏车,游览摆渡车_1,3","小型电动车,游览摆渡车_2,3","脚踏车,小型电动车,游览摆渡车_1,2,3","无_0"}, orderNum = "22" ,width = 20)
    private String vehicle;

    /**
     * 客流组成占比
     */
    @Excel(name = "客流组成占比" , orderNum = "23" ,width = 20)
    private String proportionOfPassengerFlow;


    /**
     * 修改后报备景区名称
     */
    private String filingSpotNameChange;

    /**
     * 报备景区父id表id
     */
    private Long spotRelationshipId;


    /**
     *  是否优质景区，1是2不是
     */
    private String isHighQualitySpot;

    /**
     * 报备状态1自主报备，2代人报备
     */
    private String reportingStatus;


    /**
     * 提交人id
     */
    private Long  submitId;


    /**
     * 待播报人
     */
    private Long  filingAgentId;

    /**
     * 对接单位名称
     */
    @Excel(name = "对接单位名称" , orderNum = "24" ,width = 20)
    private String  dockingUnitName;

    /**
     * 签约主体名称
     */
    @Excel(name = "签约主体名称" , orderNum = "25" ,width = 20)
    private String  signatoryName;

    /**
     * 对接时间
     */
    @Excel(name = "对接时间" , orderNum = "26" ,width = 20)
    private String   dockingDate;

    /**
     * 对接人
     */
    @Excel(name = "对接人" , orderNum = "27" ,width = 20)
    private String  dockingName;

    /**
     * 对接人职位
     */
    @Excel(name = "对接人职位" , orderNum = "28" ,width = 20)
    private String  dockingPosition;

    /**
     * 对接结果
     */
    @Excel(name = "对接结果" , orderNum = "29" ,width = 20)
    private String  dockingResult;

    /**
     * 对接地点
     */
    @Excel(name = "对接地点" , orderNum = "30" ,width = 20)
    private String  dockingPlace;
    /**
     * 理由
     */
    @Excel(name = "延期理由" , orderNum = "31" ,width = 20)
    private String  reason;





    //数据表中无此字段

    /**
     * 延期天数
     */
    @Excel(name = "延期天数" , orderNum = "32" ,width = 20)
    private String days;
    //省
    private String province;
    //市
    private String city;
    //区
    private String county;




}
