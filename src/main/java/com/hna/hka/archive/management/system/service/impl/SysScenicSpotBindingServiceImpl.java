package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.ChinaMap;
import com.hna.hka.archive.management.business.dao.BusinessWorldAreaMapper;
import com.hna.hka.archive.management.business.model.BusinessWorldArea;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBindingMapper;
import com.hna.hka.archive.management.system.model.SysRobotAdvertising;
import com.hna.hka.archive.management.system.model.SysScenicSpotBinding;
import com.hna.hka.archive.management.system.service.SysScenicSpotBindingService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysScenicSpotBindingServiceImpl
 * @Author: 郭凯
 * @Description: 景区归属地业务层（实现）
 * @Date: 2020/5/24 0:03
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotBindingServiceImpl implements SysScenicSpotBindingService {


    @Autowired
    private SysScenicSpotBindingMapper sysScenicSpotBindingMapper;

    @Autowired
    private BusinessWorldAreaMapper businessWorldAreaMapper;


    @Value("${GET_BROADCAST_PIC_URL}")
    private String getBroadcastPicUrl;

    @Value("${GET_BROADCAST_PIC_PAHT}")
    private String getBroadcastpicPaht;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author 郭凯
     * @Description 景区归属地列表查询
     * @Date 0:04 2020/5/24
     * @Param [pageNum, pageSize, sysScenicSpotBinding]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getScenicSpotBindingList(Integer pageNum, Integer pageSize, SysScenicSpotBinding sysScenicSpotBinding) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotBinding> scenicSpotBinding = sysScenicSpotBindingMapper.getScenicSpotBindingList(sysScenicSpotBinding);
        if(scenicSpotBinding.size() != 0){
            PageInfo<SysScenicSpotBinding> pageInfo = new PageInfo<>(scenicSpotBinding);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增景区归属地
     * @Date 0:19 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return int
     **/
    @Override
    public int addScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding, MultipartFile file) {

        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名

        if (type.equals(".png") || type.equals(".jpg")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = getBroadcastpicPaht + filename;// 存放位置
            File destFile = new File(path);
            try {

                // 限制大小
                long size = file.getSize() / 1024;//kb
                if (size >= 3072){
                    return 3;
                }
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image.getHeight() > 1120 || image.getHeight() < 600) {  //判断图片是否是在分辨率范围内，非水平分辨率，非垂直分辨率
                    return 4;
                }

                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//                String upload = fileUploadUtil.upload(file, getBroadcastpicPaht.substring(1) + filename);

            } catch (IOException e) {
                e.printStackTrace();
            }
//            BusinessWorldArea businessWorldArea =  businessWorldAreaMapper.selectByPrimaryKey(Integer.parseInt(sysScenicSpotBinding.getScenicSpotFname()));

//            sysScenicSpotBinding.setScenicSpotFname(businessWorldArea.getShortName());
            sysScenicSpotBinding.setCityPic(getBroadcastPicUrl+filename);
            sysScenicSpotBinding.setScenicSpotFid(IdUtils.getSeqId());
            return sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);
        }else {
            return 2;
        }

//        sysScenicSpotBinding.setScenicSpotFid(IdUtils.getSeqId());
//        return sysScenicSpotBindingMapper.insertSelective(sysScenicSpotBinding);
    }

    /**
     * @Author 郭凯
     * @Description 编辑景区归属地
     * @Date 0:38 2020/5/24
     * @Param [sysScenicSpotBinding]
     * @return int
    **/
    @Override
    public int editScenicSpotBinding(SysScenicSpotBinding sysScenicSpotBinding, MultipartFile file) {
        if(!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = getBroadcastpicPaht + filename;// 存放位置
                File destFile = new File(path);

                try {
                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 3072){
                        return 3;
                    }
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image.getHeight() > 1120 || image.getHeight() < 600) {  //判断图片是否是在分辨率范围内，非水平分辨率，非垂直分辨率
                        return 4;
                    }
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//                  String upload = fileUploadUtil.upload(file, getBroadcastpicPaht.substring(1) + filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sysScenicSpotBinding.setCityPic(getBroadcastPicUrl+filename);
            }else{
                return 2;
            }
        }


        return sysScenicSpotBindingMapper.updateByPrimaryKeySelective(sysScenicSpotBinding);
    }

    /**
     * @Author 郭凯
     * @Description 删除景区归属地
     * @Date 0:41 2020/5/24
     * @Param [scenicSpotFid]
     * @return int
    **/
    @Override
    public int delScenicSpotBinding(Long scenicSpotFid) {
        return sysScenicSpotBindingMapper.deleteByPrimaryKey(scenicSpotFid);
    }

    /**
     * @Author 郭凯
     * @Description 查询所有景区
     * @Date 15:04 2020/11/3
     * @Param []
     * @return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBinding>
    **/
    @Override
    public List<SysScenicSpotBinding> getScenicSpotBinding() {
        return sysScenicSpotBindingMapper.getScenicSpotRole();
    }

    /**
     * @Method getChinaMapList
     * @Author 郭凯
     * @Version  1.0
     * @Description 获取景区运营数据，只获取本月数据
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.ChinaMap>
     * @Date 2021/5/31 18:01
     */
    @Override
    public List<ChinaMap> getChinaMapList() {
        List<ChinaMap> chinaMap = sysScenicSpotBindingMapper.getChinaMapList();
        for(ChinaMap ChinaMap : chinaMap){
            if (ToolUtil.isNotEmpty(ChinaMap.getOperateTime())){
                int hour = (ChinaMap.getOperateTime())/60;
                ChinaMap.setOperateTime(hour);
            }
        }
        return chinaMap;
    }

    /**
     * @Method getScenicSpotById
     * @Author 郭凯
     * @Version  1.0
     * @Description 根据景区Fid查询景区
     * @Return java.util.List<com.hna.hka.archive.management.system.model.SysScenicSpotBinding>
     * @Date 2021/6/18 14:04
     */
    @Override
    public List<SysScenicSpotBinding> getScenicSpotById(String id) {
        return sysScenicSpotBindingMapper.getScenicSpotById(id);
    }

    /**
     * 归属省下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingProvince(String pid) {

        return sysScenicSpotBindingMapper.getSpotBindingProvince(pid);

    }
    /**
     * 归属市下拉选
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingCity(String pid) {

      return   sysScenicSpotBindingMapper.getSpotBindingCity(pid);

    }

    /**
     * 归属区下拉选
     * @param pid
     * @return
     */
    @Override
    public List<SysScenicSpotBinding> getSpotBindingArea(String pid) {
        return sysScenicSpotBindingMapper.getSpotBindingArea(pid);
    }
}
