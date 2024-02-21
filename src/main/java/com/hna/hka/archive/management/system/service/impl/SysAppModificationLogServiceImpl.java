package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysAppModificationLogMapper;
import com.hna.hka.archive.management.system.model.SysAppModificationLog;
import com.hna.hka.archive.management.system.service.SysAppModificationLogService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysAppModificationLogServiceImpl
 * @Author: 郭凯
 * @Description: APP操作日志业务层（实现）
 * @Date: 2020/9/10 13:26
 * @Version: 1.0
 */
@Service
@Transactional
public class SysAppModificationLogServiceImpl implements SysAppModificationLogService {

    @Autowired
    private SysAppModificationLogMapper sysAppModificationLogMapper;

    /**
     * @Author 郭凯
     * @Description APP操作日志列表查询
     * @Date 13:27 2020/9/10
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getAppModificationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysAppModificationLog> sysAppModificationLogList = sysAppModificationLogMapper.getAppModificationLogList(search);
        if (sysAppModificationLogList.size() > 0){
            PageInfo<SysAppModificationLog> pageInfo = new PageInfo<>(sysAppModificationLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
