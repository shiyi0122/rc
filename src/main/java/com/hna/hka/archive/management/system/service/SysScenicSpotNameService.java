package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotNameService
 * @Author: 郭凯
 * @Description: 景区名称管理业务层（接口）
 * @Date: 2020/12/3 13:59
 * @Version: 1.0
 */
public interface SysScenicSpotNameService {

    PageDataResult getScenicSpotNameList(Integer pageNum, Integer pageSize, Map<String, Object> search);
}
