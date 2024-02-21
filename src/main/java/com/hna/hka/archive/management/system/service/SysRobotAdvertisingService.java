package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotAdvertising;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotAdvertisingService
 * @Author: 郭凯
 * @Description: 轮播图管理业务层（接口）
 * @Date: 2020/6/4 15:25
 * @Version: 1.0
 */
public interface SysRobotAdvertisingService {

    PageDataResult getAdvertisingList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addAdvertising(SysRobotAdvertising sysRobotAdvertising, MultipartFile file);

    int delAdvertising(Long advertisingId);

    int editAdvertising(SysRobotAdvertising sysRobotAdvertising, MultipartFile file);

    int editValid(SysRobotAdvertising sysRobotAdvertising);
}
