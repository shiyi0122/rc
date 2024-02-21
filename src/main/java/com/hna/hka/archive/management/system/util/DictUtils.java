package com.hna.hka.archive.management.system.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.util
 * @ClassName: DictUtils
 * @Author: 郭凯
 * @Description: 状态对应值
 * @Date: 2020/5/20 9:57
 * @Version: 1.0
 */
public class DictUtils {

    /** 机器人运行状态 **/
    private static Map<String, String> robotRunMap = new LinkedHashMap<String, String>();

    /** 机器人故障状态 **/
    private static Map<String, String> robotFaultStateMap = new LinkedHashMap<String, String>();

    /** 景区状态 **/
    private static Map<String, String> scenicSpotStateMap = new LinkedHashMap<String, String>();

    /** 订单状态 **/
    private static Map<String, String> orderStateMap = new LinkedHashMap<String, String>();

    /** 押金缴纳状态 **/
    private static Map<String, String> depositPayStateMap = new LinkedHashMap<String, String>();

    /** 提现申请状态 **/
    private static Map<String, String> withdrawalLogStateMap = new LinkedHashMap<String, String>();

    /** 信用欠款状态 **/
    private static Map<String, String> creditArrearsStateMap = new LinkedHashMap<String, String>();

    /** 黑名单状态 **/
    private static Map<String, String> blackListTypeMap = new LinkedHashMap<String, String>();
    
    /** 支付类型 **/
    private static Map<String, String> paymentMethodMap = new LinkedHashMap<String, String>();
    
    /** 处理状态 **/
    private static Map<String, String> handleStateMap = new LinkedHashMap<String, String>();
    
    /** 问题状态 **/
    private static Map<String, String> problemStatusMap = new LinkedHashMap<String, String>();

    /** 异常订单上报原因大类 **/
    private static Map<String, String> causesMap = new LinkedHashMap<String, String>();

    /** 订单提交状态 **/
    private static Map<String, String> subStatusMap = new LinkedHashMap<String, String>();

    /** 配件类型状态 **/
    private static Map<String, String> accessoriesTypeMap = new LinkedHashMap<String, String>();

    /** 发票类型状态 **/
    private static Map<String, String> invoiceTypeMap = new LinkedHashMap<String, String>();

    /** 抬头类型状态 **/
    private static Map<String, String> riseTypeMap = new LinkedHashMap<String, String>();

    /** 发票处理进度状态 **/
    private static Map<String, String> processingProgressMap = new LinkedHashMap<String, String>();

    /** 处理进度状态 **/
    private static Map<String, String> accessoryProcessingProgressMap = new LinkedHashMap<String, String>();

    /** 配件是否已收到状态 **/
    private static Map<String, String> accessoriesReceivedTypeMap = new LinkedHashMap<String, String>();

    /** 故障审批状态 **/
    private static Map<String, String> errorRecordsApproveMap = new LinkedHashMap<String, String>();

    /** 配件审批状态 **/
    private static Map<String, String> applicantProcessingProgressMap = new LinkedHashMap<String, String>();

    static {
        /** 机器人运行状态 **/
        robotRunMap.put("10", "闲置");
        robotRunMap.put("20", "用户解锁");
        robotRunMap.put("30", "用户临时锁定");
        robotRunMap.put("40", "管理员启动");
        robotRunMap.put("50", "管理员停止");
        robotRunMap.put("60", "自检故障报警");
        robotRunMap.put("70", "扫码解锁中");
        robotRunMap.put("80", "运营人员钥匙解锁");
        robotRunMap.put("90", "运营人员维护");
        robotRunMap.put("100", "禁区锁定");
//        robotRunMap.put("110","维修");

        /** 机器人故障状态 **/
        robotFaultStateMap.put("10", "正常");
        robotFaultStateMap.put("20", "保修");
        robotFaultStateMap.put("30", "故障");
        robotFaultStateMap.put("40","维修");
        robotFaultStateMap.put("60","维护");

        /** 景区状态 **/
        scenicSpotStateMap.put("1", "已运营景区");
        scenicSpotStateMap.put("2", "测试景区");
        scenicSpotStateMap.put("3", "预运营景区");
        scenicSpotStateMap.put("4", "停运景区");

        /** 订单状态 **/
        orderStateMap.put("10", "进行中");
        orderStateMap.put("20", "未付款");
        orderStateMap.put("30", "已付款");
        orderStateMap.put("40", "交易关闭");
        orderStateMap.put("50", "免单");
        orderStateMap.put("60", "全额退款");

        /** 押金缴纳状态 **/
        depositPayStateMap.put("10", "已缴纳押金");
        depositPayStateMap.put("20", "未缴纳押金");

        /** 提现申请状态 **/
        withdrawalLogStateMap.put("0", "申请中");
        withdrawalLogStateMap.put("1", "同意");
        withdrawalLogStateMap.put("2", "驳回");

        /** 信用欠款状态 **/
        creditArrearsStateMap.put("10", "无欠款");
        creditArrearsStateMap.put("20", "欠款");

        /** 黑名单状态 **/
        blackListTypeMap.put("1", "黑名单");
        blackListTypeMap.put("0", "白名单");
        
        /** 支付类型 **/
        paymentMethodMap.put("0", "微信支付");
        paymentMethodMap.put("1", "储值支付");
        paymentMethodMap.put("2", "储值抵扣");
        
        /** 处理状态 **/
        handleStateMap.put("1", "未处理");
        handleStateMap.put("2", "已处理");
        
        /** 问题状态 **/
        problemStatusMap.put("0", "未知问题");
        problemStatusMap.put("1", "已知问题");

        /** 异常订单上报原因大类 **/
        causesMap.put("10", "常见故障");
        causesMap.put("20", "硬件故障");
        causesMap.put("30", "软件故障");
        causesMap.put("40", "非故障");

        /** 订单提交状态 **/
        subStatusMap.put("1", "未提交");
        subStatusMap.put("2", "已提交");

        /** 配件类型状态 **/
        accessoriesTypeMap.put("10", "电气类");
        accessoriesTypeMap.put("20", "机械类");
        accessoriesTypeMap.put("30", "易损易耗");
        accessoriesTypeMap.put("40", "工具类");
        accessoriesTypeMap.put("50", "仪器类");
        accessoriesTypeMap.put("60", "其他");

        /** 发票类型状态 **/
        invoiceTypeMap.put("1", "增值税电子普通发票");
        invoiceTypeMap.put("0", "增值税专业发票");

        /** 抬头类型状态 **/
        riseTypeMap.put("1", "个人");
        riseTypeMap.put("0", "企业");

        /** 发票处理进度状态 **/
        processingProgressMap.put("1", "已开具发票");
        processingProgressMap.put("0", "已申请发票");

        /** 处理进度状态 **/
        accessoryProcessingProgressMap.put("1", "已申请");
        accessoryProcessingProgressMap.put("2", "已付费");
        accessoryProcessingProgressMap.put("3", "已付费未寄出");
        accessoryProcessingProgressMap.put("4", "未付费已寄出");
        accessoryProcessingProgressMap.put("5", "已下单");
        accessoryProcessingProgressMap.put("6", "已发货");
        accessoryProcessingProgressMap.put("7", "已签收");
        accessoryProcessingProgressMap.put("8", "已关闭");

        /** 配件是否已收到状态 **/
        accessoriesReceivedTypeMap.put("1", "收到");
        accessoriesReceivedTypeMap.put("2", "未收到");

        /** 故障审批状态 **/
        errorRecordsApproveMap.put("0", "不同意");
        errorRecordsApproveMap.put("1", "同意");

        /** 配件审批状态 **/
        applicantProcessingProgressMap.put("0", "审批中");
        applicantProcessingProgressMap.put("1", "同意");
        applicantProcessingProgressMap.put("2", "不同意");
    }

    /** 机器人运行状态 **/
    public static Map<String, String> getRobotRunMap() {
        return robotRunMap;
    }

    /** 机器人故障状态 **/
    public static Map<String, String> getRobotFaultStateMap() {
        return robotFaultStateMap;
    }

    /** 景区状态 **/
    public static Map<String, String> getScenicSpotStateMap() {
        return scenicSpotStateMap;
    }

    /** 订单状态 **/
    public static Map<String, String> getOrderStateMap() {
        return orderStateMap;
    }

    /** 押金缴纳状态 **/
    public static Map<String, String> getDepositPayStateMap() {
        return depositPayStateMap;
    }

    /** 提现申请状态 **/
    public static Map<String, String> getWithdrawalLogStateMap() {
        return withdrawalLogStateMap;
    }

    /** 信用欠款状态 **/
    public static Map<String, String> getCreditArrearsStateMap() {
        return creditArrearsStateMap;
    }

    /** 黑名单状态 **/
    public static Map<String, String> getBlackListTypeMap() {
        return blackListTypeMap;
    }
    
    /** 支付类型 **/
    public static Map<String, String> getPaymentMethodMap() {
    	return paymentMethodMap;
    }
    
    /** 处理状态 **/
    public static Map<String, String> getHandleStateMap() {
    	return handleStateMap;
    }
    
    /** 问题状态 **/
    public static Map<String, String> getProblemStatusMap() {
    	return problemStatusMap;
    }

    /** 异常订单上报原因大类 **/
    public static Map<String, String> getCausesMap() {
        return causesMap;
    }

    /** 订单提交状态 **/
    public static Map<String, String> getSubStatusMap() {
        return subStatusMap;
    }

    /** 配件类型状态 **/
    public static Map<String, String> getAccessoriesTypeMap() {
        return accessoriesTypeMap;
    }

    /** 发票类型状态 **/
    public static Map<String, String> getInvoiceTypeMap() {
        return invoiceTypeMap;
    }

    /** 发票类型状态 **/
    public static Map<String, String> getRiseTypeMapMap() {
        return riseTypeMap;
    }

    /** 发票处理进度状态 **/
    public static Map<String, String> getProcessingProgressMap() {
        return processingProgressMap;
    }

    /** 处理进度状态 **/
    public static Map<String, String> getAccessoryProcessingProgressMap() {
        return accessoryProcessingProgressMap;
    }
    /** 配件是否已收到状态 **/
    public static Map<String, String> getAccessoriesReceivedTypeMap() {
        return accessoriesReceivedTypeMap;
    }
    /** 故障审批状态 **/
    public static Map<String, String> getErrorRecordsApproveMap() {
        return errorRecordsApproveMap;
    }
    /** 配件审批状态 **/
    public static Map<String, String> getApplicantProcessingProgressMap() {
        return applicantProcessingProgressMap;
    }

}
