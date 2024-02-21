package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessBankCardHisMapper;
import com.hna.hka.archive.management.business.dao.BusinessBankCardMapper;
import com.hna.hka.archive.management.business.model.BusinessBankCard;
import com.hna.hka.archive.management.business.model.BusinessBankCardHis;
import com.hna.hka.archive.management.business.service.BusinessBankCardService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessBankCardServiceImpl
 * @Author: 郭凯
 * @Description: 银行卡管理业务层（实现）
 * @Date: 2020/8/12 13:58
 * @Version: 1.0
 */
@Service
public class BusinessBankCardServiceImpl implements BusinessBankCardService {

    @Autowired
    private BusinessBankCardMapper businessBankCardMapper;
    
    @Autowired
    private BusinessBankCardHisMapper businessBankCardHisMapper;

    /**
     * @Author 郭凯
     * @Description 银行卡管理列表查询
     * @Date 14:25 2020/8/12
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getBankCardList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessBankCard> businessBankCardList = businessBankCardMapper.getBankCardList(search);
        if (businessBankCardList.size() != 0){
            PageInfo<BusinessBankCard> pageInfo = new PageInfo<>(businessBankCardList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 修改通过或者驳回状态 
     * @Date 15:50 2020/8/12
     * @Param [businessBankCard]
     * @return int
    **/
    @Override
    public int editAdopt(BusinessBankCard businessBankCard) {
    	if ("0".equals(businessBankCard.getState())) {
    		BusinessBankCardHis bankCardHis = businessBankCardHisMapper.getBusinessBankCardHisByUserId(businessBankCard.getUserId());
    		businessBankCard.setBankName(bankCardHis.getBankName());
    		businessBankCard.setBankBranch(bankCardHis.getBankBranch());
    		businessBankCard.setBankProvince(bankCardHis.getBankProvince());
    		businessBankCard.setTelephone(bankCardHis.getTelephone());
    		businessBankCard.setBankInfo(bankCardHis.getBankInfo());
    		businessBankCard.setBankCard(bankCardHis.getBankCard());
		}
        businessBankCard.setUpdateTime(DateUtil.currentDateTime());
        return businessBankCardMapper.updateByPrimaryKeySelective(businessBankCard);
    }

    /**
    * @Author 郭凯
    * @Description: 查询银行卡最新提交记录
    * @Title: getBankCardByUserId
    * @date  2021年1月13日 下午1:03:39 
    * @param @param userId
    * @param @return
    * @throws
     */
	@Override
	public BusinessBankCardHis getBankCardByUserId(Long userId) {
		// TODO Auto-generated method stub
		return businessBankCardHisMapper.getBusinessBankCardHisByUserId(userId);
	}
}
