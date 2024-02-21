package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLog;
import com.hna.hka.archive.management.system.model.SysScenicSpotPriceModificationLogVo;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotPriceModificationLogService
 * @Author: 郭凯
 * @Description: 景区操作日志业务层（接口）
 * @Date: 2020/6/1 13:51
 * @Version: 1.0
 */
public interface SysScenicSpotPriceModificationLogService {

    /**
     * 景区操作日志列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getPriceModificationLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 下载EXCEl数据查询
     * @param search
     * @return
     */
    List<SysScenicSpotPriceModificationLog> getSysScenicSpotPriceModificationLogExcel(Map<String, Object> search);

    /**
    * @Author 郭凯
    * @Description: 下载基础档案Excel表
    * @Title: getSysScenicSpotPriceModificationLogVoExcel
    * @date  2021年3月10日 下午2:23:34 
    * @param @param search
    * @param @return
    * @return List<SysScenicSpotPriceModificationLogVo>
    * @throws
     */
	List<SysScenicSpotPriceModificationLogVo> getSysScenicSpotPriceModificationLogVoExcel(Map<String, Object> search);
}
