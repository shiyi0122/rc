package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysCurrentUserAgreementLogMapper;
import com.hna.hka.archive.management.system.model.SysCurrentUserAgreementLog;
import com.hna.hka.archive.management.system.service.SysCurrentUserAgreementLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysCurrentUserAgreementLogServiceImpl
 * @Author: 郭凯
 * @Description: 用户协议签订日志业务层（实现）
 * @Date: 2020/9/9 16:59
 * @Version: 1.0
 */
@Service
@Transactional
public class SysCurrentUserAgreementLogServiceImpl implements SysCurrentUserAgreementLogService {

    @Autowired
    private SysCurrentUserAgreementLogMapper sysCurrentUserAgreementLogMapper;

    /**
     * @Author 郭凯
     * @Description 用户协议签订日志列表查询
     * @Date 17:00 2020/9/9
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getCurrentUserAgreementLogList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCurrentUserAgreementLog> sysCurrentUserAgreementLogList = sysCurrentUserAgreementLogMapper.getCurrentUserAgreementLogList(search);
        if(sysCurrentUserAgreementLogList.size() != 0){
            PageInfo<SysCurrentUserAgreementLog> pageInfo = new PageInfo<>(sysCurrentUserAgreementLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
