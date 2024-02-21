package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysScenicSpotDateTreasureHuntMapper;
import com.hna.hka.archive.management.system.model.SysScenicSpotDateTreasureHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHunt;
import com.hna.hka.archive.management.system.service.SysScenicSpotDateTreasureHuntService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.system.util.FileUploadUtil;
import com.hna.hka.archive.management.system.util.IdUtils;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/11 15:21
 */
@Service
public class SysScenicSpotDateTreasureHuntServiceImpl implements SysScenicSpotDateTreasureHuntService {

    @Autowired
    SysScenicSpotDateTreasureHuntMapper sysScenicSpotDateTreasureHuntMapper;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    @Value("${GET_BROADCASTHUNT_PIC_PAHT}")
    private String GET_BROADCASTHUNT_PIC_PAHT;
    @Value("${GET_BROADCASTHUNT_PIC_URL}")
    private String GET_BROADCASTHUNT_PIC_URL;


    @Override
    public PageDataResult getDateTreasureList(Integer pageNum, Integer pageSize, Map<String, String> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotDateTreasureHunt> dateTreasureHuntList = sysScenicSpotDateTreasureHuntMapper.getDateTreasureListNew(search);
        if(dateTreasureHuntList.size() != 0){
            PageInfo<SysScenicSpotDateTreasureHunt> pageInfo = new PageInfo<>(dateTreasureHuntList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }


    @Override
    public int addDateTreasure(SysScenicSpotDateTreasureHunt dateTreasureHunt, MultipartFile file) {

        Long seqId = IdUtils.getSeqId();
        dateTreasureHunt.setTreasureId(seqId);
        if (file.isEmpty()){
            if (StringUtils.isEmpty(dateTreasureHunt.getCouponAmount())){
                dateTreasureHunt.setCouponAmount("0.00");
            }
            dateTreasureHunt.setCreateDate(DateUtil.currentDateTime());
            dateTreasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotDateTreasureHuntMapper.insertSelective(dateTreasureHunt);
            return i;
        }else{
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048){
                        return 3;
                    }

                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //压缩上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);

                    //阿里OSS文件存储_上传图片
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dateTreasureHunt.setPicUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            }
            if (StringUtils.isEmpty(dateTreasureHunt.getCouponAmount())){
                dateTreasureHunt.setCouponAmount("0.00");
            }
            dateTreasureHunt.setCreateDate(DateUtil.currentDateTime());
            dateTreasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotDateTreasureHuntMapper.insertSelective(dateTreasureHunt);
//        if (i == 1) {
//            //更新版本号
//            SysScenicSpotResourceVersion sysScenicSpotResourceVersion = new SysScenicSpotResourceVersion();
//            sysScenicSpotResourceVersion.setScenicSpotId(treasureHunt.getScenicSpotId());
//            sysScenicSpotResourceVersion.setResType("2");
//            //查询版本号
//            SysScenicSpotResourceVersion resourceVersion = sysScenicSpotResourceVersionMapper.selectResourceVersion(sysScenicSpotResourceVersion);
//            double resVersion = Double.parseDouble(resourceVersion.getResVersion());
//            double s = 0.1;
//            BigDecimal p1 = new BigDecimal(Double.toString(resVersion));
//            BigDecimal p2 = new BigDecimal(Double.toString(s));
//            double a = p1.add(p2).doubleValue();
//            SysScenicSpotResourceVersion sysScenicSpotResourceVersion2 = new SysScenicSpotResourceVersion();
//            sysScenicSpotResourceVersion2.setScenicSpotId(treasureHunt.getScenicSpotId());
//            sysScenicSpotResourceVersion2.setResType("2");
//            sysScenicSpotResourceVersion2.setResVersion(String.valueOf(a));
//            sysScenicSpotResourceVersion2.setUpdateDate(DateUtil.currentDateTime());
//            //修改版本号
//            sysScenicSpotResourceVersionMapper.updateResourceVersion(sysScenicSpotResourceVersion2);
//        }
            return i;
        }
    }

    @Override
    public int editDateTreasure(SysScenicSpotDateTreasureHunt datetreasureHunt, MultipartFile file) {
        datetreasureHunt.setUpdateDate(DateUtil.currentDateTime());
        if (file.isEmpty()){
            if (StringUtils.isEmpty(datetreasureHunt.getCouponAmount())){
                datetreasureHunt.setCouponAmount("0.00");
            }
//            treasureHunt.setCreateDate(DateUtil.currentDateTime());
//            treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotDateTreasureHuntMapper.updateByPrimaryKeySelective(datetreasureHunt);
            return i;
        }else{
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048){
                        return 3;
                    }

                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
                    //压缩图片上传
//                    CompressUtils.compress(file.getInputStream(),destFile,10000);


                    //OSS文件存储_图片保存
//                    String upload = fileUploadUtil.upload(file, GET_BROADCASTHUNT_PIC_PAHT.substring(1) + filename);
//                    System.out.println(upload);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                datetreasureHunt.setPicUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            }
            if (StringUtils.isEmpty(datetreasureHunt.getCouponAmount())){
                datetreasureHunt.setCouponAmount("0.00");
            }
            //treasureHunt.setCreateDate(DateUtil.currentDateTime());
            //treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotDateTreasureHuntMapper.updateByPrimaryKeySelective(datetreasureHunt);
            return i;
        }
    }

    @Override
    public int delDateTreasure(Long treasureId, Long scenicSpotId) {
        int i = sysScenicSpotDateTreasureHuntMapper.deleteByPrimaryKey(treasureId);
        return i;
    }
}
