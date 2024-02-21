package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysOrderLogMapper;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.model.ExternalData.RefundOrderList;
import com.hna.hka.archive.management.system.model.SysOrder;
import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.service.SysOrderLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysOrderLogServiceImpl
 * @Author: 郭凯
 * @Description: 订单退款日志业务层（实现）
 * @Date: 2020/5/29 10:22
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOrderLogServiceImpl implements SysOrderLogService {

    @Autowired
    private SysOrderLogMapper sysOrderLogMapper;
    @Autowired
    private SysOrderMapper sysOrderMapper;

    /**
     * @Author 郭凯
     * @Description 订单退款日志列表查询
     * @Date 10:26 2020/5/29
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getOperationLogRefundOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrderLog> operationLogRefundOrderList = sysOrderLogMapper.getOperationLogRefundOrderList(search);
        if (operationLogRefundOrderList.size() != 0){
            PageInfo<SysOrderLog> pageInfo = new PageInfo<>(operationLogRefundOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 下载订单退款日志Excel表
     * @Date 13:24 2020/5/29
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysOrderLog>
    **/
    @Override
    public List<SysOrderLog> getUploadExcelOrderLog(Map<String, Object> search) {
        List<SysOrderLog> operationLogRefundOrderList = sysOrderLogMapper.getOperationLogRefundOrderList(search);
        return operationLogRefundOrderList;
    }

    /**
     * @Author 郭凯
     * @Description 订单退款日志新增
     * @Date 10:44 2020/8/19
     * @Param [orderLog]
     * @return int
    **/
    @Override
    public int insertOrderLog(SysOrderLog orderLog) {
        return sysOrderLogMapper.insertSelective(orderLog);
    }

    /**
     * 查询订单退款日志中是否 有调度费退款
     * @param orderNumber
     * @return
     */
    @Override
    public SysOrderLog selectByOrderLogNumber(String orderNumber) {
        SysOrderLog sysOrderLog = sysOrderLogMapper.selectByOrderLogNumber(orderNumber);
        return sysOrderLog;

    }
    /**
     * 退款订单日志
     * @param orderNumber
     * @return
     */
    @Override
    public List<RefundOrderList> getOrderNumberLogList(String orderNumber) {

        List<RefundOrderList> list =  sysOrderLogMapper.getOrderNumberLogList(orderNumber);

        SysOrder orderByNumber = sysOrderMapper.getOrderByNumber(orderNumber);
        String orderAmount = orderByNumber.getOrderAmount();

        for (RefundOrderList refundOrderList : list) {
            String amountRefund = refundOrderList.getAmountRefund();
            String[] split = amountRefund.split("：");

            if (orderAmount.equals(split[1])){
               refundOrderList.setOrderStatus("2");
            }else{
                refundOrderList.setOrderStatus("1");
            }
            amountRefund = split[1];
            refundOrderList.setAmountRefund(amountRefund);
        }
        return list;
    }
}
