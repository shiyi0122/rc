package com.hna.hka.archive.management.business.service;

import com.hna.hka.archive.management.business.model.BusinessBankCard;
import com.hna.hka.archive.management.business.model.BusinessBankCardHis;
import com.hna.hka.archive.management.system.util.PageDataResult;

import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service
 * @ClassName: BusinessBankCardService
 * @Author: 郭凯
 * @Description: 银行卡管理业务层（接口）
 * @Date: 2020/8/12 13:58
 * @Version: 1.0
 */
public interface BusinessBankCardService {

    PageDataResult getBankCardList(Integer pageNum, Integer pageSize, Map<String, String> search);

    int editAdopt(BusinessBankCard businessBankCard);

	BusinessBankCardHis getBankCardByUserId(Long userId);
}
