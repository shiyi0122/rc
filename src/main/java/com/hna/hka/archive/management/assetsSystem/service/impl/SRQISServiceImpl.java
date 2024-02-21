package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.dao.SRQISMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionDetail;
import com.hna.hka.archive.management.assetsSystem.model.SysRobotQualityInspectionStandard;
import com.hna.hka.archive.management.assetsSystem.service.SRQISService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-16 09:58
 **/
@Service
public class SRQISServiceImpl implements SRQISService {

    @Autowired
    SRQISMapper mapper;

    @Override
    public PageInfo<SysRobotQualityInspectionStandard> list(String name, String beginTime, String endTime, Integer pageNum, Integer pageSize) {
        Integer beginSize = beginTime == null ? 0 : beginTime.length();
        Integer endSize = endTime == null ? 0 : endTime.length();
        PageHelper.offsetPage(pageNum, pageSize);
        List<SysRobotQualityInspectionStandard> list = mapper.list(name, beginTime, beginSize, endTime, endSize);
        PageInfo<SysRobotQualityInspectionStandard> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer add(SysRobotQualityInspectionStandard standard) {
        standard.setId(IdUtils.getSeqId());
        standard.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return mapper.add(standard);
    }

    @Override
    public Integer edit(SysRobotQualityInspectionStandard standard) {
        return mapper.edit(standard);
    }

    @Override
    public Integer delete(Long id) {
        return mapper.delete(id);
    }

    @Override
    public List<SysRobotQualityInspectionDetail> showDetail(Long id) {
        return mapper.showDetail(id);
    }

    @Override
    public Integer addDetail(SysRobotQualityInspectionDetail detail) {
        detail.setId(IdUtils.getSeqId());
        detail.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return mapper.addDetail(detail);
    }

    @Override
    public Integer editDetail(SysRobotQualityInspectionDetail detail) {
        SysRobotQualityInspectionDetail targetDetail = mapper.selectById(detail);
        if (targetDetail == null) {
            return null;
        }
        if (detail.getType()==null){
            mapper.editDetail(detail);
        }else if (detail.getType() == 2&&detail.getSerial() != 1) {
            SysRobotQualityInspectionDetail previousData = mapper.selectByPre(detail);
            if (previousData == null) {
                return null;
            }

            // 同一个STANDARD_ID下，将当前数据和上一条数据的SERIAL值进行交换
            int tempSerial = targetDetail.getSerial();
            targetDetail.setSerial(previousData.getSerial());
            previousData.setSerial(tempSerial);

            // 更新数据库中的数据
            mapper.updateSerial(previousData);
            mapper.updateSerial(targetDetail);
        } else if (detail.getType() == 1) {
            SysRobotQualityInspectionDetail nextData = mapper.selectByNext(detail);
            if (nextData == null) {
                return null;
            }

            // 同一个STANDARD_ID下，将当前数据和下一条数据的SERIAL值进行交换
            int tempSerial = targetDetail.getSerial();
            targetDetail.setSerial(nextData.getSerial());
            nextData.setSerial(tempSerial);

            // 更新数据库中的数据
            mapper.updateSerial(nextData);
            mapper.updateSerial(targetDetail);
        } else {
            return null; // 可以考虑抛出异常
        }

        return 1;
    }

    @Override
    public Integer deleteDetail(Long id, Long standardId) {
        return mapper.deleteDetail(id, standardId);
    }

    @Override
    public HashMap getMsgByRobotCode(String robotCode, Integer type) {
        HashMap map = mapper.getMsgByRobotCode(robotCode, type);
        if (map != null) {
            map.put("details", mapper.getDetailByRobotCode(robotCode, type));
        }
        return map;
    }

    @Override
    public List<SysRobotQualityInspectionStandard> getAll(String type) {
        return mapper.getAll(type);
    }
}
