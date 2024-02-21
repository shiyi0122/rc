package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.FactorySendMapper;
import com.hna.hka.archive.management.assetsSystem.dao.FactorySendRobotModelMapper;
import com.hna.hka.archive.management.assetsSystem.dao.ProductionInfoRobotModelMapper;
import com.hna.hka.archive.management.assetsSystem.model.FactorySend;
import com.hna.hka.archive.management.assetsSystem.model.FactorySendRobotModel;
import com.hna.hka.archive.management.assetsSystem.model.ProductionInfoRobotModel;
import com.hna.hka.archive.management.assetsSystem.service.FactorySendService;
import com.hna.hka.archive.management.system.dao.SysRobotDispatchLogMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.service.SysRobotDispatchLogService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-29 10:44
 **/
@Service
public class FactorySendServiceImpl implements FactorySendService {

    @Autowired
    FactorySendMapper mapper;

    @Autowired
    SysRobotMapper sysRobotMapper;

    @Autowired
    FactorySendRobotModelMapper factorySendRobotModelMapper;

    @Autowired
    SysRobotDispatchLogService sysRobotDispatchLogService;


    @Autowired

    @Value("${factory_send_path}")
    private String FACTORY_SEND_PATH;




    @Override
    public List<FactorySend> list(Long spotId, String beginDate, String endDate, Long userId, Integer pageNum, Integer pageSize, Integer type, Integer form) {
        List<FactorySend> list = mapper.list(spotId, beginDate, endDate, userId, pageNum, pageSize, type, form);
        for (FactorySend factorySend : list) {
            List<FactorySendRobotModel> list1 = factorySendRobotModelMapper.list(factorySend.getId());
            if (list1.size()>0){
                    String s="";
                    Long t= 0l;
                    for (FactorySendRobotModel factorySendRobotModel : list1) {
                        if (s.equals("")){
                            s= factorySendRobotModel.getApplicableModel();
                        }else{
                            s = s+","+factorySendRobotModel.getApplicableModel();
                        }
                        t =t + Long.valueOf(factorySendRobotModel.getRobotCount());

                    }
                factorySend.setApplicableModel(s);
                factorySend.setRobotCount(t);
                factorySend.setRobotList(list1);
            }
        }

        return list;
    }

    @Override
    public Integer getCount(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form) {
        return mapper.getCount(spotId, beginDate, endDate , userId , type , form);
    }

    @Override
    public Integer add(FactorySend factorySend) {
        factorySend.setId(IdUtils.getSeqId());
        factorySend.setApplicantTime(DateUtil.currentDateTime());

        List<FactorySendRobotModel> robotList = factorySend.getRobotList();
        for (FactorySendRobotModel factorySendRobotModel : robotList) {

            factorySendRobotModel.setId(IdUtils.getSeqId());
            factorySendRobotModel.setCreateDate(DateUtil.currentDateTime());
            factorySendRobotModel.setRobotFactorySendRobotId(factorySend.getId());
            Integer i = factorySendRobotModelMapper.add(factorySendRobotModel);
        }


        return mapper.add(factorySend);
    }

    @Override
    public String getPId(long applicantId) {
        return mapper.getPId(applicantId);
    }

    @Override
    public Integer edit(FactorySend factorySend) throws IOException {

        if (factorySend.getType() == 2){

            factorySend.setConsignmentDate(DateUtil.currentDateTime());
            factorySendRobotModelMapper.deleteFactorySendRobotModelId(factorySend.getId());
            List<FactorySendRobotModel> robotList = factorySend.getRobotList();
            for (FactorySendRobotModel factorySendRobotModel : robotList) {
                if (StringUtils.isEmpty(factorySendRobotModel.getId()) || factorySendRobotModel.getId() == 0){
                    factorySendRobotModel.setId(IdUtils.getSeqId());
                    factorySendRobotModel.setCreateDate(DateUtil.currentDateTime());
                    factorySendRobotModel.setRobotFactorySendRobotId(factorySend.getId());
                }
                factorySendRobotModelMapper.add(factorySendRobotModel);

            }

        } else if (factorySend.getType() == 3){

            factorySend.setReceivingDate(DateUtil.currentDateTime());
            factorySendRobotModelMapper.deleteFactorySendRobotModelId(factorySend.getId());
            List<FactorySendRobotModel> robotList = factorySend.getRobotList();
            for (FactorySendRobotModel factorySendRobotModel : robotList) {
                if (StringUtils.isEmpty(factorySendRobotModel.getId()) || factorySendRobotModel.getId() == 0){
                    factorySendRobotModel.setId(IdUtils.getSeqId());
                    factorySendRobotModel.setCreateDate(DateUtil.currentDateTime());
                    factorySendRobotModel.setRobotFactorySendRobotId(factorySend.getId());
                }

                factorySendRobotModelMapper.add(factorySendRobotModel);

            }
        }

        return mapper.edit(factorySend);
    }

    @Override
    public FactorySend detail(Long id) {
        FactorySend byKey = mapper.findByKey(id);
        List<FactorySendRobotModel> list = factorySendRobotModelMapper.list(byKey.getId());
        if (list.size()>0){
            String s="";
            Long t= 0l;
            for (FactorySendRobotModel factorySendRobotModel : list) {
                if (s.equals("")){
                    s= factorySendRobotModel.getApplicableModel();
                }else{
                    s = s+","+factorySendRobotModel.getApplicableModel();
                }
                t = t + Long.valueOf(factorySendRobotModel.getRobotCount());
            }

          byKey.setApplicableModel(s);
          byKey.setRobotCount(t);
          byKey.setRobotList(list);
        }
        return byKey;
    }

    @Override
    public List factoryList() {
        return mapper.factoryList();
    }

    @Override
    public List spotList() {
        return mapper.spotList();
    }

    @Override
    public List userList() {
        return mapper.userList();
    }

    @Override
    public List trailList(String robotCode) {
        return mapper.trailList(robotCode);
    }

    @Override
    public List spotAllList() {

        return mapper.spotAllList();
    }


    @Override
    public List<FactorySend> list(Long spotId, String beginDate, String endDate, Long userId, Integer type, Integer form) {
        List<FactorySend> list = mapper.lists(spotId, beginDate, endDate, userId, type, form);
        for (FactorySend factorySend : list) {
            List<FactorySendRobotModel> list1 = factorySendRobotModelMapper.list(factorySend.getId());
            if (list1.size()>0){
                String s="";
                Long t= 0L;
                for (FactorySendRobotModel factorySendRobotModel : list1) {
                    if (s.equals("")){
                        s= factorySendRobotModel.getApplicableModel();
                    }else{
                        s = s+","+factorySendRobotModel.getApplicableModel();
                    }
                    t =t + Long.valueOf(factorySendRobotModel.getRobotCount());

                }
                factorySend.setApplicableModel(s);
                factorySend.setRobotCount(t);
                factorySend.setRobotList(list1);
            }
        }

        return list;
    }

    @Override
    public int upType(FactorySend factorySend) {

        String robotCodes = factorySend.getRobotCodes();

        FactorySend byKey = mapper.findByKey(factorySend.getId());
        if (factorySend.getType() == 2){

            String[] split = robotCodes.split(",");
            for (String robotCode : split) {
                SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(robotCode);
                robotCodeBy.setScenicSpotId(0l);
                sysRobotMapper.updateByPrimaryKeySelective(robotCodeBy);
            }

            factorySend.setConsignmentDate(DateUtil.currentDateTime());

        }else if (factorySend.getType() == 3){//收货
            String[] split = robotCodes.split(",");
            for (String robotCode : split) {
                SysRobot robotCodeBy = sysRobotMapper.getRobotCodeBy(robotCode);
                robotCodeBy.setScenicSpotId(factorySend.getReceivingId());
                sysRobotMapper.updateByPrimaryKeySelective(robotCodeBy);
                sysRobotDispatchLogService.insertRobotDispatchLog(byKey.getConsignorName(),byKey.getFactoryName(),byKey.getReceivingName(),robotCode);
            }
            factorySend.setReceivingDate(DateUtil.currentDateTime());
        }

        int i = mapper.edit(factorySend);
        return i;
    }

    /**
     * 根据景区id，获取机器人列表
     * @param spotId
     * @return
     */
    @Override
    public List<SysRobot> getSpotIdByRobotList(String spotId) {

        List<SysRobot> robotListAll = sysRobotMapper.getRobotListAll(Long.parseLong(spotId));
        return robotListAll;

    }


    private String uploadFile(MultipartFile img) throws IOException {
        if (img.getSize() > 0) {
            String fileName = FACTORY_SEND_PATH + System.currentTimeMillis() + img.getOriginalFilename().substring(img.getOriginalFilename().indexOf("."));
            File file = new File(fileName);
            FileUtils.copyInputStreamToFile(img.getInputStream(), file);
            return fileName;
        }
        return null;
    }
}
