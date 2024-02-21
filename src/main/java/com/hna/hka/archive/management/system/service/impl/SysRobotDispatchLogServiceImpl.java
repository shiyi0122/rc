package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotDispatchLogMapper;
import com.hna.hka.archive.management.system.model.SysRobotDispatchLog;
import com.hna.hka.archive.management.system.service.SysRobotDispatchLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: sysRobotDispatchLogServiceImpl
 * @Author: 郭凯
 * @Description: 景区机器人调度日志业务层（实现层）
 * @Date: 2020/5/21 11:38
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotDispatchLogServiceImpl implements SysRobotDispatchLogService {

    @Autowired
    private SysRobotDispatchLogMapper sysRobotDispatchLogMapper;

    /**
     * @Author 郭凯
     * @Description 添加景区机器人调度日志
     * @Date 11:41 2020/5/21
     * @Param [userName, scenicSpotNameOut, scenicSpotNameIn, robotCode]
     * @return int
    **/
    @Override
    public int insertRobotDispatchLog(String userName, String scenicSpotNameOut, String scenicSpotNameIn, String robotCode) {
        SysRobotDispatchLog sysRobotDispatchLog = new SysRobotDispatchLog();
        sysRobotDispatchLog.setRobotDispatchId(IdUtils.getSeqId());
        sysRobotDispatchLog.setRobotDispatchCallOutName(scenicSpotNameOut);
        sysRobotDispatchLog.setRobotDispatchTransferInName(scenicSpotNameIn);
        sysRobotDispatchLog.setRobotDispatchUsersName(userName);
        sysRobotDispatchLog.setRobotDispatchCode(robotCode);
        sysRobotDispatchLog.setCreateDate(DateUtil.currentDateTime());
        sysRobotDispatchLog.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotDispatchLogMapper.insertSelective(sysRobotDispatchLog);
    }

    /**
     * @Author 郭凯
     * @Description 机器人调度日志列表查询
     * @Date 15:46 2020/6/1
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getSysDepositLogList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotDispatchLog> sysDepositLogList = sysRobotDispatchLogMapper.getOperationLogRobotAlarmListNew(search);
        if (sysDepositLogList.size() != 0){
            PageInfo<SysRobotDispatchLog> pageInfo = new PageInfo<>(sysDepositLogList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 查询需要下载的Excel表数据
     * @Date 16:21 2020/6/1
     * @Param [search]
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotDispatchLog>
    **/
    @Override
    public List<SysRobotDispatchLog> getUploadExcelDispatchtLog(Map<String, Object> search) {
        return sysRobotDispatchLogMapper.getOperationLogRobotAlarmList(search);
    }
}
