package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.CommonFaultMapper;
import com.hna.hka.archive.management.assetsSystem.model.CommonFault;
import com.hna.hka.archive.management.assetsSystem.service.CommonFaultService;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 16:51
 **/
@Service
public class CommonFaultServiceImpl implements CommonFaultService {

    @Value("${common_fault_png_path}")
    private String COMMON_FAULT_PNG_PATH;


    @Value("${common_fault_png_path}")
    private String COMMON_FAULT_VIDEO_PATH;

    @Autowired
    private CommonFaultMapper mapper;

    @Override
    public PageInfo<CommonFault> list(String title, String applicableModel, Integer pageNum, Integer pageSize)  throws Exception{
        PageHelper.offsetPage(pageNum, pageSize);
        List<CommonFault> list = mapper.list(title, applicableModel);
        PageInfo<CommonFault> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer add(CommonFault fault)  throws Exception{
        fault.setId(IdUtils.getSeqId());
        fault.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return mapper.add(fault);
    }

    @Override
    public Integer edit(CommonFault fault) throws Exception {
        return mapper.edit(fault);
    }

    @Override
    public Integer delete(Long id) throws Exception {
        CommonFault fault = mapper.findByKey(id);
        if (!StringUtils.isEmpty(fault.getResolvent())){
            String[] split = fault.getResolvent().split(",");
            for (String s : split) {
                File file = new File(s);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        if (!StringUtils.isEmpty(fault.getVideo())){
            File file = new File(fault.getVideo());
            if (file.exists()){
                file.delete();
            }
        }
        return mapper.delete(id);
    }

    @Override
    public CommonFault detail(Long id) throws Exception {
        return mapper.findByKey(id);
    }

    @Override
    public List<CommonFault> commonFaultList() {
        List<CommonFault> list =  mapper.commonFaultList();
        return list;
    }
}
