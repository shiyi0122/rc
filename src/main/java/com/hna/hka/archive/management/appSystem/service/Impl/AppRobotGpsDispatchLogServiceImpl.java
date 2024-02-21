package com.hna.hka.archive.management.appSystem.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.AppRobotGpsDispatchLogMapper;
import com.hna.hka.archive.management.appSystem.model.AppRobotGpsDispatchLog;
import com.hna.hka.archive.management.appSystem.model.SysAppOrder;
import com.hna.hka.archive.management.appSystem.service.AppRobotGpsDispatchLogService;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/5/25 17:43
 */
@Service
public class AppRobotGpsDispatchLogServiceImpl implements AppRobotGpsDispatchLogService {

    @Autowired
    AppRobotGpsDispatchLogMapper appRobotGpsDispatchLogMapper;


    /**
     * 机器人更新坐标列表查询
     * @param baseQueryVo
     * @return
     */
    @Override
    public PageInfo<AppRobotGpsDispatchLog> getAppRobotGpsDispatchLogList(BaseQueryVo baseQueryVo) {

        PageHelper.startPage(baseQueryVo.getPageNum(),baseQueryVo.getPageSize());

        List<AppRobotGpsDispatchLog> list = appRobotGpsDispatchLogMapper.getAppRobotGpsDispatchLogList(baseQueryVo.getSearch());

        PageInfo<AppRobotGpsDispatchLog> pageInfo = new PageInfo<>(list);

        return pageInfo;

    }
}
