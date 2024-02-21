package com.hna.hka.archive.management.appYXBSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appYXBSystem.dao.SysGuideAppUsersFeedbackMapper;
import com.hna.hka.archive.management.appYXBSystem.model.SysGuideAppUsersFeedback;
import com.hna.hka.archive.management.appYXBSystem.service.SysGuideAppUsersFeedbackService;
import com.hna.hka.archive.management.system.model.SysUsers;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysGuideAppUsersFeedbackServiceImpl implements SysGuideAppUsersFeedbackService {

    @Autowired
    SysGuideAppUsersFeedbackMapper sysGuideAppUsersFeedbackMapper;

    /**
     * 添加反馈
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @Override
    public int addGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {
        sysGuideAppUsersFeedback.setFeedbackId(IdUtils.getSeqId());
        sysGuideAppUsersFeedback.setCreateDate(DateUtil.currentDateTime());
       return sysGuideAppUsersFeedbackMapper.insertSelective(sysGuideAppUsersFeedback);
    }

    /**
     * 修改反馈
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @Override
    public int editGuideAppUsersFeedback(SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {
                sysGuideAppUsersFeedback.setUpdateDate(DateUtil.currentDateTime());
      return   sysGuideAppUsersFeedbackMapper.updateByPrimaryKeySelective(sysGuideAppUsersFeedback);
    }

    /**
     * 删除反馈
     * @param appUserId
     * @return
     */
    @Override
    public int delGuideAppUsersFeedback(Long appUserId) {

        return sysGuideAppUsersFeedbackMapper.deleteByPrimaryKey(appUserId);
    }

    /**
     * 反馈列表
     * @param pageNum
     * @param pageSize
     * @param sysGuideAppUsersFeedback
     * @return
     */
    @Override
    public PageDataResult getGuideAppUsersFeedbackList(Integer pageNum, Integer pageSize, SysGuideAppUsersFeedback sysGuideAppUsersFeedback) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysGuideAppUsersFeedback> sysUsersList = sysGuideAppUsersFeedbackMapper.getGuideAppUsersFeedbackList(sysGuideAppUsersFeedback);
        if(sysUsersList.size() != 0){
            PageInfo<SysGuideAppUsersFeedback> pageInfo = new PageInfo<>(sysUsersList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;

    }
}
