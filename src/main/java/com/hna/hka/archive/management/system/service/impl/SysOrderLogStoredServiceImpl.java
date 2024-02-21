package com.hna.hka.archive.management.system.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysOrderLogStoredMapper;
import com.hna.hka.archive.management.system.model.SysOrderLog;
import com.hna.hka.archive.management.system.service.SysOrderLogStoredService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysOrderLogStoredServiceImpl implements SysOrderLogStoredService {

    @Autowired
    private SysOrderLogStoredMapper sysOrderLogStoredMapper;

    @Override
    public PageDataResult getOperationLogRefundOrderList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrderLog> operationLogRefundOrderList = sysOrderLogStoredMapper.getOperationLogRefundOrderList(search);
        if (operationLogRefundOrderList.size() != 0){
            PageInfo<SysOrderLog> pageInfo = new PageInfo<>(operationLogRefundOrderList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public int insertOrderLog(SysOrderLog orderLog) {
        return sysOrderLogStoredMapper.insertSelective(orderLog);
    }

    @Override
    public List<SysOrderLog> getUploadExcelOrderLog(Map<String, Object> search) {
        List<SysOrderLog> operationLogRefundOrderList = sysOrderLogStoredMapper.getOperationLogRefundOrderList(search);
        return operationLogRefundOrderList;
    }
}
