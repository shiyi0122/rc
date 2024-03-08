package com.hna.hka.archive.management.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.appSystem.dao.SysCurrentUserExchangeMapper;
import com.hna.hka.archive.management.system.dao.SysOrderMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotBroadcastHuntExtendMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotResourceVersionMapper;
import com.hna.hka.archive.management.system.dao.SysScenicSpotTreasureHuntMapper;
import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.service.SysScenicSpotTreasureHuntService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class SysScenicSpotTreasureHuntServiceImpl implements SysScenicSpotTreasureHuntService {
    @Autowired
    private SysScenicSpotTreasureHuntMapper sysScenicSpotTreasureHuntMapper;
    @Autowired
    private SysScenicSpotBroadcastHuntExtendMapper sysScenicSpotBroadcastHuntExtendMapper;
    @Autowired
    private SysScenicSpotResourceVersionMapper sysScenicSpotResourceVersionMapper;
    @Autowired
    private FileUploadUtil fileUploadUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysCurrentUserExchangeMapper sysCurrentUserExchangeMapper;
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Value("${GET_BROADCASTHUNT_PIC_PAHT}")
    private String GET_BROADCASTHUNT_PIC_PAHT;
    @Value("${GET_BROADCASTHUNT_PIC_URL}")
    private String GET_BROADCASTHUNT_PIC_URL;

    @Value("${filePathGetExchangePicUrl}")
    private String filePathGetExchangePicUrl;
    @Value("${filePathGetExchangePicPath}")
    private String filePathGetExchangePicPath;


    @Override
    public PageDataResult getTreasureList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(new Date());
        System.out.println(format);
        PageHelper.startPage(pageNum, pageSize);
        List<SysScenicSpotTreasureHunt> treasureHuntList = sysScenicSpotTreasureHuntMapper.getTreasureListNew(search);
        for (SysScenicSpotTreasureHunt treasureHunt : treasureHuntList) {
            try {
                int dates = Integer.parseInt(DateUtil.findDates(format, treasureHunt.getEndValidity()));
                if (dates - 3 <= 0) {
                    treasureHunt.setType(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            treasureHunt.setPrizeWeight(treasureHunt.getPrizeWeight() + "%");
        }
        if (treasureHuntList.size() != 0) {
            PageInfo<SysScenicSpotTreasureHunt> pageInfo = new PageInfo<>(treasureHuntList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Method 新增寻宝景点奖品
     * @Author zhang
     * @Version 1.0
     * @Description
     * @Return
     * @Date
     */
    @Override
    public int addTreasure(SysScenicSpotTreasureHunt treasureHunt, MultipartFile file) {
        Long seqId = IdUtils.getSeqId();
        treasureHunt.setTreasureId(seqId);
        treasureHunt.setPrizeWeight(treasureHunt.getPrizeWeight().split("%")[0]);
        if (file.isEmpty()) {
            if (StringUtils.isEmpty(treasureHunt.getCouponAmount())) {
                treasureHunt.setCouponAmount("0.00");
            }
            treasureHunt.setCreateDate(DateUtil.currentDateTime());
            treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotTreasureHuntMapper.insertSelective(treasureHunt);
            return i;
        } else {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
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
                treasureHunt.setPicUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            }
            if (StringUtils.isEmpty(treasureHunt.getCouponAmount())) {
                treasureHunt.setCouponAmount("0.00");
            }
            treasureHunt.setCreateDate(DateUtil.currentDateTime());
            treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotTreasureHuntMapper.insertSelective(treasureHunt);
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

    /**
     * @Method 修改寻宝景点奖品
     * @Author zhang
     * @Version 1.0
     * @Description
     * @Return
     * @Date 2021/12/9 18:01
     */
    @Override
    public int editTreasure(SysScenicSpotTreasureHunt treasureHunt, MultipartFile file) {

        String prizeWeight = treasureHunt.getPrizeWeight();
        String[] split = prizeWeight.split("%");
        treasureHunt.setPrizeWeight(split[0]);
        treasureHunt.setUpdateDate(DateUtil.currentDateTime());
        if (file.isEmpty()) {
            if (StringUtils.isEmpty(treasureHunt.getCouponAmount())) {
                treasureHunt.setCouponAmount("0.00");
            }
//            treasureHunt.setCreateDate(DateUtil.currentDateTime());
//            treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotTreasureHuntMapper.updateByPrimaryKeySelective(treasureHunt);
            return i;
        } else {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".png") || type.equals(".jpg")) {
                String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
                String path = GET_BROADCASTHUNT_PIC_PAHT + filename;// 存放位置
                File destFile = new File(path);
                try {

                    // 限制大小
                    long size = file.getSize() / 1024;//kb
                    if (size >= 2048) {
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
                treasureHunt.setPicUrl(GET_BROADCASTHUNT_PIC_URL + filename);
            }
            if (StringUtils.isEmpty(treasureHunt.getCouponAmount())) {
                treasureHunt.setCouponAmount("0.00");
            }
            //treasureHunt.setCreateDate(DateUtil.currentDateTime());
            //treasureHunt.setUpdateDate(DateUtil.currentDateTime());
            int i = sysScenicSpotTreasureHuntMapper.updateByPrimaryKeySelective(treasureHunt);
            return i;
        }
//        if (i == 1){
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

    }

    /**
     * @Method 删除寻宝景点奖品
     * @Author zhang
     * @Version 1.0
     * @Description
     * @Return
     */
    @Override
    public int delTreasure(Long treasureId, Long scenicSpotId) {
        int i = sysScenicSpotTreasureHuntMapper.deleteByPrimaryKey(treasureId);
        return i;
    }

    /**
     * 创建奖池
     *
     * @param treasureNewJackpot
     * @return
     */
    @Override
    public int addJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        treasureNewJackpot.setJackpotId(IdUtils.getSeqId());
        treasureNewJackpot.setCreateDate(DateUtil.currentDateTime());
        treasureNewJackpot.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTreasureHuntMapper.addJackpotNew(treasureNewJackpot);
    }

    /**
     * 修改奖池
     *
     * @param treasureNewJackpot
     * @return
     */
    @Override
    public int updateJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        treasureNewJackpot.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTreasureHuntMapper.updateJackpotNew(treasureNewJackpot);
    }

    /**
     * 查看奖池
     *
     * @param treasureNewJackpot
     * @return
     */
    @Override
    public PageInfo<SysScenicSpotTreasureNewJackpot> getJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        PageHelper.startPage(treasureNewJackpot.getPageNum(), treasureNewJackpot.getPageSize());
        List<SysScenicSpotTreasureNewJackpot> jackpotNew = sysScenicSpotTreasureHuntMapper.getJackpotNew(treasureNewJackpot);
        PageInfo<SysScenicSpotTreasureNewJackpot> pageInfo = new PageInfo<>(jackpotNew);
        return pageInfo;
    }

    /**
     * 删除奖池
     *
     * @param treasureNewJackpot
     * @return
     */
    @Override
    public int delectJackpotNew(SysScenicSpotTreasureNewJackpot treasureNewJackpot) {
        sysScenicSpotTreasureHuntMapper.delectJackpotHunt(treasureNewJackpot.getJackpotId());
        return sysScenicSpotTreasureHuntMapper.delectJackpotNew(treasureNewJackpot);
    }

    /**
     * 查询用户积分
     *
     * @param userIntegral
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserIntegral> getUserInteGral(SysCurrentUserIntegral userIntegral) {
        PageHelper.startPage(userIntegral.getPageNum(), userIntegral.getPageSize());
        List<SysCurrentUserIntegral> userInteGrals = sysScenicSpotTreasureHuntMapper.getUserInteGral(userIntegral);
        PageInfo<SysCurrentUserIntegral> pageInfo = new PageInfo<>(userInteGrals);
        return pageInfo;
    }

    /**
     * 查询积分修改日志表
     *
     * @param userIntegralLog
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserIntegralLog> getUserInteGralLog(SysCurrentUserIntegralLog userIntegralLog) {
        PageHelper.startPage(userIntegralLog.getPageNum(), userIntegralLog.getPageSize());
        List<SysCurrentUserIntegralLog> userInteGralLog = sysScenicSpotTreasureHuntMapper.getUserInteGralLog(userIntegralLog);
        PageInfo<SysCurrentUserIntegralLog> pageInfo = new PageInfo<>(userInteGralLog);
        return pageInfo;
    }


    /**
     * 查询用户收货地址表
     *
     * @param userAddress
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserAddress> getUserAddress(SysCurrentUserAddress userAddress) {
        PageHelper.startPage(userAddress.getPageNum(), userAddress.getPageSize());
        List<SysCurrentUserAddress> userAddress1 = sysScenicSpotTreasureHuntMapper.getUserAddress(userAddress);
        PageInfo<SysCurrentUserAddress> pageInfo = new PageInfo<>(userAddress1);
        return pageInfo;
    }

    /**
     * 修改用户收货地址表
     *
     * @param userAddress
     * @return
     */
    @Override
    public int editUserAddress(SysCurrentUserAddress userAddress) {
        return sysScenicSpotTreasureHuntMapper.editUserAddress(userAddress);
    }

    /**
     * 删除用户收货地址表
     *
     * @param userAddress
     * @return
     */
    @Override
    public int delectUserAddress(SysCurrentUserAddress userAddress) {
        return sysScenicSpotTreasureHuntMapper.delectUserAddress(userAddress);
    }

    /**
     * 添加用户收货地址表
     *
     * @param userAddress
     * @return
     */
    @Override
    public int addUserAddress(SysCurrentUserAddress userAddress) {
        userAddress.setAddressId(IdUtils.getSeqId());
        userAddress.setCreateDate(DateUtil.currentDateTime());
        userAddress.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTreasureHuntMapper.addUserAddress(userAddress);
    }

    /**
     * 修改用户积分表
     *
     * @param userIntegral
     * @return
     */
    @Override
    public int updateUserInteGral(SysCurrentUserIntegral userIntegral) {
        SysCurrentUserIntegral userIntegral1 = new SysCurrentUserIntegral();
        SysCurrentUserIntegral userByUserId = sysScenicSpotTreasureHuntMapper.getUserByUserId(userIntegral.getUserId());
        //查询旧的积分数额
        String integral = "0";
        if (userByUserId != null) {
            integral = userByUserId.getIntegral();
        }
        userIntegral1.setIntegralId(userIntegral.getIntegralId());
        userIntegral1.setIntegral(userIntegral.getIntegral());
        userIntegral1.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotTreasureHuntMapper.updateUserInteGral(userIntegral1);
        //添加道缓存
        redisUtil.set(userIntegral.getUserId().toString(), JSONObject.toJSONString(userIntegral));
        //添加积分修改日志
        SysCurrentUserIntegralLog userIntegralLog = new SysCurrentUserIntegralLog();
        SysUsers currentUser = this.getSysUser();
//        if (currentUser == null){
//            return 0;
//        }
        userIntegralLog.setUserAccount(currentUser.getLoginName());
        userIntegralLog.setAfterIntegral(Long.valueOf(integral));
        userIntegralLog.setFrontIntegral(Long.valueOf(userIntegral.getIntegral()));
        userIntegralLog.setIntegralLogId(IdUtils.getSeqId());
        userIntegralLog.setUserId(userIntegral.getUserId());
        userIntegralLog.setCreateDate(DateUtil.currentDateTime());
        userIntegralLog.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotTreasureHuntMapper.insertUserInteGralLog(userIntegralLog);
        return i;
    }

    /**
     * 查询用户奖品
     *
     * @param userExchange
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserExchange> getUserExchange(SysCurrentUserExchange userExchange) {
        PageHelper.startPage(userExchange.getPageNum(), userExchange.getPageSize());
        List<SysCurrentUserExchange> userExchanges = sysScenicSpotTreasureHuntMapper.getUserExchange(userExchange);
        PageInfo<SysCurrentUserExchange> pageInfo = new PageInfo<>(userExchanges);
        return pageInfo;
    }

    /**
     * 修改用户奖品
     *
     * @param file
     * @param userExchange
     * @return
     */
    @Override
    public int editUserExchange(MultipartFile file, SysCurrentUserExchange userExchange) {

//        if (file != null) {
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
//            if (type.equals(".jpg") || type.equals(".png")) {
//                String filename = System.currentTimeMillis() + type;
//                String path = filePathGetExchangePicPath + filename;// 存放位置
//                File destFile = new File(path);
//                try {
//                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
//                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//
//                    //阿里OSS文件存储_图片上传
//                    String upload = fileUploadUtil.upload(file, filePathGetExchangePicPath.substring(1) + filename);
//                    System.out.println("奖品图片上传地址:" + upload);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                userExchange.setPicUrl(filePathGetExchangePicUrl + filename);
//            }
//        }
        SysCurrentUserExchange userExchangeId = sysScenicSpotTreasureHuntMapper.getUserExchangeId(userExchange.getExchangeId());
        if (("0").equals(userExchangeId.getShipmentStatus())) {
            return -1;
        }
        userExchange.setUpdateDate(DateUtil.currentDateTime());

        //添加到用户兑换奖品日志
        SysCurrentUserExchangeLog exchangeLog = new SysCurrentUserExchangeLog();
        SysUsers sysUser = this.getSysUser();
        exchangeLog.setAccountName(sysUser.getLoginName());
        exchangeLog.setExchangeLogId(IdUtils.getSeqId());
        exchangeLog.setUserId(userExchange.getUserId());
        //奖品id
        exchangeLog.setPrizeId(userExchange.getExchangeId());
        exchangeLog.setScenicSpotId(userExchange.getScenicSpotId());
        exchangeLog.setExchangePort("2");
        exchangeLog.setOperationalState(userExchange.getShipmentStatus());
        exchangeLog.setCreateDate(DateUtil.currentDateTime());
        exchangeLog.setUpdateDate(DateUtil.currentDateTime());
        sysCurrentUserExchangeMapper.addExchangeLog(exchangeLog);

        return sysScenicSpotTreasureHuntMapper.editUserExchange(userExchange);
    }


    /**
     * 查询宝箱
     *
     * @param broadcastHunt
     * @return
     */
    @Override
    public PageInfo<SysScenicSpotBroadcastHunt> getBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt) {
        PageHelper.startPage(broadcastHunt.getPageNum(), broadcastHunt.getPageSize());
        List<SysScenicSpotBroadcastHunt> broadcastHunts = sysScenicSpotTreasureHuntMapper.getBroadcastHunt(broadcastHunt);
        PageInfo<SysScenicSpotBroadcastHunt> pageInfo = new PageInfo<>(broadcastHunts);
        return pageInfo;
    }

    /**
     * 修改宝箱
     *
     * @param broadcastHunt
     * @return
     */
    @Override
    public int editBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt) {
        broadcastHunt.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTreasureHuntMapper.editBroadcastHunt(broadcastHunt);
    }

    /**
     * 添加宝箱
     *
     * @param broadcastHunt
     * @return
     */
    @Override
    public int addBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt) {
        if (broadcastHunt.getIntegralNum() == null) {
            broadcastHunt.setIntegralNum(Long.valueOf(broadcastHunt.getTreasureType()));
        }
        broadcastHunt.setBroadcastId(IdUtils.getSeqId());
        broadcastHunt.setCreateDate(DateUtil.currentDateTime());
        broadcastHunt.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotTreasureHuntMapper.addBroadcastHunt(broadcastHunt);
    }

    /**
     * 删除宝箱
     *
     * @param broadcastHunt
     * @return
     */
    @Override
    public int delectBroadcastHunt(SysScenicSpotBroadcastHunt broadcastHunt) {
        return sysScenicSpotTreasureHuntMapper.delectBroadcastHunt(broadcastHunt);
    }

    /**
     * 查询用户触发宝箱日志记录
     *
     * @param broadcastHuntLog
     * @return
     */
    @Override
    public PageDataResult getBroadcastHuntLog(SysScenicSpotBroadcastHuntLog broadcastHuntLog) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(broadcastHuntLog.getPageNum(), broadcastHuntLog.getPageSize());
        List<SysScenicSpotBroadcastHuntLog> broadcastHuntLog1 = sysScenicSpotTreasureHuntMapper.getBroadcastHuntLog(broadcastHuntLog);
        if (broadcastHuntLog1.size() != 0) {
            PageInfo<SysScenicSpotBroadcastHuntLog> pageInfo = new PageInfo<>(broadcastHuntLog1);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 查询奖品列表
     *
     * @param treasureNewHunt
     * @return
     */
    @Override
    public PageInfo<SysScenicSpotTreasureNewHunt> getTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt) {
        PageHelper.startPage(treasureNewHunt.getPageNum(), treasureNewHunt.getPageSize());
        List<SysScenicSpotTreasureNewHunt> treasureNewHunts = sysScenicSpotTreasureHuntMapper.getTreasureNewHunt(treasureNewHunt);
        for (int i = 0; i < treasureNewHunts.size(); i++) {
            BigDecimal decimal = new BigDecimal(treasureNewHunts.get(i).getProbability());
            double doubleValue = decimal.stripTrailingZeros().doubleValue();
            treasureNewHunts.get(i).setProbability(doubleValue);
            treasureNewHunts.get(i).setProbability1(String.valueOf(doubleValue));
        }
        PageInfo<SysScenicSpotTreasureNewHunt> pageInfo = new PageInfo<>(treasureNewHunts);
        return pageInfo;
    }


    /**
     * 新增奖品
     *
     * @param file
     * @param treasureNewHunt
     * @return
     */
    @Override
    public int addTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt) {
        if (!file.isEmpty()) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;
                String path = filePathGetExchangePicPath + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, filePathGetExchangePicPath.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                treasureNewHunt.setPicUrl(filePathGetExchangePicUrl + filename);
            }
        }
        if (treasureNewHunt.getCouponAmount() == null || ("undefined").equals(treasureNewHunt.getCouponAmount())) {
            treasureNewHunt.setCouponAmount("0.00");
        }
        treasureNewHunt.setTreasureId(IdUtils.getSeqId());
        treasureNewHunt.setCreateDate(DateUtil.currentDateTime());
        treasureNewHunt.setUpdateDate(DateUtil.currentDateTime());
        treasureNewHunt.setProbability(Double.valueOf(treasureNewHunt.getProbability1()));
        return sysScenicSpotTreasureHuntMapper.addTreasureNewHunt(treasureNewHunt);
    }

    /**
     * 修改奖品
     *
     * @param file
     * @param treasureNewHunt
     * @return
     */
    @Override
    public int editTreasureNewHunt(MultipartFile file, SysScenicSpotTreasureNewHunt treasureNewHunt) {
        if (file != null) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
            if (type.equals(".jpg") || type.equals(".png")) {
                String filename = System.currentTimeMillis() + type;
                String path = filePathGetExchangePicPath + filename;// 存放位置
                File destFile = new File(path);
                try {
                    //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                    FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

                    //阿里OSS文件存储_图片上传
                    String upload = fileUploadUtil.upload(file, filePathGetExchangePicPath.substring(1) + filename);
                    System.out.println(upload);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                treasureNewHunt.setPicUrl(filePathGetExchangePicUrl + filename);
            }
        }
        if (treasureNewHunt.getCouponAmount() == null || ("undefined").equals(treasureNewHunt.getCouponAmount())) {
            treasureNewHunt.setCouponAmount("0.00");
        }
        treasureNewHunt.setProbability(Double.valueOf(treasureNewHunt.getProbability1()));
        treasureNewHunt.setUpdateDate(DateUtil.currentDateTime());
        //添加修改奖品日志表
        this.addTreasureNewHuntLog(treasureNewHunt);
        return sysScenicSpotTreasureHuntMapper.editTreasureNewHunt(treasureNewHunt);
    }

    public void addTreasureNewHuntLog(SysScenicSpotTreasureNewHunt treasureNewHunt) {
        //添加修改奖品日志表
        SysScenicSpotTreasureNewHunt treasureId = sysScenicSpotTreasureHuntMapper.getTreasureId(treasureNewHunt.getTreasureId());
        SysCurrentUserUpdateTreasureLog updateTreasureLog = new SysCurrentUserUpdateTreasureLog();
        updateTreasureLog.setIntegralLogId(IdUtils.getSeqId());
        //后台登录账号
        updateTreasureLog.setUserAccount(this.getSysUser().getLoginName());
        //修改权重
        updateTreasureLog.setFrontProbability(treasureId.getProbability().toString());
        updateTreasureLog.setAfterProbability(treasureNewHunt.getProbability().toString());
        //修改奖品名称
        updateTreasureLog.setFrontTreasureName(treasureId.getTreasureName());
        updateTreasureLog.setAfterTreasureName(treasureNewHunt.getTreasureName());
        //修改奖品类型0普通奖品 1大奖
        updateTreasureLog.setFrontTreasureType(treasureId.getTreasureType());
        updateTreasureLog.setAfterTreasureType(treasureNewHunt.getTreasureType());
        //修改邮寄方式 1邮寄方式 2现场兑换方式
        updateTreasureLog.setAfterWayType(treasureId.getWayType());
        updateTreasureLog.setFrontWayType(treasureNewHunt.getWayType());
        //修改类型  1代金券 2 免单  3实物 4谢谢参与
        updateTreasureLog.setFrontType(treasureId.getTreasureType());
        updateTreasureLog.setAfterType(treasureNewHunt.getTreasureType());
        //修改优惠卷额度
        updateTreasureLog.setFrontCoupon(treasureId.getCouponAmount());
        updateTreasureLog.setAfterCoupon(treasureNewHunt.getCouponAmount());
        //创建时间更新时间
        updateTreasureLog.setCreateDate(DateUtil.currentDateTime());
        updateTreasureLog.setUpdateDate(DateUtil.currentDateTime());
        sysScenicSpotTreasureHuntMapper.addTreasureNewHuntLog(updateTreasureLog);
    }

    /**
     * 删除奖品
     *
     * @param treasureNewHunt
     * @return
     */
    @Override
    public int delectTreasureNewHunt(SysScenicSpotTreasureNewHunt treasureNewHunt) {
        return sysScenicSpotTreasureHuntMapper.delectTreasureNewHunt(treasureNewHunt);
    }

    /**
     * 用户获得某项奖品日志记录
     *
     * @param integralHuntLog
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserIntegralHuntLog> getUserIntegralHuntLog(SysCurrentUserIntegralHuntLog integralHuntLog) {
        PageHelper.startPage(integralHuntLog.getPageNum(), integralHuntLog.getPageSize());
        List<SysCurrentUserIntegralHuntLog> userIntegralHuntLog = sysScenicSpotTreasureHuntMapper.getUserIntegralHuntLog(integralHuntLog);
        PageInfo<SysCurrentUserIntegralHuntLog> pageInfo = new PageInfo<>(userIntegralHuntLog);
        return pageInfo;
    }

    /**
     * 根据ID查询景区
     *
     * @param spotId
     * @return
     */
    @Override
    public List<SysScenicSpot> getSpotId(Long spotId) {
        return sysScenicSpotTreasureHuntMapper.getSpotId(spotId);
    }

    /**
     * 景点查询
     *
     * @param broadcastId
     * @param scenicSpotId
     * @return
     */
    @Override
    public List<SysScenicSpotBroadcast> getbroadCast(Long broadcastId, Long scenicSpotId) {
        return sysScenicSpotTreasureHuntMapper.getbroadCast(broadcastId, scenicSpotId);
    }

    /**
     * 查询用户兑换奖品日志
     *
     * @param userExchangeLog
     * @return
     */
    @Override
    public PageInfo<SysCurrentUserExchangeLog> getUserExchangeLog(SysCurrentUserExchangeLog userExchangeLog) {
        PageHelper.startPage(userExchangeLog.getPageNum(), userExchangeLog.getPageSize());
        List<SysCurrentUserExchangeLog> userExchangeLogs = sysScenicSpotTreasureHuntMapper.getUserExchangeLog(userExchangeLog);
        PageInfo<SysCurrentUserExchangeLog> pageInfo = new PageInfo<>(userExchangeLogs);
        return pageInfo;
    }

    @Override
    public PageInfo<SysCurrentUserUpdateTreasureLog> getUpdateTreasureLog(SysCurrentUserUpdateTreasureLog userUpdateTreasureLog) {
        PageHelper.startPage(userUpdateTreasureLog.getPageNum(), userUpdateTreasureLog.getPageSize());
        List<SysCurrentUserUpdateTreasureLog> userUpdateTreasureLogs = sysScenicSpotTreasureHuntMapper.getUpdateTreasureLog(userUpdateTreasureLog);
        PageInfo<SysCurrentUserUpdateTreasureLog> pageInfo = new PageInfo<>(userUpdateTreasureLogs);
        return pageInfo;
    }

    @Override
    public List<SysCurrentUserExchange> downloadUserExchange(SysCurrentUserExchange userExchange) {
        return sysScenicSpotTreasureHuntMapper.getUserExchange(userExchange);
    }


    @Override
    public PageInfo<SysOrderDetail> getTreasureHuntDetail(SysOrder sysOrder) throws ParseException {
        PageHelper.startPage(sysOrder.getPageNum(), sysOrder.getPageSize());
        //查询类型 0日 1月 2年
        if (("2").equals(sysOrder.getDateType())) {
            sysOrder.setOrderStartTime(DateUtil.getYearDate(sysOrder.getOrderStartTime()));
            sysOrder.setOrderEndTime(DateUtil.getYearDate(sysOrder.getOrderEndTime()));
        } else if (("1").equals(sysOrder.getDateType())) {
            sysOrder.setOrderStartTime(DateUtil.getYearsDate(sysOrder.getOrderStartTime()));
            sysOrder.setOrderEndTime(DateUtil.getYearsDate(sysOrder.getOrderEndTime()));
        }
        List<SysOrderDetail> treasureHuntDetail = sysScenicSpotTreasureHuntMapper.getTreasureHuntDetail(sysOrder);
        for (SysOrderDetail sysOrderDetail : treasureHuntDetail) {
            //查询景区是否开启寻宝
            SysScenicSpot huntsState = sysOrderMapper.getHuntsState(Long.valueOf(sysOrderDetail.getScenicSpotId()));
            //如果开启寻宝，寻宝订单数和总订单数保持一致，客单价和寻宝客单价保持一致
            if (("1").equals(huntsState.getHuntSwitch())) {
                sysOrderDetail.setHuntOrder(sysOrderDetail.getAllOrder());
                sysOrderDetail.setHuntProportion("100");
                //客单价和寻宝客单价保持一致
                sysOrderDetail.setHuntPerCustomerTransaction(sysOrderDetail.getPerCustomerTransaction());
            }
            if (sysOrderDetail.getHuntPerCustomerTransaction() == null || sysOrderDetail.getHuntPerCustomerTransaction().equals("")) {
                sysOrderDetail.setHuntPerCustomerTransaction("0.00");
            }
            if (sysOrderDetail.getLotteryPerCustomerTransaction() == null || sysOrderDetail.getLotteryPerCustomerTransaction().equals("")) {
                sysOrderDetail.setLotteryPerCustomerTransaction("0.00");
            }
            sysOrderDetail.setHuntProportion(sysOrderDetail.getHuntProportion() + "%");
            sysOrderDetail.setLotteryProportion(sysOrderDetail.getLotteryProportion() + "%");
        }
        return new PageInfo<>(treasureHuntDetail);
    }


//    @Override
//    public List<SysScenicSpotBroadcastExtendWithBLOBs> getTreasureExcel(Map<String, Object> search) {
//        return sysScenicSpotTreasureHuntMapper.getTreasureExcel(search);
//    }

    public SysUsers getSysUser() {
        SysUsers user = (SysUsers) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
