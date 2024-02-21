package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.model.SysRobotPadNew;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotPadService
 * @Author: 郭凯
 * @Description: 景区 重构 PAD版本管理业务层（接口）
 * @Date:
 * @Version: 1.0
 */
public interface SysRobotPadNewService {

    PageDataResult getRobotPadNewList(Integer pageNum, Integer pageSize, Map<String, String> search);

    SysRobotPadNew getAppPadNumber();

    int addRobotPadNew(SysRobotPadNew sysRobotPadNew, MultipartFile file);

    int editRobotPadNew(SysRobotPadNew sysRobotPadNew);

    int delRobotPadNew(Long padId);

    List<SysRobotPadNew> getRobotPadNew();

    SysRobotPadNew getAppPadNumberNew(String packageType);

    PageDataResult getSpotIdByRobotPad(Long padId,Integer pageNum,Integer pageSize);

    int delRobotPadSpotId(Long id);

    List<SysRobotPadNew> getRobotPadEdition();



}
