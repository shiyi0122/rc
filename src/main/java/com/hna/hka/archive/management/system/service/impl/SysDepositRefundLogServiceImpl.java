package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysDepositRefundLogMapper;
import com.hna.hka.archive.management.system.model.SysDepositRefundLog;
import com.hna.hka.archive.management.system.service.SysDepositRefundLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysDepositRefundLogServiceImpl
 * @Author: 郭凯
 * @Description: 押金扣款业务层（实现）
 * @Date: 2020/9/9 15:20
 * @Version: 1.0
 */
@Service
@Transactional
public class SysDepositRefundLogServiceImpl implements SysDepositRefundLogService {

    @Autowired
    private SysDepositRefundLogMapper sysDepositRefundLogMapper;



    /**
     * @Author 郭凯
     * @Description 押金扣款日志列表查询
     * @Date 15:25 2020/9/9
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getDepositRefundLogList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysDepositRefundLog> sysDepositRefundLogList = sysDepositRefundLogMapper.getDepositRefundLogList(search);
        if(sysDepositRefundLogList.size() != 0){
            PageInfo<SysDepositRefundLog> pageInfo = new PageInfo<>(sysDepositRefundLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
