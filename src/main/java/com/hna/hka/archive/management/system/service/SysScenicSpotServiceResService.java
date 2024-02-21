package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.InnercricleExcel;
import com.hna.hka.archive.management.system.model.SysResExcel;
import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.model.SysScenicSpotServiceRes;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotServiceResService
 * @Author: 郭凯
 * @Description: 服务项业务层（接口）
 * @Date: 2020/7/25 10:20
 * @Version: 1.0
 */
public interface SysScenicSpotServiceResService {

    PageDataResult getServiceResList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delServiceRes(Long serviceId);

    int addServiceRes(MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes);

    List<SysScenicSpotCustomType> selectCustomType();

    int editServiceRes(MultipartFile file, SysScenicSpotServiceRes sysScenicSpotServiceRes);

    List<SysResExcel> getSysResExcel(Map<String,Object> search);

    SysResExcel selSysRes(SysResExcel res);

    int upSysRes(SysResExcel res);

    int addSysRes(SysResExcel res);

    List<SysScenicSpotServiceRes> getScenicSpotServiceResAll();


}
