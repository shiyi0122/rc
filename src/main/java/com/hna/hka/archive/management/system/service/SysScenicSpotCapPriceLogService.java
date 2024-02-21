package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotCapPriceLog;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotCapPriceService
 * @Author: 郭凯
 * @Description: 景区封顶价格修改日志业务层（接口）
 * @Date: 2020/9/10 9:49
 * @Version: 1.0
 */
public interface SysScenicSpotCapPriceLogService {

    PageDataResult getScenicSpotCapPriceLogList(Integer pageNum, Integer pageSize, Map<String, Object> search);

//    List<SysScenicSpotCapPriceLog>  getScenicSpotCapPriceLogExcel( Map<String, String> search);
}
