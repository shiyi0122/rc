package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysRobotPadService
 * @Author: 郭凯
 * @Description: 景区PAD版本管理业务层（接口）
 * @Date: 2020/12/14 16:25
 * @Version: 1.0
 */
public interface SysRobotPadService {

    PageDataResult getRobotPadList(Integer pageNum, Integer pageSize, Map<String, String> search);

    SysRobotPad getAppPadNumber();

    int addRobotPad(SysRobotPad sysRobotPad, MultipartFile file);

    int editRobotPad(SysRobotPad sysRobotPad);

    int delRobotPad(Long padId);

    List<SysRobotPad> getRobotPad();
}
