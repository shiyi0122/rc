package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysRobotSoftAssetInformation;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysRobotSoftAssetInformationService
 * @Author: 郭凯
 * @Description: 机器人软资产信息业务层（接口）
 * @Date: 2021/5/28 14:25
 * @Version: 1.0
 */
public interface SysRobotSoftAssetInformationService {
    
    PageDataResult getRobotSoftAssetInformationList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addRobotSoftAssetInformation(SysRobotSoftAssetInformation robotSoftAssetInformation);

    int editRobotSoftAssetInformation(SysRobotSoftAssetInformation robotSoftAssetInformation);

    int delRobotSoftAssetInformation(Long softAssetInformationId);

    SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotId(Long robotId);

    List<SysRobotSoftAssetInformation> getRobotSoftAssetInformationExcel(Map<String, Object> search);

    SysRobotSoftAssetInformation getAppRobotSoftAssetInformation(Map<String, Object> search);

    SysRobotSoftAssetInformation getRobotSoftAssetInformationByRobotCode(String robotCode);

    void updateRobotSoftAssetInformation();

    SysRobotSoftAssetInformation selectRobotSoftByRobotCode(String robotCode);

    int addRobotSoftAssetInformationImport(SysRobotSoftAssetInformation robotSoftAsset);

    int editRobotSoftAssetInformationImport(SysRobotSoftAssetInformation robotSoftAssetInformation);

}
