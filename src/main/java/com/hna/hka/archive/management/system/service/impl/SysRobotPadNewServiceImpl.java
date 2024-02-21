package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotPadNewBindingMapper;
import com.hna.hka.archive.management.system.dao.SysRobotPadNewMapper;
import com.hna.hka.archive.management.system.dao.SysRobotPadNewSpotMapper;
import com.hna.hka.archive.management.system.model.SysRobotPadNew;
import com.hna.hka.archive.management.system.model.SysRobotPadNewBinding;
import com.hna.hka.archive.management.system.model.SysRobotPadNewSpot;
import com.hna.hka.archive.management.system.service.SysRobotPadNewService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotPadServiceImpl
 * @Author: 郭凯
 * @Description: 景区 重构 PAD版本管理业务层（实现）
 * @Date: 2020/12/14 16:26
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotPadNewServiceImpl implements SysRobotPadNewService {

    @Autowired
    private SysRobotPadNewMapper sysRobotPadNewMapper;
    @Autowired
    private SysRobotPadNewSpotMapper sysRobotPadNewSpotMapper;

    @Autowired
    private SysRobotPadNewBindingMapper sysRobotPadNewBindingMapper;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;

    @Value("${filePathPadUrl}")
    private String filePathPadUrl;

    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * @return com.hna.hka.archive.management.system.util.PageDataResult
     * @Author 郭凯
     * @Description PAD版本管理列表查询
     * @Date 16:27 2020/12/14
     * @Param [pageNum, pageSize, search]
     **/
    @Override
    public PageDataResult getRobotPadNewList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotPadNew> sysRobotPadList = sysRobotPadNewMapper.getRobotMapResList(search);
        if (sysRobotPadList.size() != 0) {
            PageInfo<SysRobotPadNew> pageInfo = new PageInfo<>(sysRobotPadList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @return com.hna.hka.archive.management.system.model.SysRobotPad
     * @Author 郭凯
     * @Description 查询最新版本号
     * @Date 9:12 2020/12/15
     * @Param []
     **/
    @Override
    public SysRobotPadNew getAppPadNumber() {
        return sysRobotPadNewMapper.getAppPadNumber();
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 新增PAD
     * @Date 9:29 2020/12/15
     * @Param [sysRobotPad, file]
     **/
    @Override
    public int addRobotPadNew(SysRobotPadNew sysRobotPadNew, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (".apk".equals(type)) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
                sysRobotPadNew.setPadOssUrl(upload);

            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysRobotPadNew.setPadId(IdUtils.getSeqId());
            sysRobotPadNew.setPadUrl(filePathPadUrl + filename);
            sysRobotPadNew.setCreateDate(DateUtil.currentDateTime());
            int i = sysRobotPadNewMapper.insertSelective(sysRobotPadNew);

            //判断资源包和完整包
            if ("1".equals(sysRobotPadNew.getPackageType())) {

                //判断是否为景区包
                if ("2".equals(sysRobotPadNew.getUpdateScope())) {
                    if (!StringUtils.isEmpty(sysRobotPadNew.getScenicSpotIds())) {

                        String[] split = sysRobotPadNew.getScenicSpotIds().split(",");
                        for (String spotId : split) {

                            SysRobotPadNewSpot sysRobotPadNewSpot = sysRobotPadNewSpotMapper.selectBySpotIdType(spotId, sysRobotPadNew.getPackageType());
                            if (StringUtils.isEmpty(sysRobotPadNewSpot)) {
                                sysRobotPadNewSpot = new SysRobotPadNewSpot();
                                sysRobotPadNewSpot.setId(IdUtils.getSeqId());
                                sysRobotPadNewSpot.setPadId(sysRobotPadNew.getPadId());
                                sysRobotPadNewSpot.setScenicSpotId(Long.parseLong(spotId));
                                sysRobotPadNewSpot.setCreateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setType(sysRobotPadNew.getPackageType());
                                sysRobotPadNewSpotMapper.insertSelective(sysRobotPadNewSpot);
                            } else {
                                sysRobotPadNewSpot.setPadId(sysRobotPadNew.getPadId());
                                sysRobotPadNewSpot.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setType(sysRobotPadNew.getPackageType());
                                sysRobotPadNewSpotMapper.updateByPrimaryKeySelective(sysRobotPadNewSpot);

                            }

                        }
                    }

                }

            } else {//资源包
                //判断资源包是否有归属完整包
                String[] padNumberId = sysRobotPadNew.getCompleteIds().split(",");

                for (String padNId : padNumberId) {


                    SysRobotPadNewBinding sysRobotPadNewBinding = new SysRobotPadNewBinding();
                    sysRobotPadNewBinding.setId(IdUtils.getSeqId());
                    sysRobotPadNewBinding.setPadCompleteId(Long.parseLong(padNId));
                    sysRobotPadNewBinding.setPadNaturalId(sysRobotPadNew.getPadId());
                    sysRobotPadNewBinding.setCreateDate(DateUtil.currentDateTime());
                    sysRobotPadNewBinding.setUpdateDate(DateUtil.currentDateTime());
                    sysRobotPadNewBindingMapper.insertSelective(sysRobotPadNewBinding);

                }

                //判断是否为景区包
                if ("2".equals(sysRobotPadNew.getUpdateScope())) {
                    if (!StringUtils.isEmpty(sysRobotPadNew.getScenicSpotIds())) {

                        String[] split = sysRobotPadNew.getScenicSpotIds().split(",");
                        for (String spotId : split) {

                            SysRobotPadNewSpot sysRobotPadNewSpot = sysRobotPadNewSpotMapper.selectBySpotIdType(spotId, sysRobotPadNew.getPackageType());
                            if (StringUtils.isEmpty(sysRobotPadNewSpot)) {
                                sysRobotPadNewSpot = new SysRobotPadNewSpot();
                                sysRobotPadNewSpot.setId(IdUtils.getSeqId());
                                sysRobotPadNewSpot.setPadId(sysRobotPadNew.getPadId());
                                sysRobotPadNewSpot.setScenicSpotId(Long.parseLong(spotId));
                                sysRobotPadNewSpot.setCreateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setType(sysRobotPadNew.getPackageType());
                                sysRobotPadNewSpotMapper.insertSelective(sysRobotPadNewSpot);
                            } else {
                                sysRobotPadNewSpot.setPadId(sysRobotPadNew.getPadId());
                                sysRobotPadNewSpot.setUpdateDate(DateUtil.currentDateTime());
                                sysRobotPadNewSpot.setType(sysRobotPadNew.getPackageType());
                                sysRobotPadNewSpotMapper.updateByPrimaryKeySelective(sysRobotPadNewSpot);
                            }

                        }
                    }

                }
            }

            return i;
        } else {
            return 2;
        }
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 编辑PAD信息
     * @Date 10:13 2020/12/15
     * @Param [sysRobotPad]
     **/
    @Override
    public int editRobotPadNew(SysRobotPadNew sysRobotPadNew) {
        sysRobotPadNew.setUpdateDate(DateUtil.currentDateTime());
        int i = sysRobotPadNewMapper.updateByPrimaryKeySelective(sysRobotPadNew);

        if (!StringUtils.isEmpty(sysRobotPadNew.getScenicSpotIds())) {
            sysRobotPadNewSpotMapper.deleteByPrimaryKey(sysRobotPadNew.getPadId());

            String[] split = sysRobotPadNew.getScenicSpotIds().split(",");

            for (String s : split) {
                SysRobotPadNewSpot sysRobotPadNewSpot = new SysRobotPadNewSpot();
                sysRobotPadNewSpot.setId(IdUtils.getSeqId());
                sysRobotPadNewSpot.setPadId(sysRobotPadNew.getPadId());
                sysRobotPadNewSpot.setScenicSpotId(Long.parseLong(s));
                sysRobotPadNewSpot.setCreateDate(DateUtil.currentDateTime());
                sysRobotPadNewSpot.setUpdateDate(DateUtil.currentDateTime());
                sysRobotPadNewSpot.setType(sysRobotPadNew.getPackageType());
                sysRobotPadNewSpotMapper.insertSelective(sysRobotPadNewSpot);
            }

        }

        if (!StringUtils.isEmpty(sysRobotPadNew.getCompleteIds())) {
            String[] padIdS = sysRobotPadNew.getCompleteIds().split(",");
            int i1 = sysRobotPadNewBindingMapper.deleteByPrimaryKeyNatural(sysRobotPadNew.getPadId());
            for (String padId : padIdS) {
                SysRobotPadNewBinding sysRobotPadNewBinding = new SysRobotPadNewBinding();
                sysRobotPadNewBinding.setId(IdUtils.getSeqId());
                sysRobotPadNewBinding.setPadCompleteId(Long.parseLong(padId));
                sysRobotPadNewBinding.setPadNaturalId(sysRobotPadNew.getPadId());
                sysRobotPadNewBinding.setCreateDate(DateUtil.currentDateTime());
                sysRobotPadNewBinding.setUpdateDate(DateUtil.currentDateTime());
                sysRobotPadNewBindingMapper.insertSelective(sysRobotPadNewBinding);
            }
        }


        return i;
    }

    /**
     * @return int
     * @Author 郭凯
     * @Description 删除PAD
     * @Date 10:16 2020/12/15
     * @Param [padId]
     **/
    @Override
    public int delRobotPadNew(Long padId) {
        SysRobotPadNew robotPadNew = sysRobotPadNewMapper.selectByPrimaryKey(padId);
        int i = sysRobotPadNewMapper.deleteByPrimaryKey(padId);
        int j = sysRobotPadNewSpotMapper.deleteByPrimaryKey(padId);
        if (i == 1) {
            String padUrl = robotPadNew.getPadUrl();
            String result = padUrl.substring(padUrl.lastIndexOf('/') + 1).trim();
            FileUtilOne.deleteServerFile(filePathPadPaht + result);
        }
        return i;
    }

    /**
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysRobotPad>
     * @Author 郭凯
     * @Description 查询所有PAD信息
     * @Date 13:22 2020/12/15
     * @Param []
     **/
    @Override
    public List<SysRobotPadNew> getRobotPadNew() {
        Map<String, String> search = new HashMap<>();
        List<SysRobotPadNew> sysRobotPadNewList = sysRobotPadNewMapper.getRobotMapResList(search);
        return sysRobotPadNewList;
    }

    /**
     * 获取最新版本号
     *
     * @param packageType
     * @return
     */
    @Override
    public SysRobotPadNew getAppPadNumberNew(String packageType) {
        return sysRobotPadNewMapper.getAppPadNumberNew(packageType);
    }

    /**
     * 根据padId获取景区id列表
     *
     * @param padId
     * @return
     */
    @Override
    public PageDataResult getSpotIdByRobotPad(Long padId, Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotPadNew> list = sysRobotPadNewSpotMapper.selectPadIdBySpotId(padId);

        if (list.size() > 0) {
            PageInfo<SysRobotPadNew> padPageInfo = new PageInfo<>(list);
            pageDataResult.setList(list);
            pageDataResult.setTotals((int) padPageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }

    /**
     * 删除绑定景区
     *
     * @param
     * @return
     */
    @Override
    public int delRobotPadSpotId(Long id) {

        int i = sysRobotPadNewSpotMapper.deleteById(id);
        return i;
    }

    /**
     * 获取完整包版本下拉选
     *
     * @return
     */
    @Override
    public List<SysRobotPadNew> getRobotPadEdition() {

        List<SysRobotPadNew> appPadNumberNewList = sysRobotPadNewMapper.getAppPadNumberNewList("1");
        return appPadNumberNewList;

    }
}
