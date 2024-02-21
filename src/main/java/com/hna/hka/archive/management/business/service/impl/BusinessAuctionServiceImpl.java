package com.hna.hka.archive.management.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.business.dao.BusinessAuctionMapper;
import com.hna.hka.archive.management.business.model.BusinessAuction;
import com.hna.hka.archive.management.business.service.BusinessAuctionService;
import com.hna.hka.archive.management.system.util.BelongCalendar;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.service.impl
 * @ClassName: BusinessAuctionServiceImpl
 * @Author: 郭凯
 * @Description: 竞拍\限时购业务层（实现）
 * @Date: 2020/10/14 13:41
 * @Version: 1.0
 */
@Service
public class BusinessAuctionServiceImpl implements BusinessAuctionService {

    @Autowired
    private BusinessAuctionMapper businessAuctionMapper;

    /**
     * @Author 郭凯
     * @Description 竞拍\限时购列表查询
     * @Date 13:45 2020/10/14
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getAuctionList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<BusinessAuction> businessAuctionList = businessAuctionMapper.getAuctionList(search);
        for(BusinessAuction auction : businessAuctionList) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            Date now = null;
            Date beginTime = null;
            Date endTime = null;
            try {
                now = df.parse(df.format(new Date()));
                beginTime = df.parse(auction.getStartTime());
                endTime = df.parse(auction.getEndTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Boolean flag = BelongCalendar.belongCalendar(now, beginTime, endTime);
            if (flag) {
                auction.setEffective("正常");
            }else {
                auction.setEffective("已过期");
            }
        }
        if (businessAuctionList.size() != 0){
            PageInfo<BusinessAuction> pageInfo = new PageInfo<>(businessAuctionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 限时购新增
     * @Date 9:31 2020/10/16
     * @Param [businessAuction]
     * @return int
    **/
    @Override
    public int addRushPurchase(BusinessAuction businessAuction) {
        businessAuction.setId(IdUtils.getSeqId());
        businessAuction.setUpdateDate(DateUtil.currentDateTime());
        businessAuction.setCreateDate(DateUtil.currentDateTime());
        businessAuction.setStockNumber(businessAuction.getRobotTables());
        businessAuction.setTotalPrice(businessAuction.getUnitPrice());
        return businessAuctionMapper.insertSelective(businessAuction);
    }

    /**
     * @Author 郭凯
     * @Description 限时购修改
     * @Date 14:43 2020/10/16
     * @Param [businessAuction]
     * @return int
    **/
    @Override
    public int editRushPurchase(BusinessAuction businessAuction) {
        return businessAuctionMapper.updateByPrimaryKeySelective(businessAuction);
    }

    /**
     * @Author 郭凯
     * @Description 竞拍\限时购删除
     * @Date 14:47 2020/10/16
     * @Param [id]
     * @return int
    **/
    @Override
    public int delRushPurchase(Long id) {
        return businessAuctionMapper.deleteByPrimaryKey(id);
    }


}
