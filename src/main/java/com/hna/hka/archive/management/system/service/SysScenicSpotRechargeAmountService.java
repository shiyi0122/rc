package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotRechargeAmount;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotRechargeAmountService
 * @Author: 郭凯
 * @Description: 储值充值管理业务层（接口）
 * @Date: 2020/7/11 17:03
 * @Version: 1.0
 */
public interface SysScenicSpotRechargeAmountService {

    PageDataResult getRechargeAmountList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount);

    int delRechargeAmount(Long rechargeId);

    int editRechargeAmount(SysScenicSpotRechargeAmount sysScenicSpotRechargeAmount);
}
