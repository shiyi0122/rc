package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.ProductionInfoMapper;
import com.hna.hka.archive.management.assetsSystem.dao.ProductionInfoRobotModelMapper;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfo;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfoRobotModel;
import com.hna.hka.archive.management.assetsSystem.service.ProductionInfoService;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBindingMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 13:36
 **/
@Service
public class ProductionInfoServiceImpl implements ProductionInfoService {

    @Autowired
    ProductionInfoMapper mapper;
    @Autowired
    SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    ProductionInfoRobotModelMapper productionInfoRobotModelMapper;

    @Override
    public PageInfo<ProductionInfo> list(String name, String factory, Integer pageNum, Integer pageSize) throws Exception {
        PageHelper.offsetPage(pageNum , pageSize);
        List<ProductionInfo> list = mapper.list(name , factory);
        for (ProductionInfo productionInfo : list) {
            List<ProductionInfoRobotModel> list1 = productionInfoRobotModelMapper.list(productionInfo.getId());
            if (list1.size()>0){
                String model = null;
                Integer count = 0;
                for (ProductionInfoRobotModel productionInfoRobotModel : list1) {
                    if (StringUtils.isEmpty(model)){
                        model = productionInfoRobotModel.getApplicableModel();
                        count = count + Integer.parseInt(productionInfoRobotModel.getRobotCount()) ;
                    }else {
                        model = model+","+productionInfoRobotModel.getApplicableModel();
                        count = count + Integer.parseInt(productionInfoRobotModel.getRobotCount()) ;
                    }

                    productionInfo.setApplicableModel(model);
                    productionInfo.setRobotCount(count.longValue());

                }
                productionInfo.setRobotList(list1);
            }
        }
        return new PageInfo<ProductionInfo>(list);
    }

    @Override
    public Integer add(ProductionInfo info) {
        info.setId(IdUtils.getSeqId());
        info.setCreateTime(DateUtil.currentDateTime());
        List<ProductionInfoRobotModel> robotList = info.getRobotList();
        for (ProductionInfoRobotModel productionInfoRobotModel : robotList) {
            productionInfoRobotModel.setId(IdUtils.getSeqId());
            productionInfoRobotModel.setRobotProductionInfoId(info.getId());
            productionInfoRobotModel.setCreateDate(DateUtil.currentDateTime());
            Integer i = productionInfoRobotModelMapper.add(productionInfoRobotModel);
        }
        return mapper.add(info);
    }

    @Override
    public Integer edit(ProductionInfo info) {
        productionInfoRobotModelMapper.deleteRobotProductionInfoId(info.getId());
        List<ProductionInfoRobotModel> robotList = info.getRobotList();
        for (ProductionInfoRobotModel productionInfoRobotModel : robotList) {
            if (StringUtils.isEmpty(productionInfoRobotModel.getId()) || productionInfoRobotModel.getId() ==0){
                productionInfoRobotModel.setId(IdUtils.getSeqId());
                productionInfoRobotModel.setCreateDate(DateUtil.currentDateTime());
                productionInfoRobotModel.setRobotProductionInfoId(info.getId());
            }
          int i = productionInfoRobotModelMapper.add(productionInfoRobotModel);
        }
        return mapper.edit(info);
    }

    @Override
    public Integer delete(Long id) {
        productionInfoRobotModelMapper.deleteRobotProductionInfoId(id);
        return mapper.delete(id);
    }


    @Override
    public PageInfo<SysScenicSpotBinding> spotFactoryList() {

      List<SysScenicSpotBinding> list =  sysScenicSpotBindingMapper.spotFactoryList();

     return   new PageInfo<SysScenicSpotBinding>(list);

    }
}
