package com.hna.hka.archive.management.assetsSystem.service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/3/3 13:22
 */
public interface RobotOperateSaturationService {


    List<HashMap> getOperateSaturationList(Long spotId, Integer pageNum, Integer pageSize);

    List<String> getOperateSaturationCensus(List<String> list ,Long spotId);

    int getOperateSaturationListCount(Long spotId);

    HashMap getOperateSaturationSettlement(Long spotId);

}
