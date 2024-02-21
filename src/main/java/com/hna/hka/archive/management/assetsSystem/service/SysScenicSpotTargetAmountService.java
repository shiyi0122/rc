package com.hna.hka.archive.management.assetsSystem.service;

import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotTargetAmount;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysScenicSpotTargetAmountService
 * @Author: 郭凯
 * @Description: 景区目标金额设置业务层（接口）
 * @Date: 2021/7/19 14:16
 * @Version: 1.0
 */
public interface SysScenicSpotTargetAmountService {

    PageDataResult getTargetAmountList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    int addTargetAmount(SysScenicSpotTargetAmount sysScenicSpotTargetAmount);

    int delTargetAmount(Long targetAmountId);

    int editTargetAmount(SysScenicSpotTargetAmount sysScenicSpotTargetAmount);

    List<SysScenicSpotTargetAmount> getTargetAmountExel(Map<String, Object> search);


   SysScenicSpotTargetAmount robotTotalDepreciation(Long spotId, String date);

    SysScenicSpotTargetAmount getSpotIdAndDateByTagret(Long scenicSpotId, String date);


    int editTargetAmountExcel(SysScenicSpotTargetAmount spotIdAndDateByTagret);


    int addTargetAmountExcel(SysScenicSpotTargetAmount sysScenicSpotTargetAmount);

}
