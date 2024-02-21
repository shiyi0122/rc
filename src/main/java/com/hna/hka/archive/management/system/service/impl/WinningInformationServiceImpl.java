package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.WinningInformationMapper;
import com.hna.hka.archive.management.system.model.WinningInformation;
import com.hna.hka.archive.management.system.service.WinningInformationService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinningInformationServiceImpl implements WinningInformationService {

    @Autowired
    private WinningInformationMapper winningInformationMapper;


    @Override
    public PageDataResult getWinningInformation(Integer pageNum, Integer pageSize, String currentUserPhone,String exchangeType) {
        PageHelper.startPage(pageNum, pageSize);
        List<WinningInformation> winningInformation = winningInformationMapper.getWinningInformation(currentUserPhone,exchangeType);
        PageDataResult pageDataResult = new PageDataResult();
        if(winningInformation.size() != 0){
            PageInfo<WinningInformation> pageInfo = new PageInfo<>(winningInformation);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
