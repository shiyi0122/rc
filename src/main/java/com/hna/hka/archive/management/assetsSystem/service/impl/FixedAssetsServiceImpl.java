package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.FixedAssetsMapper;
import com.hna.hka.archive.management.assetsSystem.model.*;
import com.hna.hka.archive.management.assetsSystem.service.FixedAssetsService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FixedAssetsServiceImpl implements FixedAssetsService {

    @Autowired
    private FixedAssetsMapper fixedAssetsMapper;

    @Override
    public FixedAssetsResult list(String startDate,String endDate,String scenicSpotId,Integer pageNum,Integer pageSize) {
        FixedAssetsResult pageDataResult=new FixedAssetsResult();
        PageHelper.startPage(pageNum,pageSize);
        List<Cost> costs = fixedAssetsMapper.selDateAndCost(scenicSpotId);
        List<FixedAssets> list=new ArrayList<>();
        int totalCycle=0; double totalNetProfit=0.00,totalRepay=0.00,totalActualCost=0.00;
        for (Cost cost:costs){
            FixedAssets fixedAssets=new FixedAssets();
            if (ToolUtil.isEmpty(cost.getDateProduction())){
                fixedAssets.setScenicSpotId(cost.getScenicSpotId());
                fixedAssets.setScenicSpotName(cost.getScenicSpotName());
                list.add(fixedAssets);
                continue;
            }
            Cost flowingWater = fixedAssetsMapper.selFlowingWater(cost);
            for(int i=1;i<=(flowingWater.getTotal()/10);i++){
                cost.setEnd(DateUtil.addDay(cost.getDateProduction(), (10*i)));
                Cost tenDateOrFlowingWater = fixedAssetsMapper.selFlowingWater(cost);
                if (tenDateOrFlowingWater.getFlowingWater()-cost.getCost()>=0){
                    cost.setStart(DateUtil.addDay(cost.getDateProduction(),(10*(i-1))));
                    for (int j=1;j<=10;j++){
                        cost.setStart(DateUtil.addDay(cost.getDateProduction(),((10*(i-1))+j)));
                        cost.setEnd(null);
                        Cost dateOrFlowingWater = fixedAssetsMapper.selFlowingWater(cost);
                        if (dateOrFlowingWater.getFlowingWater()-cost.getCost()>=0){
                            try {
                                String dates = DateUtil.findDates(cost.getDateProduction(), cost.getStart());
                                fixedAssets.setCycle(dates);
                                continue;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    continue;
                }
            }
            Cost actualCost = fixedAssetsMapper.selActualCost(startDate,endDate, cost.getScenicSpotId());
            Cost grossProfit = fixedAssetsMapper.selGrossProfit(startDate,endDate, cost.getScenicSpotId());
            if (ToolUtil.isEmpty(actualCost) || ToolUtil.isEmpty(grossProfit)){
                fixedAssets.setNetProfit(0.00);
                fixedAssets.setRepay(0.00);
            }else {
                fixedAssets.setNetProfit(actualCost.getGrossProfit()-grossProfit.getActualCost());
                fixedAssets.setRepay(fixedAssets.getNetProfit()/grossProfit.getActualCost());
            }
            fixedAssets.setScenicSpotId(cost.getScenicSpotId());
            fixedAssets.setScenicSpotName(cost.getScenicSpotName());
            list.add(fixedAssets);
            if (ToolUtil.isEmpty(fixedAssets.getCycle()) || ToolUtil.isEmpty(grossProfit.getActualCost()) || ToolUtil.isEmpty(totalNetProfit+=fixedAssets.getNetProfit())){
                totalCycle +=0;
                totalActualCost+=0;
                totalNetProfit+=0;
            }else {
                totalCycle += Integer.parseInt(fixedAssets.getCycle());
                totalActualCost+=grossProfit.getActualCost();
                totalNetProfit+=fixedAssets.getNetProfit();
            }
        }
        if (list.size() > 0){
            PageInfo<FixedAssets> pageInfo = new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
            pageDataResult.setTotalCycle(totalCycle);
            pageDataResult.setTotalNetProfit(totalNetProfit);
            pageDataResult.setTotalRepay(totalNetProfit/totalActualCost);
        }
        return pageDataResult;
    }

    @Override
    public PageDataResult report(String startDate,String endDate,String scenicSpotId) {
        PageDataResult pageDataResult=new PageDataResult();
        ArrayList<FixedAssets> list=new ArrayList<>();
        List<Cost> actualCosts = fixedAssetsMapper.reportActualCost(startDate, endDate, scenicSpotId);

        for (Cost actualCost : actualCosts){
            FixedAssets fixedAssets=new FixedAssets();
            fixedAssets.setDate(actualCost.getDate());
            List<Cost> grossProfits = fixedAssetsMapper.reportGrossProfit(startDate, endDate, scenicSpotId);
            for (Cost grossProfit : grossProfits){
                if (grossProfit.getDate().equals(actualCost.getDate()) && actualCost.getActualCost()!=0){
                fixedAssets.setNetProfit(grossProfit.getGrossProfit()-actualCost.getActualCost());
                if (actualCost.getActualCost()==0){
                    fixedAssets.setRepay(0);
                }else {
                fixedAssets.setRepay(fixedAssets.getNetProfit()/actualCost.getActualCost());
                }
                list.add(fixedAssets);
                }
            }
        }
        if (list.size()>0){
            PageInfo<FixedAssets> pageInfo=new PageInfo<>(list);
            pageDataResult.setList(pageInfo.getList());
        }
        return pageDataResult;
    }



}
