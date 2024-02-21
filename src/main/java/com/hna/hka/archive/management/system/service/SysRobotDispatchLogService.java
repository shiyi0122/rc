package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: sysRobotDispatchLogService
 * @Author: 郭凯
 * @Description: 景区机器人调度日志业务层（接口）
 * @Date: 2020/5/21 11:37
 * @Version: 1.0
 */
public interface SysRobotDispatchLogService {

    /**
     * 添加景区机器人调度日志
     * @param userName
     * @param scenicSpotNameOut
     * @param scenicSpotNameIn
     * @param robotCode
     * @return
     */
    int insertRobotDispatchLog(String userName, String scenicSpotNameOut, String scenicSpotNameIn, String robotCode);

    /**
     * 机器人调度日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysDepositLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 查询需要下载的Excel表数据
     * @param search
     * @return
     */
    List<SysRobotDispatchLog> getUploadExcelDispatchtLog(Map<String, Object> search);
}
