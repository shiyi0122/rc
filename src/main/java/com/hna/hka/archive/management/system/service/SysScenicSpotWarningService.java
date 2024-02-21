package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotWarning;
import com.hna.hka.archive.management.system.model.WarningExcel;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotWarningService
 * @Author: 郭凯
 * @Description: 警告管理业务层（接口）
 * @Date: 2020/7/25 13:06
 * @Version: 1.0
 */
public interface SysScenicSpotWarningService {

    PageDataResult getWarningList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addWarning(MultipartFile file, SysScenicSpotWarning sysScenicSpotWarning);

    int delWarning(Long warningId);

    int editWarning(MultipartFile file, SysScenicSpotWarning sysScenicSpotWarning);


    List<WarningExcel> getWarningExcel(Map<String,Object> search);

    WarningExcel seLWarning(WarningExcel warn);

    int addWarningExcel(WarningExcel warn);

    int upWarningExcel(WarningExcel warn);

}
