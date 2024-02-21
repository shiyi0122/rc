package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysLogMapper;
import com.hna.hka.archive.management.system.model.SysLog;
import com.hna.hka.archive.management.system.service.SysLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysLogServiceImpl
 * @Author: 郭凯
 * @Description: 储值金额修改日志业务层（实现）
 * @Date: 2020/12/2 9:45
 * @Version: 1.0
 */
@Service
@Transactional
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * @Author 郭凯
     * @Description 储值金额修改日志列表查询
     * @Date 9:47 2020/12/2
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getStoredValueAmountLogList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysLog> sysLogList = sysLogMapper.getStoredValueAmountLogList(search);
        if(sysLogList.size() != 0){
            PageInfo<SysLog> pageInfo = new PageInfo<>(sysLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
