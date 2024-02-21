package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.hna.hka.archive.management.assetsSystem.dao.SysScenicSpotOperationRulesMapper;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules;
import com.hna.hka.archive.management.assetsSystem.service.SysScenicSpotOperationRulesService;
import com.hna.hka.archive.management.system.util.DateUtil;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysScenicSpotOperationRulesServiceImpl
 * @Author: 郭凯
 * @Description: 景区运营规则业务层（实现）
 * @Date: 2021/6/27 16:03
 * @Version: 1.0
 */
@Service
@Transactional
public class SysScenicSpotOperationRulesServiceImpl implements SysScenicSpotOperationRulesService {

    @Autowired
    private SysScenicSpotOperationRulesMapper sysScenicSpotOperationRulesMapper;


    /**
     * @Method editOperationRules
     * @Author 郭凯
     * @Version  1.0
     * @Description 编辑景区运营规则
     * @Return int
     * @Date 2021/6/27 16:13
     */
    @Override
    public int editOperationRules(SysScenicSpotOperationRules scenicSpotOperationRules) throws ParseException {
        scenicSpotOperationRules.setOperatingTime(String.valueOf(DateUtil.getMinutes(scenicSpotOperationRules.getOperateStartTime(),scenicSpotOperationRules.getOperateEndTime())));
        scenicSpotOperationRules.setUpdateDate(DateUtil.currentDateTime());
        return sysScenicSpotOperationRulesMapper.updateByPrimaryKeySelective(scenicSpotOperationRules);
    }

    /**
     * @Method getOperationRulesList
     * @Author 郭凯
     * @Version  1.0
     * @Description 查询计费规则列表
     * @Return java.util.List<com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules>
     * @Date 2021/7/5 10:45
     */
    @Override
    public List<SysScenicSpotOperationRules> getOperationRulesList(Map<String,String> search) {
        List<SysScenicSpotOperationRules> scenicSpotOperationRulesList = sysScenicSpotOperationRulesMapper.getOperationRulesList(search);
        return scenicSpotOperationRulesList;
    }
}
