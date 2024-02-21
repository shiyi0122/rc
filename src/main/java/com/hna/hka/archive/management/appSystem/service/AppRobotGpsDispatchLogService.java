package com.hna.hka.archive.management.appSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.AppRobotGpsDispatchLog;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

/**
 * @Author zhang
 * @Date 2023/5/25 17:42
 */
public interface AppRobotGpsDispatchLogService {

    PageInfo<AppRobotGpsDispatchLog> getAppRobotGpsDispatchLogList(BaseQueryVo baseQueryVo);

}
