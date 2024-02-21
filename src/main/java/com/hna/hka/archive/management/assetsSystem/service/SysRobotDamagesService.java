package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotDamages;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotDamagesService
 * @Author: 郭凯
 * @Description: 机器人损坏赔偿业务层（接口）
 * @Date: 2021/6/26 17:08
 * @Version: 1.0
 */
public interface SysRobotDamagesService {

    SysRobotDamages addRobotDamages(SysRobotDamages sysRobotDamages);

    PageInfo<SysRobotDamages> getRobotDamagesAppList(BaseQueryVo baseQueryVo);

    PageDataResult getRobotDamagesList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int editCloseRobotDamages(SysRobotDamages sysRobotDamages);

    List<SysRobotDamages> getRobotDamagesExcel(Map<String, Object> search);
}
