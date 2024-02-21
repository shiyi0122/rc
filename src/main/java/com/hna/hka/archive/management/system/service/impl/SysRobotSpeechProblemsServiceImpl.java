package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotSpeechProblemsMapper;
import com.hna.hka.archive.management.system.model.SysRobotSpeechProblems;
import com.hna.hka.archive.management.system.service.SysRobotSpeechProblemsService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotSpeechProblemsServiceImpl
 * @Author: 郭凯
 * @Description: 问题汇总业务层（实现）
 * @Date: 2020/7/24 16:27
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotSpeechProblemsServiceImpl implements SysRobotSpeechProblemsService {

    @Autowired
    private SysRobotSpeechProblemsMapper sysRobotSpeechProblemsMapper;

    /**
     * @Author 郭凯
     * @Description 问题汇总列表查询
     * @Date 16:32 2020/7/24
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getSpeechProblemsList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotSpeechProblems> sysRobotSpeechProblemsList = sysRobotSpeechProblemsMapper.getSpeechProblemsList(search);
        if (sysRobotSpeechProblemsList.size() != 0){
            PageInfo<SysRobotSpeechProblems> pageInfo = new PageInfo<>(sysRobotSpeechProblemsList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 问题汇总Excel下载数据查询
     * @Date 17:34 2020/7/24
     * @Param [sysRobotSpeechProblems]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotSpeechProblems>
    **/
    @Override
    public List<SysRobotSpeechProblems> getSpeechProblemsExcel(Map<String,String> search) {
        return sysRobotSpeechProblemsMapper.getSpeechProblemsList(search);
    }

    /**
     * @Author 郭凯
     * @Description 修改状态为已处理
     * @Date 9:51 2020/7/25
     * @Param [sysRobotSpeechProblems]
     * @return int
    **/
    @Override
    public int editSpeechProblemsHandleState(SysRobotSpeechProblems sysRobotSpeechProblems) {
        sysRobotSpeechProblems.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotSpeechProblemsMapper.updateByPrimaryKeySelective(sysRobotSpeechProblems);
    }
}