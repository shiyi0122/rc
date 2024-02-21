package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotMapRes;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotMapResService
 * @Author: 郭凯
 * @Description: 地图资源管理业务层（接口）
 * @Date: 2020/11/16 15:57
 * @Version: 1.0
 */
public interface SysRobotMapResService {

    PageDataResult getRobotMapResList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRobotMapRes(MultipartFile file, SysRobotMapRes sysRobotMapRes);

    int delRobotMapRes(Long resId);

    int editResType(SysRobotMapRes sysRobotMapRes);

    SysRobotMapRes getSysRobotMapResByScenicSpotId(String scenicSpotId);
}
