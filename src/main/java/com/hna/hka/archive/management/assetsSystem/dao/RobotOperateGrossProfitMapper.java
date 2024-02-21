package com.hna.hka.archive.management.assetsSystem.dao;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/3/17 18:06
 * 机器人毛利率
 */
public interface RobotOperateGrossProfitMapper {

    List<Map> getSpotGrossProfitMarginList(Long type, Long spotId);

}
