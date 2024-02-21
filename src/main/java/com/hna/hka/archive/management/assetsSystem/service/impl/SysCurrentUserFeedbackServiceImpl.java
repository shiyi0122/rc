package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SysCurrentUserFeedbackMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysCurrentUserFeedback;
import com.hna.hka.archive.management.assetsSystem.service.SysCurrentUserFeedbackService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysCurrentUserFeedbackServiceImpl
 * @Author: 郭凯
 * @Description: 小程序上报故障业务层（实现）
 * @Date: 2021/6/27 17:43
 * @Version: 1.0
 */
@Service
@Transactional
public class SysCurrentUserFeedbackServiceImpl implements SysCurrentUserFeedbackService {

    @Autowired
    private SysCurrentUserFeedbackMapper sysCurrentUserFeedbackMapper;
    
    /**
     * @Method getRobotDamagesAppList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询小程序上报故障列表
     * @Return com.github.pagehelper.PageInfo<com.hna.hka.archive.management.assetsSystem.model.SysCurrentUserFeedback>
     * @Date 2021/6/27 17:45
     */
    @Override
    public PageInfo<SysCurrentUserFeedback> getRobotDamagesAppList(BaseQueryVo baseQueryVo) {
        PageHelper.startPage(baseQueryVo.getPageNum(), baseQueryVo.getPageSize());
        List<SysCurrentUserFeedback> currentUserFeedbackList = sysCurrentUserFeedbackMapper.getRobotDamagesAppList(baseQueryVo.getSearch());
        PageInfo<SysCurrentUserFeedback> SysRobotDamages = new PageInfo<>(currentUserFeedbackList);
        return SysRobotDamages;
    }

    /**
     * @Method editCurrentUserFeedbackApp
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑小程序上报故障
     * @Return int
     * @Date 2021/6/27 18:08
     */
    @Override
    public int editCurrentUserFeedbackApp(SysCurrentUserFeedback sysCurrentUserFeedback) {
        sysCurrentUserFeedback.setUpdateDate(DateUtil.currentDateTime());
        return sysCurrentUserFeedbackMapper.updateByPrimaryKeySelective(sysCurrentUserFeedback);
    }
}
