package com.hna.hka.archive.management.system.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysCurrentUserExchange {


    private Long exchangeId;

    private Long userId;

    @Excel(name = "用户手机号",width = 20,orderNum = "1")
    private String userPhone;

    //兑换编号
    @Excel(name = "兑换编号",width = 20,orderNum = "2")
    private String exchangeNumber;

    //景区名称
    @Excel(name = "景区名称",width = 20,orderNum = "3")
    private String scenicSpotName;

    //关联景区ID
    private Long scenicSpotId;

    //0未填写地址 1地址已提交 2已发货 3已收货
    @Excel(name = "发货状态",width = 20,orderNum = "4")
    private String shipmentStatus;

    //奖品图片地址
    private String picUrl;

    //兑换状态 0 待兑换 1已核销 2 已过期 默认0
    @Excel(name = "兑换状态",width = 20,orderNum = "5")
    private String exchangeState;

    //奖品名称
    @Excel(name = "奖品名称",width = 20,orderNum = "6")
    private String exchangeName;

    //开始兑换时间
    @Excel(name = "开始兑换时间",width = 20,orderNum = "7")
    private String startValidity;

    //结束兑换时间
    @Excel(name = "结束兑换时间",width = 20,orderNum = "8")
    private String endValidity;

    //宝箱类型 4寻宝宝箱 5随机宝箱，默认值4
    @Excel(name = "宝箱类型",width = 20,orderNum = "9")
    private String exchangeType;

    //地址Id
    private String addressId;

    //物流Id
    private String logisiticsId;

    private String createDate;

    private String updateDate;
    private String userPhone1;

    @Excel(name = "收货地址",width = 20,orderNum = "13")
    private String mailingAddress;
    @Excel(name = "收货人手机号",width = 20,orderNum = "12")
    private String mailingPhone;
    @Excel(name = "收货人",width = 20,orderNum = "11")
    private String fullName;

    private Integer pageNum;
    private Integer pageSize;

    //0未选择兑换方式  1邮寄方式 2现场兑换方式
    @Excel(name = "兑换方式",width = 20,orderNum = "10")
    private String wayType;
}
