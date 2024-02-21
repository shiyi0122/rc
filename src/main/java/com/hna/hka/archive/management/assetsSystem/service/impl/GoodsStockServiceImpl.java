package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.controller.RobotPartsManagementController;
import com.hna.hka.archive.management.assetsSystem.dao.AddressMapper;
import com.hna.hka.archive.management.assetsSystem.dao.GoodsStockMapper;
import com.hna.hka.archive.management.assetsSystem.dao.SysRobotPartsManagementMapper;
import com.hna.hka.archive.management.assetsSystem.model.Address;
import com.hna.hka.archive.management.assetsSystem.model.GoodsStock;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotPartsManagement;
import com.hna.hka.archive.management.assetsSystem.service.GoodsStockService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpot;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-20 15:35
 **/
@Service
public class GoodsStockServiceImpl implements GoodsStockService {

    @Autowired
    GoodsStockMapper mapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    SysScenicSpotMapper sysScenicSpotMapper;
    @Autowired
    SysRobotPartsManagementMapper sysRobotPartsManagementMapper;

    @Override
    public PageInfo<GoodsStock> list(Long managementId, Long spotId, Integer isDanger, Integer pageNum, Integer pageSize) {
        PageHelper.offsetPage(pageNum, pageSize);
        List<GoodsStock> list = mapper.list(managementId, spotId , isDanger);
        PageInfo<GoodsStock> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer add(GoodsStock stock) {
        stock.setId(IdUtils.getSeqId());
        stock.setUpdateTime(DateUtil.currentDateTime());
        return mapper.add(stock);
    }

    @Override
    public Integer edit(GoodsStock stock) {
        return mapper.edit(stock);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.delete(id);
    }

    @Override
    public List getSpot() {
        return mapper.getSpot();
    }

    @Override
    public List getGoods() {
        return mapper.getGoods();
    }

    /**
     * 根据仓库和配件id获取仓库是否有这个配件
     * @param spotId
     * @param managementId
     * @return
     */
    @Override
    public int isGoodsIdSpotId(Long spotId, Long managementId) {

       GoodsStock goodsStock =  mapper.getByGoods(managementId,spotId);

       if (StringUtils.isEmpty(goodsStock)){
           return 0;
       }else{
           return 1;
       }

    }

    /**
     * 列表下载
     * @param managementId
     * @param spotId
     * @param isDanger
     * @return
     */
    @Override
    public List<GoodsStock> detailList(Long managementId, Long spotId, Integer isDanger) {


        List<GoodsStock> list = mapper.list(managementId, spotId, isDanger);
        return list;


    }

    @Override
    public int importExcelEnter(GoodsStock goodsStock) {

        String spotName = goodsStock.getSpotName();
        String name = goodsStock.getName();

        SysScenicSpot spotNameById = sysScenicSpotMapper.getSpotNameById(spotName);
        Address byKey = addressMapper.getByKey(spotNameById.getScenicSpotId(),null);
        if (StringUtils.isEmpty(byKey)){
            return 0;
        }
        List<SysRobotPartsManagement> sysRobotPartsManagements = sysRobotPartsManagementMapper.selectRobotByName(name,null);
        SysRobotPartsManagement sysRobotPartsManagement = new SysRobotPartsManagement();
        if (sysRobotPartsManagements.size()>0){
            sysRobotPartsManagement = sysRobotPartsManagements.get(0);
        }else{
            return 0;
        }

        GoodsStock goodsStock1 = mapper.getByGoods(sysRobotPartsManagement.getPartsManagementId(),byKey.getSpotId());
        Integer resul = 0;
        if (StringUtils.isEmpty(goodsStock1)){
            goodsStock.setId(IdUtils.getSeqId());
            goodsStock.setSpotId(byKey.getSpotId());
            goodsStock.setManagementId(sysRobotPartsManagement.getPartsManagementId());

            resul = mapper.add(goodsStock);
        }else{
            goodsStock1.setManagementId(sysRobotPartsManagement.getPartsManagementId());
            goodsStock1.setSpotId(byKey.getSpotId());
            goodsStock1.setAmount(goodsStock.getAmount());
            goodsStock1.setThreshold(goodsStock.getThreshold());
            goodsStock1.setNotes(goodsStock.getNotes());
           resul = mapper.edit(goodsStock1);
        }

        return resul;
    }

    /**
     * 管理者app，景区配件库存列表获取
     * @param spotId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getGoodsStockList(Long spotId, int pageNum, int pageSize) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<GoodsStock> goodsStocks = mapper.list(null, spotId,null);

        if (goodsStocks.size()>0) {
            PageInfo<GoodsStock> pageInfo = new PageInfo<>(goodsStocks);
            pageDataResult.setData(goodsStocks);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);

        }
        return pageDataResult;

    }
}
