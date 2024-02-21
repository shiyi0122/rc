package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotRestrictedAreaWithBLOBs;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotRestrictedAreaService
 * @Author: 郭凯
 * @Description: 禁区告警日志业务层（接口）
 * @Date: 2020/5/31 16:31
 * @Version: 1.0
 */
public interface SysScenicSpotRestrictedAreaService {
    /**
     * 禁区告警日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getRestrictedAreatList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 查询下载所需要的的数据
     * @param search
     * @return
     */
    List<SysScenicSpotRestrictedAreaWithBLOBs> getUploadExcelRestrictedArea(Map<String, Object> search);
}
