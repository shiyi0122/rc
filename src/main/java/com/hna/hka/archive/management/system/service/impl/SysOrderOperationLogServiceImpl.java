package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysOrderOperationLogMapper;
import com.hna.hka.archive.management.system.model.SysOrderExcel;
import com.hna.hka.archive.management.system.model.SysOrderOperationLog;
import com.hna.hka.archive.management.system.model.SysOrderOperationLogExcel;
import com.hna.hka.archive.management.system.service.SysOrderOperationLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysOrderOperationLogServiceImpl
 * @Author: 郭凯
 * @Description: 订单操作日志业务层（实现）
 * @Date: 2020/6/2 9:20
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOrderOperationLogServiceImpl implements SysOrderOperationLogService {

    @Autowired
    private SysOrderOperationLogMapper sysOrderOperationLogMapper;

    /**
     * @Author 郭凯
     * @Description 订单操作日志列表查询
     * @Date 9:23 2020/6/2
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getOrderOperationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrderOperationLog> orderOperationLogList = sysOrderOperationLogMapper.getOrderOperationLogList(search);
        if (orderOperationLogList.size() != 0){
            PageInfo<SysOrderOperationLog> pageInfo = new PageInfo<>(orderOperationLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 订单操作日志列表下载
     * @param search
     * @return
     */
    @Override
    public List<SysOrderOperationLogExcel> getOrderOperationLogExcel(Map<String, Object> search) {

        List<SysOrderOperationLogExcel> orderOperationLogList = sysOrderOperationLogMapper.getOrderOperationLogExcel(search);

        return orderOperationLogList;

    }
}
