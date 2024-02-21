package com.hna.hka.archive.management.system.service;

import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastExtendWithBLOBs;
import com.hna.hka.archive.management.system.model.SysScenicSpotBroadcastHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHunt;
import com.hna.hka.archive.management.system.model.SysScenicSpotTreasureHuntRule;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.service
 * @ClassName: SysScenicSpotBroadcastHuntService
 * @Author: 郭凯
 * @Description: 寻宝奖品
 * @Date:
 * @Version: 1.0
 */
public interface SysScenicSpotTreasureHuntRuleService {
    /**
     * @Method 查询寻宝及景点列表
     * @Author 郭凯
     * @Version  1.0
     * @Description
     * @Return
     * @Date 2021/12/9 13:00
     */
    PageDataResult getBroadcastRuleList(Integer pageNum, Integer pageSize, SysScenicSpotTreasureHuntRule sysScenicSpotTreasureHuntRule);

    /**
     * @Method 新增寻宝景点
     * @Author 曲绍备
     * @Version  1.0
     * @Description
     * @Return
     * @Date 2021/12/9 18:01
     */
    int addBroadcastRule(SysScenicSpotTreasureHuntRule broadcastHuntRule,MultipartFile file);

    /**
     * @Method 修改寻宝景点
     * @Author 张
     * @Version  1.0
     * @Description
     * @Return
     */
    int editBroadcastRule(SysScenicSpotTreasureHuntRule broadcastHunt,MultipartFile file);
    /**
     * @Method 删除寻宝景点
     * @Author 张
     * @Version  1.0
     * @Description
     * @Return
     */
    int delBroadcastRule(Long broadcastId, Long scenicSpotId);

    /**
     * 查询单个景区寻宝规则
     * @param scenicSpotId
     * @return
     */
    SysScenicSpotTreasureHuntRule selectBroadcastRuleBySpotId(Long scenicSpotId ,String ruleType);
}
