package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysAttendanceRulesMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysAttendanceRules;
import com.hna.hka.archive.management.assetsSystem.service.SysAttendanceRulesService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysAttendanceRulesServiceImpl
 * @Author: 郭凯
 * @Description: 考勤规则业务层（实现）
 * @Date: 2021/6/3 11:10
 * @Version: 1.0
 */
@Service
@Transactional
public class SysAttendanceRulesServiceImpl implements SysAttendanceRulesService {

    @Autowired
    private SysAttendanceRulesMapper sysAttendanceRulesMapper;

    /**
     * @Method getAttendanceRulesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 考勤规则列表查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/6/3 11:28
     */
    @Override
    public PageDataResult getAttendanceRulesList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysAttendanceRules> attendanceRulesList = sysAttendanceRulesMapper.getAttendanceRulesList(search);
        if (attendanceRulesList.size() > 0){
            PageInfo<SysAttendanceRules> pageInfo = new PageInfo<>(attendanceRulesList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
