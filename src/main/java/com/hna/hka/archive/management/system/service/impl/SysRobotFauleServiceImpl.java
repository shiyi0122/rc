package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotFauleMapper;
import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.service.SysRobotFauleService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import com.hna.hka.archive.management.wenYuRiverInterface.model.WenYuRiverOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotFauleServiceImpl
 * @Author: 郭凯
 * @Description: 机器人报警日志业务层（实现）
 * @Date: 2020/5/26 16:17
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotFauleServiceImpl implements SysRobotFauleService {

    @Autowired
    private SysRobotFauleMapper sysRobotFauleMapper;

    /**
     * @Author 郭凯
     * @Description 机器人报警日志列表查询
     * @Date 16:34 2020/5/26
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getOperationLogRobotAlarmList(Integer pageNum, Integer pageSize, Map<String,Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotFaule> sysRobotFauleList = sysRobotFauleMapper.getOperationLogRobotAlarmList(search);
        if (sysRobotFauleList.size() != 0){
            PageInfo<SysRobotFaule> pageInfo = new PageInfo<>(sysRobotFauleList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 机器人报警日志Excel表查询
     * @Date 15:31 2020/5/29
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotFaule>
    **/
    @Override
    public List<SysRobotFaule> getUploadExcelRobotLogExcel(Map<String, Object> search) {
        return sysRobotFauleMapper.getOperationLogRobotAlarmList(search);
    }

    /**
     * @Method getRobotFauleList
     * @Author 郭凯
     * @Version  1.0
     * @Description 管理者APP机器人报警日志列表查询
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.system.model.SysRobotFaule>
     * @Date 2021/5/21 16:28
     */
    @Override
    public PageInfo<SysRobotFaule> getRobotFauleList(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysRobotFaule> sysRobotFauleList = sysRobotFauleMapper.getRobotFauleList(baseQueryVo.getSearch());
        PageInfo<SysRobotFaule> page = new PageInfo<>(sysRobotFauleList);
        return page;
    }
}
