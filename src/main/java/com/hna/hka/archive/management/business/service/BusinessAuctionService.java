package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessAuction;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessAuctionService
 * @Author: 郭凯
 * @Description: 竞拍\限时购业务层（接口）
 * @Date: 2020/10/14 13:41
 * @Version: 1.0
 */
public interface BusinessAuctionService {

    PageDataResult getAuctionList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int addRushPurchase(BusinessAuction businessAuction);

    int editRushPurchase(BusinessAuction businessAuction);

    int delRushPurchase(Long id);
}
