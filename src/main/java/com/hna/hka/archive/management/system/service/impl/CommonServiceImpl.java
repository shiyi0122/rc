package com.hna.hka.archive.management.system.service.impl;

import com.hna.hka.archive.management.system.dao.CommonMapper;
import com.hna.hka.archive.management.system.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-10-21 16:27
 **/
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonMapper mapper;

    @Override
    public String getOrderNumber(Integer type) {

        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String today = format.format(new Date());
        String prefer = orderType.getName(type) + today;
        String result = mapper.getOrderNumber(prefer);
        String fina = "";
        if (StringUtils.isEmpty(result)){
            fina = prefer + "-" + "0001";
        } else {
            Integer suffix = Integer.valueOf(result.substring(13));
            fina =  prefer + "-" + String.format("%04d", (suffix + 1));
        }
        mapper.insertOrderNumber(fina);
        return fina;
    }
}

enum orderType{
    CH("SC-CH-"),SH("SC-SH-"),PJ("PJ-SQ-");

    private String name;

    orderType(String name){
        this.name = name;
    }

    public static String getName(Integer type) {
        switch (type){
            case 1:
                return CH.name;
            case 2:
                return SH.name;
            case 3:
                return PJ.name;
            default:
                return null;
        }
    }
}
