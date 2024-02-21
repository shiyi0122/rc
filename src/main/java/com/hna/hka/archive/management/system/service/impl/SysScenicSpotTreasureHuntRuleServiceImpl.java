package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastHuntExtendMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotTreasureHuntMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotTreasureHuntRuleMapper;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntRuleService;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotBroadcastHuntServiceImpl
 * @Author: zhang
 * @Description: 寻宝奖品
 * @Date: 2021/12/9 11:21
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotTreasureHuntRuleServiceImpl implements SysScenicSpotTreasureHuntRuleService {
    @Autowired
    private SysScenicSpotTreasureHuntRuleMapper sysScenicSpotTreasureHuntRuleMapper;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${GET_BROADCASTHUNT_PIC_PAHT}")
    private String GET_BROADCASTHUNT_PIC_PAHT;
    @Value("${GET_BROADCASTHUNT_PIC_URL}")
    private String GET_BROADCASTHUNT_PIC_URL;


    @Override
    public PageDataResult getBroadcastRuleList(Integer pageNum, Integer pageSize, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotTreasureHuntRule> broadcastList = sysScenicSpotTreasureHuntRuleMapper.getBroadcastList(sysScenicSpotTreasureHuntRule.getScenicSpotId(),sysScenicSpotTreasureHuntRule.getRuleType());
        if(broadcastList.size() != 0){
            PageInfo<SysScenicSpotTreasureHuntRule> pageInfo = new PageInfo<>(broadcastList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method 新增寻宝景点规则
     * @Author zhang
     * @Version  1.0
     * @Description
     * @Return
     * @Date 2021/12/9 17:43
     */
    @Override
    public int addBroadcastRule(SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule,MultipartFile file) {
        sysScenicSpotTreasureHuntRule.setRuleId(IdUtils.getSeqId());
        sysScenicSpotTreasureHuntRule.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotTreasureHuntRule.setUpdateDate(DateUtil.currentDateTime());
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                   FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);


                    //压缩图片
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件上传
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sysScenicSpotTreasureHuntRule.setRuleUrl(GET_BROADCASTHUNT_PIC_URL+filename);
            }
        }
        int i = sysScenicSpotTreasureHuntRuleMapper.insertSelective(sysScenicSpotTreasureHuntRule);

        return i;
    }
    /**
     * @Method 修改寻宝景点规则
     * @Author zhang
     * @Version  1.0
     * @Description
     * @Return
     * @Date 2021/12/9 18:01
     */
    @Override
    public int editBroadcastRule(SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule,MultipartFile file) {

        sysScenicSpotTreasureHuntRule.setUpdateDate(DateUtil.currentDateTime());
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sysScenicSpotTreasureHuntRule.setRuleUrl(GET_BROADCASTHUNT_PIC_URL+filename);
            }
        }


        int i = sysScenicSpotTreasureHuntRuleMapper.updateByPrimaryKeySelective(sysScenicSpotTreasureHuntRule);

        return i;

    }
    /**
     * @Method 删除寻宝景点规则
     * @Author zhang
     * @Version  1.0
     * @Description
     * @Return
     */
    @Override
    public int delBroadcastRule(Long broadcastId, Long scenicSpotId) {
        int i = sysScenicSpotTreasureHuntRuleMapper.deleteByPrimaryKey(broadcastId);
        return i;
    }

    /**
     * 查询当个景区寻宝规则
     * @param scenicSpotId
     * @return
     */
    @Override
    public SysScenicSpotTreasureHuntRule selectBroadcastRuleBySpotId(Long scenicSpotId,String ruleType) {

      SysScenicSpotTreasureHuntRule  sysScenicSpotTreasureHuntRule =   sysScenicSpotTreasureHuntRuleMapper.selectBroadcastRuleBySpotId(scenicSpotId,ruleType);

      return sysScenicSpotTreasureHuntRule;
    }


}
