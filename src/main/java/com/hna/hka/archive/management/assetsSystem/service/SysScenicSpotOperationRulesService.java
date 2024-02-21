package com.hna.hka.archive.management.assetsSystem.service;

import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.SysScenicSpotOperationRules;
import com.hna.hka.archive.management.wenYuRiverInterface.model.BaseQueryVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service
 * @ClassName: SysScenicSpotOperationRulesService
 * @Author: 郭凯
 * @Description: 景区运营规则业务层（接口）
 * @Date: 2021/6/27 16:02
 * @Version: 1.0
 */
public interface SysScenicSpotOperationRulesService {

    int editOperationRules(SysScenicSpotOperationRules scenicSpotOperationRules) throws ParseException;

    List<SysScenicSpotOperationRules> getOperationRulesList(Map<String,String> search);
}
