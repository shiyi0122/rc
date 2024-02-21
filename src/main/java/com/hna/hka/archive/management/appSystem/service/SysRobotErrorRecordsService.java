package com.hna.hka.archive.management.appSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.model.SysRobotErrorRecordsApprovalLog;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.appSystem.service
 * @ClassName: SysRobotErrorRecordsService
 * @Author: 郭凯
 * @Description: 配件损坏业务层（接口）
 * @Date: 2021/6/24 16:12
 * @Version: 1.0
 */
public interface SysRobotErrorRecordsService {

    int addRobotErrorRecord(SysRobotErrorRecords sysRobotErrorRecords);

    PageInfo<SysRobotErrorRecords> getRobotErrorRecordList(BaseQueryVo BaseQueryVo);

    int robotErrorRecordApproval(SysRobotErrorRecords sysRobotErrorRecords);

    int robotErrorRecordRepair(SysRobotRepair sysRobotRepair);

    int robotErrorRecordEvaluate(SysRobotServiceRecords sysRobotServiceRecords);

    PageDataResult getRobotErrorRecordsList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getRobotErrorRecordsListNew(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int editRobotErrorRecords(SysRobotErrorRepairUser sysRobotErrorRepairUser);

    int editErrorRecords(SysRobotErrorRecords sysRobotErrorRecords);

    List<SysRobotErrorParts> accessoriesDetails(Long errorRecordsId);

    SysRobotErrorRecords robotErrorRecordDetails(String errorRecordsId);

    int addRobotErrorRecords(SysRobotErrorRecords sysRobotErrorRecords);

    int addRobotErrorRecordFile(MultipartFile file, SysRobotErrorRecords sysRobotErrorRecords);

    PageDataResult getFailureRecord(Map<String, Object> search);


    PageDataResult getAppRobotErrorRecords(Map<String, Object> search);

    int addAppRobotErrorRecords(SysRobotErrorRecords sysRobotErrorRecords);


    List<SysOrderExceptionManagement> getOrderExceptionManagement(String type);


    List<Address> getUserIdByAddress(String userId);

    List<GoodsStock> getStorageRoomList(String errorRecordsId);

    int robotErrorRecordCourierNumber(SysRobotErrorRepairUser sysRobotErrorRepairUser);


    int editErrorAccessory(String id, String signInPicture);

    SysRobotErrorRecords selectById(Long errorId);

    int delErrorRecords(Long errorRecordsId);


    int errorRecordsToExamine(SysRobotErrorRecordsApprovalLog sysRobotErrorRecordsApprovalLog);



}
