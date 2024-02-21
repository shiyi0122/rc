package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotCustomType;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotCustomTypeService
 * @Author: 郭凯
 * @Description: 自定义类型业务层（接口）
 * @Date: 2020/7/27 15:40
 * @Version: 1.0
 */
public interface SysScenicSpotCustomTypeService {

    PageDataResult getCustomTypeList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int delCustomType(Long typeId);

    int addCustomType(SysScenicSpotCustomType sysScenicSpotCustomType);

    int editCustomType(SysScenicSpotCustomType sysScenicSpotCustomType);

    PageDataResult getCueToneTypeList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addCueToneType(SysScenicSpotCustomType sysScenicSpotCustomType);

    List<SysScenicSpotCustomType> getRobotSoundsCueToneTypeList(Map<String, String> search);
}
