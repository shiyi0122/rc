package com.hna.hka.archive.management.system.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotBroadcastHuntService
 * @Author: 郭凯
 * @Description: 寻宝
 * @Date: 2021/12/9 11:21
 * @Version: 1.0
 */
public interface SysScenicSpotBroadcastHuntService {
    /**
     * @Method 查询寻宝及景点列表
     * @Author 郭凯
     * @Version  1.0
     * @Description
     * @Return
     * @Date 2021/12/9 13:00
     */
    PageInfo<SysScenicSpotBroadcastHunt> getBroadcastList(Integer pageNum, Integer pageSize, SysScenicSpotBroadcastHunt sysScenicSpotBroadcastHunt);

    /**
     * @Method 新增寻宝景点
     * @Author 曲绍备
     * @Version  1.0
     * @Description 
     * @Return 
     * @Date 2021/12/9 18:01
     */
    int addBroadcast(SysScenicSpotBroadcastHunt broadcastHunt);

    /**
     * @Method 修改寻宝景点
     * @Author 张
     * @Version  1.0
     * @Description
     * @Return
     */
    int editBroadcast(SysScenicSpotBroadcastHunt broadcastHunt);
    /**
     * @Method 删除寻宝景点
     * @Author 张
     * @Version  1.0
     * @Description
     * @Return
     */
    int delBroadcast(Long broadcastId, Long scenicSpotId);
    /**
     * @Method 启用/修改寻宝景点
     * @Author 张
     * @Version  1.0
     * @Description
     * @Return
     */
    int switchBroadcast(SysScenicSpotBroadcastHunt broadcastHunt);
    /**
     * 景点内容详情列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getBroadcastContentHuntList(Integer pageNum, Integer pageSize, Map<String, Object> search);
    /**
     * 活动景点内容新增（文字）
     * @param
     * @param
     * @param
     * @return
     */
    int addBroadcastHuntContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    /**
     * 活动景点内容新增（视频）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int addBroadcastHuntContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
    /**
     * 活动景点内容新增（音频）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int addBroadcastHuntContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
    /**
     * 景点内容修改（文字）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int editBroadcastHuntContentExtendImage(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
    /**
     * 景点内容修改（视频）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int editBroadcastHuntContentExtendVideo(MultipartFile file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
    /**
     * 景点内容修改（视频）
     * @param file
     * @param sysScenicSpotBroadcastExtendWithBLOBs
     * @return
     */
    int editBroadcastHuntContentExtendAudio(MultipartFile[] file, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
    /**
     * 景点内容删除
     */
    int delBroadcastHuntContentExtend(Long broadcastResId);

    /**
     * 下载寻宝景点表
     * @param search
     * @return
     */
    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastExcel(Map<String, Object> search);

    int addBroadcastHuntContentExtendAudioNew(MultipartFile fileAudio, MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);

    int editBroadcastHuntContentExtendAudioNew(MultipartFile fileAudio, MultipartFile fileImage, SysScenicSpotBroadcastExtendWithBLOBs sysScenicSpotBroadcastExtendWithBLOBs);
}
