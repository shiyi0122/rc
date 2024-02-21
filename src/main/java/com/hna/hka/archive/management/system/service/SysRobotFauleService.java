package com.hna.hka.archive.management.system.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.model.SysRobotFaule;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotFauleService
 * @Author: 郭凯
 * @Description: 机器人报警日志业务层（接口）
 * @Date: 2020/5/26 16:17
 * @Version: 1.0
 */
public interface SysRobotFauleService {
    /**
     * 机器人报警日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getOperationLogRobotAlarmList(Integer pageNum, Integer pageSize, Map<String,Object> search);

    /**
     * 机器人报警日志Excel表查询
     * @param search
     * @return
     */
    List<SysRobotFaule> getUploadExcelRobotLogExcel(Map<String, Object> search);

    PageInfo<SysRobotFaule> getRobotFauleList(BaseQueryVo baseQueryVo);
}
