package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysCurrentUserFeedback;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysCurrentUserFeedbackService
 * @Author: 郭凯
 * @Description: 小程序上报故障业务层（接口）
 * @Date: 2021/6/27 17:43
 * @Version: 1.0
 */
public interface SysCurrentUserFeedbackService {

    PageInfo<SysCurrentUserFeedback> getRobotDamagesAppList(BaseQueryVo baseQueryVo);

    int editCurrentUserFeedbackApp(SysCurrentUserFeedback sysCurrentUserFeedback);
}
