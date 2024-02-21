package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotWarningMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotWarning;
import com.hna.hka.archive.management.system.model.WarningExcel;
import com.hna.hka.archive.management.system.service.SysScenicSpotWarningService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotWarningServiceImpl
 * @Author: 郭凯
 * @Description: 警告管理业务层（实现）
 * @Date: 2020/7/25 13:07
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotWarningServiceImpl implements SysScenicSpotWarningService {

    @Autowired
    private SysScenicSpotWarningMapper sysScenicSpotWarningMapper;

    @Value("${warningPicUrl}")
    private String warningPicUrl;

    @Value("${warningPicPaht}")
    private String warningPicPaht;

    /**
     * @Author 郭凯
     * @Description 警告管理列表查询
     * @Date 13:33 2020/7/25
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getWarningList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotWarning> sysScenicSpotWarningList = sysScenicSpotWarningMapper.getWarningList(search);
        if (sysScenicSpotWarningList.size() != 0){
            PageInfo<SysScenicSpotWarning> pageInfo = new PageInfo<>(sysScenicSpotWarningList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 警告信息新增
     * @Date 14:13 2020/7/25
     * @Param [file, sysScenicSpotWarning]
     * @return int
    **/
    @Override
    public int addWarning(MultipartFile file,SysScenicSpotWarning sysScenicSpotWarning) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".png") || type.equals(".jpg")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = warningPicPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
            sysScenicSpotWarning.setWarningId(IdUtils.getSeqId());
            sysScenicSpotWarning.setWarningPic(warningPicUrl + filename);
            sysScenicSpotWarning.setCreateDate(DateUtil.currentDateTime());
            sysScenicSpotWarning.setUpdateDate(DateUtil.currentDateTime());
            return sysScenicSpotWarningMapper.insertSelective(sysScenicSpotWarning);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 删除警告
     * @Date 9:51 2020/7/26
     * @Param [warningId]
     * @return int
    **/
    @Override
    public int delWarning(Long warningId) {
        return sysScenicSpotWarningMapper.deleteByPrimaryKey(warningId);
    }

    @Override
    public int upWarningExcel(WarningExcel warn) {
        return sysScenicSpotWarningMapper.upWarningExcel(warn);
    }

    @Override
    public int addWarningExcel(WarningExcel warn) {
        return sysScenicSpotWarningMapper.addWarningExcel(warn);
    }

    @Override
    public WarningExcel seLWarning(WarningExcel warn) {
       return sysScenicSpotWarningMapper.selWarning(warn);
    }

    @Override
    public List<WarningExcel> getWarningExcel(Map<String, Object> search) {
        return sysScenicSpotWarningMapper.getWarningExcel(search);
    }

    /**
     * @Author 郭凯
     * @Description 警告信息修改
     * @Date 14:10 2020/7/26
     * @Param [file, sysScenicSpotWarning]
     * @return int
    **/
    @Override
    public int editWarning(MultipartFile file, SysScenicSpotWarning sysScenicSpotWarning) {
        if(!file.isEmpty()){
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = warningPicPaht + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }//复制文件到指定目录
                sysScenicSpotWarning.setWarningPic(warningPicUrl + filename);
            }else {
                return 2;
            }
        }
        sysScenicSpotWarning.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotWarningMapper.updateByPrimaryKeySelective(sysScenicSpotWarning);
    }
}